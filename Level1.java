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
    
    // when boss is dead, load Gameover screen
    private int numberOfEnemies = 10;

    private Player player;
    public UI ui;

    public Level1(int width, int height, int goldCount) {
        super(width, height, 1, BOUNDED);
        Level1.screenWidth = width;
        Level1.screenHeight = height;
        groundY = getHeight() - 10;
    
        setBackground("images/level1bg.png");
    
        addStoneTiles(); // Ground Tiles
        addStoneAndBarriers();
        addCoins(20, 13, 70, 530);
    
        addEnemies();
        
        addPlayer();
    
        ui = new UI(this);
    }
    
    private void addStoneAndBarriers() {
        int[][] positions = {
            {540, 430},
            {420, 430},
            {350, 430},
            
            {730, 380},
            
            {850, 340},
            
            {150, 400},
            {50, 400},
            
            {230, 300},
            {300, 300},
            
            {500, 250},
            {525, 250},
            {550, 250}
        };
    
        // Sort positions for easier block detection
        java.util.Arrays.sort(positions, (a, b) -> a[1] != b[1] ? Integer.compare(a[1], b[1]) : Integer.compare(a[0], b[0]));
    
        java.util.List<int[]> block = new java.util.ArrayList<>();
    
        for (int i = 0; i <= positions.length; i++) {
            if (i == positions.length || (i > 0 && positions[i][1] != positions[i - 1][1]) ||
                (i > 0 && Math.abs(positions[i][0] - positions[i - 1][0]) > targetWidth + 5)) {
                
                // Process previous block
                if (!block.isEmpty()) {
                    for (int[] pos : block) {
                        addObject(new Stone(targetWidth), pos[0], pos[1]);
                    }
                    // Add barriers to edges of this block
                    int[] leftMost = block.get(0);
                    int[] rightMost = block.get(block.size() - 1);
    
                    // Left barrier exactly at left edge
                    addObject(new Barrier(10, 40), leftMost[0] - targetWidth / 2, leftMost[1] - 10);
                    
                    // Right barrier exactly at right edge
                    addObject(new Barrier(10, 40), rightMost[0] + targetWidth / 2, rightMost[1] - 10);
                    block.clear();
                }
            }
            if (i < positions.length) {
                block.add(positions[i]);
            }
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

    private void addEnemies() {
        for (int i = 0; i < numberOfEnemies; i++) {
            int x = Greenfoot.getRandomNumber(getWidth() - 100) + 50;
            int y = 100;
    
            Enemy enemy = new Enemy(targetWidth);
            addObject(enemy, x, y);
        }
    }
    
    public void enemyDied() {
        numberOfEnemies--;
        if (numberOfEnemies <= 0) {
            spawnPortal();
        }
    }
    
    private void spawnPortal() {
        Portal portal = new Portal();
        addObject(portal, screenWidth - 20, groundY - 60);
    }

    public Player getPlayer() {
        return player;
    }
    
    public int getScreenWidth() {
        return screenWidth;
    }
    
    public int getScreenHeight() {
        return screenHeight;
    }
}