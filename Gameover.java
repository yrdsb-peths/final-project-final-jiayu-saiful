import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Gameover here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Gameover extends World
{
    private Player player;
    public UI ui;
    
    public Gameover()
    {    
        super(900, 540, 1);
        
        GreenfootImage bg = new GreenfootImage("images/end.png");
        bg.drawImage(bg, 0, 0);
        setBackground(bg);
        
        addObject(new Trophy(), 690, 270);
        
        addCoins(40, 13, 70);
        
        addPlayer();
    }
    
    private void addCoins(int startX, int count, int spacing) {
        for (int i = 0; i < count; i++) {
            int coinX = startX + i * spacing;
            addObject(new Coin(), coinX, 440);
        }
    }
    
    private void addPlayer() {
        player = new Player(150);
        addObject(player, 0, 400);
    }
}
