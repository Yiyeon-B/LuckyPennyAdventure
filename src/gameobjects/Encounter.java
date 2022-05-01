package gameobjects;

public class Encounter extends Thing {

    private int value;

    public Encounter(String aName, String aDescription, int aValue) {
        super(aName, aDescription);
        this.value = aValue;
    }

    public int getValue() {
        return value;
    }


}
