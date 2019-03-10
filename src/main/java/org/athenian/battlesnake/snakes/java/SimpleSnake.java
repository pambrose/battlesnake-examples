package org.athenian.battlesnake.snakes.java;

import io.battlesnake.core.*;

import java.util.Comparator;
import java.util.List;

import static io.battlesnake.core.JavaConstants.*;
import static org.athenian.battlesnake.snakes.java.SimpleSnake.GameContext;

public class SimpleSnake extends AbstractBattleSnake<GameContext> {

    public static void main(String[] args) {
        new SimpleSnake().run(8080);
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
                return new StartResponse("#ff00ff", "beluga", "bolt");
            }

            @Override
            public MoveResponse onMove(GameContext context, MoveRequest request) {
                if (request.isFoodAvailable())
                    return moveTo(request, nearestFood(request.getHeadPosition(), request.getFoodList()));
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

    Position nearestFood(Position headPosition, List<Food> foodList) {
        return foodList.stream()
                .min(Comparator.comparingInt(food -> headPosition.minus(food.getPosition())))
                .get()
                .getPosition();
    }


    static class GameContext extends AbstractGameContext {
    }
}
