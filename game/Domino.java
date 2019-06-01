package game;

import java.util.Arrays;
import java.util.List;

public class Domino {

    private Integer left;
    private Integer right;

    public Domino(Integer left, Integer right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null ) {
            return false;
        }
        if (!(o instanceof Domino)) {
            return false;
        }
        Domino other = (Domino) o;
        return other.getLeft().equals(left) && other.getRight().equals(right);
    }

    @Override
    public String toString() {
        return "[ " + left + " | " + right + " ]";
    }

    public Integer getLeft() {
        return left;
    }

    public Integer getRight() {
        return right;
    }

    public List<Integer> getValues() {
        return Arrays.asList(left, right);
    }
}
