import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Level0: Side-scrolling world with parallax background and terrain.
 * Player stays centered while the world scrolls.
 * 
 * @author Saiful Shaik
 * @version May 22, 2025
 */
public class Level0 extends World {
    public static int screenWidth = 0;
    public static int screenHeight = 0;
    private static final boolean BOUNDED = false;
    private static final int IMAGE_OVERLAP = 30;
    private static final int STARTING_X = -30;
    private static final int PLAYER_START_X = 450 - 15;
    private static final int PLAYER_START_Y = 300;
    private static final int NUM_BACKGROUND_LAYERS = 4;

    private final int groundY = getHeight() - 10;
    private final int coinOffset = 20;
    public final int npcX = 950;
    public int npcYPos;
    public final int npcY = npcYPos;
    
    // UI
    private int uibgSize = 70;
    

    private Player player;
    public UI ui;

    public Level0(int width, int height) {
        super(width, height, 1, BOUNDED);
    
        // Set dimensions for other classes to grab
        Level0.screenWidth = width;
        Level0.screenHeight = height;
    
        // Background
        setupBackground(width, height);
    
        // Ground Tiles
        addGroundTiles();
    
        // Trees
        addTrees("03.png", 250, getWidth() / 6);
    
        // NPC
        addNPC("00.png", 100, npcX, "left");
        
        // Speech
        addSpeech("00.png", 150, "Greeting Sir Knight.", 15);
    
        // Coins
        addCoinsOnGround(200, 5, 80);
    
        // House
        addHouse("house.png", 350, 700);
        addHouse("house.png", 350, 1100);
    
        // Player
        addPlayer();
    
        // UI Background
        UIBackground UIbg = new UIBackground(this, screenWidth, uibgSize);
        addObject(UIbg, screenWidth / 2, uibgSize - 35);
    
        // UI Labels - instantiate UI but DO NOT add UI as an Actor
        ui = new UI(this);
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
        int tileCount = (worldWidth - STARTING_X) * 2 / tileWidth + 2;
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
    
    private void addCoinsOnGround(int startX, int count, int spacing) {
        int grassHeight = new Grass().getImage().getHeight();
        int coinY = groundY - (grassHeight / 2) - (new Coin().getImage().getHeight() / 2);

        for (int i = 0; i < count; i++) {
            int coinX = startX + i * spacing;
            addObject(new Coin(), coinX, coinY - 4);
        }
    }
    
    private void addNPC(String fileName, int npcSize, int x, String facing) {
        NPC npc = new NPC("images/npc/" + fileName, npcSize, facing);
        int npcHeight = npc.getImage().getHeight();
        int grassHeight = new Grass().getImage().getHeight();
        int grassTopY = groundY - (grassHeight / 2);
        int npcYPos = grassTopY - (npcHeight / 2);
        addObject(npc, x, npcYPos);
    }
    
    private void addHouse(String fileName, int houseSize, int x) {
        House house = new House(fileName, houseSize);
        int houseHeight = house.getImage().getHeight() + 30;
        int grassHeight = new Grass().getImage().getHeight();
        int grassTopY = groundY - (grassHeight / 2);
        int houseY = grassTopY - (houseHeight / 3);
        addObject(house, x, houseY);
    }
    
    private void addSpeech(String directory, int speechSize, String text, int textSize) {
        Speech speech = new Speech(directory, speechSize, text, textSize);
        addObject(speech, 100, 100);
    }
    
    public void scrollWorld(int dx) {
        for (Object obj : getObjects(null)) {
            if (obj instanceof Base) {
                Base base = (Base) obj;
                base.setLocation(base.getX() + dx, base.getY());
            }
        }
    }
}