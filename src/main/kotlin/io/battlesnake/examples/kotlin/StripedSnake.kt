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
import io.battlesnake.core.MoveResponse
import io.battlesnake.core.RIGHT
import io.battlesnake.core.SnakeContext
import io.battlesnake.core.StartRequest
import io.battlesnake.core.StartResponse
import io.battlesnake.core.UP
import io.battlesnake.core.isOdd
import io.battlesnake.core.strategy

object StripedSnake : AbstractBattleSnake<StripedSnake.MySnakeContext>() {

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

  override fun snakeContext(): MySnakeContext = MySnakeContext()

  class MySnakeContext : SnakeContext() {
    lateinit var gotoOriginMoves: Iterator<MoveResponse>
    lateinit var stripedMoves: Iterator<MoveResponse>
    var goneToOrigin = false
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