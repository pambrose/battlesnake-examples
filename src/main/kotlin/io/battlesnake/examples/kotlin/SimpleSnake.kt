@file:Suppress("UndocumentedPublicClass", "UndocumentedPublicFunction")

package io.battlesnake.examples.kotlin

import io.battlesnake.core.AbstractBattleSnake
import io.battlesnake.core.DOWN
import io.battlesnake.core.Food
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

object SimpleSnake : AbstractBattleSnake<SimpleSnake.MySnakeContext>() {

  override fun gameStrategy(): GameStrategy<MySnakeContext> =
    strategy(verbose = true) {

      onStart { context: MySnakeContext, request: StartRequest ->
        StartResponse("#ff00ff", "beluga", "bolt")
      }

      onMove { context: MySnakeContext, request: MoveRequest ->
        if (request.isFoodAvailable)
          moveTo(request, nearestFood(request.headPosition, request.foodList).position)
        else
          moveTo(request, request.boardCenter)
      }
    }

  override fun snakeContext(): MySnakeContext = MySnakeContext()

  class MySnakeContext : SnakeContext() {
    // Add whatever is necessary here
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