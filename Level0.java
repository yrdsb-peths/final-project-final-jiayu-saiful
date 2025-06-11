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

    private static final int TREE_SIZE = 250;
    private static final int HOUSE_SIZE = 350;
    private static final int COIN_SPACING = 70;
    private static final int COIN_COUNT = 7;
    private static final int COIN_START_X = 485;

    private static final int GRASS_HEIGHT = new Grass().getImage().getHeight();
    private static final int COIN_HEIGHT = new Coin().getImage().getHeight();

    private final int groundY = getHeight() - 10;
    public final int npcX = 950;
    public int npcY;

    public static int setting = 0;

    private Player player;
    public UI ui;
    private static boolean uiInitialized = false;

    public Level0(int width, int height) {
        super(width, height, 1, BOUNDED);

        Level0.screenWidth = width;
        Level0.screenHeight = height;

        // Background
        setupBackground(width, height);

        // Ground Tiles
        addGroundTiles();

        // Trees
        addTrees("03.png", TREE_SIZE, getWidth() / 6);

        // House
        addHouse("house.png", HOUSE_SIZE, 700);

        // Coins
        addCoinsOnGround(COIN_START_X, COIN_COUNT, COIN_SPACING);

        // NPC and Speech
        addNPC("00.png", 100, npcX, "left");
        addNPC2("0.png", 100, 1200, "left");
        addObject(new ShopSpeech(150), 1300, 380);
        switch (setting) {
            case 0 -> addLevel1Speech();
            case 1 -> addBoss1Speech();
            case 2 -> addLevel2Speech();
        }

        // Player
        addPlayer();
        
        addBarriers();

        // UI Background
        UIBackground UIbg = new UIBackground(this, screenWidth, 70);
        addObject(UIbg, screenWidth / 2, 35);

        // UI (hearts, gold, labels, etc.)
        ui = new UI(this);
        if (!uiInitialized) {
            ui.reset(this);
            uiInitialized = true;
        }
    }

    public static void resetUIState() {
        uiInitialized = false;
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
        addObject(new WorldText(), PLAYER_START_X, PLAYER_START_Y + 120);
        player = new Player(150);
        addObject(player, PLAYER_START_X, PLAYER_START_Y);
    }

    private void addGroundTiles() {
        Grass sampleGrass = new Grass();
        int tileWidth = sampleGrass.getTargetWidth() - IMAGE_OVERLAP;
        int worldWidth = getWidth();
        int tileCount = (worldWidth - STARTING_X) * 2 / tileWidth + 2 + 100;

        for (int i = 0; i < tileCount; i++) {
            int x = STARTING_X + i * tileWidth;
            addObject(new Grass(), x-1000, groundY);
        }
    }

    private void addTrees(String fileName, int treeSize, int x) {
        Trees tree = new Trees("images/trees/" + fileName, treeSize);
        int treeY = getYAboveGrass(tree.getImage().getHeight());
        addObject(tree, x, treeY);
    }

    private void addCoinsOnGround(int startX, int count, int spacing) {
        int coinY = getYAboveGrass(COIN_HEIGHT) - 4;
        for (int i = 0; i < count; i++) {
            int coinX = startX + i * spacing;
            addObject(new Coin(), coinX, coinY);
        }
    }

    private void addNPC(String fileName, int npcSize, int x, String facing) {
        NPC npc = new NPC("images/npc/" + fileName, npcSize, facing);
        npcY = getYAboveGrass(npc.getImage().getHeight());
        addObject(npc, x, npcY);
    }
    
    private void addNPC2(String fileName, int npcSize, int x, String facing) {
        NPC2 npc = new NPC2(fileName, npcSize, facing);
        npcY = getYAboveGrass(npc.getImage().getHeight());
        addObject(npc, x, npcY);
    }

    private void addHouse(String fileName, int houseSize, int x) {
        House house = new House(fileName, houseSize);
        
        // Scale image properly
        GreenfootImage img = house.getImage();
        int scaledHeight = houseSize * img.getHeight() / img.getWidth();
        img.scale(houseSize, scaledHeight);

        int houseY = getYAboveGrass(img.getHeight());
        addObject(house, x, houseY);
    }

    private void addBarriers() {
        Barrier leftBarrier = new Barrier(10, screenHeight);
        addObject(leftBarrier, 50, screenHeight / 2);
    
        Barrier rightBarrier = new Barrier(10, screenHeight);
        addObject(rightBarrier, 1600, screenHeight / 2);
    }
    
    private void addLevel1Speech() {
        Speech speech = new Speech();
        addObject(speech, npcX + 80, npcY - 80);
    }

    private void addBoss1Speech() {
        
    }

    private void addLevel2Speech() {
        // Add speech for level 2 here
    }

    private int getYAboveGrass(int objectHeight) {
        return groundY - GRASS_HEIGHT / 2 - objectHeight / 2;
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