import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Level2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Level2 extends World
{
    public static int screenWidth = 50;
    public static int screenHeight = 50;
    private static final boolean BOUNDED = true;
    private static final int IMAGE_OVERLAP = 30;
    private static final int STARTING_X = -30;

    private final int groundY;
    private final int PLAYER_START_X = 450;
    private final int PLAYER_START_Y = 300;
    
    private final int targetWidth = 120;

    private Player player;
    public UI ui;

    public Level2()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(900, 540, 1, false); 
        groundY = getHeight() - 10;
        
        //addBoss();
        
        addStoneTiles();
        
        UIBackground UIbg = new UIBackground(this, screenWidth, 70);
        addObject(UIbg, screenWidth / 2, 35);
    
        ui = new UI(this);
    }
    
    private void addStoneTiles() {
        Stone sampleStone = new Stone(targetWidth);
        int targetWidth = sampleStone.getTargetWidth();
        int tileWidth = targetWidth > IMAGE_OVERLAP ? targetWidth - IMAGE_OVERLAP : targetWidth;

        if (tileWidth <= 0) {
            tileWidth = 50;
        }

        int worldWidth = getWidth();
        int tileCount = (worldWidth - STARTING_X + tileWidth - 1) / tileWidth;

        for (int i = 0; i < tileCount; i++) {
            int x = STARTING_X + i * tileWidth;
            addObject(new Stone(targetWidth), x, groundY);
        }
    }
}
