import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class level0 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class level0 extends World {

    private static final int IMAGE_OVERLAP = 30;
    private static final int STARTING_X = -30;
    private static final int PLAYER_START_X = 300;
    private static final int PLAYER_START_Y = 300;
    private static final int NUM_BACKGROUND_LAYERS = 4;
    
    private final int groundY = getHeight() - 20;

    public level0(int width, int height) {
        super(width, height, 1);
        
        setupBackground(width, height);
        addTrees("00.png", 100, getWidth()-75);
        addTrees("02.png", 300, getWidth()/6);
        
        // Main Front Actors
        addPlayer();
        addGroundTiles();
    }

    private void setupBackground(int width, int height) {
        GreenfootImage background = new GreenfootImage(width, height);

        for (int i = 0; i < NUM_BACKGROUND_LAYERS; i++) {
            GreenfootImage layer = new GreenfootImage("background/background0/" + i + ".png");
            layer.scale(width, height);
            background.drawImage(layer, 0, 0);
        }

        setBackground(background);
    }

    private void addPlayer() {
        Player player = new Player();
        addObject(player, PLAYER_START_X, PLAYER_START_Y);
    }

    private void addGroundTiles() {
        Grass sampleGrass = new Grass();
        int tileWidth = sampleGrass.getTargetWidth() - IMAGE_OVERLAP;
        int worldWidth = getWidth();
        int tileCount = (worldWidth - STARTING_X) / tileWidth + 2;

        for (int i = 0; i < tileCount; i++) {
            int x = STARTING_X + i * tileWidth;
            addObject(new Grass(), x, groundY);
        }
    }
    
    private void addTrees(String fileName, int treeSize, int x) {
        Trees tree = new Trees("images/trees/" + fileName, treeSize);
        int treeHeight = tree.getImage().getHeight();
        int grassHeight = new Grass().getImage().getHeight();
        int grassTopY = groundY - (grassHeight / 2);
        int treeY = grassTopY - (treeHeight / 2);
        addObject(tree, x, treeY);
    }
}