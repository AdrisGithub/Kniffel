package de.nuernberger.kniffel;

public enum KniffelConstant {
    BONUS(35),
    FULLHOUSE(25),
    SMALLSTRAIGHT(30),
    BIGSTRAIGHT(40),
    KNIFFEL(50),
    CROSSEDOUT(0),
    KNIFFELBONUS(25);
    private final int value;
    KniffelConstant(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
