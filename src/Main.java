import javafx.application.Application;  
import javafx.scene.Group;  
import javafx.scene.Scene;  
import javafx.scene.paint.Color;  
import javafx.stage.Stage;  
import javafx.scene.layout.AnchorPane; 
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import java.util.*;
import javafx.scene.shape.*; 
import java.lang.Math; 
import java.awt.geom.Line2D; 
import javafx.scene.text.Text; 
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Main extends Application {
    static boolean left, right, up, down, shooting = false; 
    static double PLAYER_SIZE = 20; 
    static int SMALL_SPEED = 2;
    static int SMALL_HP = 100; 
    static int MEDIUM_SPEED = 5;
    static int MEDIUM_HP = 150; 
    static int BIG_SPEED = 10;
    static int BIG_HP = 200; 
    Pair curMove;
    long lastAttack;
    long lastSpawn;  
    IntegerProperty score = new SimpleIntegerProperty(0);
    int scoreVal = 0;
    long asteroidCD = 750; 
    int shipSpeed = 3;
    long asteroid_spawn_increase = 100; 
    long asteroid_increase_threshold = 100;
    int asteroid_type_probability = 0; 
    int asteroid_speedup = 0;
    int lastUpdate = 0;
    Pair moves[] = {new Pair(0, -shipSpeed), new Pair(0, shipSpeed), new Pair(-shipSpeed, 0), new Pair(shipSpeed, 0), new Pair(0, 0)};
    @Override
    public void start(Stage stage) throws Exception { 
        AnchorPane root = new AnchorPane();
        ArrayList<Projectile> projectiles = new ArrayList();
        ArrayList<Asteroid> asteroids = new ArrayList();
        Ship ship = new Ship();
        Scene scene = new Scene(root, 900,900);  
        root.getChildren().addAll(ship.getSprite());
        root.getChildren().addAll(ship.getLeftRocket());
        root.getChildren().addAll(ship.getRightRocket());
        scene.setFill(Color.BLACK);
        stage.setScene(scene);  
        AnchorPane root1 = new AnchorPane();
        Scene endingScreen = new Scene(root1, 900, 900);
        Text endingText = new Text(); 
        endingText.setX(450);
        endingText.setY(450);
        endingText.setText("THE END");
        root1.getChildren().addAll(endingText);
        stage.show();
        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            switch (key) {
                case LEFT:
                    left = true;
                    break;
                case RIGHT:
                    right = true; 
                    break;
                case UP: 
                    up = true;
                    break;
                case DOWN:
                    down = true;
                    break; 
                case SPACE:
                    long time = System.currentTimeMillis();
                    if (time > lastAttack + 250) {
                        lastAttack = time;
                        Projectile projectile = new Projectile(ship.getSprite().getCenterX() , ship.getSprite().getCenterY() - ship.getSprite().getRadius());
                        root.getChildren().add(projectile.getSprite()); 
                        projectiles.add(projectile);
                        shooting = true;
                    } 
            }
        });
        scene.setOnKeyReleased(event -> {
            KeyCode key = event.getCode();
            switch (key) {
                case LEFT:
                    left = false;
                    break;
                case RIGHT:
                    right = false;
                    break;
                case UP: 
                    up = false;
                    break;
                case DOWN:
                    down = false;
                    break; 
                case SPACE:
                    shooting = false;
                    break;
            }
        });
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Text scoreText = new Text();
                scoreText.setX(50);
                scoreText.setY(50);
                scoreText.textProperty().bind(score.asString());
                scoreText.setFill(Color.WHITE);
                root.getChildren().addAll(scoreText);
                long time = System.currentTimeMillis();
                if (scoreVal % asteroid_increase_threshold == 0 && scoreVal != lastUpdate){
                    asteroidCD -= asteroid_spawn_increase;
                    lastUpdate += asteroid_increase_threshold;
                    asteroid_speedup++;
                    asteroid_type_probability++;
                }
                if (time > lastSpawn + Math.floor((Math.random() * 500) + asteroidCD)) {
                    lastSpawn = time;
                    int asteroid_type = 1 + (int) (Math.random() * asteroid_type_probability);
                    int posX = (int)(Math.random() * 900);
                    int posY = 0; 
                    if (asteroid_type <= 1) {
                        AsteroidSmall asteroid = new AsteroidSmall(posX, posY , createSmallSprite(posX, posY), SMALL_HP, SMALL_SPEED);
                        root.getChildren().add(asteroid.getSprite()); 
                        asteroids.add(asteroid);
                    }
                    else if (asteroid_type <= 2) {
                        AsteroidMedium asteroid = new AsteroidMedium(posX, posY , createMediumSprite(posX, posY), MEDIUM_HP, MEDIUM_SPEED);
                        root.getChildren().add(asteroid.getSprite()); 
                        asteroids.add(asteroid);
                    }
                    else {
                        AsteroidBig asteroid = new AsteroidBig(posX, posY , createBigSprite(posX, posY), BIG_HP, BIG_SPEED);
                        root.getChildren().add(asteroid.getSprite()); 
                        asteroids.add(asteroid);
                    }

                } 
                if (left == true) {
                    curMove = moves[2];
                }
                else if (right == true) {
                    curMove = moves[3];
                }
                else if (up == true) {
                    curMove = moves[0];
                }
                else if (down == true) {
                    curMove = moves[1];
                }
                else {
                    curMove = moves[4];
                }
                boolean bad = false;
                for (Asteroid a : asteroids) {
                    if (a instanceof AsteroidSmall){
                        AsteroidSmall curA = (AsteroidSmall) a;
                        curA.fall(asteroid_speedup);
                    }
                    else if (a instanceof AsteroidMedium){
                        AsteroidMedium curA = (AsteroidMedium) a;
                        curA.fall(asteroid_speedup);
                    }
                    else if (a instanceof AsteroidBig){
                        AsteroidBig curA = (AsteroidBig) a;
                        curA.fall(asteroid_speedup);
                    }

                    if (shipCollision(new Pair(ship.getSprite().getCenterX() + curMove.f, ship.getSprite().getCenterY() + curMove.s), new Pair( a.getPosX(), a.getPosY()))) {
                        ship.takeDamage();
                        ship.respawn(); 
                        bad = true;
                        if (ship.getLives() == 0) {
                            this.stop();
                            endingScreen.setFill(Color.BLACK);
                            endingText.setFill(Color.WHITE);
                            stage.setScene(endingScreen);
                        }
                        
                    }
                    for (Projectile p : projectiles) {
                        if (projectileCollision(a, p)) {
                            p.delete();
                            a.takeDamage(50);
                            if (a.getHP() <= 0) {
                                asteroids.remove(a);
                                a.delete();
                                scoreVal += 50; 
                                score.set(scoreVal);
                                scoreText.textProperty().bind(score.asString());
                                break;

                            }
                        }
                    }
                    if (a.getPosY() <= 0) {
                        asteroids.remove(a);
                    }
                }
                for (Projectile p : projectiles) {
                    p.shoot();
                    if (p.getSprite().getY() <= -50) {
                        projectiles.remove(p); 
                    }
                }
                if (!bad) {
                    ship.move(curMove.f, curMove.s);
                }

            }
        };
        timer.start();
    }

    public boolean compare(Pair A, Pair B, Pair C) {
        return ((C.s-A.s) * (B.f-A.f) > (B.s-A.s) * (C.f-A.f)); 
    }
    
    public boolean projectileCollision(Asteroid asteroid, Projectile projectile) {
        double aX = asteroid.getPosX();
        double aY = asteroid.getPosY();
        Pair projectile1 = new Pair(projectile.getSprite().getX(), projectile.getSprite().getY());
        Pair projectile2 = new Pair(projectile.getSprite().getX() + 10, projectile.getSprite().getY());
        Pair[] arr = {};
        if (asteroid instanceof AsteroidSmall){
            AsteroidSmall curA = (AsteroidSmall) asteroid;
            arr = curA.getPoints();
        }
        else if (asteroid instanceof AsteroidMedium){
            AsteroidMedium curA = (AsteroidMedium) asteroid;
            arr = curA.getPoints();
        }
        else if (asteroid instanceof AsteroidBig){
            AsteroidBig curA = (AsteroidBig) asteroid;
            arr = curA.getPoints();
        }

        int[] xpoints = new int[arr.length];
        int[] ypoints = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            xpoints[i] = (int) arr[i].f;
            ypoints[i] = (int) arr[i].s;
        }
        java.awt.Polygon asteroidShape = new java.awt.Polygon(xpoints, ypoints, arr.length);
        for (int i = 0; i < arr.length; i++) {
            Line2D line1 = new Line2D.Double(arr[(i+1)%arr.length].f, arr[(i+1)%arr.length].s, arr[i].f, arr[i].s);
            Line2D line2 = new Line2D.Double(projectile1.f, projectile1.s, projectile2.f, projectile2.s);
            if (line1.intersectsLine(line2)) {
                return true;
            }
            if (asteroidShape.contains(projectile1.f, projectile2.s, 10, 50)) {
                return true;
            }
        }
        return false; 
    }

    public boolean shipCollision(Pair ship, Pair asteroid) {
        double aX = asteroid.f;
        double aY = asteroid.s;
        if (lineIntersection(new Pair(aX, aY), new Pair(aX+50, aY-40), ship)) {
            return true;
        }
        if (lineIntersection(new Pair(aX+50, aY-40), new Pair(aX+100, aY-60), ship)) {
            return true;
        }
        if (lineIntersection(new Pair(aX+100, aY-60), new Pair(aX+130, aY+20), ship)) {
            return true;
        }
        if (lineIntersection(new Pair(aX+130, aY+20), new Pair(aX+70, aY+40), ship)) {
            return true;
        }
        if (lineIntersection(new Pair(aX+70, aY+40), asteroid, ship)) {
            return true;
        }
        return false;
    }

    public boolean lineIntersection(Pair A, Pair B, Pair C) {
        Pair AC = sub(C,A);
        Pair AB = sub(B,A);
        Pair D = add(proj(AC, AB), A);
        Pair AD = sub(D, A);
        double k;
        if (Math.abs(AB.f) >= Math.abs(AB.s)) {
            k = AD.f/AB.f;
        }
        else {
            k = AD.s/AB.s;
        }
        if (k <= 0.0) {
            return (Math.sqrt(hypot(A, C)) <= PLAYER_SIZE);
        }
        else if (k >= 1.0) {
            return (Math.sqrt(hypot(C, B)) <= PLAYER_SIZE);
        }
        return (Math.sqrt(hypot(C,D)) <= PLAYER_SIZE);

    }
    public static void main(String[] args) {
        launch(args);
    }

    public Polygon createSmallSprite(double posX, double posY) {
        double[] points = {posX, posY, posX+50, posY-40, posX+100, posY-60, posX + 130, posY+20, posX+70, posY+40};
        Polygon sprite = new Polygon(points);
        sprite.setFill(Color.GREY);
        return sprite; 
    }

    public Polygon createMediumSprite(double posX, double posY) {
        double[] points = {posX, posY, posX+50, posY-40, posX+100, posY-60, posX + 130, posY+20, posX+70, posY+40};
        Polygon sprite = new Polygon(points);
        sprite.setFill(Color.DARKORCHID);
        return sprite; 
    }
    
    public Polygon createBigSprite(double posX, double posY) {
        double[] points = {posX, posY, posX+50, posY-40, posX+100, posY-60, posX + 130, posY+20, posX+70, posY+40};
        Polygon sprite = new Polygon(points);
        sprite.setFill(Color.DARKRED);
        return sprite; 
    }

    public Pair sub(Pair A, Pair B) {
        return new Pair(A.f - B.f, A.s-B.s); 
    }
    public Pair add(Pair A, Pair B) {
        return new Pair(A.f + B.f, A.s + B.s); 
    }
    public double dot(Pair A, Pair B) {
        return ((A.f * B.f) + (A.s * B.s)); 
    }
    public double hypot(Pair A, Pair B) {
        return dot(sub(A, B), sub(A, B));
    }
    public Pair proj(Pair A, Pair B) {
        double k = dot(A, B) / dot(B, B);
        return (new Pair(k * B.f, k*B.s));
    }
}   