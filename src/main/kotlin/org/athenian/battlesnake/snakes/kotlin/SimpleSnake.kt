package org.athenian.battlesnake.snakes.kotlin

import io.battlesnake.core.*
import org.athenian.battlesnake.snakes.kotlin.SimpleSnake.GameContext

object SimpleSnake : BattleSnake<GameContext>() {

    class GameContext : AbstractGameContext()

    override fun gameContext() = GameContext()

    override fun gameStrategy() =
        strategy<GameContext>(true) {
            onStart { context: GameContext, request: StartRequest ->
                StartResponse("#ff00ff", "beluga", "bolt")
            }

            onMove { context: GameContext, request: MoveRequest ->
                if (request.isFoodAvailable)
                    moveTo(request, request.nearestFoodPosition)
                else
                    moveTo(request, request.boardCenter)
            }
        }

    fun moveTo(request: MoveRequest, position: Position) =
        when {
            request.headPosition.x > position.x -> LEFT
            request.headPosition.x < position.x -> RIGHT
            request.headPosition.y > position.y -> UP
            else -> DOWN
        }

    @JvmStatic
    fun main(args: Array<String>) {
        run()
    }
}