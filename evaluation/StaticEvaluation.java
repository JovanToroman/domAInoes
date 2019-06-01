package evaluation;

public enum StaticEvaluation {

    SE1("SE1"), SE2("SE2"), SE3("SE3");

    private final String code;

    StaticEvaluation(String code) {
        this.code = code;
    }

    public String toString() {
        return this.code;
    }
}
