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

package io.battlesnake.examples.java;

import io.battlesnake.core.AbstractBattleSnake;
import io.battlesnake.core.AbstractGameStrategy;
import io.battlesnake.core.DescribeResponse;
import io.battlesnake.core.MoveRequest;
import io.battlesnake.core.MoveResponse;
import io.battlesnake.core.Position;
import io.battlesnake.core.SnakeContext;
import io.battlesnake.core.StartRequest;
import io.ktor.server.application.ApplicationCall;

import java.util.LinkedList;
import java.util.List;

import static io.battlesnake.core.JavaConstants.DOWN;
import static io.battlesnake.core.JavaConstants.LEFT;
import static io.battlesnake.core.JavaConstants.RIGHT;
import static io.battlesnake.core.JavaConstants.UP;

public class PerimeterSnake extends AbstractBattleSnake<PerimeterSnake.MySnakeContext> {

  public static void main(String[] args) {
    new PerimeterSnake().run(8080);
  }

  static class MySnakeContext extends SnakeContext {
    private final List<MoveResponse> path = new LinkedList<>();

    private MySnakeContext addToPath(int count, MoveResponse response) {
      for (int i = 0; i < count; i++) {
        path.add(response);
      }
      return this;
    }
  }

  static class MyGameStrategy extends AbstractGameStrategy<MySnakeContext> {
    public MyGameStrategy(boolean verbose) {
      super(verbose);
    }

    @Override
    public DescribeResponse onDescribe(ApplicationCall call) {
      return new DescribeResponse("me", "#aaddff", "beluga", "bolt");
    }

    @Override
    public void onStart(MySnakeContext context, StartRequest request) {
      // Add moves that get the snake to origin
      Position pos = request.getYou().getHeadPosition();
      context.addToPath(pos.getY(), DOWN)
          .addToPath(pos.getX(), LEFT);
    }

    @Override
    public MoveResponse onMove(MySnakeContext context, MoveRequest request) {
      // If the snake is at the origin, add the moves for a lap around the perimeter
      if (request.isAtOrigin()) {
        int width = request.getBoard().getWidth();
        int height = request.getBoard().getHeight();
        context.addToPath(height - 1, UP)
            .addToPath(width - 1, RIGHT)
            .addToPath(height - 1, DOWN)
            .addToPath(width - 1, LEFT);
      }

      // Remove a move from the head of the list
      return context.path.remove(0);
    }
  }

  @Override
  public MySnakeContext snakeContext() {
    return new MySnakeContext();
  }

  @Override
  public MyGameStrategy gameStrategy() {
    return new MyGameStrategy(true);
  }
}
