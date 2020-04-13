@file:Suppress("UndocumentedPublicClass", "UndocumentedPublicFunction")

package io.battlesnake.examples.kotlin

import io.battlesnake.core.AbstractBattleSnake
import io.battlesnake.core.DOWN
import io.battlesnake.core.GameStrategy
import io.battlesnake.core.LEFT
import io.battlesnake.core.MoveRequest
import io.battlesnake.core.MoveResponse
import io.battlesnake.core.RIGHT
import io.battlesnake.core.SnakeContext
import io.battlesnake.core.StartRequest
import io.battlesnake.core.StartResponse
import io.battlesnake.core.UP
import io.battlesnake.core.isOdd
import io.battlesnake.core.strategy

object StripedSnake : AbstractBattleSnake<StripedSnake.MySnakeContext>() {

  class MySnakeContext : SnakeContext() {
    lateinit var gotoOriginMoves: Iterator<MoveResponse>
    lateinit var stripedMoves: Iterator<MoveResponse>
    var goneToOrigin = false
  }

  override fun snakeContext(): MySnakeContext = MySnakeContext()

  override fun gameStrategy(): GameStrategy<MySnakeContext> =
    strategy(verbose = true) {

      onStart { context: MySnakeContext, request: StartRequest ->
        val you = request.you
        val board = request.board
        context.gotoOriginMoves = originPath(you.headPosition.x, you.headPosition.y).iterator()
        context.stripedMoves = stripePath(board.width, board.height).iterator()
        StartResponse("#ff00ff", "beluga", "bolt")
      }

      onMove { context: MySnakeContext, request: MoveRequest ->
        if (request.isAtOrigin)
          context.goneToOrigin = true

        (if (!context.goneToOrigin) context.gotoOriginMoves else context.stripedMoves).next()
      }
    }

  private fun originPath(x: Int, y: Int) =
    sequence {
      repeat(x) { yield(LEFT) }
      repeat(y) { yield(UP) }
    }

  private fun stripePath(width: Int, height: Int) =
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

        if (height.isOdd) {
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
    }

  @JvmStatic
  fun main(args: Array<String>) {
    run()
  }
}