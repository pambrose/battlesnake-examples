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
import io.battlesnake.core.Food;
import io.battlesnake.core.MoveRequest;
import io.battlesnake.core.MoveResponse;
import io.battlesnake.core.Position;
import io.battlesnake.core.SnakeContext;
import io.ktor.application.ApplicationCall;

import java.util.Comparator;
import java.util.List;

import static io.battlesnake.core.JavaConstants.DOWN;
import static io.battlesnake.core.JavaConstants.LEFT;
import static io.battlesnake.core.JavaConstants.RIGHT;
import static io.battlesnake.core.JavaConstants.UP;

public class SimpleSnake extends AbstractBattleSnake<SimpleSnake.MySnakeContext> {

  public static void main(String[] args) {
    new SimpleSnake().run(8080);
  }

  static class MySnakeContext extends SnakeContext {
  }

  static class MyGameStrategy extends AbstractGameStrategy<MySnakeContext> {
    public MyGameStrategy(boolean verbose) {
      super(verbose);
    }

    @Override
    public DescribeResponse onDescribe(ApplicationCall call) {
      return new DescribeResponse("me", "#ff00ff", "beluga", "bolt");
    }

    @Override
    public MoveResponse onMove(MySnakeContext context, MoveRequest request) {
      List<Food> foodList = request.getFoodList();
      Position headPosition = request.getHeadPosition();
      Position boardCenter = request.getBoardCenter();
      // Go to the center if the foodList is empty
      Position newPosition =
          foodList.stream()
              .min(Comparator.comparingInt(food -> headPosition.minus(food.getPosition())))
              .map(Food::getPosition)
              .orElse(boardCenter);
      return moveTo(request, newPosition);
    }

    private MoveResponse moveTo(MoveRequest request, Position position) {
      if (request.getHeadPosition().getX() > position.getX())
        return LEFT;
      else if (request.getHeadPosition().getX() < position.getX())
        return RIGHT;
      else if (request.getHeadPosition().getY() > position.getY())
        return DOWN;
      else
        return UP;
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
