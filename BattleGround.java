
//Purpose of this code is to create the battle field in which the game is played in
import java.util.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

public class BattleGround 
{

    public static void createSpawnZone (Pane pane, Rectangle topHalf, Rectangle bottomHalfRectangle)
    {
        topHalf.setFill(Color.LIGHTCORAL); // Top half color
        bottomHalfRectangle.setFill(Color.LIGHTGREEN); // Bottom half color
        pane.getChildren().addAll(topHalf, bottomHalfRectangle);//append to pane
    }

    public static void createButtons(Pane pane, ArrayList<Button> buttons, Button currentButton, int index)
    {
        currentButton.setLayoutX(30); //x coord of button
        currentButton.setLayoutY(200 + 200*index); //y coord of button
        currentButton.setMinSize(200, 70); //width and height of button
        currentButton.setFont(new Font("Arial", 30));

        //adds button to arraylist and to the UI
        buttons.add(currentButton);
        pane.getChildren().add(currentButton);
    }

    public static void createScoreBoard(Pane pane, Text ScoreText)
    {
        ScoreText.setFont(new Font(40)); // Set font size
        ScoreText.setFill(Color.BLACK); // Set text color
        ScoreText.setX(20); ScoreText.setY(50);
        pane.getChildren().add(ScoreText);//adds score board & gold balance to screen
    }

    public static void createBalanceBoard(Pane pane, Text goldBalance)
    {
        goldBalance.setFont(new Font(20)); // Set font size
        goldBalance.setFill(Color.DARKGOLDENROD); // Set text color
        goldBalance.setX(20); goldBalance.setY(100);
        pane.getChildren().add(goldBalance);//adds score board & gold balance to screen
    }

    public static void createTimerBoard(Pane pane, Text timerText)
    {
        timerText.setFont(new Font(20)); // Set font size
        timerText.setFill(Color.BLACK); // Set text color
        timerText.setX(20); timerText.setY(140);
        pane.getChildren().add(timerText);//adds score board & gold balance to screen

    }

    public static void createEnemyTurrets(Pane pane, ArrayList<EnemyBuilding> arrayList)
    {
        for (int k = 0; k < 5; k++)
        {
            for (int i = 0; i < 2; i++)//for each coloumn
            {
                for (int j = 0; j < 3; j++)//within each coloumn
                {
                    HelperMethods.addEnemyBuilding(pane, arrayList, 350 + j*100+ k*325, i*100+30);
                }
            }
        }
    }
}