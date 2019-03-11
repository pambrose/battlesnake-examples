package io.battlesnake.examples.kotlin

import io.battlesnake.core.*
import io.battlesnake.examples.kotlin.PerimeterSnake.GameContext


object PerimeterSnake : AbstractBattleSnake<GameContext>() {

    class GameContext : AbstractGameContext() {
        lateinit var gotoOriginMoves: Iterator<MoveResponse>
        lateinit var perimeterMoves: Iterator<MoveResponse>
        var goneToOrigin = false
    }

    fun originPath(x: Int, y: Int) =
        sequence {
            repeat(x) { yield(LEFT) }
            repeat(y) { yield(UP) }
        }.iterator()

    fun perimeterPath(width: Int, height: Int) =
        sequence {
            while (true) {
                repeat(width - 1) { yield(RIGHT) }
                repeat(height - 1) { yield(DOWN) }
                repeat(width - 1) { yield(LEFT) }
                repeat(height - 1) { yield(UP) }
            }
        }.iterator()

    override fun gameContext(): GameContext = GameContext()

    override fun gameStrategy(): Strategy<GameContext> =
        strategy(true) {

            onStart { context: GameContext, request: StartRequest ->
                val you = request.you
                val board = request.board
                context.gotoOriginMoves = originPath(you.headPosition.x, you.headPosition.y)
                context.perimeterMoves = perimeterPath(board.width, board.height)
                StartResponse("#ff00ff", "beluga", "bolt")
            }

            onMove { context: GameContext, request: MoveRequest ->
                if (request.isAtOrigin)
                    context.goneToOrigin = true

                (if (context.goneToOrigin) context.perimeterMoves else context.gotoOriginMoves).next()
            }
        }

    @JvmStatic
    fun main(args: Array<String>) {
        run()
    }
}