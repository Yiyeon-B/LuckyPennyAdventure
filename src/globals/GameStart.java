package globals;

import game.Game;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameStart {

    public static void main(String[] args) throws IOException { //throws keyword indicates what exception type can be thrown by a method. IOException (Input/Output Exception)
        BufferedReader in; //Basically like Scanner but simpler, takes in player input efficiently
        String input; //assigns player input to variable input
        String output; //assigns game output to variable output
        Game newGame = new Game(); //initializes the Game() object
        in = new BufferedReader(new InputStreamReader(System.in)); //System.in allows input to be put into the console and returned to the program, which goes into InputStreamReader, which turns byte streams (8 bit data storing characters/audio/videos/images/etc) into character streams (16 bit Unicode which allows reading/writing text data only), making it more palatable for BufferedReader to intake and then make more efficient
        newGame.showIntro();
        do {
            System.out.print("> "); //the cursor
            input = in.readLine(); //assigns the input into the BufferedReader in (initialized 19) into the String input
            output = newGame.runCommand(input); //puts the String input (init 23) into the runCommand() method, and assigns that output into variable output
            System.out.println(output);
        } while (!"q".equals(input)); //only do the above if player doesn't type "q", which exits the game
    }

}
