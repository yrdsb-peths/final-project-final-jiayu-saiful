import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Level1 here.
 * 
 * @author (your name) Jiayu Chen
 * @version (a version number or a date) 05/26/25
 */
public class Level1 extends World
{
    private static final boolean BOUNDED = true;
    private static final int IMAGE_OVERLAP = 30;
    private static final int STARTING_X = -30;
    
    private final int groundY = getHeight() - 10;
    
    public Level1(int width, int height)
    {    
        super(width, height, 2, BOUNDED); 
        
        // add stone tiles
        addStoneTiles();
    }
    
    private void addStoneTiles() {
        Stone sampleStone = new Stone();
        int tileWidth = sampleStone.getTargetWidth() - IMAGE_OVERLAP;
        int worldWidth = getWidth();
        int tileCount = (worldWidth - STARTING_X) / tileWidth + 2;
        for (int i = 0; i < tileCount - 10; i++) {
            int x = STARTING_X + i * tileWidth;
            addObject(new Stone(), x, groundY);
        }
    }
}
