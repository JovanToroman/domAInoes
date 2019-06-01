package game;

import ai.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {
    private Player first;
    private Player second;
    private Integer turn;
    private Table current;

    public Game() {
    }

    public static Game create(Player first, Player second) {
        Game ret = new Game();
        ret.first = first;
        ret.second = second;
        ret.first.setId(1);
        ret.second.setId(2);
        return ret;
    }

    public Integer commence(){
        List<Domino> dominoes = new ArrayList<>();

        generateDominoes(dominoes);

        List<Domino> playerOneDominos = new ArrayList<>();
        List<Domino> playerTwoDominos = new ArrayList<>();

        java.util.Random rand = new Random();

        while (!dominoes.isEmpty()){
            Domino domino1 = dominoes.get(rand.nextInt(dominoes.size()));
            playerOneDominos.add(domino1);
            dominoes.remove(domino1);
            Domino domino2 = dominoes.get(rand.nextInt(dominoes.size()));
            playerTwoDominos.add(domino2);
            dominoes.remove(domino2);
        }

        Domino startDomino = new Domino(6, 6);

        if (playerOneDominos.contains(startDomino)) {
            playerOneDominos.remove(startDomino);
            turn = 2;
            System.out.printf("%s has [%d|%d] domino and starts the game\n", first.getName(), startDomino.getLeft(), startDomino.getRight());
        } else {
            playerTwoDominos.remove(startDomino);
            turn = 1;
            System.out.printf("%s has [%d|%d] domino and starts the game\n", second.getName(), startDomino.getLeft(), startDomino.getRight());
        }

        current = Table.create(playerOneDominos, playerTwoDominos, startDomino.getLeft(), startDomino.getRight());

        while (!current.gameOver()) {
            if (turn.equals(1)) {
                if (current.canMove(1)) {
                    System.out.printf("It is %s 's turn\n", first.getName());
                    current = first.makeMove(current);

                } else {
                    System.out.printf("%s cannot play any move and therefore skips this turn\n", first.getName());
                }
                if (current.getDominoes(1).isEmpty()) {
                    break;
                }
                turn = 2;
            } else {
                if (current.canMove(2)) {
                    System.out.printf("It is %s 's turn\n", second.getName());
                    current = second.makeMove(current);

                } else {
                    System.out.printf("%s cannot play any move and therefore skips this turn\n", second.getName());
                }
                if (current.getDominoes(2).isEmpty()) {
                    break;
                }
                turn = 1;
            }
        }

        Integer[] result = current.determineResult();
        if (result[0].equals(1)) {
            if (result[1].equals(1)) {
                System.out.printf("Player %s wins by putting all dominoes down\n", first.getName());
            } else {
                System.out.printf("Game is stuck. Player %s wins by having less points in their hand\n", first.getName());
            }
            return 1;
        }
        if (result[0].equals(2)) {
            if (result[1].equals(1)) {
                System.out.printf("Player %s wins by putting all dominoes down\n", second.getName());
            } else {
                System.out.printf("Game is stuck. Player %s wins by having less points in their hand\n", second.getName());
            }
            return 2;
        }
        System.out.println("Draw");
        return 0;
    }

    private void generateDominoes(List<Domino> dominoes) {
        for (int l = 0; l < 7; l++) {
            for (int r = l; r < 7; r++) {
                dominoes.add(new Domino(l, r));
            }
        }
        Collections.shuffle(dominoes);
    }
}
