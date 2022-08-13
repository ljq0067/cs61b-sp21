package byow.findFriends.Core;

import edu.princeton.cs.introcs.StdDraw;
import java.util.concurrent.TimeUnit;
import byow.findFriends.TileEngine.TERenderer;

public class InputKey implements Inputs {

    private TERenderer ter;

    public InputKey(TERenderer ter) {
        this.ter = ter;
    }

    public char getNextKey() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                c = Character.toUpperCase(c);
                return c;
            } else {
                try {
                    TimeUnit.SECONDS.sleep((long) .01);
                    ter.updateHover(((int) StdDraw.mouseX()) + "_" + ((int) StdDraw.mouseY()));
                } catch (NullPointerException | InterruptedException ignored) {

                }
            }
        }
    }

    public boolean possibleNextInput() {
        return true;
    }
}
