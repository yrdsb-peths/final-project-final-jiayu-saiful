import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Level0: Side-scrolling world with parallax background and terrain.
 * Player stays centered while the world scrolls.
 * 
 * @author Saiful Shaik
 * @version May 22, 2025
 */
public class Level0 extends World {

    private static final int IMAGE_OVERLAP = 30;
    private static final int STARTING_X = -30;
    private static final int PLAYER_START_X = 450-15;
    private static final int PLAYER_START_Y = 300;
    private static final int NUM_BACKGROUND_LAYERS = 4;

    private final int groundY = getHeight() - 10;

    private Player player;

    public Level0(int width, int height) {
        // BUG: Sets fixed boundaries. Collision and ScrollWorld still have problems
        super(width, height, 1);
        
        setupBackground(width, height);
        addGroundTiles();
        addTrees("03.png", 250, getWidth() / 6);
        Coin coin = new Coin();
        int coinX = 200;
        int coinY = groundY - (new Grass().getImage().getHeight() / 2) - (coin.getImage().getHeight() / 2);
        addObject(coin, coinX, coinY);
        
        
        // Player has to be on top of everything
        addPlayer();
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
        player = new Player();
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

    public void scrollWorld(int dx) {
        for (Object obj : getObjects(null)) {
            if (obj != player) {
                Actor actor = (Actor) obj;
                actor.setLocation(actor.getX() + dx, actor.getY());
            }
        }
    }
}
