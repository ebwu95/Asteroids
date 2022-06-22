import javafx.application.Application;  
import javafx.scene.Group;  
import javafx.scene.Scene;  
import javafx.scene.paint.Color;  
import javafx.stage.Stage;  
import javafx.scene.shape.*;

public class Projectile {
    /* Attributes */

    /** sprite of the ship */
    private Rectangle sprite;

    /*
    Constructors
    */

    /**
    Creates a basic projectile
    @param posX the x value of the position of the projectile
    @param posY the y value of the position of the projectile
    */
    public Projectile(double posX, double posY) {
        this.sprite = new Rectangle(posX, posY, 10, 50);
        this.sprite.setFill(Color.DARKBLUE);
    }

    //Accessors

    /**
    Gets the projectile sprite
    @return the projectile sprite 
    */
    public Rectangle getSprite() {
        return this.sprite; 
    }

    //General Methods

    /**
    Shoots the projectile
    */
    public void shoot() {
        if (this.sprite.getY() >= -50) {
            this.sprite.setY(this.sprite.getY() - 5);
        }
    }
    
    /**
    Deletes the projectile
    */
    public void delete() {
        this.sprite.setY(-500);
    }

}
