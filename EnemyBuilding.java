import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


class EnemyBuilding extends Building
{
    //double horizSpeed = 0;//horizontal speed here if you have the building move left and right
    //boolean hasScored = false;
    //int health;//to be added for the collision thing


    EnemyBuilding (double x, double y) 
    {
        super.rectangle = new Rectangle(x, y, 50, 75);
        super.rectangle.setFill(Color.DARKGRAY); // Fill the rectangle with red
        super.rectangle.setStroke(Color.BLACK); // Outline with black
        super.rectangle.setStrokeWidth(2); // Outline thickness
    }

    public void buildingHealth()//to be implemented later
    {
        //code to be implemented at a latter date
    }

    public void buildingStrength()//to be implemented in the future
    {
        //code to be implemented at a latter date

    }

    public void buidlingSideSpeed()//to be implemented in the future
    {
        //code to be implemented at a latter date
    }
    //public bool
}
