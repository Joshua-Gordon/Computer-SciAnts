import java.awt.*;

public class Main {

    public static final int speed = 1;

    public static void main(String[] args) {
        Grid g = new Grid(32);
        g.activate();
        g.render();
        g.update();
        g.update();
        g.addQueen(5, 3, 100, Color.BLUE);
        g.addQueen(28,15, 100, Color.RED);
        g.addQueen(14, 18, 100, Color.GREEN);
        g.addRandomFood();
        g.addRandomFood();
        g.addRandomFood();
        long dt = 0;
        long now;
        while(true) {
            now = System.currentTimeMillis();
            g.render();
            dt += System.currentTimeMillis() - now;
            if(dt >= speed) {
                g.update();
                dt = 0;
            }
        }
    }

}
