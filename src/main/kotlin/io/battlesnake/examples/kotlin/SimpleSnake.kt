@file:Suppress("UndocumentedPublicClass", "UndocumentedPublicFunction")

package io.battlesnake.examples.kotlin

import io.battlesnake.core.*

object SimpleSnake : AbstractBattleSnake<SimpleSnake.GameContext>() {

  class GameContext : AbstractGameContext()

  override fun gameContext(): GameContext = GameContext()

  override fun gameStrategy(): Strategy<GameContext> =
    strategy(true) {

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

  private fun moveTo(request: MoveRequest, position: Position) =
    when {
      request.headPosition.x > position.x -> LEFT
      request.headPosition.x < position.x -> RIGHT
      request.headPosition.y > position.y -> UP
      else -> DOWN
    }

  private fun nearestFood(head: Position, foodList: List<Food>) =
    foodList.maxBy { head - it.position }!!

  @JvmStatic
  fun main(args: Array<String>) {
    run()
  }
}