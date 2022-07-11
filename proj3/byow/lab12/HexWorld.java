package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);


    /**
     * find the location to put hexagon
     */
    private static class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position shift(int dx, int dy) {
            return new Position(this.x + dx, this.y + dy);
        }
    }

    /**
     * Draw a row of tiles to the board, anchored at a given position.
     */

    public static void drawRow(TETile[][] tiles, Position p, TETile tile, int length) {
        for (int dx = 0; dx < length; dx++) {
            tiles[p.x + dx][p.y] = tile;
        }
    }

    /**
     * Helper method for addHexagon
     * b is the start position of each row, l is the length of tile for each row
     * Hexagon is like: p=(0,0), b=1, l=2, and positive direction is up and right.
       pb
        **
       ****
       ****
        **
     */
    public static void addHexagonHelper(TETile[][] tiles, Position p, TETile tile, int b, int l) {
        /* draw the first row at position b with length l */
        Position startOfRow = p.shift(b, 0);
        drawRow(tiles, startOfRow, tile, l);

        /* draw middle rows recursively */
        if (b > 0) {
            Position nextP = p.shift(0, -1);
            addHexagonHelper(tiles, nextP, tile, b - 1, l + 2);
        }

        /* draw this same row again to be reflection */
        Position startOfReflectedRow = startOfRow.shift(0, -(2 * b + 1));
        drawRow(tiles, startOfReflectedRow, tile, l);
    }

    /**
     * Adds a hexagon to the world at position p of size
     */
    public static void addHexagon(TETile[][] tiles, Position p, TETile t, int size) {
        if (size < 2) {
            return;
        }

        addHexagonHelper(tiles, p, t, size - 1, size);
    }

    /**
     * Get the position of the bottom neighbor of a hexagon with size n at position p.
     * N is the size of the hexagons we just put.
     */
    public static Position getBottomNeighbor(Position p, int n) {
        return p.shift(0, -2 * n);
    }


    /**
     * Adds a column of NUM hexagons, each of whose biomes are chosen randomly at position P.
     * Each of the hexagons are of SIZE.
     */
    public static void addHexColumn(TETile[][] tiles, Position p, int size, int num) {
        if (num < 1) return;

        /* Draw this hexagon */
        addHexagon(tiles, p, randomTile(), size);

        /* Draw n-1 hexagons below it */
        if (num > 1) {
            Position bottomNeighbor = getBottomNeighbor(p ,size);
            addHexColumn(tiles, bottomNeighbor, size, num - 1);
        }
    }

    /**
     * Fills the given 2D array of tiles with blank tiles.
     */
    public static void fillBoardWithNothing(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Picks a RANDOM tile a wall chance of being empty space.
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(8);
        switch (tileNum) {
            case 0:
                return Tileset.COIN;
            case 1:
                return Tileset.TREE;
            case 2:
                return Tileset.FLOOR;
            case 3:
                return Tileset.WATER;
            case 4:
                return Tileset.GRASS;
            case 5:
                return Tileset.SAND;
            case 6:
                return Tileset.MOUNTAIN;
            default:
                return Tileset.WALL;
        }
    }

    /**
     * get the start position of next column
     * the first hexagon of next column is top right of this column
     * @param p: the start position of current column
     * @param s: the size of hexagon
     */
    private static Position getTopRightNeighbor(Position p, int s) {
        return p.shift(2 * s - 1, s);
    }

    /**
     * @param p: the start position of current column
     * @param s: the size of hexagon
     * @param d: distance between reflected column and current column
     * @return the start position of reflected column
     */
    private static Position getReflectedColumn(Position p, int s, int d) {
        return p.shift(d * (2 * s - 1), 0);
    }

    /**
     *  Draw the hexagonal world:
     *  Each hexagon has size hexSize.
     *  The first column has colSize hexagons and increase one each time to middle and then decrease.
     *  The world is reflected with total (2 * colSize - 1) columns.
     *  The i-th column has (colSize + i) hexagons where i is from 0 (first) to colSize - 1 (middle).
     *  The reflected column of i-th column is (2 * colSize - 2 - i).
     */
    public static void drawWorld(TETile[][] tiles, Position p, int hexSize, int colSize) {

        /* Draw the hexagon column and its reflected column */
        for (int i = 0; i < colSize - 1; i++) {
            addHexColumn(tiles, p, hexSize, colSize + i);
            Position reflectedColumn = getReflectedColumn(p, hexSize, 2 * (colSize - 1 - i));
            addHexColumn(tiles, reflectedColumn, hexSize, colSize + i);
            p = getTopRightNeighbor(p, hexSize);
        }

        /* Draw the middle hexagon column */
        addHexColumn(tiles, p, hexSize, 2 * colSize - 1);
    }

    public static void main(String[] args) {
        /* initialize the tile rendering engine with a window of size WIDTH x HEIGHT */
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        /* initialize tiles */
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        fillBoardWithNothing(world);
        Position p = new Position(5, 35);
        drawWorld(world, p, 3, 4);

        /* draws the world to the screen */
        ter.renderFrame(world);
    }


}
