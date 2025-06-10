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
        img.scale(img.getWidth() / 4, img.getHeight() / 4);

        // Hitbox
        //img.setColor(Color.RED);
        //img.drawRect(0, 0, img.getWidth() - 1, img.getHeight() - 1);

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
