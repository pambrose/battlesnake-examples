package io.battlesnake.examples.java;

import io.battlesnake.core.AbstractBattleSnake;
import io.battlesnake.core.AbstractGameStrategy;
import io.battlesnake.core.MoveRequest;
import io.battlesnake.core.MoveResponse;
import io.battlesnake.core.Position;
import io.battlesnake.core.SnakeContext;
import io.battlesnake.core.StartRequest;
import io.battlesnake.core.StartResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import static io.battlesnake.core.JavaConstants.DOWN;
import static io.battlesnake.core.JavaConstants.LEFT;
import static io.battlesnake.core.JavaConstants.RIGHT;
import static io.battlesnake.core.JavaConstants.UP;

public class PerimeterSnake extends AbstractBattleSnake<PerimeterSnake.MySnakeContext> {

  @Override
  public MySnakeContext snakeContext() {
    return new MySnakeContext();
  }

  @Override
  public MyGameStrategy gameStrategy() {
    return new MyGameStrategy(true);
  }

  static class MySnakeContext extends SnakeContext {
    private final List<MoveResponse> path = new LinkedList<>();

    private MySnakeContext addToPath(int count, MoveResponse response) {
      IntStream.range(0, count).forEach(i -> path.add(response));
      return this;
    }
  }

  static class MyGameStrategy extends AbstractGameStrategy<MySnakeContext> {
    public MyGameStrategy(boolean verbose) {
      super(verbose);
    }

    @Override
    public StartResponse onStart(MySnakeContext context, StartRequest request) {
      // Add moves that get the snake to origin
      Position pos = request.getYou().getHeadPosition();
      context.addToPath(pos.getX(), LEFT)
          .addToPath(pos.getY(), UP);

      return new StartResponse("#ff00ff", "beluga", "bolt");
    }

    @Override
    public MoveResponse onMove(MySnakeContext context, MoveRequest request) {
      // If the snake is at the origin, add the moves for a lap around the perimeter
      if (request.isAtOrigin()) {
        int width = request.getBoard().getWidth();
        int height = request.getBoard().getHeight();
        context.addToPath(width - 1, RIGHT)
            .addToPath(height - 1, DOWN)
            .addToPath(width - 1, LEFT)
            .addToPath(height - 1, UP);
      }

      // Remove a move from the head of the list
      return context.path.remove(0);
    }
  }

  public static void main(String[] args) {
    new PerimeterSnake().run(8080);
  }
}
