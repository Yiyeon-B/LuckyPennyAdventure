package gameobjects;

public class Room {

    private String name;
    private String description;
    private Encounter encounter;


    public Room(String name, String description, Encounter encounter) {
        this.name = name;
        this.description = description;
        this.encounter = encounter;
    }


    public void setDescription(String changedDescription) {
        description = changedDescription;
    }

    public void setEncounter(Encounter changedEncounter) {
        encounter = changedEncounter;
    }
}
