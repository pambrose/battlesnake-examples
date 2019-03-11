package io.battlesnake.examples.java;

import io.battlesnake.core.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import static io.battlesnake.core.JavaConstants.*;
import static io.battlesnake.examples.java.PerimeterSnake.GameContext;

public class PerimeterSnake extends AbstractBattleSnake<GameContext> {

    public static void main(String[] args) {
        new PerimeterSnake().run(8080);
    }

    @Override
    public GameContext gameContext() {
        return new GameContext();
    }

    @Override
    public Strategy<GameContext> gameStrategy() {
        return new AbstractStrategy<GameContext>(true) {
            @Override
            public StartResponse onStart(GameContext context, StartRequest request) {
                // Add moves that get the snake to origin
                Position pos = request.getYou().getHeadPosition();
                context.addToPath(pos.getX(), LEFT)
                        .addToPath(pos.getY(), UP);

                return new StartResponse("#ff00ff", "beluga", "bolt");
            }

            @Override
            public MoveResponse onMove(GameContext context, MoveRequest request) {
                // If the snake is at the origin, add the moves for a lap around the board
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
        };
    }

    static class GameContext extends AbstractGameContext {
        List<MoveResponse> path = new LinkedList<>();

        GameContext addToPath(int count, MoveResponse reponse) {
            IntStream.range(0, count).forEach(i -> path.add(reponse));
            return this;
        }
    }
}
