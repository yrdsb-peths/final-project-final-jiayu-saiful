import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EnemyMissile here.
 * 
 * @author Saiful Shaik
 * @version (a version number or a date)
 */

public class EnemyMissile extends Base {
    private boolean facingRight;
    private int speed = 5;
    private GreenfootImage[] images;
    private int frame = 0;
    private int animationTimer = 0;
    private final int ANIMATION_SPEED = 4;

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
        animate();
        move();
        checkCollision();
    }

    private void move() {
        int dx = facingRight ? speed : -speed;
        setLocation(getX() + dx, getY());
        if (isAtEdge()) {
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

    private void checkCollision() {
        Player player = (Player) getOneIntersectingObject(Player.class);
        if (player != null) {
            player.takeDamage();
            getWorld().removeObject(this);
        }
    }
}
