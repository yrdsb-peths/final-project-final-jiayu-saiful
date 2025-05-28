import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Level1: Contains player, enemy, and stone ground tiles.
 * 
 * @author Jiayu Chen
 * @version 05/26/25
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

        setBackground("images/level1bg.png");
        addStoneTiles();
        addEnemy();
        addPlayer();
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