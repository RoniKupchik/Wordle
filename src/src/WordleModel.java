import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordleModel {
    enum WordleResponse {CORRECT, WRONG, WRONG_POSITION}
    private List<String> words = new ArrayList<>();
    private String newWord = "";
    Random r = new Random();

    public void newWord() throws IOException {
        words = Files.readAllLines(Paths.get("C:\\Users\\ronik\\downloads\\answers.txt"));
        int rand = r.nextInt(words.size());
        newWord = words.get(rand);
        System.out.println(newWord);
        new WordleView();
    }
    public boolean isWord(String s){
        return words.contains(s);
    }

    public WordleResponse[] checkGuess(String s) {

        WordleResponse [] responses = new WordleResponse[5];
        for (int i = 0; i < newWord.length(); i++) {
            if (s.charAt(i) == newWord.charAt(i)){
                responses[i] = WordleResponse.CORRECT;
            }
            else if (newWord.contains(s.charAt(i)+ "")){
                responses[i] = WordleResponse.WRONG_POSITION;
            }
            else{
                responses[i] = WordleResponse.WRONG;
            }
        }
        return responses;
    }
}

