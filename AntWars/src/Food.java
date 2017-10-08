import java.awt.*;

public class Food extends Entity implements InfoSharing{

    private int amount;
    private int[][] here;

    public Food(int x, int y, int amount) {
        super(x, y, "res/nugs.png");
        this.amount = amount;
        here = new int[Grid.spaces.length][Grid.spaces[0].length];
        for(int x0 = 0; x0 < here.length; x0++) {
            for(int y0 = 0; y0 < here[0].length; y0++) {
                here[x0][y0] = -1;
                /*
                -1 means unknown
                0 means no food
                1 means food
                 */
            }
        }
        here[x][y] = 1;
    }

    @Override
    public void update(Grid g) {

    }

    public int takeFood(Ant a) {
        if(amount - a.FOOD_TAKEN_AMOUNT <= 0) {

            //System.out.println(amount);
            Grid.entities[x][y] = null;
            Grid.spaces[x][y] = false;
            return amount;
        }
        amount -= a.FOOD_TAKEN_AMOUNT;
        return a.FOOD_TAKEN_AMOUNT;
    }

    @Override
    public Color getTeam() {
        return Color.BLACK;
    }

    @Override
    public void shareInfo(InfoSharing other) {

    }

    @Override
    public int[][] getInfo() {
        return here;
    }

    @Override
    public void scan(Grid g) {

    }

    public String toString() {
        return "Nug: (" + x + "," + y + ")";
    }
}
