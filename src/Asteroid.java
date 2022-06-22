import javafx.application.Application;  
import javafx.scene.Group;  
import javafx.scene.Scene;  
import javafx.scene.paint.Color;  
import javafx.stage.Stage;  
import javafx.scene.shape.*;
public class Asteroid {
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

    public Asteroid(double posX, double posY, Polygon sprite, int hp, int speed) {
        this.posX = posX;
        this.posY = posY;
        this.sprite = sprite;
        this.hp = hp;
        this.speed = speed;
    }
    
    //Accessors

    /**
    Gets the sprite of the asteroid
    @return the sprite of the asteroid 
    */
    public Polygon getSprite() {
        return this.sprite; 
    }

    /**
    Gets the hp of the asteroid
    @return the hp of the asteroid 
    */
    public int getHP() {
        return this.hp;  
    }

    /**
    Gets the lowest y of the asteroid
    @return the lowest y of the asteroid 
    */
    public double getPosY() {
        return this.posY;  
    }

    /**
    Gets the lowest x of the asteroid
    @return the lowest x of the asteroid 
    */
    public double getPosX() {
        return this.posX;  
    }

    /**
    Gets an array of the points in the asteroid
    @return an array of points in the asteroid
    */
    public Pair[] getPoints() {
        Pair[] points = {new Pair(posX, posY), new Pair(posX+50, posY-40), new Pair(posX+100, posY-60), new Pair(posX + 130, posY+20), new Pair(posX+70, posY+40)};
        return points;
    }
    //Mutators
    public void fall(int speedUp) {
        this.sprite.setLayoutY(this.sprite.getLayoutY()+this.speed+speedUp);
        this.posY += this.speed+speedUp; 
    }

    public void takeDamage(int damage) {
        this.hp -= damage; 
    }

    public void delete() {
        this.sprite.setLayoutY(1500);
        this.posY +=1; 
    }    
    
    public void setSprite(Polygon newSprite) {
        this.sprite = newSprite;
    }
    
    public void setHP(int newHP) {
        this.hp = newHP;
    }

    public void setSpeed(int newSpeed) {
        this.speed = newSpeed;
    }
}