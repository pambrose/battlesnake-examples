package org.athenian.battlesnake.snakes.kotlin

import io.battlesnake.core.*
import org.athenian.battlesnake.snakes.kotlin.CenterSquareSnake.GameContext


object CenterSquareSnake : BattleSnake<GameContext>() {

    class GameContext : AbstractGameContext() {
        var goneToCenter = false

        val squareMoves =
            sequence {
                while (true)
                    for (move in listOf(RIGHT, DOWN, LEFT, UP))
                        repeat(4) { yield(move) }
            }.iterator()
    }

    override fun gameContext() = GameContext()

    override fun gameStrategy() =
        strategy<GameContext>(true) {

            onStart { context: GameContext, request: StartRequest ->
                StartResponse("#ff00ff", "beluga", "bolt")
            }

            onMove { context: GameContext, request: MoveRequest ->
                if (request.isAtCenter)
                    context.goneToCenter = true

                if (!context.goneToCenter)
                    moveTo(request, request.boardUpperLeft)
                else
                    context.squareMoves.next()
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