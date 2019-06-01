import ai.*;
import evaluation.StaticEvaluation;
import game.Game;

public class Main {

    public static void main(String[] args) {
        PlayerFactory factory = new PlayerFactory();
        Player aiWithStaticEval = factory.makePlayer(PlayerType.AI, "AI1", StaticEvaluation.SE1,  9);
        Player aiSearchOnly = factory.makePlayer(PlayerType.AI, "AI2", StaticEvaluation.SE2,  9);
        Player random = factory.makePlayer(PlayerType.RANDOM, "Random", null, null);

        int wins1 = 0;
        int wins2 = 0;
        int draws = 0;
        for (int i = 0; i < 500; i++) {
            System.out.println("\nIteration "+i);
            Game g = Game.create(aiWithStaticEval, random);
            int result = g.commence();
            if (result == 1) {
                wins1++;
            } else if (result == 2) {
                wins2++;
            } else {
                draws++;
            }
            System.out.printf("wins1 %d; wins2 %d; draws %d\n", wins1, wins2, draws);
        }

        System.out.printf("wins1 %d; wins2 %d; draws %d", wins1, wins2, draws);
    }
}
