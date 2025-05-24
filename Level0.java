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
    
    // UI
    private int uibgSize = 150;

    private Player player;

    public Level0(int width, int height) {
        super(width, height, 1, BOUNDED);
        
        // Set dimentions for other classes to grab
        Level0.screenWidth = width;
        Level0.screenHeight = height;
        
        // Background
        setupBackground(width, height);
        
        
        // Ground Tiles
        addGroundTiles();
        
        // Trees
        // REFER TO THIS LINE OF CODE IF YOU NEED AN EXAMPLE --> (FOR TASK 2 & 3) <--
        addTrees("03.png", 250, getWidth() / 6);
        
        
        
        // Coins
        addCoinsOnGround(200, 5, 80);
        
        
        
        // Plater
        addPlayer();
        
        
        //UI bar
        UIBackground UIbg = new UIBackground(uibgSize);
        addObject(UIbg, screenWidth/2, 0);
        
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
    
    
    
    /**
     * 1) Comment by Saiful: Add more ground tiles by extending to the right the length of the variable --> tileCount <-- to add objects on top.
     *  ONLY CHANGE THE VARIABLE LISTED
     *  I PUT COMMENTS FOR YOU TO UNDERSTAND HOW THIS METHOD WORKS.
     *  TEXT ME IF YOU NEED HELP FOR ANYTHING!!!
     *  GOOD LUCK! :)
     */
    

    private void addGroundTiles() {
        // This creates a sample Grass object to determine the width of each tile
        Grass sampleGrass = new Grass();
        int tileWidth = sampleGrass.getTargetWidth() - IMAGE_OVERLAP;  // Adjust for overlap between tiles
    
        int worldWidth = getWidth();  // Get the total width of the world
    
        // Calculate how many tiles are needed to fill the screen, including a buffer on both ends
        // Think of how you can make the grass tiles extended to the right.
        //It doesnt matter how long it is, make sure it is long enough to add multiple houses and a npc. 
        int tileCount = (worldWidth - STARTING_X) * 2 / tileWidth + 2;
    
        // Add each Grass tile at the correct horizontal position
        for (int i = 0; i < tileCount; i++) {
            int x = STARTING_X + i * tileWidth;  // Calculate the x position of the tile
            addObject(new Grass(), x, groundY);  // Add the Grass tile at the calculated position and ground level
        }
    }
    
    
    // REFER TO THIS CODE IF YOU NEED HELP --> (FOR TASK 2) <--
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
    
    
    /**
     *  2) Comment by Saiful: Make a method BELOW that adds NPC's on the Map when calling in the constructor class
     *  REMEMBER to add an Actor class exactly named --> NPC <-- under the Base class. Take a look at the class
     *  Coin.java to understand how the animations work for the NPC.
     *  
     *  FOLLOW THE METHOD --> ADDTREES() <-- IF YOU NEED HELP. --> DO NOT LOOK AT OTHER METHODS IT WILL CONFUSE YOU <--
     *  
     *  Hints: Make sure the method has mutliple reasonable parameters to spawn any npc that we call from the constructor.
     *  AMAZING WORK SO FAR, KEEP IT UP. ;)
     */
    
    private void addNPCs(String fileName, int NPCSize, int x) {
        Trees tree = new Trees("images/trees/" + fileName, NPCSize);
        int treeHeight = tree.getImage().getHeight();
        int grassHeight = new Grass().getImage().getHeight();
        int grassTopY = groundY - (grassHeight / 2);
        int treeY = grassTopY - (treeHeight / 2);
        addObject(tree, x, treeY);
    }
    
    
    
    /**
     *  3) Comment by Saiful: Make a method BELOW that adds any Houses on the Map when calling in the constructor class
     *  REMEMBER to add an actor class for this too under the Base class.
     *  This time the houses do not need animations so refer to class Trees.java for help.
     *  
     *  FOLLOW THE METHOD YOU MADE IN TASK 2.
     *  --> NO HINTS GIVEN THIS TIME. <--
     *  
     *  THIS IS YOUR FINAL TASK. WONDERFUL JOB FOR COMPLETING THESE 3 TASKS. :)
     */
    

    public void scrollWorld(int dx) {
        for (Object obj : getObjects(null)) {
            if (!(obj instanceof Player) && !(obj instanceof UI)) {
                Actor actor = (Actor) obj;
                actor.setLocation(actor.getX() + dx, actor.getY());
            }
        }
    }
    
} 