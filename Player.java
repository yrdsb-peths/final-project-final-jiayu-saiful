import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Player here.
 * 
 * @author (your name) Jiayu
 * @version (a version number or a date) 05/22/25
 */
public class Player extends Actor
{
    private GreenfootImage baseImage;
    private int vSpeed = 0;  // Vertical speed
     private boolean onGround = false;  // Flag to indicate if the player is on the ground
    private final int jumpStrength = -28; // Initial jump force
    private final int acceleration = 2; // Acceleration due to gravity
    public Player() {
        baseImage = new GreenfootImage("images/knight.png");

        int targetWidth = 75;
        int targetHeight = (int)(baseImage.getHeight() * ((double) targetWidth / baseImage.getWidth()));
        baseImage.scale(targetWidth, targetHeight);

        setImage(new GreenfootImage(baseImage));
    }
    
    public void act()
    {
        // Add your action code here.
        if (Greenfoot.isKeyDown("left")) {
            move(-3);
        } else if (Greenfoot.isKeyDown("right")) {
            move(3);
        } else if (onGround && Greenfoot.isKeyDown("up")) {
            vSpeed = jumpStrength;
            onGround = false; // Player is no longer on the ground
        }
        if (!onGround) {
            vSpeed += acceleration; // Add gravity
        }
        setLocation(getX(), getY() + vSpeed); // Move based on vertical speed
        if (isTouching(Grass.class)) { // Ground detection - adjust as needed
            setLocation(getX(), getY() - vSpeed); // Move back up to ground
            vSpeed = 0;
            onGround = true; // Set onGround to true
        }
}
    }
