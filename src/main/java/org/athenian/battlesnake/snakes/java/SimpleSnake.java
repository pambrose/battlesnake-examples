package org.athenian.battlesnake.snakes.java;

import io.battlesnake.core.*;

import static io.battlesnake.core.JavaConstants.*;
import static org.athenian.battlesnake.snakes.java.SimpleSnake.GameContext;

public class SimpleSnake extends BattleSnake<GameContext> {

    public static void main(String[] args) {
        SimpleSnake snake = new SimpleSnake();
        snake.run(8080);
    }

    @Override
    public GameContext gameContext() {
        return new GameContext();
    }

    @Override
    public Strategy<GameContext> gameStrategy() {
        return new Strategy<GameContext>(true) {
            @Override
            public StartResponse onStart(GameContext context, StartRequest request) {
                return new StartResponse("#ff00ff", "beluga", "bolt");
            }

            @Override
            public MoveResponse onMove(GameContext context, MoveRequest request) {
                if (request.isFoodAvailable())
                    return moveTo(request, request.getNearestFoodPosition());
                else
                    return moveTo(request, request.getBoardCenter());
            }
        };
    }

    MoveResponse moveTo(MoveRequest request, Position position) {
        if (request.getHeadPosition().getX() > position.getX())
            return LEFT;
        else if (request.getHeadPosition().getX() < position.getX())
            return RIGHT;
        else if (request.getHeadPosition().getY() > position.getY())
            return UP;
        else
            return DOWN;
    }

    static class GameContext extends AbstractGameContext {
    }
}
