package com.wilson;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // initialize word files to be read in the game.
        WordFrenzy game = new WordFrenzy(new java.io.File("words.txt"));
        game.loadWordToList(); // load words from the file to the list.

        int noOfPlay = 0;
        while (noOfPlay < 12) {
            String randWord =  game.getWord();
            System.out.print("==>  ");
            game.displayBlankWord(randWord);
            System.out.println();

            System.out.print("enter the correct word : ".toUpperCase());
            game.getPlayerInput();
            System.out.println("HInt is " + game.getHint());

            if (game.isCorrect() || game.isAlternateAnswer()) {
                System.out.println("YOU ARE CORRECT");
            }else System.out.println("YOU ARE WRONG " + "** " + randWord +" **");

            System.out.println();

            System.out.println("SCORE: " + game.getScore());
            noOfPlay += 1;

        }
    }
}
