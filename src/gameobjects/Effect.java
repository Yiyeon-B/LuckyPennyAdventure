package gameobjects;

public abstract class Effect {

    //Abstract class = can't have version of class. Can't make instance of abstract class.
    //Effect will take place because an encounter has been resolved
    //Encounter unlocking effect and description changing effect
    //applyEffect() is going to be a ***polymorphic*** function: applyEffect() changes based on the child class of Effect. Child classes are guaranteed to at least have the functionality of their parent class.
    //Interface is not a parent, it's a promise that the class has certain methods you can call. Has some functionality of a parent class, but not all. It's a solution to ***diamond dependency*** (when you want a class to derive from two siblings of a parent class). Interfaces can also be for capturing similar behavior in unrelated classes. [Flying for both birds and planes]

    public abstract void applyEffect();

}
