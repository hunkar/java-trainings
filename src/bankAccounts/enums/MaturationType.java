package bankAccounts.enums;

public enum MaturationType {
    MONTH(0),
    SIX_MONTHS(1),
    YEAR(2);

    private final int numVal;

    MaturationType(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }

}
