package gameobjects;

public class EncounterCount extends Thing {

    private EncounterList encounterList = new EncounterList( );

    public EncounterCount(String aName, String aDescription, EncounterList el) {
        super(aName, aDescription);
        encounterList = el;
    }

    /**
     * @return the things
     */
    public EncounterList getEncounterList() {
        return encounterList;
    }

    /**
     * @param encounterList - the things to set
     */
    public void setEncounterList(EncounterList encounterList) {
        this.encounterList = encounterList;
    }

}
