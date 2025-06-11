import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Boss here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Boss extends Base
{
    // when boss is dead, load Gameover screen
    public static boolean isBossDead = false;
    
    public void act()
    {
        if (isBossDead) {
            Gameover gameover = new Gameover();
            Greenfoot.setWorld(gameover);
        }
    }
}
