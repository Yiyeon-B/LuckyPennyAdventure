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

        // Define rooms
        Room subway = new Room("subway", "a magical place where humans hop on an underground train \nand get whisked away to a destination of their heart's desire!");
        Room subwayEntrance = new Room("subway entrance", "Right outside the entrance to your home and before the whole, wide world! \nYou can go anywhere from here!");

        // Define encounters
        Encounter tapEncounter = new Encounter(
                """
                          \nYou bounce up the steps, deftly darting between shoes and ankles before bursting outside!
                          \nYou breathe in the fresh inner city smog and smile.       
                          \nJust before you decide to keep rolling, you pause to watch a pair of bright red heels
                          \nrush by a newspaper stand.
                          \nThe heels go marching past but then slowly come to a halt and turn around.    
                          \n"James?"
                          \nA pair of black suede shoes facing the stand turn to face the red heels. 
                          \n"Jenny?"
                          \nThe red heels take a step forward. "Oh my goodness! It's been-"
                          \n"-six years now, hasn't it?" One of the suede shoes scuffs the pavement.
                          \n"Yes! Yes..." The left red heel hooks around the other ankle. "The time sure does fly..."
                          \nA beat of silence.
                          \n"Well... It's good to see you looking well, Jenny. Truly."
                          \n"Thank you, James." 
                          \nA slightly longer silence.
                          \nThe suede shoes slightly turn away. "You look busy as always - I wouldn't want to hold you."
                          \n"No, not at all, James, never! It was... good to see you as well."
                          \nThe suede shoes turn back to the stand. After a few moments, the red heels turn away as well,
                          \nand slowly begin to walk away...
                          \n
                          \nThese two look in need of a little luck, don't you think?
                          \n[YES/NO]
                          """,
                """
                            \nYou look up from the red heels and see a white handkerchief sticking out of an equally red purse.
                            \nYou roll as fast as you can before the red heels, skid to a stop,
                            \nand take a mighty leap, snagging the handkerchief and rolling away...
                            \n...and towards the black suede shoes.
                            \nAs you deposit the handkerchief onto the shoes, you discreetly slip under the newspaper stand.
                            \nYou witness the red heels return in a flurry of steps.
                            \n"Oh! Well, I - How embarrassing! Thank you, James."
                            \n"Nothing embarrassing about the wind. Hope it isn't too dirty..."
                            \n"It looks just fine. Thank you."
                            \nAnother pause.
                            \n"James?"
                            \n"Yes, Jenny?"
                            \n"Would you like to go out for coffee?"
                            \nYou smile and roll away.
                            """,
                "Best to let sleeping dogs lie.");
        Encounter finalEncounter = new Encounter(
                "Do you think you did your best today?",
                "You roll back to your favorite place and watch the people pass by, feeling content.",
                "That's the thing - there's always tomorrow!");

        // Define encounter effects
        DescriptionChangeEffect changeSubwayEntrance = new DescriptionChangeEffect(
                subwayEntrance,
                "Right outside the entrance to your home and before the whole, wide world! \nIt's so cool, the stuff that can happen here!",
                "Right outside the entrance to your home. It sure was a lot of working going up \nthose stairs. Maybe it's time for a nap...");

        UnlockEncounterEffect unlockFinalEncounter = new UnlockEncounterEffect(subway, finalEncounter);
        unlockFinalEncounter.addDependentEncounter(tapEncounter); // only unlock finalEncounter once tapEncounter is done

        EndGameEffect gameOverEffect = new EndGameEffect(this);

        // Setup effects for encounters
        tapEncounter.addEffect(changeSubwayEntrance);
        tapEncounter.addEffect(unlockFinalEncounter);
        finalEncounter.addEffect(gameOverEffect);

        // Setup encounters for rooms
        subwayEntrance.setEncounter(tapEncounter);

        // Setup map
        List<Room> hallwayConnections = new ArrayList<Room>(Arrays.asList(subwayEntrance));
        List<Room> bathroomConnections = new ArrayList<Room>(Arrays.asList(subway));

        map = new HashMap<>();
        map.put(subway, hallwayConnections);
        map.put(subwayEntrance, bathroomConnections);

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
