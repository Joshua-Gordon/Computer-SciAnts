import java.awt.*;
import java.util.ArrayList;

public interface InfoSharing {

    Color getTeam(); //-1 is food
    void shareInfo(InfoSharing other);
    int[][] getInfo();
    public void scan(Grid g);

}
