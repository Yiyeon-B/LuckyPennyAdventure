package com.yibrown.game.effects;

public abstract class Effect {

    //Abstract class = can't have version of class. Can't make instance of abstract class. (Cannot instantiate abstract classes.) Useful if there are parent classes that shouldn't be made into objects themselves.
    //Effect will take place because an encounter has been resolved
    //Encounter unlocking effect and description changing effect

    //Interface is not a parent, it's a promise that the class has certain methods you can call. Has some functionality of a parent class, but not all. It's a solution to ***diamond dependency*** (when you want a class to derive from two siblings of a parent class). Interfaces can also be for capturing similar behavior in unrelated classes. [Flying for both birds and planes]
    //Example of Interface class:

    // public interface AnimalStuff {
    //    int age = 24;
    //    public void poop();
    //}

    //public class Cat implements AnimalStuff {
    //    @Override
    //    public void poop(){
    //      System.out.println("plooplooop");
    //  }
    //}
    //Interface does not need abstract keyword because it is assumed
    //Difference between abstract and interface:
    // - you can implement as many interfaces as you want, but extend only one (abstract) class.
    // - every field declared inside an interface is static and final (final - fields must be instantiated with a value (e.g. int age; must be int age = 24;)/static - every field's value must be inherited by classes that implement the interface(e.g. all classes that implement AnimalStuff must have int age = 24;))
    // -

    public abstract void applyEffect(boolean doesHelp); //abstract method. Since each child class of Effect will use applyEffect differently, you don't specify a body for the method here. Get rid of the brackets.

}
