package ai;

import evaluation.StaticEvaluation;
import human.Human;

public class PlayerFactory {

    public Player makePlayer(PlayerType type, String name, StaticEvaluation eval, Integer searchDepth) {
        if (type.equals(PlayerType.AI)) {
            return new AI(name, eval, searchDepth);
        }
        if (type.equals(PlayerType.RANDOM)) {
            return new Random(name);
        }
        if (type.equals(PlayerType.HUMAN)) {
            return  new Human(name);
        }
        return null;
    }
}
