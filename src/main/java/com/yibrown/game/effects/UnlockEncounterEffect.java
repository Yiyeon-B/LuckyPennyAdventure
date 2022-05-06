package com.yibrown.game.effects;

import com.yibrown.game.core.Encounter;
import com.yibrown.game.core.Room;

public class UnlockEncounterEffect extends Effect { //encounter is owned by a room. This class gives rooms encounters. Need to know which room to give an encounter, whether the room has an encounter, and an encounter to give

    private final Room changedRoom;
    public final Encounter changedEncounter; //Encounter at SUBWAY is available after other encounters have been completed

    public UnlockEncounterEffect(Room changedRoom, Encounter changedEncounter) {
        this.changedRoom = changedRoom;
        this.changedEncounter = changedEncounter;
    }



    public void applyEffect(boolean doesHelp) {
        changedRoom.setEncounter(changedEncounter);
    }

}
