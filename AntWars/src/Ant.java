import java.awt.*;
import java.util.Random;

public class Ant extends Entity implements InfoSharing{

    Color team;
    public final int FOOD_TAKEN_AMOUNT = 5;
    public final int ANT_LIFESPAN = 100;
    int life = 0;


    int foodCarried;
    int carryCapacity;
    int[] closestFood;
    int[] queenLocation;

    private int[][] foodLocations;

    public Ant(int x, int y, Color team, int[] queenLocation) {
        super(x,y,0,"res/ant.png");
        this.team = team;
        carryCapacity = 15;
        this.queenLocation = queenLocation;
        init();
    }
    public Ant(int x, int y, Color team, int maxCarry, int[] queenLocation) {
        super(x,y,0,"res/ant.png");
        this.team = team;
        carryCapacity = maxCarry;
        this.queenLocation = queenLocation;
        init();
    }

    private void init() {
        sprite = Utilities.colorImage(sprite,team);
        foodCarried = 0;
        closestFood = null;
        foodLocations = new int[Grid.spaces.length][Grid.spaces.length];
        for(int x = 0; x < foodLocations.length; x++) {
            for(int y = 0; y < foodLocations[0].length; y++) {
                foodLocations[x][y] = -1;
                /*
                -1 means unknown
                0 means no food
                1 means food
                 */
            }
        }
    }


    @Override
    public void update(Grid grid) {
        life++;
        if(life > ANT_LIFESPAN) {
            die(grid);
        }
        if(closestFood == null) {
            moveRandomly(grid);
            return;
        }
        if(foodCarried < carryCapacity - FOOD_TAKEN_AMOUNT) {

                moveTowardPoint(grid, closestFood[0],closestFood[1]);

                if(mag(new int[]{closestFood[0]-x,closestFood[1]-y}) < 2) {
                    try {
                        Food nug = ((Food) Grid.entities[closestFood[0]][closestFood[1]]);
                        foodCarried += nug.takeFood(this);
                        //System.out.println(nug.toString());
                    } catch (NullPointerException npe) {
                        foodLocations[closestFood[0]][closestFood[1]] = 0;
                        updateFoodInfo(foodLocations);
                    } catch (ClassCastException cce) {
                        foodLocations[closestFood[0]][closestFood[1]] = 0;
                        updateFoodInfo(foodLocations);
                    }
                }
        } else {
            System.out.println(foodCarried);
            moveTowardPoint(grid, queenLocation[0],queenLocation[1]);
            if(mag(new int[]{queenLocation[0]-x,queenLocation[1]-y}) < 2) {
                try {
                    ((Queen) Grid.entities[queenLocation[0]][queenLocation[1]]).giveFood(foodCarried);
                    foodCarried = 0;
                } catch (NullPointerException npe) {
                    moveRandomly(grid);
                } catch (ClassCastException cce) {
                    moveRandomly(grid);
                }
            }
        }
        /*for(int x = 0; x < foodLocations.length; x++) {
            for(int y = 0; y < foodLocations[0].length; y++) {

            }
        }*/
    }

    public void moveTowardPoint(Grid g, int xf, int yf) {
        try {
            int dx = xf-x;
            int dy = yf-y;
            boolean success = super.move((int)Math.signum(dx), (int)Math.signum(dy), g);
            //System.out.println("Trying to move forward: " + success);
            if(!success){
                moveRandomly(g);
                //System.err.println("Can't move forward!");
            }
        } catch(ArithmeticException ae) {
            //System.err.println("Can't move forward. Randoming now");
            moveRandomly(g);
        }
    }

    public void moveRandomly(Grid grid) {
        Random rand = new Random();
        super.move(rand.nextInt(3)-1,rand.nextInt(3)-1, grid);
    }

    public Color getTeam() {
        return team;
    }

    @Override
    public void scan(Grid g) {
        for(int x0 = -1; x0 < 2; x0++) {
            for(int y0 = -1; y0 < 2; y0++) {
                try{
                    if(x0 != 0 && y0 != 0 && g.entities[x0 + x][y0 + y] instanceof InfoSharing) {
                        //System.out.println("Found something!");
                        shareInfo((InfoSharing)g.entities[x0 + x][y0 + y]);
                    }
                    //System.out.println("x0: " + x0 + "\ny0: " + y0);
                } catch (ArrayIndexOutOfBoundsException aioobe) {

                }
            }
        }
    }

    @Override
    public void shareInfo(InfoSharing other) {
        //Gotta prevent Ant Fake News
        updateFoodInfo(other.getInfo());
    }

    /**
     * Ants do not currently know how to forget about food other than dying
     * @param info
     */
    public void updateFoodInfo(int[][] info) {
        //System.out.println("Updating food info!");
        for(int x = 0; x < info.length; x++) {
            for(int y = 0; y < info[0].length; y++) {
                if(foodLocations[x][y] == -1) {
                    foodLocations[x][y] = info[x][y];
                } else if (foodLocations[x][y] == 1 && info[x][y] == 0) {
                    foodLocations[x][y] = 0;
                }
            }
        }
        boolean knownFood = false;
        for(int x0 = 0; x0 < info.length; x0++) {
            for(int y0 = 0; y0 < info[0].length; y0++) {
                if(foodLocations[x0][y0] == 1) {
                    knownFood = true;
                    //System.out.println("Food found");
                    try{
                        closestFood = mag(closestFood) < mag(new int[]{x0,y0}) ? closestFood : new int[]{x0,y0};
                    } catch (NullPointerException npe) {
                        closestFood = new int[]{x0,y0};
                    }
                }
            }
        }
        if(!knownFood){
           // closestFood = null;

        }
    }

    @Override
    public int[][] getInfo() {
        return foodLocations;
    }
}
