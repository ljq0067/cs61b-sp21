package byow.lab13;

import byow.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    /** The width of the window of this game. */
    private int width;
    /** The height of the window of this game. */
    private int height;
    /** The current round the user is on. */
    private int round;
    /** The Random object used to randomly generate Strings. */
    private Random rand;
    /** Whether or not the game is over. */
    private boolean gameOver;
    /** Whether or not it is the player's turn. Used in the last section of the
     * spec, 'Helpful UI'. */
    private boolean playerTurn;
    /** The characters we generate random Strings from. */
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /** Encouraging phrases. Used in the last section of the spec, 'Helpful UI'. */
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        // Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {

        // Generate random string of letters of length n
        char[] randomLetters = new char[n];

        for (int i = 0; i < n; i++){
            int letter = rand.nextInt(26);
            randomLetters[i] = CHARACTERS[letter];
        }

        return new String(randomLetters);
    }

    public void drawFrame(String s) {

        // clear screen
        StdDraw.clear();
        StdDraw.clear(Color.BLACK);
        //StdDraw.enableDoubleBuffering();

        // Take the string and display it in the center of the screen
        int midWidth = width / 2;
        int midHeight = height / 2;

        // If game is not over, display relevant game information at the top of the screen
        if (!gameOver) {
            Font smallFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(smallFont);
            StdDraw.textLeft(1, height - 1, "Round: " + round);
            StdDraw.text(midWidth, height - 1, playerTurn ? "Type!" : "Watch!");
            StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
            StdDraw.line(0, height - 2, width, height - 2);
        }

        // Draw and display text
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight, s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {

        // Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i++) {
            StdDraw.pause(500);
            drawFrame(String.valueOf(letters.charAt(i)));
            StdDraw.pause(1000);
            drawFrame("");
        }
    }

    public String solicitNCharsInput(int n) {

        // Read n letters of player input
        String input = "";
        drawFrame(input);
        while (input.length() < n) {
            while(StdDraw.hasNextKeyTyped()) {
                char letter = StdDraw.nextKeyTyped();
                input += letter;
                drawFrame(input);
            }
        }

        StdDraw.pause(500);
        return input;
    }

    public void startGame() {

        // Set any relevant variables before the game starts
        round = 0;
        gameOver = false;
        playerTurn = false;

        // Establish Engine loop
        while (!gameOver) {
            // begin game
            round++;
            playerTurn = false;
            drawFrame("Round " + round);
            StdDraw.pause(1000);

            // generate random string and display
            String randomString = generateRandomString(round);
            flashSequence(randomString);

            // player input string
            playerTurn = true;
            String playerInput = solicitNCharsInput(round);

            if (!playerInput.equals(randomString)) {
                drawFrame("Game Over! You made it to round: " + round);
                gameOver = true;
            }
        }
    }
}
