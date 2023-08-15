package wordle;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class WordleModel {
 //   enum WordleResponse {CORRECT, WRONG, WRONG_POSITION}
    private List<String> wordDictionary = new ArrayList<>();
    private String secretWord = "TRAIN";
    Random r = new Random();

    public WordleModel() throws IOException {

        wordDictionary = Files.readAllLines(Paths.get("C:\\Users\\ronik\\downloads\\answers.txt"));
    }

    public String newWord() throws IOException {
     //   wordDictionary = Files.readAllLines(Paths.get("C:\\Users\\ronik\\downloads\\answers.txt"));
        int rand = r.nextInt(wordDictionary.size());
        secretWord = "TRAIN";
      //  secretWord = wordDictionary.get(rand);
        System.out.println(secretWord);
    //    new wordle.WordleView(new wordle.WordleModel());
        return secretWord;
    }
    public boolean isWord(String s){

        return wordDictionary.contains(s);
    }


    public List<WordleResponse> checkGuess(String guessWord) {
        if (!wordDictionary.contains(guessWord.toLowerCase())){
            System.out.println(wordDictionary.size());
            System.out.println("illegal word");
        }
        // to store letters which aren't 'greens' to avoid corner case where correct word is 'train' and guess is 'teeth'
        // so the 2nd 't' should not return as yellow
        ArrayList<Character> nonMatches = new ArrayList<>();
        //array of enums to keep track of responses
      //  List<WordleResponse> responses = new ArrayList<>(5);
        List<WordleResponse> responses = new ArrayList<>(Collections.nCopies(5, null));
       // WordleResponse [] responses = new WordleResponse[5];
        //first find all the 'greens' then separate loop for other characters
        System.out.println("secret word: " + secretWord);
        for (int i = 0; i < secretWord.length(); i++) {
            if (guessWord.charAt(i) == secretWord.charAt(i)){
                System.out.println("here10");
                responses.set(i, WordleResponse.CORRECT);
            }
            else{
                System.out.println("here11");
                nonMatches.add(secretWord.charAt(i));
            }
        }

        for (int i = 0; i < guessWord.length(); i++) {
            //only checks the non-greens
            if (responses.get(i) == null) {
                char guessChar = guessWord.charAt(i);
                if (nonMatches.contains(guessChar)) {
                    System.out.println("here12");
                    responses.set(i, WordleResponse.WRONG_POSITION);
                    //checks for corner case where secret word is 'lands' and guess is 'calls', only one 'l' should be yellow
                    nonMatches.remove(Character.valueOf(guessChar));  // Remove the character from nonMatches
                //    Object ch = guessWord.charAt(i);
                 //   nonMatches.remove(ch);
                } else {
                    System.out.println("here13");
                    responses.set(i, WordleResponse.WRONG);

                }
            }
        }
        System.out.println("in wordle model checkguess: " + responses);
        return responses;
    }
}

