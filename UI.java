import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

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
    private final int goldImgSize = 30;

    private static int goldCoinsCounter = 0;
    public static int playerLives;
    private int bossLives = 10;

    private ArrayList<Heart> playerHearts = new ArrayList<>();

    public UI(World world) {
        goldLabel = new Label("-- GOLD --", fixedUILabelSize);
        lifeLabel = new Label("-- LIFE --", fixedUILabelSize);
        bossLabel = new Label("-- BOSS --", fixedUILabelSize);
        goldCounter = new Label(goldCoinsCounter, fixedUILabelSize + 3);

        GreenfootImage goldImg = new GreenfootImage("images/coin_an/00.png");
        goldImg.scale(goldImgSize, goldImgSize);
        goldIcon = new UIimage(goldImg);

        world.addObject(goldLabel, 80, fixedUILabelHeight);
        world.addObject(goldCounter, 65, fixedUILabelHeight + 30);
        world.addObject(goldIcon, 35, fixedUILabelHeight + 30);
        world.addObject(lifeLabel, 250, fixedUILabelHeight);
        playerLives = 5;

        // Player hearts
        int heartStartX = 200;
        int heartY = fixedUILabelHeight + 30;
        int spacing = 25;

        for (int i = 0; i < playerLives; i++) {
            Heart heart = new Heart();
            playerHearts.add(heart);
            world.addObject(heart, heartStartX + i * spacing, heartY);
        }

        // Boss label and hearts
        int screenWidth = world.getWidth();
        int labelWidth = bossLabel.getImage().getWidth();
        int bossLabelX = screenWidth - labelWidth / 2 - 15;
        int bossHeartsY = fixedUILabelHeight + 30;

        world.addObject(bossLabel, bossLabelX, fixedUILabelHeight);

        int spacingBoss = 25;
        int bossTotalWidth = (bossLives - 1) * spacingBoss;
        int bossHeartStartX = Math.min(bossLabelX - bossTotalWidth / 2, screenWidth - bossTotalWidth - 10);
        bossHeartStartX = Math.max(bossHeartStartX, bossLives) - 10;

        for (int i = 0; i < bossLives; i++) {
            Heart heart = new Heart();
            world.addObject(heart, bossHeartStartX + i * spacingBoss, bossHeartsY);
        }
    }

    public void incrementGoldCounter() {
        goldCoinsCounter++;
        goldCounter.setValue(goldCoinsCounter);
    }
    
    public static int getGoldCount() {
        return goldCoinsCounter;
    }
    

    public void decreaseLife(World world) {
        if (playerLives > 0) {
            playerLives--;
            updateHearts(world);
        }
    }

    private void updateHearts(World world) {
        for (int i = 0; i < playerHearts.size(); i++) {
            Heart heart = playerHearts.get(i);
            if (i < playerLives) {
                if (!world.getObjects(Heart.class).contains(heart)) {
                    world.addObject(heart, heart.getX(), heart.getY());
                }
            } else {
                world.removeObject(heart);
            }
        }
    }
    
    public void reset(World world) {
        // Reset gold
        goldCoinsCounter = 0;
        goldCounter.setValue(goldCoinsCounter);
    
        // Reset player lives
        playerLives = 5;
    
        // Remove existing hearts from world
        for (Heart heart : playerHearts) {
            world.removeObject(heart);
        }
        playerHearts.clear();
    
        // Re-add player hearts
        int heartStartX = 200;
        int heartY = fixedUILabelHeight + 30;
        int spacing = 25;
    
        for (int i = 0; i < playerLives; i++) {
            Heart heart = new Heart();
            playerHearts.add(heart);
            world.addObject(heart, heartStartX + i * spacing, heartY);
        }
    }
}