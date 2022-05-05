package gameobjects;

import java.util.HashSet;
import java.util.Set;

public class Encounter {

    private String description;
    private String resolutionYes;
    private String resolutionNo;
    private Set<Effect> effectSet; //Set of Effects because we might want many effects from one Encounter. Therefore, an encounter needs to own many effects. effect will change description, unlock final encounter



    public Encounter(String description, String resolutionYes, String resolutionNo) {
        this.description = description;
        this.resolutionYes = resolutionYes;
        this.resolutionNo = resolutionNo;
        this.effectSet = new HashSet<Effect>(4); //encounters generally have two effects; double that just to be safe
    }

    public String resolveEncounter(boolean doesHelp) {
        for (Effect effect : effectSet) { // for every effect in effectSet, call the code in the body
            effect.applyEffect(doesHelp);
        }
        if(doesHelp) {
            return resolutionYes;
        } else {
            return resolutionNo;
        }
    }

    public String getDescription() {
        return description;
    }

    public void addEffect(Effect newEffect) { //adds an Effect to effectSet
        effectSet.add(newEffect);
    }

}