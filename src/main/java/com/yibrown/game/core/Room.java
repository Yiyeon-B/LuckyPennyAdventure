package com.yibrown.game.core;

public class Room {

    private String name;
    private String description;
    private Encounter encounter;


    public Room(String name, String description, Encounter encounter) {
        this.name = name;
        this.description = description;
        this.encounter = encounter;
    }

    public Room(String name, String description) {
        this(name, description, null);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String changedDescription) {
        description = changedDescription;
    }

    public Encounter giveEncounter() {
        Encounter result = encounter;
        encounter = null;
        return result;
    }

    public void setEncounter(Encounter changedEncounter) {
        if(encounter == null){
            encounter = changedEncounter; //ideally, error should be thrown if encounter is not null. Alas, time constraints
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}
