package gameobjects;

public class DescriptionChangeEffect extends Effect {

    private final Room changedRoom; //Room is changed after Encounter. (name is the same, but description changes and Encounter is no longer available). Final means the value changedRoom cannot be changed
    private final String changedDescription; //description in Room is set to something different.


    public DescriptionChangeEffect(Room changedRoom, String changedDescription) {
        this.changedRoom = changedRoom;
        this.changedDescription = changedDescription;
    }

    public void applyEffect() {
        changedRoom.setDescription(changedDescription); //changedRoom and changedDescription are ***members*** of DescriptionChangeEffect
        // changedDescription = "3"; doesn't work because changedDescription is final
    }

}

//DescriptionChangeEffect newEffect = new DescriptionChangeEffect(kitchen, "the kitchen is dirty");
//DescriptionChangeEffect newEffect2 = new DescriptionChangeEffect(kitchen, "the kitchen is clean");
//newEffect2.applyEffect();