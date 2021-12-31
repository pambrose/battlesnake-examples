/*
 * Copyright © 2021 Paul Ambrose (pambrose@mac.com)
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
import io.battlesnake.core.DescribeResponse
import io.battlesnake.core.GameStrategy
import io.battlesnake.core.LEFT
import io.battlesnake.core.MoveRequest
import io.battlesnake.core.MoveResponse
import io.battlesnake.core.Position
import io.battlesnake.core.RIGHT
import io.battlesnake.core.SnakeContext
import io.battlesnake.core.UP
import io.battlesnake.core.strategy
import io.ktor.server.application.*

object CenterSquareSnake : AbstractBattleSnake<CenterSquareSnake.MySnakeContext>() {

  override fun gameStrategy(): GameStrategy<MySnakeContext> =
    strategy(verbose = true) {

      onDescribe { call: ApplicationCall ->
        DescribeResponse(color = "#ff00ff", head = "beluga", tail = "bolt")
      }

      onMove { context: MySnakeContext, request: MoveRequest ->
        fun moveTo(request: MoveRequest, position: Position): MoveResponse =
          when {
            request.headPosition.x > position.x -> LEFT
            request.headPosition.x < position.x -> RIGHT
            request.headPosition.y > position.y -> DOWN
            else -> UP
          }

        if (request.isAtCenter)
          context.visitedCenter = true

        if (!context.visitedCenter)
          moveTo(request, request.boardCenter)
        else
          context.squareMoves.next()
      }
    }

  override fun snakeContext(): MySnakeContext = MySnakeContext()

  class MySnakeContext : SnakeContext() {
    var visitedCenter = false

    val squareMoves: Iterator<MoveResponse> =
      sequence {
        while (true)
          for (move in listOf(RIGHT, DOWN, LEFT, UP))
            repeat(4) { yield(move) }
      }.iterator()
  }

  @JvmStatic
  fun main(args: Array<String>) {
    run()
  }
}