package gameobjects;

public class Encounter {

    private String description;
    private String resolutionYes;
    private String resolutionNo;
    private Effect effect; //effect will change description, unlock final encounter

    public Encounter(String description, String resolutionYes, String resolutionNo, Effect effect) {
        this.description = description;
        this.resolutionYes = resolutionYes;
        this.resolutionNo = resolutionNo;
        this.effect = effect;
    }

    public Encounter(String description, String resolutionYes, String resolutionNo) { //if the constructor is called with three arguments, use this constructor. It will call the four argument constructor but assign the effect value to null
        this(description, resolutionYes, resolutionNo, null);
    }

    public String resolveEncounter(boolean doesHelp) {
        if(doesHelp) {
            return resolutionYes;
        } else {
            return resolutionNo;
        }
    }

    public String getDescription() {
        return description;
    }

}