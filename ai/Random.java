package ai;

import game.Table;

import java.util.List;

public class Random extends Player {

    public Random(String name) {
        super(name);
    }

    @Override
    public Table makeMove(Table table) {
        List<Table> possibleMoves = table.possibleBoards(id);
        java.util.Random random = new java.util.Random();
        return possibleMoves.get(random.nextInt(possibleMoves.size()));
    }
}
