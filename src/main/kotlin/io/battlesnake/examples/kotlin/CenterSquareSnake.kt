@file:Suppress("UndocumentedPublicClass", "UndocumentedPublicFunction")

package io.battlesnake.examples.kotlin

import io.battlesnake.core.*

object CenterSquareSnake : AbstractBattleSnake<CenterSquareSnake.GameContext>() {

  class GameContext : AbstractGameContext() {
    var goneToCenter = false

    val squareMoves =
      sequence {
        while (true)
          for (move in listOf(RIGHT, DOWN, LEFT, UP))
            repeat(4) { yield(move) }
      }.iterator()
  }

  override fun gameContext(): GameContext = GameContext()

  override fun gameStrategy(): Strategy<GameContext> =
    strategy(true) {

      onStart { context: GameContext, request: StartRequest ->
        StartResponse("#ff00ff", "beluga", "bolt")
      }

      onMove { context: GameContext, request: MoveRequest ->
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