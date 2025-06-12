import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Gameover here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Gameover extends World
{
    private static final int IMAGE_OVERLAP = 30;
    private static final int STARTING_X = -30;
    private static final int PLAYER_START_X = 450 - 15;
    private static final int PLAYER_START_Y = 300;
    private static final int NUM_BACKGROUND_LAYERS = 4;

    private final int groundY = getHeight() - 65;
    private final int targetWidth = 120;
    
    private Player player;
    public UI ui;
    
    public Gameover()
    {    
        super(900, 540, 1);
        
        GreenfootImage bg = new GreenfootImage("images/end.png");
        bg.drawImage(bg, 0, 0);
        setBackground(bg);
        
        addObject(new Trophy(), 690, 270);
        
        //addGroundTiles();
        addStoneTiles();
        
        // addCoins(80, 12, 70);
        
        addPlayer();

        ui = new UI(this);
        
        UI.goldCoinsCounter += 1000;
    }
    
    private void addCoins(int startX, int count, int spacing) {
        for (int i = 0; i < count; i++) {
            int coinX = startX + i * spacing;
            addObject(new Coin(), coinX, 440);
        }
    }
    
    private void addPlayer() {
        player = new Player(150);
        addObject(player, 30, 300);
    }
    
    private void addGroundTiles() {
        Grass sampleGrass = new Grass();
        int tileWidth = sampleGrass.getTargetWidth() - IMAGE_OVERLAP;
        int worldWidth = getWidth();
        int tileCount = (worldWidth - STARTING_X) * 2 / tileWidth + 2;

        for (int i = 0; i < tileCount; i++) {
            int x = STARTING_X + i * tileWidth;
            addObject(new Grass(), x, groundY);
        }
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
