package wordle;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
       new WordleView(new WordleModel());
       /*
        //This code I used to test the checkGuess method by setting the "target" value directly, making sure values align properly
        wordle.WordleModel w = new wordle.WordleModel();

        w.newWord();
        wordle.WordleModel.WordleResponse[] result = w.checkGuess("train");
        for (wordle.WordleModel.WordleResponse wordleResponse : result) {
            System.out.println(wordleResponse);
        }

        */
    }
}
