import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Player here.
 * 
 * @author (your name) Jiayu
 * @version (a version number or a date) 05/21/25
 */
public class Player extends Actor
{
    private GreenfootImage baseImage;
    private int vSpeed = 3;
    private int acceleration = 1;
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
        } else if (Greenfoot.isKeyDown("up")) {
            move(2);
        }
        
        if (!onGround()) {
            fall();
        }
    }
    
    public boolean onGround()
    {
        Object under = getOneObjectAtOffset(0, getImage().getHeight()/2 + 2, Grass.class);
        return under != null;
    }
    
    public void fall()
    {
        setLocation(getX(), getY() + vSpeed);
        vSpeed += acceleration;
    }
    
    public void checkFall()
    {
        if (onGround()) {
            vSpeed = 0;
        }
        else {
            fall();
        }
    }
}
