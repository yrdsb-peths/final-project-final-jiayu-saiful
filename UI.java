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

    public UI(World world) {
        // Labels
        goldLabel = new Label("-- GOLD --", fixedUILabelSize);
        lifeLabel = new Label("-- LIFE --", fixedUILabelSize);
        bossLabel = new Label("-- BOSS --", fixedUILabelSize);
        goldCounter = new Label(goldCoinsCounter, fixedUILabelSize+3);

        // Gold image icon
        GreenfootImage goldImg = new GreenfootImage("images/coin_an/00.png");
        goldImg.scale(20, 20);
        goldIcon = new UIimage(goldImg);

        // Add UI elements to world
        world.addObject(goldLabel, 80, fixedUILabelHeight);
        world.addObject(goldCounter, 65, fixedUILabelHeight + 30);
        world.addObject(goldIcon, 35, fixedUILabelHeight + 30);
        world.addObject(lifeLabel, 250, fixedUILabelHeight);

        int screenWidth = world.getWidth();
        int labelWidth = bossLabel.getImage().getWidth();
        world.addObject(bossLabel, screenWidth - labelWidth / 2 - 15, fixedUILabelHeight);
    }

    public void incrementGoldCounter() {
        goldCoinsCounter++;
        goldCounter.setValue(goldCoinsCounter);
        System.out.println("Gold updated to: " + goldCoinsCounter);
    }
}