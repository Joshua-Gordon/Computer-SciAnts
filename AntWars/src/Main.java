import java.awt.*;

public class Main {

    public static final int speed = 100;

    public static void main(String[] args) {
        Grid g = new Grid(32);
        g.activate();
        g.render();
        g.update();
        g.update();
        g.addQueen(5, 3, Color.BLUE);
        g.addQueen(28,15,Color.RED);
        ((Queen)(g.entities[28][15])).giveFood(100);
        ((Queen)(g.entities[5][3])).giveFood(100);
        g.addFood(11,11,100);
        g.addFood(15,8,500);
        g.addFood(14, 5, 1000);

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
