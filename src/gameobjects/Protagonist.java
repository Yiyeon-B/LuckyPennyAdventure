package gameobjects;

public class Protagonist extends EncounterCount {

    private Room location; // Room where Protagonist is at present

    public Protagonist(String aName, String aDescription, Room aRoom, EncounterList tl) {
        super(aName, aDescription, tl);
        location = aRoom;
    }

    public void setLocation(Room aRoom) {
        location = aRoom;
    }

    public Room getLocation() {
        return location;
    }

}
