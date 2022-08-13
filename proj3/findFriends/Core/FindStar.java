package findFriends.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.findStarInDark.StringToWorld;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.Stopwatch;

import java.awt.*;

public class FindStar {

    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    private static TETile[][] world;
    private int charX;
    private int charY;
    private StringToWorld creator;

    private boolean result;


    public int interactWithInputString(String seed, int currentHealth) {

        while (!result) {
            StringBuilder tracker = new StringBuilder();
            double time = 0.0;
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
            creator = new StringToWorld(seed, world);
            charX = creator.getCharX();
            charY = creator.getCharY();
            ter.renderFrame(world);
            roomHider(charX, charY);
            StdDraw.show();

            KeyboardInputSource inputSource;
            inputSource = new KeyboardInputSource();
            Stopwatch timer = new Stopwatch();
            while (inputSource.possibleNextInput()) {
                char choice = inputSource.getNextKey();
                tracker.append(choice);
                if (timer.elapsedTime() + time > 30) {
                    // lose
                    result = true;
                    break;
                }
                result = moveCharacter(choice);
                if (result) {
                    currentHealth += 1;
                    break;
                }

                StdDraw.enableDoubleBuffering();
                StdDraw.clear();
                ter.renderFrame(world);
                roomHider(charX, charY);
                StdDraw.setPenColor(Color.white);
                StdDraw.setPenColor(Color.white);
                Font font2 = new Font("Futura Bold Italic", Font.PLAIN, 15);
                StdDraw.setFont(font2);
                roomHider(charX, charY);
                StdDraw.setPenColor(Color.white);
                StdDraw.text(3, HEIGHT - 1, "Time: " + timer.elapsedTime());
                StdDraw.text(9, HEIGHT - 1, world[(int) Math.round(StdDraw.mouseX())][(int) Math.round(StdDraw.mouseY())].description());
                StdDraw.show();
            }
        }
        return currentHealth;
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

    private boolean moveCharacter(char choide) {

        boolean outcome = false;
        switch (choide) {
            case 'W':

            case 'w':
                if ((world[charX][charY + 1].description().equals("wall"))) {
                    break;
                } else if (world[charX][charY + 1].description().equals("star")) {
                    //outcomeDisplay("WON");
                    outcome = true;
                    break;

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
                    //outcomeDisplay("WON");
                    outcome = true;
                    break;

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
                    //outcomeDisplay("WON");
                    outcome = true;
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
                    //outcomeDisplay("WON");
                    outcome = true;
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
        return outcome;
    }
/*
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
        StdDraw.show();
    }
*/
    private class KeyboardInputSource {

        public KeyboardInputSource() {
        }

        public char getNextKey() {
            while (true) {
                if (StdDraw.hasNextKeyTyped()) {
                    return Character.toUpperCase(StdDraw.nextKeyTyped());
                }
            }
        }

        public boolean possibleNextInput() {
            return true;
        }

    }

}
