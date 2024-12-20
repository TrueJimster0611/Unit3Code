import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
public abstract class Building 
{
    //double horizSpeed = 0;//horizontal speed here if you have the building move left and right
    //boolean hasScored = false;
    Rectangle rectangle;
    int vertSpeed = 0;

    public int getVertSpeed()
    {
        return vertSpeed;
    }
    abstract void buildingHealth();

    abstract void buildingStrength();

    abstract void buildingSideSpeed();

    //above 3 methods will be implememted/further used in during the winter break
}
