import java.awt.*;
import java.util.Random;

public class Queen extends Entity {

    Random rand;
    double food;
    Color team;

    public Queen(int x, int y, int food, Color team) {
        super(x, y, "AntWars/res/queen.png");
        rand = new Random();
        this.food = food;
        this.team = team;
        sprite = Utilities.colorImage(sprite,team);
    }



    @Override
    public void update(Grid grid) {
        if(food <= 0) die(grid);
        if(food > 40) {
            if(rand.nextInt(10) < 7) {
                boolean success = grid.addAnt(x + rand.nextInt(2)-1, y + rand.nextInt(2)-1,team, new int[]{x,y});
            } else {
                SoldierAnt sa = new SoldierAnt(x + rand.nextInt(2)-1, y + rand.nextInt(2)-1, team, new int[]{x,y},(int)(30 + rand.nextGaussian()*10), rand.nextBoolean());
            }
        }

        food -= 0.25;
    }


    public void giveFood(int amount) {
        food += amount;
    }
}
