import javafx.application.Application;  
import javafx.scene.Group;  
import javafx.scene.Scene;  
import javafx.scene.paint.Color;  
import javafx.stage.Stage;  
import javafx.scene.shape.*;
public class AsteroidMedium extends Asteroid {
    /** Attributes */

    /* the sprite of the asteroid */
    private Polygon sprite; 
 
    /* the x value of the leftmost point of the asteroid */
    private double posX;

    /* the y value of the leftmost point of the asteroid */
    private double posY; 

    /* Current health of the asteroid */
    private int hp; 
    
    /* Speed at which the asteroids fall down */
    private int speed; 

    public AsteroidMedium(double posX, double posY, Polygon sprite, int hp, int speed) {
        super(posX, posY, sprite, hp, speed);
    }

}