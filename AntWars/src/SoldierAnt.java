import java.awt.*;

public class SoldierAnt extends Ant implements Militaristic {

    int combatEffectiveness;
    int[][] enemyQueenLocations;
    int[] closestEnemyQueen;
    boolean isGuard;

    public static final int GUARD_RADIUS = 5;

    public SoldierAnt(int x, int y, Color team, int[] queenLocation, int combatEffectiveness, boolean isGuard) {
        super(x,y,team,queenLocation);
        this.combatEffectiveness = combatEffectiveness;
        enemyQueenLocations = new int[Grid.spaces.length][Grid.spaces[0].length];
    }

    @Override
    public void update(Grid g) {
        if(isGuard) {
            patrol(g,queenLocation,GUARD_RADIUS);
          return;
        } else if(closestFood != null) {
            patrol(g,closestFood,GUARD_RADIUS);
        } else {
            moveRandomly(g);
        }
    }

    public void patrol(Grid g, int[] location, int radius) {
        if(Math.abs(mag(location) - mag(new int[]{x,y})) > radius) {
            moveTowardPoint(g,location[0],location[1]);
        }
    }


    @Override
    public int getCombatEffectiveness() {
        return combatEffectiveness;
    }

    @Override
    public void fight(Militaristic other) {
        System.out.println("Fighting");
        int ceOther = other.getCombatEffectiveness();
        combatEffectiveness -= ceOther;
        if(combatEffectiveness <= 0) {
            Grid.spaces[x][y] = false;
            Grid.entities[x][y] = null;

        }
    }

    @Override
    public void scan(Grid g) {
        for(int x0 = -1; x0 < 2; x0++) {
            for(int y0 = -1; y0 < 2; y0++) {
                try{
                    if(x0 != 0 && y0 != 0 && g.entities[x0 + x][y0 + y] instanceof Militaristic) {
                        //System.out.println("Found something!");
                        fight((Militaristic) g.entities[x0 + x][y0 + y]);
                    }
                    //System.out.println("x0: " + x0 + "\ny0: " + y0);
                } catch (ArrayIndexOutOfBoundsException aioobe) {

                }
            }
        }
    }
}
