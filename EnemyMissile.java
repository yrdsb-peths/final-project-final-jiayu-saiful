import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EnemyMissile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EnemyMissile extends Actor {
    private int speed;

    public EnemyMissile(boolean goingRight) {
        setImage("images/golem/attackMissile/0.png");
        speed = goingRight ? 5 : -5;
        if (!goingRight) {
            getImage().mirrorHorizontally();
        }
    }

    public void act() {
        setLocation(getX() + speed, getY());

        if (getX() < 0 || getX() > getWorld().getWidth()) {
            getWorld().removeObject(this);
        }
    }
}
