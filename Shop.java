import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Shop here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Shop extends World
{


    public Shop()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(900, 540, 1); 
        setBackground("images/shopbg.png");
        
        UIBackground UIbg = new UIBackground(this, 900, 70);
        addObject(UIbg, 900 / 2, 35);
    
        UI ui = new UI(this);
        
        addObject(new EnhanceAttack(), 160, 330);
        addObject(new EnhanceSpeed(), 450, 330);
        addObject(new ResetLives(), 740, 330);
        addObject(new ExitShopButton(), 820, 120);
    }
}
