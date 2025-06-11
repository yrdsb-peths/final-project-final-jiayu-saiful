import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Rectangle;
import java.util.List;

// Boss class
public class Boss extends Base {
    public static boolean isBossDead = false;

    private int health = 30;
    private int damage = 2;

    public Boss() {
        GreenfootImage bossImage = new GreenfootImage("images/golem/idle/0.png");
        bossImage.scale(bossImage.getWidth() * 3, bossImage.getHeight() * 3);
        setImage(bossImage);
    }

    public void act() {
        super.act();

        if (health <= 0 && !isBossDead) {
            isBossDead = true;
            Greenfoot.setWorld(new Gameover());
        }
    }

    public int getHealth() {
        return health;
    }

    public void receiveDamage(int amount) {
        health -= amount;
    }

    public int getDamage() {
        return damage;
    }
}