package ai;

import evaluation.StaticEvaluation;
import game.Table;

public class AI extends Player {
    private StaticEvaluation staticEvaluation;
    private Integer maxDepthAlphaBeta;
    private Evaluation eval;

    public AI(String name, StaticEvaluation staticEvaluation, Integer maxlevelab) {
        super(name);
        this.staticEvaluation = staticEvaluation;
        this.maxDepthAlphaBeta = maxlevelab;
        eval = new Evaluation();
    }

    @Override
    public Table makeMove(Table table) {
        System.out.printf("Possible choices: %d and %d" , table.getLeft(), table.getRight());
        System.out.println(table.getPlayersDominoesInfo(id));

        System.out.println("\nHold on");

        Table newTable = miniMaxWithAlphaBeta(table, 0, id, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY).table;

        return newTable;
    }

    private Evaluation miniMaxWithAlphaBeta(Table table, Integer level, Integer player, Float alpha, Float beta) {

        if (table.gameOver()) {
            if (table.determineResult()[0] == id || table.getDominoes(id).isEmpty()) {
                return staticEvaluation == StaticEvaluation.SE1
                        ? eval.create(evaluate(table), table) : eval.create(Float.POSITIVE_INFINITY, table);
            } else if (table.determineResult()[0] == 0) {
                return eval.create(0.0f, table);
            } else {
                return staticEvaluation == StaticEvaluation.SE1
                        ? eval.create(evaluate(table), table) : eval.create(Float.NEGATIVE_INFINITY, table);
            }
        } else if (level == maxDepthAlphaBeta) {
            return eval.create(evaluate(table), table);
        } else {
            Table bestTable = null;
            float alphaLocal = alpha;
            float betaLocal = beta;


            for (Table b : table.possibleBoards(player)) {
                Evaluation evaluationLocal;

                if (player.equals(id) && alphaLocal < beta) {
                    evaluationLocal = miniMaxWithAlphaBeta(b, level + 1, getOpponentId(id), alphaLocal, beta);
                }
                else if (betaLocal > alpha) {
                    evaluationLocal = miniMaxWithAlphaBeta(b, level + 1, getOpponentId(id), alpha, betaLocal);
                }
                else {
                    continue;
                }

                if (player.equals(id) && evaluationLocal.result >= alphaLocal) {
                    alphaLocal = evaluationLocal.result;
                    bestTable = b;
                } else if (evaluationLocal.result <= betaLocal) {
                    betaLocal = evaluationLocal.result;
                    bestTable = b;
                }
            }

            if (player.equals(id))
                return eval.create(alphaLocal, bestTable);
            else
                return eval.create(betaLocal, bestTable);
        }

    }


    private Float evaluate(Table table) {
        float h = 0.0f;
        if (staticEvaluation.equals(StaticEvaluation.SE1)) {
            int diff = table.getOtherPlayerDominoes(id).size() - table.getDominoes(id).size();
            h += diff;

            int mine = table.getDominoes(id).stream().map(p -> p.getLeft() + p.getRight()).mapToInt(i -> i).sum();
            int other = table.getOtherPlayerDominoes(id).stream().map(p -> p.getLeft() + p.getRight()).mapToInt(i -> i).sum();
            int diff2 = other - mine;
            h += diff2;

            long numberOfDoubles = table.getDominoes(id).stream().filter(p -> p.getRight() == p.getLeft()).count();
            h -= numberOfDoubles;
            if (table.canMove(id) && !table.canMove(getOpponentId(id))) {
                h += 10;
            } else if (!table.canMove(id) && table.canMove(getOpponentId(id))) {
                h -= 10;
            }
        } else if (staticEvaluation.equals(StaticEvaluation.SE2)) {
            h = 0;
        }
        return h;
    }

    private class Evaluation {
        Float result;
        Table table;

        private Evaluation() {}

        private Evaluation create(Float value, Table table) {
            Evaluation eval = new Evaluation();
            eval.result = value;
            eval.table = table;
            return eval;
        }
    }
}
