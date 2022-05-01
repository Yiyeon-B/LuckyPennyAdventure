package gameobjects;

import java.util.ArrayList;

public class EncounterList extends ArrayList<Thing> {

    //ThingsList is a generic ArrayList, holds collection of Thing objects/descendants of Thing

    public String describeThings() { //declare method describeThings()
        String s = ""; //declare an empty String
        if (this.size() == 0) {
            s = "nothing.\n";
        } else {
            for (Thing t : this) { //for every item in this, item is assigned to t. Singles out every item in the ArrayList
                s = s + t.getName() + ": " + t.getDescription() + "\n";
            }
        }
        return s;
    }

    public Thing thisOb(String aName) {
        Thing athing = null;
        String thingName = "";
        String aNameLowCase = aName.trim().toLowerCase();
        for (Thing t : this) {
            thingName = t.getName().trim().toLowerCase();
            if (thingName.equals(aNameLowCase)) {
                athing = t;
            }
        }
        return athing;
    }

}

