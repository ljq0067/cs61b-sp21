package byow.findStarInDark;


import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.Random;


public class StringToWorld {
    private ArrayList<room> Rooms;
    private ArrayList<room> RoomsCheck;
    private ArrayList<hallway> Halls;
    private Long Seed;
    private TETile[][] world;
    private Random RANDOM;
    private int height;
    private int width;
    private int charX;
    private int charY;


    public StringToWorld(String seed, TETile[][] world2) {
        this.Seed = Long.parseLong(seed.substring(1, seed.length() - 1));
        world = world2;
        Rooms = new ArrayList<>();
        Halls = new ArrayList<>();
        RANDOM = new Random(Seed);
        height = world[0].length;
        width = world.length;
        int y = RANDOM.nextInt(height);
        int x = RANDOM.nextInt(width);
        room FirsRoom = new room(y + RANDOM.nextInt(height / 6) + 3, y - RANDOM.nextInt(height / 6), x + RANDOM.nextInt(width / 6) + 3, x - RANDOM.nextInt(width / 6) - 3, 4, 0);
        RoomsCheck = new ArrayList<>();
        while (!(roomarraychecker(FirsRoom) && hallwayarraychekcer(FirsRoom))) {
            y = RANDOM.nextInt(height);
            x = RANDOM.nextInt(width);
            FirsRoom = new room(y + RANDOM.nextInt(height / 6) + 3, y - RANDOM.nextInt(height / 6) - 3, x + RANDOM.nextInt(width / 6) + 3, x - RANDOM.nextInt(width / 6) - 3, 4, 0);
        }
        roomBuild(FirsRoom, -2, 0);
        Rooms.add(FirsRoom);
        RoomsCheck.add(FirsRoom);
        charX = FirsRoom.xLeft + ((FirsRoom.xRight - FirsRoom.xLeft) / 2);
        charY = FirsRoom.yBottom + ((FirsRoom.yTop - FirsRoom.yBottom) / 2);
        world[charX][charY] = Tileset.AVATAR;
        int i = 0;
        while (!Rooms.isEmpty() && i < 200000) {
            i++;
            int index = RANDOM.nextInt(Rooms.size());
            room current = Rooms.get(index);
            hallway currenthall = generateRandomHall(current, index);
            if (currenthall == null) {
                continue;
            }
            int random = RANDOM.nextInt(4);
            if (random <= 3) {
                BuildRoom(currenthall);
            }

        }
        if(RoomsCheck.size() == 1){
            hallway coin = Halls.get(RANDOM.nextInt(Halls.size()));
            int xCoin = coin.xLeft + ((coin.xRight - coin.xLeft) / 2);
            int yCoin = coin.yBottom + ((coin.yTop - coin.yBottom) / 2);
            world[xCoin][yCoin] = Tileset.STAR;
        }
        else {
            room coin = RoomsCheck.get(RANDOM.nextInt(RoomsCheck.size()));
            while (coin == FirsRoom) {
                coin = RoomsCheck.get(RANDOM.nextInt(RoomsCheck.size()));
            }
            int xCoin = coin.xLeft + ((coin.xRight - coin.xLeft) / 2);
            int yCoin = coin.yBottom + ((coin.yTop - coin.yBottom) / 2);
            world[xCoin][yCoin] = Tileset.STAR;

        }




    }

    /* Room Class, contains all the information about a room */
    private class room {
        public int yTop; /* max y value of room */
        public int yBottom; /* lowest y value of room */
        public int xLeft; /* lowest x value of room */
        public int xRight; /* highest x value of room */
        public int maxNumHall; /* random generated int of the max number of hallways coming of room */
        public int takenHalls; /* number of current hallways coming out of room */
        public boolean[] Halls;

        public room(int yt, int yb, int xr, int xl, int mh, int th) {
            yTop = yt;
            yBottom = yb;
            xRight = xr;
            xLeft = xl;
            maxNumHall = mh;
            takenHalls = th;
            Halls = new boolean[4];
        }

    }

    /* Hallway class is similar to room but adds directions of the
    hallway, and makes the code easier to understand */
    private class hallway {
        public int yTop; /* max y value of hall */
        public int yBottom; /* lowest y value of hall */
        public int xLeft; /* lowest x value of hall */
        public int xRight; /* highest x value of hall */
        private int direction;

        public hallway(int yt, int yb, int xr, int xl, int d) {
            yTop = yt;
            yBottom = yb;
            xRight = xr;
            xLeft = xl;
            direction = d;
        }


    }

    /* takes in a room or a Hallways and adds the tiles into the world */
    private void roomBuild(room str, int Xhall, int Yhall) {
        for (int x = str.xLeft; x <= str.xRight; x++) {
            for (int y = str.yBottom; y <= str.yTop; y++) {
                if ((x == str.xLeft) || (x == str.xRight) || y == str.yBottom || y == str.yTop) {
                    world[x][y] = Tileset.WALL;
                } else {
                    world[x][y] = Tileset.SAND;
                }
            }

        }
        if (Xhall < 0) {
            return;
        }
        world[Xhall][Yhall] = Tileset.LOCKED_DOOR;

    }

    private void hallbuild(hallway str, int Xhall, int Yhall) {
        for (int x = str.xLeft; x <= str.xRight; x++) {
            for (int y = str.yBottom; y <= str.yTop; y++) {
                if ((x == str.xLeft) || (x == str.xRight) || y == str.yBottom || y == str.yTop) {
                    world[x][y] = Tileset.WALL;
                } else {
                    world[x][y] = Tileset.SAND;
                }
            }
        }
        world[Xhall][Yhall] = Tileset.LOCKED_DOOR;

    }


    /* iterates through room array and hallway array and ensures rooms do not overlap before building*/
    /* iterates through room array and hallway array and ensures rooms do not overlap before building*/
    public void BuildRoom(hallway Hallway) {
        for (int i = 0; i < 3; i++) {
            room builtr = new room(Hallway.yBottom - 1, Hallway.yBottom - RANDOM.nextInt(height / 8) - 3, Hallway.xRight + RANDOM.nextInt(width / 8) + 3, Hallway.xLeft - RANDOM.nextInt(width / 8) - 3, 4, 1);
            int x = Hallway.xLeft + 1;
            int y = Hallway.yBottom;
            int xhall = Hallway.xLeft + 1;
            int yhall = Hallway.yBottom - 1;
            int hallindex = 0;
            if (Hallway.direction != 1) {

                if (Hallway.direction == 0) {
                    builtr = new room((Hallway.yTop + RANDOM.nextInt(height / 8)) + 4, Hallway.yTop + 1, (Hallway.xRight + RANDOM.nextInt(width / 6)) + 3, Hallway.xLeft - RANDOM.nextInt(width / 8) - 3, 4, 1);
                    x = Hallway.xLeft + 1;
                    y = Hallway.yTop;
                    xhall = Hallway.xLeft + 1;
                    yhall = Hallway.yTop + 1;
                    hallindex = 1;

                } else if (Hallway.direction == 2) {
                    builtr = new room(Hallway.yTop + RANDOM.nextInt(height / 8) + 3, Hallway.yBottom - RANDOM.nextInt(height / 8) - 3, Hallway.xRight + RANDOM.nextInt(width / 8) + 4, Hallway.xRight + 1, 4, 1);
                    x = Hallway.xRight;
                    y = Hallway.yBottom + 1;
                    xhall = Hallway.xRight + 1;
                    yhall = Hallway.yTop - 1;
                    hallindex = 3;
                } else {
                    builtr = new room(Hallway.yTop + RANDOM.nextInt(height / 8) + 3, Hallway.yBottom - RANDOM.nextInt(height / 8) - 3, Hallway.xLeft - 1, Hallway.xLeft - RANDOM.nextInt(width / 8) - 4, 4, 1);
                    x = Hallway.xLeft;
                    y = Hallway.yBottom + 1;
                    xhall = Hallway.xLeft - 1;
                    yhall = Hallway.yBottom + 1;
                    hallindex = 2;
                }
            }

            if (roomarraychecker(builtr) && hallwayarraychekcer(builtr)) {
                builtr.Halls[hallindex] = true;
                roomBuild(builtr, x, y);
                world[xhall][yhall] = Tileset.LOCKED_DOOR;
                RoomsCheck.add(builtr);
                Rooms.add(builtr);
                return;
            }


        }

    }


    public boolean roomarraychecker(room builtr) {
        if (!(builtr.xLeft >= 0) || !(builtr.xRight <= width - 1) || (!(builtr.yBottom >= 0) || !(builtr.yTop < height - 1))) {
            return false;
        }
        for (int l = 0; l < RoomsCheck.size(); l++) {
            room arrayr = RoomsCheck.get(l);
            if (arrayr.yTop < builtr.yBottom
                    || arrayr.yBottom > builtr.yTop) {
                continue;

            }
            if (arrayr.xRight < builtr.xLeft
                    || arrayr.xLeft > builtr.xRight) {
                continue;
            } else {
                return false;

            }

        }
        return true;

    }

    public boolean hallwayarraychekcer(room builtr) {
        for (int a = 0; a < Halls.size(); a++) {
            hallway h = Halls.get(a);
            if (h.yTop < builtr.yBottom
                    || h.yBottom > builtr.yTop) {
                continue;

            }
            if (h.xRight < builtr.xLeft
                    || h.xLeft > builtr.xRight) {
                continue;
            } else {
                return false;

            }

        }
        return true;
    }

    public boolean roomarraychecker(hallway h) {
        if (!(h.xLeft >= 0) || !(h.xRight <= width - 1) || (!(h.yBottom >= 0) || !(h.yTop < height - 1))) {
            return false;
        }
        boolean val = true;
        for (int l = 0; l < RoomsCheck.size(); l++) {
            room arrayr = RoomsCheck.get(l);
            if (h.yTop < arrayr.yBottom
                    || h.yBottom > arrayr.yTop) {
                continue;

            }
            if (h.xRight < arrayr.xLeft
                    || h.xLeft > arrayr.xRight) {
                continue;
            } else {
                return false;

            }

        }
        return true;

    }

    public boolean hallwayarraychekcer(hallway hall) {
        if (!(hall.xLeft >= 0) || !(hall.xRight <= width - 1) || (!(hall.yBottom >= 0) || !(hall.yTop < height - 1))) {
            return false;
        }
        for (int a = 0; a < Halls.size(); a++) {
            hallway h_ar = Halls.get(a);
            if (hall.yTop < h_ar.yBottom
                    || hall.yBottom > h_ar.yTop) {
                continue;

            }
            if (hall.xRight < h_ar.xLeft
                    || hall.xLeft > h_ar.xRight) {
                continue;
            } else {
                return false;

            }

        }
        return true;
    }


    private hallway generateRandomHall(room str, int indexofArray) {
        if (str.takenHalls == str.maxNumHall) {
            Rooms.remove(indexofArray);
            return null;
        }
        int i = RANDOM.nextInt(4);
        boolean occupied = str.Halls[i];
        hallway h = null;
        int timer = 0;
        while (occupied && timer < 40 ) {
            i = RANDOM.nextInt(4);
            occupied = str.Halls[i];
            timer++;

        }
        switch (i) {
            case 0:
                h = verticalHall(str, 0, 0);
                break;
            case 1:
                h = verticalHall(str, 1, 0);
                break;
            case 2:
                h = horizontalHall(str, 2, 0);
                break;
            case 3:
                h = horizontalHall(str, 3, 0);
                break;


        }
        return h;
    }

    private hallway verticalHall(room str, int direction, int tries) {

        hallway h;
        if (tries > 3) {
            return null;
        }
        int middle = 0;
        if (str.xRight - str.xLeft == 2) {
            middle = 1;
        } else {
            middle = RANDOM.nextInt(str.xRight - str.xLeft);
            while (middle == 0 || (middle + str.xLeft == str.xRight))
                middle = RANDOM.nextInt(str.xRight - str.xLeft);
        }


        if (direction == 0) {
            h = new hallway(str.yTop + RANDOM.nextInt(height / 8) + 3, str.yTop + 1, str.xLeft + middle + 1, str.xLeft + middle - 1, 0);
        } else {
            h = new hallway(str.yBottom - 1, str.yBottom - RANDOM.nextInt(height / 8) - 3, str.xLeft + middle + 1, str.xLeft + middle - 1, 1);
        }

        if (hallwayarraychekcer(h) && roomarraychecker(h)) {
            Halls.add(h);
            if (h.direction == 0) {
                hallbuild(h, h.xLeft + 1, str.yTop + 1);
                world[h.xLeft + 1][str.yTop] = Tileset.LOCKED_DOOR;
                str.takenHalls += 1;
                str.Halls[direction] = true;
                return h;
            } else {
                hallbuild(h, h.xLeft + 1, str.yBottom - 1);
                world[h.xLeft + 1][str.yBottom] = Tileset.LOCKED_DOOR;
                str.takenHalls += 1;
                str.Halls[direction] = true;
                return h;
            }


        }
        return verticalHall(str, direction, tries + 1);

    }


    private hallway horizontalHall(room str, int direction, int tries) {
        hallway h;
        if (tries > 3) {
            return null;
        }
        int middle;
        if (str.yTop - str.yBottom == 2) {
            middle = 1;
        } else {
            middle = RANDOM.nextInt(str.yTop - str.yBottom);
            while (middle == 0 || (middle + str.yBottom == str.yTop))
                middle = RANDOM.nextInt(str.yTop - str.yBottom);
        }


        if (direction == 2) {
            h = new hallway(str.yBottom + middle + 1, str.yBottom + middle - 1, str.xRight + RANDOM.nextInt(width / 8) + 3, str.xRight + 1, 2);
        } else {
            h = new hallway(str.yBottom + middle + 1, str.yBottom + middle - 1, str.xLeft - 1, str.xLeft - ((RANDOM.nextInt(width / 8) + 3)), 3);
        }

        if (hallwayarraychekcer(h) && roomarraychecker(h)) {
            Halls.add(h);
            if (h.direction == 2) {
                hallbuild(h, str.xRight + 1, h.yBottom + 1);
                world[str.xRight][h.yBottom + 1] = Tileset.LOCKED_DOOR;
                str.takenHalls += 1;
                str.Halls[direction] = true;
                return h;

            } else {
                hallbuild(h, str.xLeft - 1, h.yBottom + 1);
                world[str.xLeft][h.yBottom + 1] = Tileset.LOCKED_DOOR;
                str.takenHalls += 1;
                str.Halls[direction] = true;
                return h;


            }

        } else {
            return horizontalHall(str, direction, tries + 1);
        }


    }

    public TETile[][] world() {
        return world;
    }
    public int getCharX(){
        return charX;
    }
    public int getCharY(){
        return charY;
    }
    public ArrayList getRooms(){
        return RoomsCheck;
    }
    public ArrayList getHalls(){
        return Halls;
    }

    public int[] currentRoom(int x,int y){
        room current = null;

        for (room e:RoomsCheck){
            if ( x < e.xLeft || x > e.xRight){
                continue;
            }
            else if ( y < e.yBottom || y > e.yTop) {
                continue;
            } else {
                current = e;
            }

        }
        if (current == null){
            return null;
        }
        int[] room = new int[4];
        room[0] = current.yTop;
        room[1] = current.yBottom;
        room[2] = current.xRight;
        room[3] = current.xLeft;
        return room;
    }
    public int[] currentHall(int x,int y){
        hallway current = null;

        for (hallway e:Halls){
            if ( x < e.xLeft || x > e.xRight){
            }
            else if ( y < e.yBottom || y > e.yTop) {
            } else {
                current = e;
            }

        }
        if (current == null){
            return null;
        }
        int[] hal = new int[4];
        hal[0] = current.yTop;
        hal[1] = current.yBottom;
        hal[2] = current.xRight;
        hal[3] = current.xLeft;
        return hal;
    }




}

