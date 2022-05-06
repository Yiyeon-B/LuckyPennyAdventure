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
