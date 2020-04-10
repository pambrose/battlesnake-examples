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
import org.jetbrains.annotations.NotNull;

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

  @NotNull
  @Override
  public SnakeContext snakeContext(String gameId, String snakeId) {
    return new SnakeContext(gameId, snakeId);
  }

  @NotNull
  @Override
  public Strategy<SnakeContext> gameStrategy() {
    return new AbstractStrategy<SnakeContext>(true) {

      @NotNull
      @Override
      public StartResponse onStart(@NotNull SnakeContext context, @NotNull StartRequest request) {
        return new StartResponse("#ff00ff", "beluga", "bolt");
      }

      @NotNull
      @Override
      public MoveResponse onMove(@NotNull SnakeContext context, @NotNull MoveRequest request) {
        if (request.isFoodAvailable())
          return moveTo(request, nearestFood(request.getHeadPosition(), request.getFoodList()));
        else
          return moveTo(request, request.getBoardCenter());
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

  private Position nearestFood(Position headPosition, List<Food> foodList) {
    return foodList.stream()
        .min(Comparator.comparingInt(food -> headPosition.minus(food.getPosition())))
        .get()
        .getPosition();
  }

  static class SnakeContext extends AbstractSnakeContext {
    public SnakeContext(@NotNull String gameId, @NotNull String snakeId) {
      super(gameId, snakeId);
    }
  }
}
