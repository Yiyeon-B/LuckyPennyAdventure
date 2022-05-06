package com.yibrown.game.effects;

import java.util.HashSet;
import java.util.Set;

import com.yibrown.game.core.Encounter;
import com.yibrown.game.core.Room;

public class UnlockEncounterEffect extends Effect { //encounter is owned by a room. This class gives rooms encounters. Need to know which room to give an encounter, whether the room has an encounter, and an encounter to give

    private final Room changedRoom;
    private final Encounter changedEncounter; //Encounter at SUBWAY is available after other encounters have been completed
    public Set<Encounter> dependentEncounters;

    public UnlockEncounterEffect(Room changedRoom, Encounter changedEncounter) {
        this.changedRoom = changedRoom;
        this.changedEncounter = changedEncounter;
        this.dependentEncounters = new HashSet<>();
    }

    public void applyEffect(boolean doesHelp) {

        boolean areAllComplete = true;

        for (Encounter dependentEncounter : dependentEncounters) {
            areAllComplete = areAllComplete && dependentEncounter.isComplete();
        }

        if (areAllComplete) {
            changedRoom.setEncounter(changedEncounter);
        }
    }

    public void addDependentEncounter(Encounter encounter) {
        dependentEncounters.add(encounter);
    }

}
