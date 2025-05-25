import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * UI displays gold, life, and boss labels with counters and icons.
 * 
 * @author Saiful Shaik
 * @version May 25, 2025
 */

public class UI extends Actor {
    private Label goldLabel;
    private Label lifeLabel;
    private Label bossLabel;
    private Label goldCounter;

    private UIimage goldIcon;

    private final int fixedUILabelSize = 23;
    private final int fixedUILabelHeight = 18;

    private int goldCoinsCounter = 0;
    private int playerLives = 5;
    private int bossLives = 10;

    public UI(World world) {
        // Labels
        goldLabel = new Label("-- GOLD --", fixedUILabelSize);
        lifeLabel = new Label("-- LIFE --", fixedUILabelSize);
        bossLabel = new Label("-- BOSS --", fixedUILabelSize);
        goldCounter = new Label(goldCoinsCounter, fixedUILabelSize + 3);
    
        // Gold image icon
        GreenfootImage goldImg = new GreenfootImage("images/coin_an/00.png");
        goldImg.scale(20, 20);
        goldIcon = new UIimage(goldImg);
    
        // Add UI elements to world
        world.addObject(goldLabel, 80, fixedUILabelHeight);
        world.addObject(goldCounter, 65, fixedUILabelHeight + 30);
        world.addObject(goldIcon, 35, fixedUILabelHeight + 30);
        world.addObject(lifeLabel, 250, fixedUILabelHeight);
    
        // Add 5 animated player hearts
        int heartStartX = 200;
        int heartY = fixedUILabelHeight + 30;
        int spacing = 25;
    
        for (int i = 0; i < playerLives; i++) {
            Heart heart = new Heart();
            world.addObject(heart, heartStartX + i * spacing, heartY);
        }
    
        // Add boss label and hearts
        int screenWidth = world.getWidth();
        int labelWidth = bossLabel.getImage().getWidth();
        int bossLabelX = screenWidth - labelWidth / 2 - 15;
        int bossHeartsY = fixedUILabelHeight + 30;
    
        world.addObject(bossLabel, bossLabelX, fixedUILabelHeight);
    
        // Boss hearts below the boss label, spread to the left
        int bossTotalWidth = (bossLives - 1) * spacing;
        int bossHeartStartX = Math.min(bossLabelX - bossTotalWidth / 2, screenWidth - bossTotalWidth - 10);
        bossHeartStartX = Math.max(bossHeartStartX, bossLives) - 10;
        for (int i = 0; i < bossLives; i++) {
            Heart heart = new Heart();
            world.addObject(heart, bossHeartStartX + i * spacing, bossHeartsY);
        }
    }

    public void incrementGoldCounter() {
        goldCoinsCounter++;
        goldCounter.setValue(goldCoinsCounter);
        System.out.println("Gold updated to: " + goldCoinsCounter);
    }
    
}