import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * UI displays gold, life, and boss labels with counters and icons.
 * 
 * @author Saiful Shaik
 * @version May 25, 2025
 */
import greenfoot.*;  
import java.util.ArrayList;

public class UI extends Actor {
    private Label goldLabel;
    private Label lifeLabel;
    private Label bossLabel;
    private static Label goldCounter;

    private UIimage goldIcon;

    private static int fixedUILabelSize = 23;
    private static int fixedUILabelHeight = 18;
    private final int goldImgSize = 30;

    public static int goldCoinsCounter = 0;
    public static int playerLives = 5;
    private int bossLives = 10;

    private static ArrayList<Heart> playerHearts = new ArrayList<>();

    private static GreenfootImage baseImage = new GreenfootImage("images/UIbg.jpg");

    public UI(World world) {
        GreenfootImage bg = new GreenfootImage(baseImage);
        bg.scale(900, 70);
        setImage(bg);
        world.addObject(this, 450, 35);

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

        setupPlayerHearts(world);

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

    private static void setupPlayerHearts(World world) {
        playerHearts.clear();
        int heartStartX = 200;
        int heartY = fixedUILabelHeight + 30;
        int spacing = 25;

        for (int i = 0; i < playerLives; i++) {
            Heart heart = new Heart();
            playerHearts.add(heart);
            world.addObject(heart, heartStartX + i * spacing, heartY);
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

    private static void updateHearts(World world) {
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

    public static void reset(World world) {
        goldCoinsCounter = 0;
        goldCounter.setValue(goldCoinsCounter);
        playerLives = 5;

        for (Heart heart : playerHearts) {
            world.removeObject(heart);
        }

        setupPlayerHearts(world);
    }
}