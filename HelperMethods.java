import javafx.scene.layout.Pane;
import java.util.*;
import javafx.scene.paint.Color;


//entire purpose of this class is to allow for code reusabillity + saves space in the Main Class
public class HelperMethods 
{
    public static void addEnemyBuilding(Pane pane, ArrayList<EnemyBuilding> arrayList, double x, double y) 
    {
        EnemyBuilding newBuilding = new EnemyBuilding(x, y);
        arrayList.add(newBuilding);
        pane.getChildren().add(newBuilding.rectangle);  // Add the speedy circle to the pane
    }
    public static void addBullet(Pane pane, ArrayList<Bullet> arrayList, double x, double y, double radius, double vertSpeed, Color color, int hp) 
    {
        Bullet newBullet = new Bullet(x, y, radius, vertSpeed, color, hp);
        arrayList.add(newBullet);
        pane.getChildren().add(newBullet.circle);  // Add the speedy circle to the pane
    }

    //grab the current bullet, grab the arraylist it is in, remove it from both pane and it's arraylist
    public static void removeBullet(Pane pane, ArrayList<Bullet> arrayList, Bullet bulletToRemove)
    {
        pane.getChildren().remove(bulletToRemove.circle);
        arrayList.remove(bulletToRemove);
    }

    //code will return t/f if the troop can be spawned
    public static boolean spawnable (double x, double y, int boardHeight, int boardWidth)
    {
        //checks if summon is to close to the vertical seperators
        for (int i = 0; i <= 6; i++) if (Math.abs(x - boardWidth / 6 * i) <= 32) return false;

        //checks if line is too close to the purchase menu
        if (x <= boardWidth / 6) return false;

        //makes sure the summons are spawned within the alloted area        
        if (y <= boardHeight / 4 * 3 || y >= boardHeight - 26) return false;

        return true;
    }
}


