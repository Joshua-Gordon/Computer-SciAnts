import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Entity {


    public int x, y, r;

    BufferedImage sprite;

    public Entity(int x, int y, int r, String s) {
        this.x = x;
        this.y = y;
        this.r = r;
        /* Rotation from ?:
        7 0 1
        6 ? 2
        5 4 3
         */
        init(s);
    }

    public Entity(int x, int y, String s) {
        this.x = x;
        this.y = y;
        this.r = 0;
        init(s);
    }

    private void init(String s) {
        try {
            this.sprite = ImageIO.read(new File(s));
        } catch (IOException e) {
            System.err.println("Can't read sprite: " + s);
            System.exit(1);
        }
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean move(int dx, int dy, Grid g) {
        if(x + dx >= Grid.spaces.length || y + dy >= Grid.spaces[0].length
                || x + dx < 0 || y + dy < 0) {
            return false;
        } else if(!g.spaces[x+dx][y+dy]) {
            g.entities[x][y] = null;
            g.spaces[x][y] = false;
            x += dx;
            y += dy;
            g.entities[x][y] = this;
            g.spaces[x][y] = true;
        } else return false;
        /*
        Rotation from ?:
        7 0 1
        6 ? 2
        5 4 3
         */
        if(dx > 0 && dy < 0) {
            rotate(1 - r);
        } else if(dx > 0 && dy == 0) {
            rotate(2 - r);
        } else if (dx > 0 && dy > 0) {
            rotate(3 - r);
        } else if (dx == 0 && dy > 0) {
            rotate(4 - r);
        }else if(dx < 0 && dy > 0) {
            rotate(5 - r);
        } else if (dx < 0 && dy == 0) {
            rotate(6 - r);
        } else if (dx < 0 && dy < 0) {
            rotate(7 - r);
        } else if ( dx == 0 && dy < 0) {
            rotate(8 - r);
        } else {
        }
        return true;
    }

    /**
     *
     * @param dr positive to rotate clockwise
     */
    public void rotate(int dr) {
        BufferedImage temp = new BufferedImage(Grid.PIXELS_PER_SQUARE,Grid.PIXELS_PER_SQUARE,BufferedImage.TYPE_INT_RGB);
        AffineTransform at = new AffineTransform();
        at.translate(Grid.PIXELS_PER_SQUARE/2,Grid.PIXELS_PER_SQUARE/2);
        at.rotate(dr * -Math.PI/4);
        at.translate(-Grid.PIXELS_PER_SQUARE/2,-Grid.PIXELS_PER_SQUARE/2);
        Graphics2D g2d = (Graphics2D) temp.getGraphics();
        g2d.setColor(new Color(215,215,215));
        g2d.fillRect(0,0,Grid.PIXELS_PER_SQUARE,Grid.PIXELS_PER_SQUARE);
        g2d.drawImage(sprite,at,null);
        sprite = temp;
        r = (r + dr) % 8;
    }

    public BufferedImage render() {
        //System.out.println("Rendering " + this.getClass().toString());
        return sprite;
    }

    public abstract void update(Grid g);

    public double mag(int[] location) {
        return Math.sqrt(location[0]*location[0] + location[1] * location[1]);
    }

    public void die(Grid grid) {
        grid.setEntity(null,x,y);
        grid.spaces[x][y] = false;
    }

}
