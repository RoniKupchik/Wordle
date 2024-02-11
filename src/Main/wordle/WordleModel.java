package wordle;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class WordleModel {

    private HashSet<String> wordDictionary = new HashSet<>();
    private String secretWord;
    Random r = new Random();

    public WordleModel() throws IOException {
        List<String> words = Files.readAllLines(Paths.get("/Users/ronikupchik/Downloads/answers.txt"));
        wordDictionary.addAll(words);
        newWord();
    }

    public String newWord() throws IOException {
//        int rand = r.nextInt(wordDictionary.size());
//        secretWord = wordDictionary.get(rand).toUpperCase();
        int rand = r.nextInt(wordDictionary.size());
        Iterator<String> iterator = wordDictionary.iterator();
        for (int i = 0; i < rand; i++) {
            iterator.next();
        }
        secretWord = iterator.next().toUpperCase();
        return secretWord;
    }
    public boolean isWord(String s){
        return wordDictionary.contains(s);
    }

    public List<WordleResponse> checkGuess(String guessWord) {
        if (!wordDictionary.contains(guessWord.toLowerCase())){
            return Collections.singletonList(WordleResponse.ILLEGAL_WORD);
        }
        // to store letters which aren't 'greens' to avoid corner case where correct word is 'train' and guess is 'teeth'
        // so the 2nd 't' should not return as yellow
        ArrayList<Character> nonMatches = new ArrayList<>();
        //array of enums to keep track of responses
        List<WordleResponse> responses = new ArrayList<>(Collections.nCopies(5, null));
        //first find all the 'greens' then separate loop for other characters
        for (int i = 0; i < secretWord.length(); i++) {
            if (guessWord.charAt(i) == secretWord.charAt(i)){
                responses.set(i, WordleResponse.CORRECT);
            }
            else{
                nonMatches.add(secretWord.charAt(i));
            }
        }

        for (int i = 0; i < guessWord.length(); i++) {
            //only checks the non-greens
            if (responses.get(i) == null) {
                char guessChar = guessWord.charAt(i);
                if (nonMatches.contains(guessChar)) {
                    responses.set(i, WordleResponse.WRONG_POSITION);
                    //checks for corner case where secret word is 'lands' and guess is 'calls', only one 'l' should be yellow
                    nonMatches.remove(Character.valueOf(guessChar));
                } else {
                    responses.set(i, WordleResponse.WRONG);

                }
            }
        }
        return responses;
    }
}

