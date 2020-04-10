@file:Suppress("UndocumentedPublicClass", "UndocumentedPublicFunction")

package io.battlesnake.examples.kotlin

import io.battlesnake.core.AbstractBattleSnake
import io.battlesnake.core.AbstractSnakeContext
import io.battlesnake.core.DOWN
import io.battlesnake.core.LEFT
import io.battlesnake.core.MoveRequest
import io.battlesnake.core.MoveResponse
import io.battlesnake.core.RIGHT
import io.battlesnake.core.StartRequest
import io.battlesnake.core.StartResponse
import io.battlesnake.core.Strategy
import io.battlesnake.core.UP
import io.battlesnake.core.strategy

object PerimeterSnake : AbstractBattleSnake<PerimeterSnake.SnakeContext>() {

  class SnakeContext(gameId: String, snakeId: String) : AbstractSnakeContext(gameId, snakeId) {
    lateinit var gotoOriginMoves: Iterator<MoveResponse>
    lateinit var perimeterMoves: Iterator<MoveResponse>
    var goneToOrigin = false
  }

  override fun snakeContext(gameId: String, snakeId: String): SnakeContext = SnakeContext(gameId, snakeId)

  override fun gameStrategy(): Strategy<SnakeContext> =
    strategy(verbose = true) {

      onStart { context: SnakeContext, request: StartRequest ->
        val you = request.you
        val board = request.board
        context.gotoOriginMoves = originPath(you.headPosition.x, you.headPosition.y).iterator()
        context.perimeterMoves = perimeterPath(board.width, board.height).iterator()
        StartResponse("#ff00ff", "beluga", "bolt")
      }

      onMove { context: SnakeContext, request: MoveRequest ->
        if (request.isAtOrigin)
          context.goneToOrigin = true

        (if (context.goneToOrigin) context.perimeterMoves else context.gotoOriginMoves).next()
      }
    }

  private fun originPath(x: Int, y: Int) =
    sequence {
      repeat(x) { yield(LEFT) }
      repeat(y) { yield(UP) }
    }

  private fun perimeterPath(width: Int, height: Int) =
    sequence {
      while (true) {
        repeat(width - 1) { yield(RIGHT) }
        repeat(height - 1) { yield(DOWN) }
        repeat(width - 1) { yield(LEFT) }
        repeat(height - 1) { yield(UP) }
      }
    }

  @JvmStatic
  fun main(args: Array<String>) {
    run()
  }
}