package gameobjects;

public class Room extends EncounterCount {

    private int n, s, e, w;

    public Room(String aName, String aDescription, int aN, int aS, int aE, int aW, EncounterList tl) {
        super(aName, aDescription, tl); // inherits from Thing.java
        n = aN;
        s = aS;
        e = aE;
        w = aW;
    }

    // --- accessor methods ---
    // n
    public int getN() {
        return n;
    }

    public void setN(int aN) {
        n = aN;
    }

    // s
    public int getS() {
        return s;
    }

    public void setS(int aS) {
        s = aS;
    }

    // e
    public int getE() {
        return e;
    }

    public void setE(int aE) {
        e = aE;
    }

    // w
    public int getW() {
        return w;
    }

    void setW(int aW) {
        w = aW;
    }

    public String describe() {
        return String.format("%s. %s", //initial output: "You are in the Troll Room. A dank room that smells of troll. Only one %s. outputs "You are in the Troll Room." Omits description. No %s. outputs "You are in the" only
                getName(), getDescription())
                + "\nYou notice... \n" + getEncounterList().describeThings(); //initial output: "Things here: \n carrot: It is a very crunchy carrot"
    }

}
