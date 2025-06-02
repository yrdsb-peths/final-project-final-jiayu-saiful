import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Level1: Contains player, enemy, and stone ground tiles.
 * 
 * @author Jiayu Chen
 * @version 05/28/25
 */
public class Level1 extends World {
    public static int screenWidth = 0;
    public static int screenHeight = 0;
    private static final boolean BOUNDED = true;
    private static final int IMAGE_OVERLAP = 30;
    private static final int STARTING_X = -30;

    private final int groundY;
    private final int PLAYER_START_X = 450;
    private final int PLAYER_START_Y = 300;
    
    private final int targetWidth = 120;

    private Player player;
    public UI ui;

    public Level1(int width, int height, int goldCount) {
        super(width, height, 1, BOUNDED);
        
        Level1.screenWidth = width;
        Level1.screenHeight = height;
        groundY = getHeight() - 10;
        
        // add background
        setBackground("images/level1bg.png");
        
        // add stone platforms
        addStoneTiles();
        addStone();
        
        // add coins
        addCoins(20, 13, 70, 530);
        
        
        // add enemies
        addEnemy();
        addEnemy();
        
        // add player
        addPlayer();
        
        UIBackground UIbg = new UIBackground(this, screenWidth, 70);
        addObject(UIbg, screenWidth / 2, 35);

        ui = new UI(this);
        //ui.reset(this);
    }
    
    private void addStone() {
        int[][] positions = {
            {540, 430},
            {420, 430},
            {350, 430},
            {730, 380},
            {990, 120},
            {0, 200},
            {230, 100},
            {420, 100}
        };
    
        for (int[] pos : positions) {
            addObject(new Stone(targetWidth), pos[0], pos[1]);
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
    
    private void addCoins(int startX, int count, int spacing, int groundY) {
        int stoneHeight = new Stone(targetWidth).getImage().getHeight();
        int coinY = groundY - (stoneHeight / 2) - (new Coin().getImage().getHeight() / 2);

        for (int i = 0; i < count; i++) {
            int coinX = startX + i * spacing;
            addObject(new Coin(), coinX, coinY - 4);
        }
    }
    
    private void addPlayer() {
        player = new Player(targetWidth);
        addObject(player, PLAYER_START_X, PLAYER_START_Y);
    }

    private void addEnemy() {
        Enemy enemy = new Enemy(targetWidth);
        addObject(enemy, PLAYER_START_X + 200, PLAYER_START_Y);
    }

    public Player getPlayer() {
        return player;
    }
}