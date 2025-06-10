import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Portal here.
 * 
 * @author Saiful Shaik
 * @version June 9, 2025
 */
public class Portal extends Base
{
    public Portal() {
        GreenfootImage img = new GreenfootImage("images/portal.png");
        img.scale(img.getWidth() / 2, img.getHeight() / 2);
        setImage(img);
    }
    public void act()
    {
        if (isTouching(Player.class)) {
            Level0.setting++;
            Greenfoot.setWorld(new Level0(900, 540));
        }
    }
}
