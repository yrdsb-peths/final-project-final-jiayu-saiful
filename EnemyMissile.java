import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Rectangle;

/**
 * Write a description of class EnemyMissile here.
 * 
 * @author Saiful Shaik
 * @version (a version number or a date)
 */

public class EnemyMissile extends Base {
    private GreenfootSound missileSound = new GreenfootSound("rock.mp3");
    private boolean facingRight;
    private int speed = 3;
    private GreenfootImage[] images;
    private int frame = 0;
    private int animationTimer = 0;
    private final int ANIMATION_SPEED = 4;
    private boolean hasHit = false;

    public EnemyMissile(boolean facingRight) {
        this.facingRight = facingRight;
        images = new GreenfootImage[4];
        for (int i = 0; i < images.length; i++) {
            images[i] = new GreenfootImage("images/golem/attackMissile/" + i + ".png");
        }
        if (!facingRight) {
            for (GreenfootImage img : images) {
                img.mirrorHorizontally();
            }
        }
        setImage(images[0]);
    }

    public void act() {
        missileSound.play();
        animate();
        move();
    }

    private void move() {
        if (hasHit) return;
    
        Player player = (Player) getWorld().getObjects(Player.class).get(0);
        if (player == null) return;
    
        // Calculate direction to player
        int dx = player.getX() - getX();
        int dy = player.getY() - getY();
    
        // Calculate angle in degrees
        double angle = Math.toDegrees(Math.atan2(dy, dx));
        // Adjust rotation if your sprite faces left by default
        setRotation((int)(angle + 180)); 
    
        // Normalize direction vector
        double length = Math.sqrt(dx * dx + dy * dy);
        double moveX = speed * dx / length;
        double moveY = speed * dy / length;
    
        // Move missile
        setLocation((int)(getX() + moveX), (int)(getY() + moveY));
    
        if (isAtEdge()) {
            getWorld().removeObject(this);
            return;
        }
    
        // Check collision with player
        Rectangle missileBounds = new Rectangle(getX() - 10, getY() - 10, 20, 20);
        if (missileBounds.intersects(player.getHitbox())) {
            hasHit = true;
            player.takeDamage();
            getWorld().removeObject(this);
        }
    }

    private void animate() {
        animationTimer++;
        if (animationTimer >= ANIMATION_SPEED) {
            animationTimer = 0;
            frame = (frame + 1) % images.length;
            setImage(images[frame]);
        }
    }

}
