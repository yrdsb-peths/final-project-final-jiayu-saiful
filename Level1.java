import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Level1: Contains player, enemy, and stone ground tiles.
 * 
 * @author Jiayu Chen
 * @version 05/28/25
 */
public class Level1 extends World {
    private static final boolean BOUNDED = true;
    private static final int IMAGE_OVERLAP = 30;
    private static final int STARTING_X = -30;

    private final int groundY;
    private final int PLAYER_START_X = 450;
    private final int PLAYER_START_Y = 300;

    private Player player;

    public Level1(int width, int height) {
        super(width, height, 1, BOUNDED);
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
    }
    
    private void addStone() {
        Stone platform = new Stone();
        addObject(new Stone(), 540, 330);
        addObject(new Stone(), 350, 330);
        addObject(new Stone(), 220, 330);
        addObject(new Stone(), 740, 220);
        addObject(new Stone(), 990, 120);
        addObject(new Stone(), 0, 200);
        addObject(new Stone(), 230, 100);
        addObject(new Stone(), 420, 100);
    }
    
    private void addStoneTiles() {
        Stone sampleStone = new Stone();
        int targetWidth = sampleStone.getTargetWidth();
        int tileWidth = targetWidth > IMAGE_OVERLAP ? targetWidth - IMAGE_OVERLAP : targetWidth;

        if (tileWidth <= 0) {
            tileWidth = 50;
        }

        int worldWidth = getWidth();
        int tileCount = (worldWidth - STARTING_X + tileWidth - 1) / tileWidth;

        for (int i = 0; i < tileCount; i++) {
            int x = STARTING_X + i * tileWidth;
            addObject(new Stone(), x, groundY);
        }
    }
    
    private void addCoins(int startX, int count, int spacing, int groundY) {
        int stoneHeight = new Stone().getImage().getHeight();
        int coinY = groundY - (stoneHeight / 2) - (new Coin().getImage().getHeight() / 2);

        for (int i = 0; i < count; i++) {
            int coinX = startX + i * spacing;
            addObject(new Coin(), coinX, coinY - 4);
        }
    }
    
    private void addPlayer() {
        player = new Player();
        addObject(player, PLAYER_START_X, PLAYER_START_Y);
    }

    private void addEnemy() {
        Enemy enemy = new Enemy();
        addObject(enemy, PLAYER_START_X + 200, PLAYER_START_Y);
    }

    public Player getPlayer() {
        return player;
    }
}