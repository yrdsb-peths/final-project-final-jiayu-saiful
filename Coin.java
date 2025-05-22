import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Coin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Coin extends Actor
{
    /**
     * Act - do whatever the Coin wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private GreenfootImage currentImage;
    Player player = new Player();
    GreenfootImage[] coinAnimation = new GreenfootImage[4];
    public Coin() {
        setImage("images/coin_an/00.png");
        player.scaleImage(new GreenfootImage("images/coin_an/00.png"), 10);
    }
    
    public void act()
    {
        // Add your action code here.
    }
}
