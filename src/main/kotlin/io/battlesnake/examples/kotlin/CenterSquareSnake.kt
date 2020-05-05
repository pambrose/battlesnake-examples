/*
 * Copyright © 2020 Paul Ambrose (pambrose@mac.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

  override fun snakeContext(): MySnakeContext = MySnakeContext()

  class MySnakeContext : SnakeContext() {
    var goneToCenter = false

    val squareMoves =
      sequence {
        while (true)
          for (move in listOf(RIGHT, DOWN, LEFT, UP))
            repeat(4) { yield(move) }
      }.iterator()
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