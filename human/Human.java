package human;

import ai.Player;
import game.Table;
import game.Domino;
import util.ValidationException;

import java.util.List;
import java.util.Scanner;

public class Human extends Player {

    private Scanner input;

    public Human(String name) {
        super(name);
        input = new Scanner(System.in);
    }

    @Override
    public Table makeMove(Table table){
        do {
            try {
                System.out.println("Left: " + table.getLeft() + " Rigth: " + table.getRight());
                List<Domino> dominos = table.getDominoes(id);
                for (int i = 0; i < dominos.size(); i++)
                    System.out.print(i + ": " + dominos.get(i) + "  ");

                System.out.println("\nSelect a piece: ");
                int n = Integer.parseInt(input.nextLine());
                if (n < 0 || n > (dominos.size()-1))
                    throw new ValidationException();

                System.out.println("Select position: ");
                char pos = input.nextLine().charAt(0);

                if (pos != 'l' && pos != 'r') {
                    throw new ValidationException();
                }
                boolean side = pos == 'l';

                return table.setPiece(dominos.get(n), side);
            } catch (NumberFormatException | ValidationException e) {
                System.out.println(e);
            }
        } while (true);
    }
}
