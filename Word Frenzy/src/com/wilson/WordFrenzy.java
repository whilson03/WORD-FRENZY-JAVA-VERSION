package com.wilson;
/**
 * author : Adeyoriju Olabode Wilson
 * Department of Computer Science and Enginneering,
 * Obafemi Awolowo University
 * Date created; 23 - 10 - 2018.
 * Date last modified; 2 - 11 - 2018.
 * purpose:
 * simple word game where users has to type in the correct words
 * from the words displayed with blank spaces.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class WordFrenzy implements Cloneable {
    ArrayList <Integer> indexes;
    private java.io.File txtFile;
    private java.util.ArrayList wordList;
    private java.util.Random rand;
    private boolean alternateAnswer;
    private String randomWord;
    private String playerInput;
    private int score = 0;


    //constructor with an array list of words
    public WordFrenzy(java.io.File newTxtFile) {
        rand = new Random();
        this.txtFile = newTxtFile;
    }


    public WordFrenzy() {
        rand = new Random();
    }

    // set file if no arg constructor is used.
    public void setFile(String filename) {
        this.txtFile = new java.io.File(filename);
    }


    /**
     * randomly select a word from a list of words.
     * @return the selected word.
     */
    public String getWord() throws IOException {
        this.wordList = loadWordToList();
        int randomListIndex;
        // get a random index for word to be chosen from.
        randomListIndex = rand.nextInt(wordList.size());
        // return a word from a random index in the array list.
        randomWord = String.valueOf(wordList.get(randomListIndex));
        // shuffle the list after each selection to reduce the no of repetitions a word is chosen.
        java.util.Collections.shuffle(wordList);
        return randomWord;
    }


    /**
     * get players input.
     * @return players input.
     */
    public String getPlayerInput() {
        Scanner input = new Scanner(System.in);
        playerInput = input.nextLine().toLowerCase();
        return playerInput;
    }

      /**
     * check if word thew user entered is the correct word.
     * @return true or false
     */
    public boolean isCorrect() {
        if (playerInput.equalsIgnoreCase(randomWord)) {
            return true;
        }
        return false;
    }


    /**
     * takes a String : word and replaces random characters in the string
     * to display an incomplete word.
     * @param newWord
     */
    public void displayBlankWord(String newWord) {
        int indexToReplace;
        // word to be displayed with blank spaces.
        String word = newWord.toUpperCase();

        // assign no of blank spaces based on the length of the word.
        int noOfBlanks;
        if (word.length() < 6) {
            noOfBlanks = 2;
        } else if (word.length() > 6  && word.length() <= 9) {
            noOfBlanks = 3;
        } else {
            noOfBlanks = 4;
        }

        ArrayList<Integer> indexesToReplaceArray = new ArrayList<>();
        // ensure that the indexes to replace are unique.
        while (indexesToReplaceArray.size() < noOfBlanks) {
            // generate random numbers in the range of the length of the word.
            indexToReplace = (int) (Math.random() * (word.length() - 1));
            //  check to ensure that the random indexes to be replaced are unique.
            // add only unique indexes to the indexes to ReplaceArray;
            if (!(indexesToReplaceArray.contains(indexToReplace))) {
                indexesToReplaceArray.add(indexToReplace);
            }
        }
        // create an array of characters of the word the words.
        char[] wordArray = word.toCharArray();
        /*
         replace characters in the wordArray using the random indexes in the indexesToReplaceArray)
         with  dashes to depict the blank spaces.
          */
        for (int i = 0; i < noOfBlanks; i++) {
            wordArray[indexesToReplaceArray.get(i)] = '-';
        }
        //display the word to the user
        for (char aWordArray : wordArray) {
            System.out.print(aWordArray + " ");
        }
        System.out.println();
        // clone indexes to replace list so as to be used to check for alternate answers in later function.
        indexes = (ArrayList<Integer>) indexesToReplaceArray.clone();
    }

    /**
     * compute the player score,
     * default point for each correct answer is 1 point.
     * @return current score.
     */
    public int getScore() {
        // check if the word chosen is either the chosen word or the alternate word which is also in the array list.
        if (isCorrect() || alternateAnswer) {
            score++;
            return score;
        }
        return score;
    }


    /**
     * checks to see if the letter in the word the user entered are in the random word and the letters also
     * match the letters that were not changed including their positions in the word
     * @return : true if word is alternate.
     */
    public boolean isAlternateAnswer() {
        //list to hold indexes that were not changed to dashes in the word.
        ArrayList<Integer> unchangedIndexesList = new ArrayList<>();

        // ensure that the indexes that are appended to new array list are the unchanged indexes.
        for (int i = 0; i < randomWord.length(); i++) {
            if (randomWord.length() == playerInput.length()) {
                // append indexes that were not changed to the list.
               if (!(indexes.contains(i)))
                    unchangedIndexesList.add(i);
            }
        }

        // use count to ensure that all the characters that are really being compared are at the right indexes.
        int count = 0;
        for (int i = 0; i < unchangedIndexesList.size(); i++) {
            //compare the characters of the randomWord with user entered word and ensure that all the characters that were not changed match.
            if (randomWord.charAt(unchangedIndexesList.get(i)) == playerInput.charAt(unchangedIndexesList.get(i))) {
                count += 1;
            }
        }
        // if no of the correct no of counts is equal to the no of unchanged indexes in the array list.
        if (count == unchangedIndexesList.size()) {
            // check to see if the alternate word is also in the list of defined words for the game.
            if (wordList.contains(playerInput)) {
                alternateAnswer = true;
            } else {
                alternateAnswer = false;}
        } else {
            alternateAnswer = false;
        }
        return alternateAnswer;
    }


    /**
     * load words from a text file to the array list of words to be used in the program.
     *
     * @return list of the loaded words.
     * @throws IOException : if the txt file to be read is not found
     */
    public java.util.ArrayList loadWordToList() throws IOException {
        java.util.ArrayList<String> listOfWords = new ArrayList<>();
        String word;

        try (Scanner readWord = new Scanner(txtFile)) {
            while (readWord.hasNext()) {
                word = readWord.nextLine();
                // add only words that have length of four and above.
                if (word.length() >= 4)
                    listOfWords.add(word.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("word file \"txt\" not found");
        }return listOfWords;
    }


    // To Do get a hint: a character from the chosen random
    public char getHint() {
        String word = randomWord;
        int hintIndex = rand.nextInt((word.length()));
        return word.charAt(hintIndex);

    }


}





