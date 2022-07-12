package byow.findStarInDark;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.Stopwatch;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class Engine {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static TETile[][] world;
    TERenderer ter = new TERenderer();
    private int charX;
    private int charY;
    private StringToWorld creator;
    private Stopwatch timer;
    private String tracker;
    private Double time;
    private String oldInput;


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */

    public static void main(String[] args) {
        Engine e = new Engine();
        e.interactWithKeyboard();
    }

    /**
     * @source: howtodoinjava.com for file creation and file reading
     */
    public void interactWithKeyboard() {
        tracker = "";
        time = 0.0;
        oldInput = "";
        StdDraw.enableDoubleBuffering();
        Random rand = new Random(32423423);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.picture(0.5, 0.65, "byow/findStarInDark/star.png");
        StdDraw.filledRectangle(0.92, 0.65, 0.333, .1);
        StdDraw.setPenColor(Color.white);
        for (int i = 0; i < 200; i++) {
            StdDraw.filledCircle(rand.nextDouble(), rand.nextDouble(), 0.001);
        }

        StdDraw.setPenColor(Color.YELLOW);
        Font font = new Font("Futura Bold Italic", Font.BOLD, 60);
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.setFont(font);
        StdDraw.text(.5, .800, "STAR FINDER");
        Font font2 = new Font("Futura Bold Italic", Font.PLAIN, 20);
        StdDraw.setFont(font2);
        StdDraw.text(.5, .5, "NEW GAME (N)");
        StdDraw.text(.5, .45, "LOAD GAME (L)");
        StdDraw.text(.5, .4, "STORY/RULES (S)");
        StdDraw.text(.5, .35, "QUIT (Q)");
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.show();

        KeyboardInputSource inputSource;
        inputSource = new KeyboardInputSource();
        while (inputSource.possibleNextInput()) {

            char nextChar = inputSource.getNextKey();

            if (nextChar == 'N') {
                tracker = tracker + nextChar;
                StdDraw.clear();
                StdDraw.clear(Color.black);
                StdDraw.setPenColor(Color.white);
                for (int i = 0; i < 200; i++) {
                    StdDraw.filledCircle(rand.nextDouble(), rand.nextDouble(), 0.001);
                }
                StdDraw.setPenColor(Color.yellow);
                font = new Font("Futura Bold Italic", Font.BOLD, 50);
                StdDraw.setFont(font);
                StdDraw.text(.5, .600, "TYPE SEED");
                font2 = new Font("Futura Bold Italic", Font.PLAIN, 20);
                font = new Font("Futura Bold Italic", Font.BOLD, 25);
                StdDraw.setFont(font);
                StdDraw.text(.5, .54, "N");
                StdDraw.setFont(font2);
                StdDraw.text(.5, .5, "CONFIRM (S)");
                StdDraw.show();

                String seed = "N";
                while (inputSource.possibleNextInput()) {
                    char c = inputSource.getNextKey();
                    tracker = tracker + c;
                    if (c == 'S') {
                        seed = seed + c;
                        break;
                    } else if ((48 <= c) && (c <= 57)) {
                        seed = seed + c;
                        StdDraw.enableDoubleBuffering();
                        StdDraw.clear(Color.black);
                        StdDraw.setPenColor(Color.white);
                        for (int i = 0; i < 200; i++) {
                            StdDraw.filledCircle(rand.nextDouble(), rand.nextDouble(), 0.001);
                        }
                        StdDraw.setPenColor(Color.yellow);
                        font = new Font("Futura Bold Italic", Font.BOLD, 50);
                        StdDraw.setFont(font);
                        StdDraw.text(.5, .600, "TYPE SEED");
                        font = new Font("Futura Bold Italic", Font.BOLD, 25);
                        StdDraw.setFont(font);
                        StdDraw.text(.5, .54, seed);
                        font2 = new Font("Futura Bold Italic", Font.PLAIN, 20);
                        StdDraw.setFont(font2);
                        StdDraw.text(.5, .5, "CONFIRM (S)");
                        StdDraw.show();
                    }
                }
                TERenderer ter = new TERenderer();
                /* Feel free to change the width and height. */
                int w = WIDTH;
                int h = HEIGHT;
                ter.initialize(w, h);

                world = new TETile[w][h];

                for (int x = 0; x < w; x += 1) {
                    for (int y = 0; y < h; y += 1) {
                        world[x][y] = Tileset.NOTHING;
                    }
                }

                creator = new StringToWorld(seed, world);
                charX = creator.getCharX();
                charY = creator.getCharY();
                ter.renderFrame(world);
                roomHider(charX, charY);
                StdDraw.show();
                break;

            } else if (nextChar == 'Q') {
                tracker = tracker + nextChar;
                StdDraw.clear();
                StdDraw.show();
                return;
            } else if (nextChar == 'S') {
                tracker = tracker + nextChar;
                StdDraw.clear();
                StdDraw.enableDoubleBuffering();
                StdDraw.clear(Color.black);
                StdDraw.setPenColor(Color.white);
                StdDraw.setPenColor(Color.yellow);
                StdDraw.text(0.5, 0.9, "Alien Joe needs to save");
                StdDraw.text(0.5, 0.8, "his home planet!");
                StdDraw.text(0.5, 0.7, "Trillions are imperiled!");
                StdDraw.text(0.5, 0.6, "Hidden deep in the universe is a");
                StdDraw.text(0.5, 0.5, "Secret Star with the power to");
                StdDraw.text(0.5, 0.4, "resuscitate dying planet cores.");
                StdDraw.text(0.5, 0.3, "Navigate Joe and find it");
                StdDraw.text(0.5, 0.2, "before time runs out!");
                StdDraw.show();
                StdDraw.pause(5000);

                StdDraw.clear(Color.BLACK);
                StdDraw.square(0.3, 0.8, 0.06);
                StdDraw.square(0.3, 0.65, 0.06);
                StdDraw.square(0.45, 0.65, 0.06);
                StdDraw.square(0.15, 0.65, 0.06);

                StdDraw.text(0.3, 0.8, "W");
                StdDraw.text(0.3, 0.65, "S");
                StdDraw.text(0.45, 0.65, "D");
                StdDraw.text(0.15, 0.65, "A");
                StdDraw.text(0.8, 0.75, "W -- MOVE UP");
                StdDraw.text(0.8, 0.7, "S -- MOVE DOWN");
                StdDraw.text(0.8, 0.65, "D -- MOVE RIGHT");
                StdDraw.text(0.8, 0.6, "A -- MOVE LEFT");
                StdDraw.square(0.35, 0.4, 0.06);
                StdDraw.square(0.35, 0.25, 0.06);
                StdDraw.text(0.35, 0.4, ":");
                StdDraw.text(0.35, 0.25, "Q");
                StdDraw.text(0.7, 0.4, ":Q -- TO SAVE AND QUIT");
                StdDraw.text(0.2, 0.1, "MAIN MENU (M)");
                StdDraw.picture(0.82, 0.18, "byow/findStarInDark/alien.png");
                StdDraw.show();
                while (inputSource.possibleNextInput()) {
                    char c = inputSource.getNextKey();
                    if (c == 'M') {
                        interactWithKeyboard();
                    }
                }
            } else if (nextChar == 'L') {
                tracker = tracker + nextChar;

                String fullinput = saveWorldtoString();

                StdDraw.enableDoubleBuffering();
                world = interactWithInputString(fullinput);
                roomHider(charX, charY);
                StdDraw.show();
                int i = 0;
                while (i < fullinput.length() && fullinput.charAt(i) != ':') {
                    i++;
                }
                oldInput = fullinput.substring(0, i);
                ter.renderFrame(world);
                i = i + 2;
                String t = "";
                while (i < fullinput.length()) {
                    t = t + fullinput.charAt(i);
                    i++;
                }
                if (!t.isEmpty()) {
                    time = Double.valueOf(t);
                }
                break;
            }
        }
        timer = new Stopwatch();

        while (inputSource.possibleNextInput()) {
            char choice = inputSource.getNextKey();
            tracker = tracker + choice;
            if (timer.elapsedTime() + time > 30) {
                outcomeDisplay("LOSE");
            }
            if (choice == ':') {
                System.out.println(':');
                while (inputSource.possibleNextInput()) {
                    choice = inputSource.getNextKey();
                    if (choice == 'Q') {
                        tracker = tracker + choice;
                        StdDraw.clear();
                        StdDraw.show();
                        tracker = tracker + Double.toString(timer.elapsedTime());
                        save(oldInput + tracker);
                        return;

                    } else {
                        break;
                    }
                }
            } else {
                moveCharacter(choice);
                StdDraw.enableDoubleBuffering();
                StdDraw.clear();
                ter.renderFrame(world);
                roomHider(charX, charY);
                StdDraw.setPenColor(Color.white);
                StdDraw.setPenColor(Color.white);
                font2 = new Font("Futura Bold Italic", Font.PLAIN, 15);
                StdDraw.setFont(font2);
                roomHider(charX, charY);
                StdDraw.setPenColor(Color.white);
                StdDraw.text(3, HEIGHT - 1, "Time: " + timer.elapsedTime());
                StdDraw.text(9, HEIGHT - 1, world[(int) Math.round(StdDraw.mouseX())][(int) Math.round(StdDraw.mouseY())].description());
                StdDraw.show();


            }

        }


    }

    private String saveWorldtoString() {
        StringBuilder input = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("./save_data.txt"))) {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                input.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return input.toString();
    }

    private void moveCharacter(char choide) {

        switch (choide) {
            case 'W':

            case 'w':
                if ((world[charX][charY + 1].description().equals("wall"))) {
                    break;
                } else if (world[charX][charY + 1].description().equals("star")) {
                    outcomeDisplay("WON");
                    return;

                } else {
                    world[charX][charY + 1] = Tileset.AVATAR;
                    world[charX][charY] = Tileset.FLOOR;
                    charY += 1;
                    break;
                }

            case 'S':
            case 's':
                if ((world[charX][charY - 1].description().equals("wall"))) {
                    break;
                } else if (world[charX][charY - 1].description().equals("star")) {
                    outcomeDisplay("WON");
                    return;

                } else {
                    world[charX][charY - 1] = Tileset.AVATAR;
                    world[charX][charY] = Tileset.FLOOR;
                    charY -= 1;
                    break;
                }

            case 'D':
            case 'd':
                if ((world[charX + 1][charY].description().equals("wall"))) {
                    break;
                } else if (world[charX + 1][charY].description().equals("star")) {
                    outcomeDisplay("WON");
                    break;

                } else {
                    world[charX + 1][charY] = Tileset.AVATAR;
                    world[charX][charY] = Tileset.FLOOR;
                    charX += 1;
                    break;
                }
            case 'A':
            case 'a':
                if ((world[charX - 1][charY].description().equals("wall"))) {
                    break;
                } else if (world[charX - 1][charY].description().equals("star")) {
                    outcomeDisplay("WON");
                    break;

                } else {
                    world[charX - 1][charY] = Tileset.AVATAR;
                    world[charX][charY] = Tileset.FLOOR;
                    charX -= 1;
                    break;
                }
            default:
                break;
        }
    }

    private void roomHider(int charX1, int charY1) {
        int[] currentLocation = creator.currentRoom(charX1, charY1);
        if (currentLocation == null) {
            currentLocation = creator.currentHall(charX1, charY1);
        }

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(WIDTH / 2, HEIGHT, WIDTH, HEIGHT - currentLocation[0] - 1);
        StdDraw.filledRectangle(WIDTH / 2, 0, WIDTH, currentLocation[1]);
        StdDraw.filledRectangle(0, HEIGHT / 2, currentLocation[3], HEIGHT);
        StdDraw.filledRectangle(WIDTH, HEIGHT / 2, WIDTH - currentLocation[2] - 1, HEIGHT);
    }


    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param s: the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */

    private void outcomeDisplay(String s) {
        StdDraw.setCanvasSize();
        Stopwatch endanimation = new Stopwatch();
        int i = 25;
        int p = 10;
        int t = 0;
        while (t < 20) {
            StdDraw.enableDoubleBuffering();
            StdDraw.clear();
            StdDraw.clear(Color.BLACK);
            t++;
            StdDraw.setPenColor(Color.YELLOW);
            if (i > 90 || i < 25) {
                p = p * -1;
            }
            Font font = new Font("Futura Bold Italic", Font.BOLD, i);
            StdDraw.setFont(font);
            StdDraw.text(.5, .5, "YOU " + s);
            i = i + p;
            StdDraw.show();
            StdDraw.pause(100);
        }
        StdDraw.clear();
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.yellow);
        Font font = new Font("Futura Bold Italic", Font.BOLD, 50);
        StdDraw.setFont(font);
        StdDraw.text(.5, .8, "YOU " + s);
        Font font2 = new Font("Futura Bold Italic", Font.PLAIN, 20);
        StdDraw.setFont(font2);
        StdDraw.text(.5, .5, "PLAY AGAIN (P)");
        StdDraw.text(.5, .4, "QUIT (Q)");
        StdDraw.picture(0.5, 0.20, "byow/findStarInDark/UFO.png");
        StdDraw.show();
        KeyboardInputSource inputSource;
        inputSource = new KeyboardInputSource();
        while (inputSource.possibleNextInput()) {
            char nextChar = inputSource.getNextKey();
            if (nextChar == 'P') {
                double x = 0.5;
                double y = 0.2;
                double mover = 0.1;
                int timer = 0;
                while (timer < 13) {
                    timer++;
                    StdDraw.clear();
                    StdDraw.enableDoubleBuffering();
                    StdDraw.clear(Color.black);
                    StdDraw.picture(x, y, "byow/findStarInDark/UFO.png");
                    StdDraw.show();
                    y = y + mover;
                    if (y > 0.5) {
                        x = x + mover;
                    }
                    StdDraw.pause(100);

                }
                interactWithKeyboard();


            } else if (nextChar == 'Q') {
                return;
            }
        }


    }

    /**
     * @source: howtodoinjava.com for file creation and file reading
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        TERenderer ter = new TERenderer();
        /* Feel free to change the width and height. */
        int W = 80;
        int H = 30;
        StdDraw.enableDoubleBuffering();
        ter.initialize(WIDTH, HEIGHT);

        world = new TETile[W][H];


        for (int x = 0; x < W; x += 1) {
            for (int y = 0; y < H; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        int i = 0;
        String seed = "";
        if (i < input.length() && (input.charAt(0) == 'L' || input.charAt(0) == 'l')) {
            char s = 'S';
            while (i < saveWorldtoString().length() && (saveWorldtoString().charAt(i) != s && saveWorldtoString().charAt(i) != 's')) {
                seed = seed + saveWorldtoString().charAt(i);
                i++;

            }
            seed = seed + s;
            creator = new StringToWorld(seed, world);


            charX = creator.getCharX();
            charY = creator.getCharY();
            i++;

            char q = ':';
            while (i < saveWorldtoString().length() && saveWorldtoString().charAt(i) != q) {

                char choice = saveWorldtoString().charAt(i);

                moveCharacter(choice);

                i++;
            }
            String prev = saveWorldtoString().substring(0, i);


            i = i + 2;
            String t = "";
            while (i < saveWorldtoString().length() - 1) {
                t = t + saveWorldtoString().charAt(i);
                i++;
            }
            if (t != "") {
                time = Double.valueOf(t);
            }
            i = 0;
            while (i < input.length() && input.charAt(i) != q) {
                char choice = input.charAt(i);

                moveCharacter(choice);
                i++;
            }
            save(prev + input.substring(1));

        }
        if (i < input.length() && (input.charAt(0) == 'N' || input.charAt(0) == 'n')) {
            char s = 'S';
            char smalls = 's';
            while (input.charAt(i) != s && input.charAt(i) != smalls) {
                seed = seed + input.charAt(i);
                i++;

            }
            seed = seed + s;
            creator = new StringToWorld(seed, world);


            charX = creator.getCharX();
            charY = creator.getCharY();
            i++;
            char q = ':';
            while (i < input.length() && input.charAt(i) != q) {
                char choice = input.charAt(i);

                moveCharacter(choice);

                i++;
            }
            save(input);

            while (i < input.length() && (input.charAt(i) != 'l' && input.charAt(i) != 'L')) {
                i++;


            }

            if (i < input.length() && (input.charAt(i) == 'l' || input.charAt(i) != 'L')) {
                save(input.substring(0, i));
                if (i != input.length() - 1) {
                    String track = input.substring(i, input.length());
                    return interactWithInputString(track);
                }
            }


        }

        ter.renderFrame(world);
        StdDraw.show();


        return world;

    }

    public void save(String input) {
        File f = new File("./save_data.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter writer = new FileWriter(f);
            writer.write(input);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    private class KeyboardInputSource {
        private static final boolean PRINT_TYPED_KEYS = false;

        public KeyboardInputSource() {
        }

        public char getNextKey() {
            while (true) {
                if (StdDraw.hasNextKeyTyped()) {
                    char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                    return c;
                }
            }
        }

        public boolean possibleNextInput() {
            return true;
        }


    }


}




