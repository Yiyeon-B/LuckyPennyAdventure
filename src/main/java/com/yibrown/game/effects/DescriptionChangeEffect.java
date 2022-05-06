package com.yibrown.game.effects;

import com.yibrown.game.core.Room;

public class DescriptionChangeEffect extends Effect {

    private final Room changedRoom; //Room is changed after Encounter. (name is the same, but description changes and Encounter is no longer available). Final means the value changedRoom cannot be changed
    private final String changedDescriptionYes; //description in Room is set to something different. You do this to change a component of Room instead of deleting and reinstating a new Room with a different definition

    private final String changedDescriptionNo;

    public DescriptionChangeEffect(Room changedRoom, String changedDescriptionYes, String changedDescriptionNo) {
        this.changedRoom = changedRoom;
        this.changedDescriptionYes = changedDescriptionYes;
        this.changedDescriptionNo = changedDescriptionNo;
    }



    public void applyEffect(boolean doesHelp) {
        if(doesHelp) {
            changedRoom.setDescription(changedDescriptionYes); //changedRoom and changedDescriptionYes are ***members*** of DescriptionChangeEffect
            // changedDescription = "3"; doesn't work because changedDescription is final
        } else {
            changedRoom.setDescription(changedDescriptionNo); //changedRoom and changedDescriptionNo are ***members*** of DescriptionChangeEffect
        }
    }
}

//DescriptionChangeEffect newEffect = new DescriptionChangeEffect(kitchen, "the kitchen is dirty");
//DescriptionChangeEffect newEffect2 = new DescriptionChangeEffect(kitchen, "the kitchen is clean");
//newEffect2.applyEffect();