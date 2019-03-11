package io.battlesnake.examples.kotlin

import io.battlesnake.core.*
import io.battlesnake.examples.kotlin.StripedSnake.GameContext

object StripedSnake : AbstractBattleSnake<GameContext>() {

    class GameContext : AbstractGameContext() {
        lateinit var gotoOriginMoves: Iterator<MoveResponse>
        lateinit var stripedMoves: Iterator<MoveResponse>
        var goneToOrigin = false
    }

    fun originPath(x: Int, y: Int) =
        sequence {
            repeat(x) { yield(LEFT) }
            repeat(y) { yield(UP) }
        }.iterator()

    fun stripePath(width: Int, height: Int) =
        sequence {
            while (true) {
                repeat((height / 2) - 1) {
                    repeat(width - 1) { yield(RIGHT) }
                    yield(DOWN)
                    repeat(height - 1) { yield(LEFT) }
                    yield(DOWN)
                }

                repeat(width - 1) { yield(RIGHT) }
                yield(DOWN)
                repeat(height - 1) { yield(LEFT) }

                if (height.isOdd()) {
                    // Finish odd bottom line
                    yield(DOWN)
                    repeat(width - 1) { yield(RIGHT) }
                    // Get back to origin with S pattern
                    repeat((height - 1) / 2) { yield(UP) }
                    repeat(width - 1) { yield(LEFT) }
                    repeat((height - 1) / 2) { yield(UP) }
                } else {
                    // Go straight back to origin
                    repeat(height - 1) { yield(UP) }
                }
            }
        }.iterator()

    override fun gameContext(): GameContext = GameContext()

    override fun gameStrategy(): Strategy<GameContext> =
        strategy(true) {

            onStart { context: GameContext, request: StartRequest ->
                val you = request.you
                val board = request.board
                context.gotoOriginMoves = originPath(you.headPosition.x, you.headPosition.y)
                context.stripedMoves = stripePath(board.width, board.height)
                StartResponse("#ff00ff", "beluga", "bolt")
            }

            onMove { context: GameContext, request: MoveRequest ->
                if (request.isAtOrigin)
                    context.goneToOrigin = true

                (if (!context.goneToOrigin) context.gotoOriginMoves else context.stripedMoves).next()
            }
        }

    @JvmStatic
    fun main(args: Array<String>) {
        run()
    }
}