import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new WordleView();
        /* //This code I used to test the checkGuess method by setting the "target" value directly, making sure values align properly
        WordleModel w = new WordleModel();
        w.newWord();
        WordleModel.WordleResponse[] result = w.checkGuess("pizza");
        for (WordleModel.WordleResponse wordleResponse : result) {
            System.out.println(wordleResponse);
        }

         */
    }
}
