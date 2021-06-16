package calc.model;

public enum Operation {
    ADDITION ("+"),
    SUBTRACTION ("-"),
    MULTIPLICATION ("×"),
    DIVISION ("÷"),
    SQUAREROOT ("√"),
    EXPONENTIATION ("²");

    private String oper;

    Operation(String oper) {
        this.oper = oper;
    }

    public String getOper() {
        return oper;
    }
}
