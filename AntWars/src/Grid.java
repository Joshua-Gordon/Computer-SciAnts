
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Grid {

    public final static int PIXELS_PER_SQUARE = 32;

    public static boolean[][] spaces;
    public static Entity[][] entities;
    final private JFrame frame = new JFrame("Ant Wars!!!");
    private JLabel screen;
    private BufferedImage empty, toRender;

    public Grid(int size) {
        assert size > 0;
        spaces = new boolean[size][size];
        entities = new Entity[size][size];
        initialize();
    }

    public Grid(int xsize, int ysize) {
        assert xsize > 0 && ysize > 0;
        spaces = new boolean[xsize][ysize];
        entities = new Entity[xsize][ysize];
        initialize();
    }

    private void initialize() {
        screen = new JLabel();
        try {
            empty = ImageIO.read(new File("AntWars/res/empty.png"));
        } catch (IOException e) {
            System.err.println("Couldn't read empty image");
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(PIXELS_PER_SQUARE * spaces.length + 64, PIXELS_PER_SQUARE * spaces[0].length + 64);
        frame.add(screen);
    }

    public void render() {
        toRender = new BufferedImage(spaces.length*PIXELS_PER_SQUARE,spaces[0].length*PIXELS_PER_SQUARE,BufferedImage.TYPE_INT_RGB);
        for(int x = 0; x < spaces.length; x++) {
            for(int y = 0; y < spaces[0].length; y++) {
                if(spaces[x][y]) {
                    toRender.getGraphics().drawImage(entities[x][y].render(),x*PIXELS_PER_SQUARE,y*PIXELS_PER_SQUARE,null);
                    //System.out.println("yo");
                } else {
                    toRender.getGraphics().drawImage(empty,x*PIXELS_PER_SQUARE,y*PIXELS_PER_SQUARE,null);
                }
            }
        }
        screen.setIcon(new ImageIcon(toRender));
    }

    public void update() {
        Entity[][] old = entities.clone();
        for(int h = 0; h < old.length; h++) {
            Entity[] es = old[h];
            for(int i = 0; i < es.length; i++) {
                Entity e = es[i];
                if(!(e==null)) {
                    e.update(this);
                    if(e instanceof InfoSharing) {
                        ((InfoSharing) e).scan(this);
                    }
                    //System.out.println("Updating entity: " + e.toString());
                }

            }
        }
    }


    public void activate() {
        frame.setVisible(true);
    }

    public void setEntity(Entity e, int x, int y) {
        if(e == null) {
            entities[x][y] = null;
            spaces[x][y] = false;
        } else {
            entities[x][y] = e;
            spaces[x][y] = true;
        }
    }



    public boolean addAnt(int x, int y, Color team, int[] queenLocation) {
        if(spaces[x][y]) return false;
        try {
            spaces[x][y] = true;
            entities[x][y] = new Ant(x, y, team, queenLocation);
            return true;
        } catch(ArrayIndexOutOfBoundsException aioobe) {
            return false;
        }
    }

    public boolean addAnt(Ant a) {
        if(spaces[a.x][a.y]) return false;
        try {
            spaces[a.x][a.y] = true;
            entities[a.x][a.y] = a;
            return true;
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            return false;
        }
    }

    public boolean addQueen(int x, int y, Color team) {
        try {
            spaces[x][y] = true;
            entities[x][y] = new Queen(x, y, team);
            return true;
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            return false;
        }
    }

    public static void removeFood(Food f) {
        for(int x = 0; x < spaces.length; x++) {
            for(int y = 0; y < spaces[0].length; y++) {
                if(entities[x][y].equals(f)) {
                    entities[x][y] = null;
                    spaces[x][y] = false;
                }
            }
        }
    }

    public boolean addFood(int x, int y, int amount) {
        if(!spaces[x][y]) {
            spaces[x][y] = true;
            entities[x][y] = new Food(x,y,amount);
            return true;
        }
        System.err.println("Couldn't add food");
        return false;
    }

}
