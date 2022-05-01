package gameobjects;

public class Thing {

    private String name;
    private String description;

    public Thing(String aName, String aDescription) {
        // constructor
        name = aName;
        description = aDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String aName) {
        name = aName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String aDescription) {
        description = aDescription;
    }

}
