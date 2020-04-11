package io.battlesnake.examples.java;

import io.battlesnake.core.AbstractBattleSnake;
import io.battlesnake.core.AbstractSnakeContext;
import io.battlesnake.core.AbstractStrategy;
import io.battlesnake.core.Food;
import io.battlesnake.core.MoveRequest;
import io.battlesnake.core.MoveResponse;
import io.battlesnake.core.Position;
import io.battlesnake.core.StartRequest;
import io.battlesnake.core.StartResponse;
import io.battlesnake.core.Strategy;

import java.util.Comparator;
import java.util.List;

import static io.battlesnake.core.JavaConstants.DOWN;
import static io.battlesnake.core.JavaConstants.LEFT;
import static io.battlesnake.core.JavaConstants.RIGHT;
import static io.battlesnake.core.JavaConstants.UP;

public class SimpleSnake extends AbstractBattleSnake<SimpleSnake.SnakeContext> {

  public static void main(String[] args) {
    new SimpleSnake().run(8080);
  }

  @Override
  public SnakeContext snakeContext() {
    return new SnakeContext();
  }

  @Override
  public Strategy<SnakeContext> gameStrategy() {
    return new AbstractStrategy<SnakeContext>(true) {

      @Override
      public StartResponse onStart(SnakeContext context, StartRequest request) {
        return new StartResponse("#ff00ff", "beluga", "bolt");
      }

      @Override
      public MoveResponse onMove(SnakeContext context, MoveRequest request) {
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
    };
  }

  private MoveResponse moveTo(MoveRequest request, Position position) {
    if (request.getHeadPosition().getX() > position.getX())
      return LEFT;
    else if (request.getHeadPosition().getX() < position.getX())
      return RIGHT;
    else if (request.getHeadPosition().getY() > position.getY())
      return UP;
    else
      return DOWN;
  }

  static class SnakeContext extends AbstractSnakeContext {
  }
}
