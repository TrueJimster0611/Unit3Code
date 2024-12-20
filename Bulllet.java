import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


class Bullet
{
    Circle circle;
    //double horizSpeed = 0;
    double vertSpeed;
    boolean hasScored = false;
    int hitpoint = 1;//to be added for the collision thing
    double radius;

    Bullet (double x, double y, double rad, double v, Color ballColor, int hp) 
    {
        this.circle = new Circle(rad, ballColor);
        this.circle.setCenterX(x);
        this.circle.setCenterY(y);
        this.vertSpeed = v+Math.random()*3;  // set vertical speed
        hitpoint = hp;
        radius = rad;
    }

    public double getRadius()
    {
        return radius;
    }

}
