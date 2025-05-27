import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Level1 here.
 * 
 * @author (your name) Jiayu Chen
 * @version (a version number or a date) 05/26/25
 */
public class Level1 extends World {
    private static final boolean BOUNDED = true;
    private static final int IMAGE_OVERLAP = 30;
    private static final int STARTING_X = -30;

    private final int groundY = getHeight() - 10;
    private final int PLAYER_START_X = 450;
    private final int PLAYER_START_Y = 300;

    public Level1(int width, int height) {
        super(width, height, 1, BOUNDED);

        addStoneTiles();
        addPlayer();
    }

    private void addStoneTiles() {
        Stone sampleStone = new Stone();
        int tileWidth = sampleStone.getTargetWidth() - IMAGE_OVERLAP;
        if (tileWidth <= 0) {
            tileWidth = sampleStone.getTargetWidth();
        }
        int worldWidth = getWidth();
        int tileCount = (worldWidth - STARTING_X + tileWidth - 1) / tileWidth;
    
        for (int i = 0; i < tileCount; i++) {
            int x = STARTING_X + i * tileWidth;
            addObject(new Stone(), x, groundY);
        }
    }

    private void addPlayer() {
        Player player = new Player();
        addObject(player, PLAYER_START_X, PLAYER_START_Y);
    }
}
