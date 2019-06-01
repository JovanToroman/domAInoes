package ai;

import game.Table;

public abstract class Player {

    protected Integer id;
    protected String name;

    public Player(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOpponentId(Integer id) {
        return 2 / id;
    }

    public abstract Table makeMove(Table table);

    public String getName() {
        return name;
    }
}
