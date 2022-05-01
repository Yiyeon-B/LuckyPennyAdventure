package game;

import java.util.*;     // required for ArrayList

import gameobjects.*;
import globals.Direction;

public class Game {

    private ArrayList <Room>map; // declared the map - an ArrayList of Rooms. You can modify ArrayLists (e.g. add/subtract items, set new values), but not Arrays. <Room> object is the data type of the ArrayList. map is the name of the ArrayList
    private Protagonist player;  // the player - provides 'first person perspective'

    List<String> commands = new ArrayList<>(Arrays.asList( // ** List<String> command initialized
            "y", "n",
            "n", "s", "w", "e"));
    List<String> objects = new ArrayList<>(Arrays.asList("ENCOUNTER"));

    public Game() {
        map = new ArrayList<Room>(); //initialized map // TODO: Make map a Generic list of Room

        EncounterList subwayEncounter = new EncounterList();

        EncounterList subwayEntranceEncounter = new EncounterList();

        EncounterList bookstoreEncounter = new EncounterList();
        bookstoreEncounter.add(new Encounter("ENCOUNTER", "AN ENCOUNTER", 1));

        EncounterList coffeeShopEncounter = new EncounterList();
        coffeeShopEncounter.add(new Encounter("ENCOUNTER", "AN ENCOUNTER", 1));

        EncounterList crosswalkEncounter = new EncounterList();

        EncounterList parkEncounter = new EncounterList();
        parkEncounter.add(new Encounter("ENCOUNTER", "AN ENCOUNTER", 1));

        EncounterList swingSetEncounter = new EncounterList();
        swingSetEncounter.add(new Encounter("ENCOUNTER", "AN ENCOUNTER", 1));

        EncounterList lakeEncounter = new EncounterList();
        lakeEncounter.add(new Encounter("ENCOUNTER", "AN ENCOUNTER", 1));

        EncounterList playerlist = new EncounterList(); //populated by encounter count

        // Add Rooms to the map
        //                 Room( name,   description,                             N,       S,      E,      W )
        map.add(new Room("SUBWAY", "\nA magical place where humans hop on an underground train \nand get whisked away to a destination of their heart's desire!", Direction.NOEXIT, 1, Direction.NOEXIT, Direction.NOEXIT, subwayEncounter));
        map.add(new Room("SUBWAY ENTRANCE", "\nRight outside the entrance to my home and before the whole, wide world! \nYou can go anywhere from here!", 0, 4, 3,  2, subwayEntranceEncounter));
        map.add(new Room("BOOKSTORE", "\nA store brimming with stories and ideas to peruse!", Direction.NOEXIT, Direction.NOEXIT, 1, Direction.NOEXIT, bookstoreEncounter));
        map.add(new Room("COFFEE SHOP", "\nHumans frequent this location to imbibe curious liquids...", Direction.NOEXIT, Direction.NOEXIT, Direction.NOEXIT, 1, coffeeShopEncounter));
        map.add(new Room("CROSS WALK", "\nYou're crossing the street!", 1, 5, Direction.NOEXIT, Direction.NOEXIT, crosswalkEncounter));
        map.add(new Room("PARK", "\nA lovely recreational area to enjoy fresh air, blue sky, and dogs!", 4, Direction.NOEXIT, 7, 6, parkEncounter));
        map.add(new Room("SWING SET", "\nHumans employ these to mimic flight while remaining safely seated.", Direction.NOEXIT, Direction.NOEXIT, 5, Direction.NOEXIT, swingSetEncounter));
        map.add(new Room("LAKE", "\nThe water has a calming effect on coins and humans alike.", Direction.NOEXIT, Direction.NOEXIT, Direction.NOEXIT, 5, lakeEncounter));

        // create player and place in Room 0 (i.e. the Room at 0 index of map)
        player = new Protagonist("Penny", "a helpful little friend", map.get(0), playerlist); //map.get(0) is the "SUBWAY" Room entry in the ArrayList map. If this was a normal Array it would be map[0]. "SUBWAY" and everything in it is now the value of Room aRoom
    }

    // access methods
    // map
    private ArrayList getMap() {
        return map;
    }

    private void setMap(ArrayList<Room> aMap) {
        map = aMap;
    }

    // player
    public Protagonist getPlayer() {
        return player;
    }

    public void setPlayer(Protagonist aPlayer) {
        player = aPlayer;
    }

    private void transferOb(Thing t, EncounterList notComplete, EncounterList complete) {
        notComplete.remove(t);
        complete.add(t);
    }

    private String takeOb(String obname) { //transfers Thing from Room Thinglist to player inventory player.getThings()
        String retStr = "";
        Thing t = player.getLocation().getEncounterList().thisOb(obname);
        if (obname.equals("")) {
            obname = "nameless object"; // if no object specified
        }
        if (t == null) {
            retStr = "There is no " + obname + " here!";
        } else {
            transferOb(t, player.getLocation().getEncounterList(), player.getEncounterList());
            retStr = obname + " taken!";
        }
        return retStr;
    }

    private String dropOb(String obname) {
        String retStr = "";
        Thing t = player.getEncounterList().thisOb(obname);
        if (obname.equals("")) {
            retStr = "You'll have to tell me which object you want to drop!"; // if no object specified
        } else if (t == null) {
            retStr = "You haven't got one of those!";
        } else {
            transferOb(t, player.getEncounterList(), player.getLocation().getEncounterList());
            retStr = obname + " dropped!";
        }
        return retStr;
    }

    // move a Person (typically the player) to a Room
    private void moveActorTo(Protagonist p, Room aRoom) {
        p.setLocation(aRoom); // p (init 57) setLocation (init Actor.java 19) returns Room location = Room aRoom
    }

    // move an Actor in direction 'dir'
    private int moveTo(Protagonist anProtagonist, Direction dir) {
        // return: Constant representing the room number moved to
        // or NOEXIT
        //
        // try to move any Person (typically but not necessarily player)
        // in direction indicated by dir
        Room r = anProtagonist.getLocation(); //getLocation returns location (init Actor.java 22)
        int exit;

        switch (dir) {
            case NORTH:
                exit = r.getN(); //return value of int n stored in Room object r (init 68)
                break;
            case SOUTH:
                exit = r.getS(); //return value of int s stored in Room object r (init 68)
                break;
            case EAST:
                exit = r.getE(); //return value of int e stored in Room object r (init 68)
                break;
            case WEST:
                exit = r.getW(); //return value of int w stored in Room object r (init 68)
                break;
            default:
                exit = Direction.NOEXIT; // noexit - stay in same room
                break;
        }
        if (exit != Direction.NOEXIT) {
            moveActorTo(anProtagonist, map.get(exit)); //map ArrayList (init 15) gets int n/s/e/w or -1 (noexit). Use moveActorTo (init 57) to set Actor p location to aRoom.
        }
        return exit;
    }

    public int movePlayerTo(Direction dir) {
        // return: Constant representing the room number moved to
        // or NOEXIT (see moveTo())
        //
//        return moveTo(player, dir); //moveTo (init 62) player (init 16/34)

        if (moveTo(player, dir) == Direction.NOEXIT) {
            showStr("No Exit!");
        } else {
            look();
        }
        return 0;
    }

    private void goN() { //method that moves player north
//        updateOutput(movePlayerTo(Direction.NORTH)); //movePlayerTo (init 94)
        movePlayerTo(Direction.NORTH);
    }

    private void goS() { //method that moves player south
//        updateOutput(movePlayerTo(Direction.SOUTH)); //Direction.SOUTH is entered into movePlayerTo method (init 94) which is entered into updateOutput method (init 117)
        movePlayerTo(Direction.SOUTH);
    }

    private void goW() { //method that moves player east
//        updateOutput(movePlayerTo(Direction.WEST));
        movePlayerTo(Direction.WEST);
    }

    private void goE() { //method that moves player west
//        updateOutput(movePlayerTo(Direction.EAST));
        movePlayerTo(Direction.EAST);
    }

    private void look() {
        showStr("You are in the " + getPlayer().getLocation().describe());
    }

    private void showStr(String s) {
        System.out.println(s);
    }

    private void showInventory() {
        showStr("You have " + getPlayer().getEncounterList().describeThings());
    }

    private void updateOutput(int roomNumber) {
        // if roomNumber = NOEXIT, display a special message, otherwise
        // display text (e.g. name and description of room)
        String s;
        if (roomNumber == Direction.NOEXIT) {
            s = "No Exit!";
        } else {
            Room r = getPlayer().getLocation();
            s = "You arrive at "
                    + r.getName() + ". " + r.getDescription();
        }
        System.out.println(s);
    }

    private String processVerb(List<String> wordlist) {
        String verb;
        String msg = "";
        verb = wordlist.get(0);
        if (!commands.contains(verb)) {
            msg = verb + " is not a known verb! ";
        } else {
            switch (verb) {
                case "n":
                    goN();
                    break;
                case "s":
                    goS();
                    break;
                case "w":
                    goW();
                    break;
                case "e":
                    goE();
                    break;
                case "l":
                case "look":
                    look();
                    break;
                case "inventory":
                case "i":
                    showInventory();
                    break;
                default:
                    msg = verb + " (not yet implemented)";
                    break;
            }
        }
        return msg;
    }

    public String processVerbNoun(List<String> wordlist) {
        String verb;
        String noun;
        String msg = "";

        verb = wordlist.get(0);
        noun = wordlist.get(1);
        if (!commands.contains(verb)) {
            msg = verb + " is not a known verb! ";
        }
        if (!objects.contains(noun)) {
            msg += (noun + " is not a known noun!");
        }
        msg += " (not yet implemented)";
        return msg;
    }

    public String parseCommand(List<String> wordlist) { //creating String method parseCommand with List<String> wordlist as a parameter
        String msg; // Declaring String msg
        if (wordlist.size() == 1) { // if player enters one word
            msg = processVerb(wordlist); // try to process as verb
        } //else if (wordlist.size() == 2) { //if player enters two words
//            msg = processVerbNoun(wordlist); // try to process as verb then noun
        //}
        else {
            msg = "Sorry, didn't catch that..."; //else say only two commands allowed
        }
        return msg;
    }

    public static List<String> wordList(String input) { //make a list that takes in string objects called wordlist that has a parameter of String input
        String delims = "[ \t,.:;?!\"']+"; //defining delimiter characters, used to split tokens (aka words)
        List<String> strlist = new ArrayList<>(); //create an ArrayList object named strlist which is is List<String>
        String[] words = input.split(delims); //takes in the input, splits the tokens between delims characters, and puts the output in an array of Strings called words

        for (String word : words) { //for every word (initialized here) in words String array (initialized in line 192)
            strlist.add(word); //add word into strlist (initialized on line 191)
        }
        return strlist; //Once for loop is completed, return strlist (initialized on line 191)
    }

    public void showIntro(){
        String s;
        s = "\n"+
                "Ah, another bright, shiny day under a bench at the subway...\n"+
                "You wake up next to your two closest friends, Gum Wad and Pocket Lint.\n" +
                "They smile back at you.\n" +
                "\n" +
                "Where do you want to go?\n" +
                "[Enter n, s, w, e]\n" +
                "[...or q to quit]";
        System.out.println(s);
    }

    public String runCommand(String inputstr) { //new method defined
        List<String> wordlist; //**it's a generic list that can hold multiple types of objects, but the type can be specified by writing between <>
        String s = "Off you go, then!";
        String lowstr = inputstr.trim().toLowerCase(); //takes in inputstr parameter, trims off space, and converts to lowercase
        if (!lowstr.equals("q")) { //if lowstr does not equal q
            if (lowstr.equals("")) { //if player enters nothing
                s = "You must enter a command"; //this is the output
            } else {
                wordlist = wordList(lowstr); //calls the wordlist method on lowstr (initialized on 189)
                s = parseCommand(wordlist);
            }
        }
        return s;
    }

}
