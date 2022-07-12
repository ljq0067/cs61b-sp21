package byow.Core;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byow.Core.Engine class take over
 *  in either keyboard or input string mode.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 2) {
            System.out.println("Can only have two arguments - the flag and input string");
            System.exit(0);
        } else if (args.length == 2 && args[0].equals("-s")) {
            Engine engine = new Engine();
            engine.interactWithInputString(args[1]);
            System.out.println(engine.toString());
        // DO NOT CHANGE THESE LINES YET ;)
        } else if (args.length == 2 && args[0].equals("-p")) { System.out.println("Coming soon."); }
        // DO NOT CHANGE THESE LINES YET ;)
        else {
            Engine engine = new Engine();
            engine.interactWithKeyboard();
        }
    }
}


/**
 * Collect all the coins randomly in the rooms.
 * you can dodge the enemy to avoid touch it. but there has some special enemies you must beat.
 * If you meet enemy, you need find the star in the dark map with limited time to beat it.(similar to proj3-2)
 * You have several lives and will lose one heart each time you lose the change to enemy.
 *
 * OPTIONAL:
 * you may find stairs to go upstairs to another floor to next level but required you to collect all coins first.
 * you need collect ax/chopper first to cut down trees and chopper has limited times to use.
 * you need find boat to across the water to get into another room.
 *
 */