import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Coin class handles animation and collection logic.
 * Coins are collected when the player is within a certain range.
 * 
 * @author Saiful Shaik 
 * @version May 25, 2025
 */

public class Coin extends Base {
    private GreenfootImage[] coinAnimation = new GreenfootImage[4];
    private int animationFrame = 0;
    private int animationTimer = 0;
    private final int ANIMATION_SPEED = 6;
    private final int TARGET_WIDTH = 23;
    
    private final int interDist = 35;

    public Coin() {
        for (int i = 0; i < 4; i++) {
            GreenfootImage img = new GreenfootImage("images/coin_an/0" + i + ".png");
            scaleImage(img, TARGET_WIDTH);
            coinAnimation[i] = img;
        }
        setImage(coinAnimation[0]);
    }

    public void act() {
        animateCoin();

        Player player = (Player) getOneIntersectingObject(Player.class);
        if (player != null && isCloseTo(player, interDist)) {
            World world = getWorld();
            if (world instanceof Level0) {
                Level0 level = (Level0) world;
                if (level.ui != null) {
                    level.ui.incrementGoldCounter();
                }
            }
            world.removeObject(this);
        }
    }

    private void animateCoin() {
        animationTimer++;
        if (animationTimer >= ANIMATION_SPEED) {
            animationTimer = 0;
            animationFrame = (animationFrame + 1) % coinAnimation.length;
            setImage(coinAnimation[animationFrame]);
        }
    }

    private void scaleImage(GreenfootImage img, int targetWidth) {
        int targetHeight = (int)(img.getHeight() * ((double) targetWidth / img.getWidth()));
        img.scale(targetWidth, targetHeight);
    }

    private boolean isCloseTo(Actor actor, int range) {
        int dx = getX() - actor.getX();
        int dy = getY() - actor.getY();
        return dx * dx + dy * dy <= range * range;
    }
}