package com.yibrown;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.yibrown.game.Game;

public class Application {

    public static void main(String[] args) throws IOException { //throws keyword indicates what exception type can be thrown by a method. IOException (Input/Output Exception)
        BufferedReader in; //Basically like Scanner but simpler, takes in player input efficiently
        String input; //assigns player input to variable input

        Game game = new Game(); //initializes the Game() object
        in = new BufferedReader(new InputStreamReader(System.in)); //System.in allows input to be put into the console and returned to the program, which goes into InputStreamReader, which turns byte streams (8 bit data storing characters/audio/videos/images/etc) into character streams (16 bit Unicode which allows reading/writing text data only), making it more palatable for BufferedReader to intake and then make more efficient

        writeOutput(game.getIntro());
        game.start();

        while(!game.isGameOver()) {
            writeOutput(game.info());

            System.out.print("> "); //the cursor
            input = in.readLine(); //assigns the input into the BufferedReader in (initialized 19) into the String input
            game.runCommand(input);
        }

        writeOutput(game.info());
    }

    private static void writeOutput(String output) {
        System.out.print(output);
    }

}
