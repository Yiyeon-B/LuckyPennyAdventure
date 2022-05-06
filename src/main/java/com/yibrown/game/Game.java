package com.yibrown.game;

import java.util.*;

import com.yibrown.game.effects.DescriptionChangeEffect;
import com.yibrown.game.effects.EndGameEffect;
import com.yibrown.game.effects.UnlockEncounterEffect;
import com.yibrown.game.core.Encounter;
import com.yibrown.game.core.Room;

public class Game {

    private static Set<String> EXIT_COMMANDS = new HashSet<>(Arrays.asList("Q", "QUIT"));
    private static Map<String, Boolean> ENCOUNTER_COMMANDS = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Y", Boolean.TRUE),
            new AbstractMap.SimpleEntry<>("YES", Boolean.TRUE),
            new AbstractMap.SimpleEntry<>("N", Boolean.FALSE),
            new AbstractMap.SimpleEntry<>("NO", Boolean.FALSE)
    );

    private Map<Room, List<Room>> map; // Created a Map with key value pair of Room to ArrayList of Room(s) named map. ArrayList<Room> shows a list of rooms you can enter from the specified Room
    private Room currentRoom;
    private Encounter currentEncounter;
    private String nextInfo; // would be more efficient as a StringBuilder
    private boolean gameOver;

    public Game() {

//        Encounter subwayEncounter = new Encounter();
//
//        Encounter subwayEntranceEncounter = new Encounter();
//
//        Encounter bookstoreEncounter = new Encounter();
//        bookstoreEncounter.add(new Encounter("ENCOUNTER", "AN ENCOUNTER", 1));
//
//        Encounter coffeeShopEncounter = new Encounter();
//        coffeeShopEncounter.add(new Encounter("ENCOUNTER", "AN ENCOUNTER", 1));
//
//        Encounter crosswalkEncounter = new Encounter();
//
//        Encounter parkEncounter = new Encounter();
//        parkEncounter.add(new Encounter("ENCOUNTER", "AN ENCOUNTER", 1));
//
//        Encounter swingSetEncounter = new Encounter();
//        swingSetEncounter.add(new Encounter("ENCOUNTER", "AN ENCOUNTER", 1));
//
//        Encounter lakeEncounter = new Encounter();
//        lakeEncounter.add(new Encounter("ENCOUNTER", "AN ENCOUNTER", 1));
//
//        Encounter playerlist = new Encounter(); //populated by encounter count
//
//        // Add Rooms to the map
//        //                 Room( name,   description,                             N,       S,      E,      W )
//        map.add(new Room("SUBWAY", "\nA magical place where humans hop on an underground train \nand get whisked away to a destination of their heart's desire!"));
//        map.add(new Room("SUBWAY ENTRANCE", "\nRight outside the entrance to my home and before the whole, wide world! \nYou can go anywhere from here!"));
//        map.add(new Room("BOOKSTORE", "\nA store brimming with stories and ideas to peruse!"));
//        map.add(new Room("COFFEE SHOP", "\nHumans frequent this location to imbibe curious liquids..."));
//        map.add(new Room("CROSS WALK", "\nYou're crossing the street!"));
//        map.add(new Room("PARK", "\nA lovely recreational area to enjoy fresh air, blue sky, and dogs!"));
//        map.add(new Room("SWING SET", "\nHumans employ these to mimic flight while remaining safely seated."));
//        map.add(new Room("LAKE", "\nThe water has a calming effect on coins and humans alike."));

        // Define rooms
        Room hallway = new Room("hallway", "a hallway in the Damascus house");
        Room bathroom = new Room("bathroom", "a bathroom in the Damascus house");

        // Define encounters
        Encounter tapEncounter = new Encounter(
                "You see the tap is running. Do you turn it off?",
                "The tap is now off.",
                "The tap remains on.");
        Encounter finalEncounter = new Encounter(
                "Are you proud of yourself?",
                "Monkey purrs.",
                ";(");

        // Define encounter effects
        DescriptionChangeEffect changeBathroom = new DescriptionChangeEffect(
                bathroom,
                "The bathroom in the Damascus house, but less moist.",
                "The bathroom in the Damascus house is ultra moist");

        UnlockEncounterEffect unlockFinalEncounter = new UnlockEncounterEffect(hallway, finalEncounter);
        unlockFinalEncounter.addDependentEncounter(tapEncounter); // only unlock finalEncounter once tapEncounter is done

        EndGameEffect gameOverEffect = new EndGameEffect(this);

        // Setup effects for encounters
        tapEncounter.addEffect(changeBathroom);
        tapEncounter.addEffect(unlockFinalEncounter);
        finalEncounter.addEffect(gameOverEffect);

        // Setup encounters for rooms
        bathroom.setEncounter(tapEncounter);

        // Setup map
        List<Room> hallwayConnections = new ArrayList<Room>(Arrays.asList(bathroom));
        List<Room> bathroomConnections = new ArrayList<Room>(Arrays.asList(hallway));

        map = new HashMap<>();
        map.put(hallway, hallwayConnections);
        map.put(bathroom, bathroomConnections);

        // Setup starting game state
        currentRoom = hallway;
        currentEncounter = null;
        nextInfo = "";
        gameOver = false;
    }

//    public Room kitchen = new Room("kitchen", "There is a raccoon in the kitchen.", new Encounter("Let the raccoon out?", true, "You open a window and the raccoon skedaddles.", "Your window of opportunity has closed. The raccoon lives here now.", "The kitchen is squeaky clean. (Will be a class later)"));
//    public DescriptionChangeEffect kitchenCleaned = new DescriptionChangeEffect(kitchen, "The kitchen is squeaky clean.");
//
//    public Encounter bathroom = new Encounter("There is a tiny chair in the toilet.", true, "You put the tiny chair on the floor for it to dry off.", "You flush the tiny chair.", "The chair is now out of the toilet.");
//    public UnlockEncounterEffect chairSave = new UnlockEncounterEffect(new Encounter("A tiny man three inches tall stands on the bath mat and thanks you for saving his chair. He holds out an itty bitty hand for you to take. Will you adventure with him?", true, "You take his hand and he whisks you away.", "You flush him down the toilet.", "You have reached the end of this game."));

//    private void updateOutput(int roomNumber) {
//        // if roomNumber = NOEXIT, display a special message, otherwise
//        // display text (e.g. name and description of room)
//        String s;
//        if (roomNumber == Direction.NOEXIT) {
//            s = "No Exit!";
//        } else {
//            Room r = getPlayer().getLocation();
//            s = "You arrive at "
//                    + r.getName() + ". " + r.getDescription();
//        }
//        System.out.println(s);
//    }

//    private String processVerb(List<String> wordlist) {
//        String verb;
//        String msg = "";
//        verb = wordlist.get(0);
//        if (!commands.contains(verb)) {
//            msg = verb + " is not a known verb! ";
//        } else {
//            switch (verb) {
//                case "SUBWAY":
//                    //go to SUBWAY;
//                    //description
//                    //if all encounters completed, final unlocked
//                    break;
//                case "SUBWAY ENTRANCE":
//                    //go to SUBWAY ENTRANCE;
//                    //description
//                    //no encounter
//                    break;
//                case "BOOKSTORE":
//                    //go to BOOKSTORE;
//                    //description
//                    break;
//                case "COFFEE SHOP":
//                    //go to COFFEE SHOP;
//                    //description
//                    break;
//                case "CROSSWALK":
//                    //go to CROSSWALK;
//                    //description
//                    break;
//                case "PARK":
//                    //go to PARK;
//                    //description
//                    break;
//                case "SWING SET":
//                    //go to SWING SET;
//                    //description
//                    break;
//                case "LAKE":
//                    //go to LAKE;
//                    //description
//                    break;
//                default:
//                    msg = verb + " (not yet implemented)";
//                    break;
//            }
//        }
//        return msg;
//    }

//    public String parseCommand(List<String> wordlist) { //creating String method parseCommand with List<String> wordlist as a parameter
//        String msg; // Declaring String msg
//        if (wordlist.size() == 1) { // if player enters one word
//            msg = processVerb(wordlist); // try to process as verb
//        } //else if (wordlist.size() == 2) { //if player enters two words
////            msg = processVerbNoun(wordlist); // try to process as verb then noun
//        //}
//        else {
//            msg = "Sorry, didn't catch that..."; //else say only two commands allowed
//        }
//        return msg;
//    }

//    public static List<String> wordList(String input) { //make a list that takes in string objects called wordlist that has a parameter of String input
//        String delims = "[ \t,.:;?!\"']+"; //defining delimiter characters, used to split tokens (aka words)
//        List<String> strlist = new ArrayList<>(); //create an ArrayList object named strlist which is is List<String>
//        String[] words = input.split(delims); //takes in the input, splits the tokens between delims characters, and puts the output in an array of Strings called words
//
//        for (String word : words) { //for every word (initialized here) in words String array (initialized in line 192)
//            strlist.add(word); //add word into strlist (initialized on line 191)
//        }
//        return strlist; //Once for loop is completed, return strlist (initialized on line 191)
//    }

    private void appendInfo(String output) {
        nextInfo += String.format("%s\n", output);
    }

    private void appendRoomInfo() {
        appendInfo(String.format("You are in the %s", currentRoom.getName()));
        appendInfo(currentRoom.getDescription());
    }

    private void appendEncounterInfo() {
        appendInfo(currentEncounter.getDescription());
    }

    private void appendMovementInfo() {
        appendInfo("Where do you want to go?");
        appendInfo(map.get(currentRoom).toString());
    }

    private void appendCurrentState() {
        appendRoomInfo();

        if (currentEncounter != null) {
            appendEncounterInfo();
        } else {
            appendMovementInfo();
        }
    }

    private void moveRoom(Room room) {
        currentRoom = room;
        currentEncounter = currentRoom.giveEncounter();
        appendCurrentState();
    }

    public String getIntro() {
//        return """
//                "Ah, another bright, shiny day under a bench at the subway...\\n"+
//                "You wake up next to your two closest friends, Gum Wad and Pocket Lint.\\n" +
//                "They smile back at you.\\n" +
//                "\\n" +
//                "Where do you want to go?\\n" +
//                "[Enter ]\\n" + //Set of Commands
//                "[...or q to quit]";
//                """;
        return """
                You have come into existence.
                """;
    }

    public void start() {
        moveRoom(currentRoom);
    }

    public String info() {
        String result = nextInfo;
        nextInfo = "";
        return result;
    }

    public void runCommand(String input) {

        if (EXIT_COMMANDS.contains(input.toUpperCase(Locale.ROOT))) {
            appendInfo("Off you go, then!");
            endGame();
        } else if (currentEncounter != null) {
            String commandInput = input.toUpperCase(Locale.ROOT);

            if (ENCOUNTER_COMMANDS.containsKey(commandInput)) {
                String result = currentEncounter.resolveEncounter(ENCOUNTER_COMMANDS.get(commandInput));
                currentEncounter = null;
                appendInfo(result);

                if (!isGameOver()) {
                    appendCurrentState();
                }
            } else {
                appendInfo("Invalid command!");
            }
        } else {
            for (Room option : map.get(currentRoom)) {
                if (input.equals(option.getName())) {
                    moveRoom(option);
                    return;
                }
            }

            appendInfo("Invalid command!");
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void endGame() {
        gameOver = true;
    }
}
