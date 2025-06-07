import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Gameover here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Gameover extends World
{

    public Gameover()
    {    
        super(900, 540, 1);
        
        GreenfootImage bg = new GreenfootImage("images/gameover.png");
        bg.drawImage(bg, 0, 0);
        setBackground(bg);
    }
}
