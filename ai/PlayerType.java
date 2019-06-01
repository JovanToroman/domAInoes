package ai;

public enum PlayerType {
    AI(0),
    RANDOM(1),
    HUMAN(2);

    private final Integer id;

    PlayerType(Integer id) {
        this.id = id;
    }
}
