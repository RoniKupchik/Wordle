package wordle;

import wordle.WordleModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import static java.awt.event.KeyEvent.VK_ENTER;

public class WordleView extends JFrame {
    static final int NUM_ROWS = 6;
    static final int NUM_COLS = 5;

    private final WordleModel MODEL;

    private JLabel[][] cells;
    private int numGuess = 0;
    private StringBuilder currentGuess;

    /**
     * Runs the Wordle GUI based on the model
     *
     * @param model the WordleModel where the game logic takes place
     */
    public WordleView(WordleModel model) {
        super("Wordle Game");
        MODEL = model;

        this.setSize(500, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Handle key-presses
        this.addKeyListener(new KeyHandler());

        populateGameBoard();
        currentGuess = new StringBuilder(NUM_COLS);  // Initialize the currentGuess StringBuilder

        this.setVisible(true);
    }

    /**
     * Gets a representation of the current guess as it stands
     *
     * @return a String containing the current guess as it stands
     */
    private String getCurrentGuess() {
        StringBuilder guess = new StringBuilder();
        for (JLabel label : cells[numGuess]) {
            String labelText = label.getText();
            if (labelText.isEmpty()) break;
            guess.append(labelText);
        }
     //   System.out.println("this is method return: " + guess);
        return guess.toString();
    }

    /**
     * Check the guess and handle the model's response
     */
    private void checkGuess() throws IOException {
        List<WordleResponse> response = (MODEL.checkGuess(getCurrentGuess()));
        System.out.println("in checkguess:" + response + " :size of res: " + response.size());
        //this check doesnt work since response size is always 0
//        System.out.println("this is response size:# "+ response.size());
//        if (response.size() <  NUM_COLS) {
//            JOptionPane.showMessageDialog(this, "Invalid entry.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
        int countCorrect = 0;
        for (int i = 0; i < 5; i++) {
            WordleResponse code = response.get(i);
            System.out.println("response at position " + i+ ": "+ response.get(i));
            switch (code) {
                case WRONG:
                    cells[numGuess][i].setBackground(UIManager.getColor("Panel.background"));
                    break;
                case WRONG_POSITION:
                    cells[numGuess][i].setBackground(Color.orange);
                    break;
                case CORRECT:
                    cells[numGuess][i].setBackground(Color.GREEN);
                    countCorrect++;
                    break;
            }
        }
        if (countCorrect == NUM_COLS) {
            gameWon();
            return;
        }
        numGuess++;
    }

    /**
     * Start a new game or exit based on the user's input
     */
    private void gameWon() throws IOException {
        int input = JOptionPane.showConfirmDialog(this, "You won!\nWould you like to play again?", "You Won", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (input == 1) System.exit(0);
        // Initialize new game
        populateGameBoard();
        numGuess = 0;
        MODEL.newWord();
    }

    /**
     * Populates the GUI game board and sets the corresponding game board in the model
     */
    private void populateGameBoard() {
        // Add panel with grid
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(NUM_ROWS, NUM_COLS, 4, 4));
        this.add(panel, BorderLayout.CENTER);
        panel.updateUI();

        cells = new JLabel[NUM_ROWS][NUM_COLS];

        // Populate the GUI and virtual game boards
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                JLabel label = new JLabel();
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setOpaque(true);
                label.setBackground(Color.white);
                // Add the cell to the virtual game board
                cells[i][j] = label;
                // Add the cell to the GUI game board
                panel.add(label);
            }
        }
    }

    /**
     * KeyHandler for the frame
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
         //   int currentLength = getCurrentGuess().length();
            int currentLength = currentGuess.length();
            char keyCode = e.getKeyChar();
            if (keyCode == VK_ENTER && currentLength == NUM_COLS) { // Enter
                try {
                    System.out.println("here4");
                    checkGuess();
                    currentGuess.setLength(0); // Clear the currentGuess StringBuilder
                    Arrays.stream(cells[numGuess]).forEach(label -> label.setText("")); // Clear the text in JLabels
              //      numGuess++;
                    if (numGuess >= NUM_ROWS) {
                        numGuess = 0; // Reset numGuess if it exceeds the number of rows
                    }
                    SwingUtilities.invokeLater(() -> {
                        requestFocusInWindow(); // Set the focus back to the WordleView frame
                    });
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            } else if (keyCode == VK_BACK_SPACE && currentLength >= 1) { // Backspace
                System.out.println("here5");
                currentGuess.deleteCharAt(currentLength - 1);  // Remove the last character from the currentGuess
                cells[numGuess][currentLength - 1].setText("");
            } else if (currentLength != NUM_COLS && keyCode >= 'A' && keyCode <= 'z' && (keyCode >= 'a' || keyCode <= 'Z')) {
                System.out.println("here6");
                currentGuess.append(Character.toUpperCase(keyCode));  // Append the entered character to currentGuess
                int newLength = currentGuess.length(); // Get the new length
                // if guess is not currently full, and is a letter in the English alphabet:
                cells[numGuess][newLength - 1].setText(String.valueOf(Character.toUpperCase(keyCode))); // Update the text
             //   cells[numGuess][currentLength].setText(String.valueOf(Character.toUpperCase(e.getKeyChar())));
            }

        }

    }
}
