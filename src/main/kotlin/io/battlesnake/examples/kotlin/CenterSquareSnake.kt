@file:Suppress("UndocumentedPublicClass", "UndocumentedPublicFunction")

package io.battlesnake.examples.kotlin

import io.battlesnake.core.AbstractBattleSnake
import io.battlesnake.core.DOWN
import io.battlesnake.core.GameStrategy
import io.battlesnake.core.LEFT
import io.battlesnake.core.MoveRequest
import io.battlesnake.core.Position
import io.battlesnake.core.RIGHT
import io.battlesnake.core.SnakeContext
import io.battlesnake.core.StartRequest
import io.battlesnake.core.StartResponse
import io.battlesnake.core.UP
import io.battlesnake.core.strategy

object CenterSquareSnake : AbstractBattleSnake<CenterSquareSnake.MySnakeContext>() {

  class MySnakeContext : SnakeContext() {
    var goneToCenter = false

    val squareMoves =
      sequence {
        while (true)
          for (move in listOf(RIGHT, DOWN, LEFT, UP))
            repeat(4) { yield(move) }
      }.iterator()
  }

  override fun snakeContext(): MySnakeContext = MySnakeContext()

  override fun gameStrategy(): GameStrategy<MySnakeContext> =
    strategy(verbose = true) {

      onStart { context: MySnakeContext, request: StartRequest ->
        StartResponse("#ff00ff", "beluga", "bolt")
      }

      onMove { context: MySnakeContext, request: MoveRequest ->
        if (request.isAtCenter)
          context.goneToCenter = true

        if (!context.goneToCenter)
          moveTo(request, request.boardCenter)
        else
          context.squareMoves.next()
      }
    }

  private fun moveTo(request: MoveRequest, position: Position) =
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