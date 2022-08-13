package com.yibrown.game;

import java.util.*;

import com.yibrown.game.effects.DescriptionChangeEffect;
import com.yibrown.game.effects.EndGameEffect;
import com.yibrown.game.effects.UnlockEncounterEffect;
import com.yibrown.game.core.Encounter;
import com.yibrown.game.core.Room;

public class Game {

    private static Set<String> EXIT_COMMANDS = new HashSet<>(Arrays.asList("Q", "QUIT")); // creating an Array which has the 2 elements (QUIT & Q), covering it up with the List interface (which extends the Collection interface), and passing that in as input to a new HashSet, thus creating a Set with the two elements QUIT and Q. It's necessary to do it this way because HashSet can only be declared with no input (new HashSet<>()), an initial capacity (new HashSet<>(50)), or initial input in an object which extends the interface Collection (new HashSet<>(myListOfItems))
    private static Map<String, Boolean> ENCOUNTER_COMMANDS = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Y", Boolean.TRUE),
            new AbstractMap.SimpleEntry<>("YES", Boolean.TRUE),
            new AbstractMap.SimpleEntry<>("N", Boolean.FALSE),
            new AbstractMap.SimpleEntry<>("NO", Boolean.FALSE)
    );

    private Map<Room, List<Room>> map; // Created a Map with key value pair of Room to ArrayList of Room(s) named map. ArrayList<Room> shows a list of rooms you can enter from the specified Room
    private Room currentRoom;
    private Encounter currentEncounter;
    private String nextInfo; // would be more efficient as a StringBuilder. When you add text into a String, it makes a new copy of everything and adds the new characters. Over time and with long strings, this can be very inefficient and slow down the program. A StringBuilder collects text in a buffer until it's called. Then it turns all the data into a String and outputs it
    private boolean gameOver;

    public Game() {

        // Define rooms
        Room subway = new Room("subway", "a magical place where humans hop on an underground train \nand get whisked away to a destination of their heart's desire!");
        Room subwayEntrance = new Room("subway entrance", "Right outside the entrance to your home and before the whole, wide world!");
        Room park = new Room("park", "A lovely recreational area. \nIt has a calming effect on coins and humans.");

        // Define encounters
        Encounter subwayEntranceEncounter = new Encounter(
                """
                          \nYou bounce up the steps, darting between shoes and ankles and bursting outside!
                          \nYou move out of the way of foot traffic, breathe in the fresh inner city smog, and smile.       
                          \nWhat a great day! 
                          \nThen again - when wasn't it?
                          \nJust before you decide to keep rolling you notice a flash of bright red heels 
                          rush past a newspaper stand.
                          \nThe heels march onwards (towards very important business, you imagine)
                          but then slowly come to a halt. 
                          \nThey turn around.    
                          \n"James?"
                          \nThe pair of black suede shoes facing the newspaper stand turn to face the red heels. 
                          \n"...Jenny?"
                          \nThe red heels take a step forward. "Oh my goodness! It's been-"
                          \n"-six years now, hasn't it?" 
                          \n"Yes! Yes..." A red heel hooks around an ankle. "Time flies, doesn't it?
                          How have you been?"
                          \nOne of the suede shoes scuffs the pavement. "I've been doing... well. Quite well."
                          \n"Do you still paint?"
                          \n"I do."
                          \n"I'm glad some things don't change."
                          \n"As am I."
                          \nA beat of silence.
                          \n"Well... It's good to see you looking well, Jenny. Truly."
                          \n"Thank you, James." 
                          \nA slightly longer silence.
                          \nThe suede shoes begin to turn away. "You look busy as always - 
                          I wouldn't want to hold you back."
                          \n"No, not at all, James, never! It was... good to see you as well."
                          \nThe suede shoes turn back to the stand. 
                          \nAfter a few moments the red heels turn away as well, slowly walking away...
                          \nThese two look in need of a little luck, don't you think?
                          [YES/NO]
                          """,
                """
                            \nYou look up from the red heels and see a white handkerchief
                            sticking out of an equally red purse.
                            \nYou roll as fast as you can before the red heels, skid to a stop,
                            and take a mighty leap, snagging the handkerchief and rolling away...
                            \n...towards the black suede shoes.
                            \nYou deposit the handkerchief onto them and discreetly slip under the newspaper stand.
                            \nThe red heels return in a flurry of steps.
                            \n"Oh! Well, I - How embarrassing! Thank you, James."
                            \n"Nothing to be embarrassed about." A hand leans over and plucks the handkerchief.
                            "Hope it isn't too dirty..."
                            \n"It looks just fine... Thank you."
                            \nAnother pause.
                            \n"James?"
                            \n"Yes, Jenny?"
                            \n"Would you like to go out for coffee?"
                            """,
                """
                           \nAfter some distance you think you see the red shoes pause
                           \nbut before you can tell, a small crowd gets in the way.
                           \nBy the time they pass, the red heels have continued walking.
                           \nThe black suede shoes turn to watch them go.
                           \nThe two of you watch the red heels walk further and further away 
                           until the flashes of red round a corner and disappear.
                           """);

        Encounter parkEncounter = new Encounter("""
                                            \nYou bounce along the sidewalk to the park.
                                            \nAlong the way you pass by a pair of dirty white sneakers. They march around
                                            back and forth, around in circles, looking very hard for their mittens.
                                            \nThe mittens must be very important to them. You vow to keep an eye out.
                                            \nYou eventually roll across a shady grove. It's usually pretty secluded here...
                                            \n...but what's this?
                                            \nYou witness a brown cat-sized dog sniffing around in circles, its leash
                                            dragging on the ground.
                                            \nYou creep a little closer...
                                            \nThe collar has a tag that reads "MITTENS".
                                            \nThese two look in need of a little luck, don't you think?
                                            [YES/NO]   
                                            """, """
                                            \nYou roll up to the small dog and flip back and forth to catch its attention.
                                            \nIts gaze latches onto you. Then it barks, wagging its tail.
                                            \nYou skip along a few steps, making sure the dog follows as you head back to
                                            the main area of the park.
                                            \nYou have a bit of a hard time keeping an eye out for the dirty white sneakers
                                            while the dog tries its best to swallow you whole.   
                                            \nLuckily, The dirty white sneakers rush up to you.
                                            \n"Mittens!"
                                            \nJust before you are caught in a slobbery mouth, the dog is whisked away.
                                            \n"I was looking all over for you... You can't just run off like that!"
                                            """, """
                                             \nYou watch the dog sniff around in circles for a bit, not doing anything
                                             particularly interesting.
                                             \nIt suddenly lifts its head - another dog sighted across the street!
                                             \nThe brown dog breaks into a sprint across the grove and into the road,
                                             where it became well-acquainted with a semi-truck.
                                             """);
        Encounter finalEncounter = new Encounter(
                "Do you think you did your best today?",
                "You roll back to your favorite place and watch the people pass by, feeling content...",
                "That's the thing - there's always tomorrow!");

        // Define encounter effects
        DescriptionChangeEffect changeSubwayEntrance = new DescriptionChangeEffect(
                subwayEntrance,
                "Right outside the entrance to your home and before the whole, wide world! \nIt's so cool, the stuff that can happen here!",
                "Right outside the entrance to your home. It sure was a lot of working going up \nthose stairs. Maybe it's time for a nap...");

        UnlockEncounterEffect unlockFinalEncounter = new UnlockEncounterEffect(subway, finalEncounter);
        unlockFinalEncounter.addDependentEncounter(parkEncounter);
        unlockFinalEncounter.addDependentEncounter(subwayEntranceEncounter); //139 and 140 unlock final encounter

        EndGameEffect gameOverEffect = new EndGameEffect(this);

        // Setup effects for encounters
        subwayEntranceEncounter.addEffect(changeSubwayEntrance);
        parkEncounter.addEffect(unlockFinalEncounter);
        subwayEntranceEncounter.addEffect(unlockFinalEncounter); //*cp
        finalEncounter.addEffect(gameOverEffect);

        // Setup encounters for rooms
        subwayEntrance.setEncounter(subwayEntranceEncounter);
        park.setEncounter(parkEncounter);

        // Setup map
        List<Room> subwayConnections = new ArrayList<Room>(Arrays.asList(subwayEntrance));
        List<Room> subwayEntranceConnections = new ArrayList<Room>(Arrays.asList(subway, park));
        List<Room> parkConnections = new ArrayList<Room>(Arrays.asList(subwayEntrance));

        map = new HashMap<>();
        map.put(subway, subwayConnections);
        map.put(subwayEntrance, subwayEntranceConnections);
        map.put(park, parkConnections);

        // Setup starting game state
        currentRoom = subway;
        currentEncounter = null;
        nextInfo = "";
        gameOver = false;
    }



    private void appendInfo(String output) {
        nextInfo += String.format("%s\n", output);
    }

    private void appendRoomInfo() {
        appendInfo(String.format("You are in the %s:", currentRoom.getName()));
        appendInfo(currentRoom.getDescription());
    }

    private void appendEncounterInfo() {
        appendInfo(currentEncounter.getDescription());
    }

    private void appendMovementInfo() {
        appendInfo("Where would you like to roll off to?");
        appendInfo(map.get(currentRoom).toString());
        appendInfo("[or press q to quit]");
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

        return """
                \nAh... another brand new day under your favorite bench across the tracks...           
                """;
    }

    public void start() {
        moveRoom(currentRoom);
    }

    public String info() {
        String result = nextInfo; //nextInfo is a buffer. A buffer is like a bus stop. The stuff waits until called.
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
