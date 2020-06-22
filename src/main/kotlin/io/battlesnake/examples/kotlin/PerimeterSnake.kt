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
import io.battlesnake.core.DescribeResponse
import io.battlesnake.core.GameStrategy
import io.battlesnake.core.LEFT
import io.battlesnake.core.MoveRequest
import io.battlesnake.core.MoveResponse
import io.battlesnake.core.RIGHT
import io.battlesnake.core.SnakeContext
import io.battlesnake.core.StartRequest
import io.battlesnake.core.UP
import io.battlesnake.core.strategy
import io.ktor.application.ApplicationCall

object PerimeterSnake : AbstractBattleSnake<PerimeterSnake.MySnakeContext>() {

  override fun gameStrategy(): GameStrategy<MySnakeContext> =
    strategy(verbose = true) {

      onDescribe { call: ApplicationCall ->
        DescribeResponse("me", "#ff00ff", "beluga", "bolt")
      }

      onStart { context: MySnakeContext, request: StartRequest ->
        val you = request.you
        val board = request.board
        context.gotoOriginMoves = originPath(you.headPosition.x, you.headPosition.y).iterator()
        context.perimeterMoves = perimeterPath(board.width, board.height).iterator()
        logger.info { "Goto origin moves: ${you.headPosition.x},${you.headPosition.y} game id: ${request.gameId}" }
        logger.info { "Perimeter moves: ${board.width}x${board.height} game id: ${request.gameId}" }
      }

      onMove { context: MySnakeContext, request: MoveRequest ->
        if (request.isAtOrigin)
          context.goneToOrigin = true

        (if (context.goneToOrigin) context.perimeterMoves else context.gotoOriginMoves).next()
      }
    }

  class MySnakeContext : SnakeContext() {
    lateinit var gotoOriginMoves: Iterator<MoveResponse>
    lateinit var perimeterMoves: Iterator<MoveResponse>
    var goneToOrigin = false
  }

  override fun snakeContext(): MySnakeContext = MySnakeContext()

  private fun originPath(x: Int, y: Int) =
    sequence {
      repeat(y) { yield(DOWN) }
      repeat(x) { yield(LEFT) }
    }

  private fun perimeterPath(width: Int, height: Int) =
    sequence {
      while (true) {
        repeat(height - 1) { yield(UP) }
        repeat(width - 1) { yield(RIGHT) }
        repeat(height - 1) { yield(DOWN) }
        repeat(width - 1) { yield(LEFT) }
      }
    }

  @JvmStatic
  fun main(args: Array<String>) {
    run()
  }
}