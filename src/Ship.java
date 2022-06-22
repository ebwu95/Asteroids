import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

public class Ship {
    static double STARTX = 500;
    static double STARTY = 850; 
    static double RADIUS = 20; 

    /* Attributes */

    /** sprite of the ship */
    private Circle sprite; 

    /** lives of the ship */
    private int lives;

    /** sprite of the left rocket of the ship */
    private Rectangle leftRocket; 
    
    /** sprite of the right rocket of the ship */
    private Rectangle rightRocket; 

    /** projectiles from the ship */
    List<Projectile> projectiles = new ArrayList<>();

    /*
    Constructors
    */

    /**
    Creates a basic ship
    */
    public Ship() {
        this.sprite = new Circle(STARTX, STARTY, RADIUS);
        this.leftRocket = new Rectangle(STARTX - 20, STARTY-5, 10, 30);
        this.rightRocket = new Rectangle(STARTX + 10, STARTY-5, 10, 30);
        this.leftRocket.setFill(Color.RED);
        this.rightRocket.setFill(Color.RED);
        this.sprite.setFill(Color.RED);
        this.lives = 3;
    }

    //Accessors

    /**
    Gets the ship sprite
    @return the ship sprite 
    */
    public Circle getSprite() {
        return this.sprite; 
    }

    /**
    Gets the sprite of the left rocket
    @return the left rocket sprite 
    */
    public Rectangle getLeftRocket() {
        return this.leftRocket; 
    }

    /**
    Gets the sprite of the right rocket
    @return the right rocket sprite 
    */
    public Rectangle getRightRocket() {
        return this.rightRocket; 
    }

    /**
    Gets the ship's lives
    @return the ship's lives 
    */
    public int getLives() {
        return this.lives; 
    }

    /**
    Gets the ships projectiles 
    @return the list of the ships projectiles
    */
    public List<Projectile> getProjectiles() {
        return this.projectiles; 
    }

    //Mutators

    /**
    Moves the sprite up    
    @param x_delta how much the x value changes
    @param y_delta how much the y value changes 
    */
    public void move(double x_delta, double y_delta) {
        if (this.sprite.getCenterX()+x_delta-this.sprite.getRadius() >= 0 && this.sprite.getCenterX()+x_delta+this.sprite.getRadius() <= 900) {
            this.sprite.setCenterX(this.sprite.getCenterX()+x_delta);
            this.leftRocket.setX(this.leftRocket.getX()+x_delta);
            this.rightRocket.setX(this.rightRocket.getX()+x_delta);
        }
        if (this.sprite.getCenterY()+y_delta-this.sprite.getRadius() >= 0 && this.sprite.getCenterY()+y_delta+this.sprite.getRadius() <= 900) {
            this.sprite.setCenterY(this.sprite.getCenterY()+y_delta);
            this.leftRocket.setY(this.leftRocket.getY()+y_delta);
            this.rightRocket.setY(this.rightRocket.getY()+y_delta);
        }
    }

    /**
    Makes the ship take damage after hitting an asteroid
    */
    public void takeDamage() {
        this.lives -= 1; 
    }

    /**
    Respawns the ship at the starting point after getting hit
    */
    public void respawn() {
        this.sprite.setCenterX(STARTX);
        this.sprite.setCenterY(STARTY); 
        this.leftRocket.setX(STARTX - 20);
        this.leftRocket.setY(STARTY-5);
        this.rightRocket.setX(STARTX + 10);
        this.rightRocket.setY(STARTY - 5);
    }
}
