package org.athenian.battlesnake.snakes.kotlin

import io.battlesnake.core.*
import org.athenian.battlesnake.snakes.kotlin.SimpleSnake.GameContext

object SimpleSnake : AbstractBattleSnake<GameContext>() {

    class GameContext : AbstractGameContext()

    override fun gameContext() = GameContext()

    override fun gameStrategy() =
        strategy<GameContext>(true) {
            onStart { context: GameContext, request: StartRequest ->
                StartResponse("#ff00ff", "beluga", "bolt")
            }

            onMove { context: GameContext, request: MoveRequest ->
                if (request.isFoodAvailable)
                    moveTo(request, nearestFood(request.headPosition, request.foodList).position)
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

    fun nearestFood(head: Position, foodList: List<Food>) =
        foodList.maxBy { head - it.position }!!

    @JvmStatic
    fun main(args: Array<String>) {
        run()
    }
}