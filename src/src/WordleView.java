import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class WordleView extends JFrame{
    WordleModel wordleModel = new WordleModel();
    private JButton enter = new JButton("Enter");
    private JButton newGame = new JButton("New Game");
    String guess = "";
    ArrayList<JLabel> Guesslist = new ArrayList<>();
    WordleView() {
        super("My Wordle");

        this.setSize(500, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel canvasPanel = new JPanel();
        this.add(canvasPanel, BorderLayout.CENTER);
        canvasPanel.setLayout(new GridLayout(6, 5, 6, 6));
        final int[] focusLabel = {0};
        for (int i = 0; i < 30; i++) {
            this.getContentPane().setLayout(new FlowLayout());
            JLabel jl = new JLabel();
            jl.setHorizontalAlignment(SwingConstants.CENTER);
            jl.setOpaque(true);
            Guesslist.add(jl);
            Border border = BorderFactory.createLineBorder(Color.WHITE, 5);
            jl.setBorder(border);
            canvasPanel.add(jl);
        }
        canvasPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Guesslist.get(focusLabel[0]).setText(String.valueOf(e.getKeyChar()));
                focusLabel[0]++;
                char ch = e.getKeyChar();
                guess += ch;
            }
        });
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    wordleModel.newWord();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(wordleModel.words.size());
                System.out.println("test");
                if (!wordleModel.isWord(guess)) {
                    WordleModel.WordleResponse[] result = wordleModel.checkGuess(guess);
                    changeBackground(result);
                }
                else{
                    JOptionPane.showMessageDialog(canvasPanel, "Invalid entry.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                guess = "";
            }
            private void changeBackground(WordleModel.WordleResponse[] result) {
                for (int j = 0; j < result.length; j++) {
                    if (result[j] == WordleModel.WordleResponse.CORRECT){
                        Guesslist.get(j).setBackground(Color.GREEN);
                    }
                    else if (result[j] == WordleModel.WordleResponse.WRONG_POSITION){
                        Guesslist.get(j).setBackground(Color.BLUE);
                    }
                    else{
                        Guesslist.get(j).setBackground(Color.GRAY);
                    }
                }
            }
        });
        enter.setFocusable(false);
        canvasPanel.setFocusable(true);
        this.add(enter, BorderLayout.SOUTH);
        this.add(newGame,BorderLayout.SOUTH);
        this.setVisible(true);
    }

}

