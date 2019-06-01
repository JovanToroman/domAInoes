package game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Table {
    private int left;
    private int right;

    private List<Domino> playerOneDominoes;
    private List<Domino> playerTwoDominoes;

    private Table() {
    }

    public static Table create(List<Domino> playerOneDominoes, List<Domino> playerTwoDominoes, int left, int right) {
        Table ret = new Table();
        ret.playerOneDominoes = playerOneDominoes;
        ret.playerTwoDominoes = playerTwoDominoes;
        ret.left = left;
        ret.right = right;
        return ret;
    }

    public List<Table> possibleBoards (Integer id) {
        List<Table> tables = new ArrayList<>();

        for (Domino p : getDominoes(id)) {
            Table bl = setPiece(p, true);
            if (bl != null) {
                tables.add(bl);
            }
            Table br = setPiece(p, false);
            if (br != null) {
                tables.add(br);
            }
        }
        return tables;
    }

    public Table setPiece(Domino domino, Boolean side) {
        if (!validPiece(domino, side)) {
            return null;
        }

        int left = this.left;
        int rigth = this.right;

        if (side) {
            if (domino.getRight().equals(this.left)) {
                left = domino.getLeft();
            } else if (domino.getLeft().equals(this.left)) {
                left = domino.getRight();
            }
        } else {
            if (domino.getRight().equals(this.right)) {
                left = domino.getLeft();
            } else if (domino.getLeft().equals(this.right)) {
                left = domino.getRight();
            }
        }

        List<Domino> dominoesOne = new ArrayList<>(playerOneDominoes);
        List<Domino> dominoesTwo = new ArrayList<>(playerTwoDominoes);

        if (dominoesOne.contains(domino)) {
            dominoesOne.remove(domino);
        } else {
            dominoesTwo.remove(domino);
        }

        return Table.create(dominoesOne, dominoesTwo, left, rigth);
    }

    public boolean gameOver() {
        return (!canMove(1)) && (!canMove(2));
    }

    public Integer[] determineResult() {
        if (playerOneDominoes.isEmpty() && !playerTwoDominoes.isEmpty()) {
            return new Integer[]{1,1};
        } else if (playerTwoDominoes.isEmpty() && !playerOneDominoes.isEmpty()) {
            return new Integer[]{2,1};
        }

        int playerOnePoints = playerOneDominoes.stream().flatMapToInt(p -> p.getValues().stream().mapToInt(i -> i)).sum();
        int playerTwoPoints = playerTwoDominoes.stream().flatMapToInt(p -> p.getValues().stream().mapToInt(i -> i)).sum();

        return playerOnePoints < playerTwoPoints ? new Integer[]{1,2} : playerOnePoints > playerTwoPoints ? new Integer[]{2,1} : new Integer[]{0,0};
    }

    public String getPlayersDominoesInfo(Integer id) {
        List<Domino> l = id.equals(1) ? playerOneDominoes : playerTwoDominoes;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < l.size(); i++) {
            sb.append(i + ": " + l.get(i) + "  ");
        }
        return sb.toString();
    }

    public boolean canMove(Integer id) {
        List<Integer> values = getDominoes(id).stream().flatMapToInt(
                p -> p.getValues().stream().mapToInt(i -> i)).distinct().boxed().collect(Collectors.toList());
        return values.contains(left) || values.contains(right);
    }

    private Boolean validPiece(Domino domino, Boolean side) {
        return side ? domino.getValues().contains(left) : domino.getValues().contains(right);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null ) {
            return false;
        }
        if (!(o instanceof Table)) {
            return false;
        }
        Table other = (Table) o;
        for (Domino d : other.getDominoes(1)) {
            if (!getDominoes(1).contains(d)) {
                return false;
            }
        }
        for (Domino d : other.getDominoes(2)) {
            if (!getDominoes(2).contains(d)) {
                return false;
            }
        }
        return true;
    }

    public List<Domino> getDominoes(Integer player) {
        return player.equals(1) ? playerOneDominoes : playerTwoDominoes;
    }

    public List<Domino> getOtherPlayerDominoes(Integer player) {
        return player.equals(2) ? playerOneDominoes : playerTwoDominoes;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }
}
