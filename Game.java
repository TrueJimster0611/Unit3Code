import java.util.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

public class Game extends Application 
{
    //dimensions of the bullet's radius and dimensions of the pane
    final private double radius = 25;
    final private int boardWidth = 1936, boardHeigth = 995;

    int playerBalance = 10000, score = 0;
    double time = 5000, enemyTurretAttackSpeed = 2;
    
    String currentSummon = "Bullet";
    ArrayList<Bullet> bullets = new ArrayList<>(), enemyBullets = new ArrayList<>(); 

    ArrayList<EnemyBuilding> enemyTurrets = new ArrayList<>(); 
    ArrayList<String> bulletNames = new ArrayList<>(Arrays.asList("Bullet", "Speedy Bullet", "Armoured Bullet", "THE BIG ONE"));
    ArrayList<Integer> bulletCosts = new ArrayList<>(Arrays.asList(100, 300, 500, 10000));

    ArrayList<Button> buttons = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) 
    {
        //---------------------Code Below Deals with Creating/Displaying the playing field--------------------------------------------------------------------------------
        // Create a pane to hold the ball
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: grey;");//sets background to the colour grey

        //Creates the troop spawn and no-spawn zone
        Rectangle topHalf = new Rectangle(boardWidth/6, 0, boardWidth, boardHeigth / 4 * 3);
        Rectangle bottomHalf = new Rectangle(boardWidth/6, boardHeigth / 4 * 3, boardWidth, boardHeigth / 4);
        BattleGround.createSpawnZone(pane, topHalf, bottomHalf);

        //lane's borders
        for (int i = 0; i <= 6; i++) 
        {
            Line line = new Line(boardWidth / 6 * i, 0, boardWidth / 6 * i, boardHeigth);
            line.setStroke(Color.BLACK); line.setStrokeWidth(20);
            pane.getChildren().add(line);
        }
        
        //creates a score board, code can be found in battlegrounds file
        Text scoreText = new Text("Score: " + score);
        BattleGround.createScoreBoard(pane, scoreText);
        
        //creates player's goldbalance
        Text goldBalance = new Text("Balance: " + playerBalance);
        BattleGround.createBalanceBoard(pane, goldBalance);

        //creates timer
        Text timerText = new Text("timer: " + time/100);
        BattleGround.createTimerBoard(pane, timerText);


        //creates the buttons
        for (int i = 0; i < 4; i++)
        {
            Button currentButton = new Button(bulletNames.get(i) + "\nCost: " + bulletCosts.get(i));
            BattleGround.createButtons(pane, buttons, currentButton, i);
        }

        //creates the enemy turrets
        BattleGround.createEnemyTurrets(pane, enemyTurrets);
        
        //------------------------------------------Code Below Deals With Object's Animation--------------------------------------------------------------------------------------        
        AnimationTimer timer = new AnimationTimer() 
        {
            @Override
            public void handle(long now) 
            {
                for (var t: enemyTurrets)//goes through each of the turrets (rectangles), and shoots out a bullet
                {   
                    int randomNum = (int)((Math.random()*2500)/enemyTurretAttackSpeed);//determines the turret's probabillity of shooting a bullet

                    if (randomNum == 1) 
                    {
                        HelperMethods.addBullet(pane, enemyBullets, t.rectangle.getX() + t.rectangle.getWidth()/2, t.rectangle.getY() + t.rectangle.getHeight()/2, radius, 3, Color.BLACK, 1);
                    }
                    else if (randomNum == 2) 
                    {
                        HelperMethods.addBullet(pane, enemyBullets, t.rectangle.getX() + t.rectangle.getWidth()/2, t.rectangle.getY() + t.rectangle.getHeight()/2, radius*2, 5, Color.DARKGRAY, 1);
                    }
                }
                
                for (var playerBullet : bullets)//updates movement of player's bullets
                {  
                    double y = playerBullet.circle.getCenterY();// Update the ball's position
                    if ((y <= radius || y >= boardHeigth - radius) && !playerBullet.hasScored) //checks to see if it passed the screen
                    {
                        playerBullet.hasScored = true;
                        score++;
                        playerBalance+=1000;//everytime a score is made, reward player
                        scoreText.setText("Score: " + score);//update scoreboard
                    }
                    playerBullet.circle.setCenterY(y - playerBullet.vertSpeed);// Move the ball (speed determines how much in y coordinates it moves in one frame)
                
                }

                for (var botBullet : enemyBullets)//updates movement of enemy's bullets
                {  
                    //below chunck of code deals with collision between the enemys' balls with playersball, checking if enemy ball has collided with player ball
                    for (var playerBullet : bullets) 
                    {
                        double distX = playerBullet.circle.getCenterX() - botBullet.circle.getCenterX();//a
                        double distY = playerBullet.circle.getCenterY() - botBullet.circle.getCenterY();//b
                        double distance = Math.sqrt(distX * distX + distY * distY);//c, pythagorean theorem for distance

                        if (distance <= playerBullet.getRadius() + botBullet.getRadius()) 
                        {
                            playerBullet.hitpoint--; botBullet.hitpoint--; //bullets have collided, hp-=1 both

                            //for the below two lines, if any of the bullets hp is 0, remove them from arraylist and from pane
                            if (playerBullet.hitpoint <= 0) {HelperMethods.removeBullet(pane, bullets, playerBullet);}
                            if (botBullet.hitpoint <= 0) {HelperMethods.removeBullet(pane, enemyBullets, botBullet); break;}
                        }
                    }//end of collision detection between player and enemey bullets

                    double y = botBullet.circle.getCenterY();// determine ball's current position

                    if (y >= boardHeigth && !botBullet.hasScored) //checks to see if it has passed the screen
                    {
                        botBullet.hasScored = true;
                        score-=10;
                        playerBalance-=200;//everytime a score is made by the bot, player's player balance is decreased
                        scoreText.setText("Score: " + score);//update score board
                    }

                    botBullet.circle.setCenterY(y + botBullet.vertSpeed);// Move the ball (speed determines how much in y coordinates it moves in one frame)
                    if (botBullet.hasScored) HelperMethods.removeBullet(pane, enemyBullets, botBullet); //removes the bullet from screen+arraylist
                }     
                
                //passively increases player gold and counts down timter
                playerBalance++; 
                time-= 1;

                if (time == 0) //if time is 0, stop the game
                {
                    playerBalance = 0; 
                    stop();
                }

                goldBalance.setText("Balance: " + playerBalance);
                timerText.setText("Time: " + time/100);
            }

        };
        
        //-------------------------------Below Chunk of Code will deal with user's input----------------------------------------------------------------------------------------------
            
        //Below deals with mouse actions, specifically player summoning bullets
        pane.setOnMouseClicked(e -> 
        {
            //checks to see if the user spawned a bullet within the alloted green space
            boolean validSpawn = HelperMethods.spawnable(e.getX(), e.getY(), boardHeigth, boardWidth);

            if (validSpawn)//checks see if ball is spawned in the green zone 
            {
                if (currentSummon.equals("Bullet") && playerBalance-100 >= 0) 
                {
                    HelperMethods.addBullet(pane, bullets, e.getX(), e.getY(), radius, 5.0, Color.BLUE, 1);
                    playerBalance-=100;
                }
                else if (currentSummon.equals("Speedy Bullet") && playerBalance-300 >= 0) 
                {
                    HelperMethods.addBullet(pane, bullets, e.getX(), e.getY(), radius*.75, 10.0, Color.GRAY, 1);
                    playerBalance-=300;
                }
                else if (currentSummon.equals("Armoured Bullet") && playerBalance-500 >= 0) 
                {
                    HelperMethods.addBullet(pane, bullets, e.getX(), e.getY(), radius*2, 2.0, Color.RED, 3);
                    playerBalance-=500;
                }
                else if (currentSummon.equals("THE BIG ONE") && playerBalance-10000 >= 0) 
                {
                    HelperMethods.addBullet(pane, bullets, e.getX(), e.getY(), radius*10, 10.0, Color.WHITE, 100);
                    playerBalance-=10000;
                }
            }
        });
        
        //below handles the player's keyboard actions
        pane.setOnKeyPressed(e -> 
        {
            if (e.getCode() == KeyCode.DIGIT1) currentSummon = "Bullet";//if key '1' is pressed, changes what is summoned to normal Bullet
            else if (e.getCode() == KeyCode.DIGIT2) currentSummon = "Speedy Bullet";
            else if (e.getCode() == KeyCode.DIGIT3) currentSummon = "Armoured Bullet";
            else if (e.getCode() == KeyCode.DIGIT4) currentSummon = "THE BIG ONE";

        });

        //below handles button actions
        for (int i = 0; i < bulletNames.size(); i++)
        {
            final int index = i;//just makes sure the i value doesn't get change or do smth whonky bonky tonky
            buttons.get(i).setOnAction(e -> { currentSummon = bulletNames.get(index);});
        }


        //----------------Below Chunk of Code Shows the FrontEnd UI and starts the BackEnd onject animations----------------------------------------------------------------------------------------------
        
        timer.start();//begins animations

        // Create a scene and set the stage
        Scene scene = new Scene(pane, boardWidth, boardHeigth);
        primaryStage.setTitle("Disboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) 
    {
        launch(args);
    }


}