import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class NPC2 here.
 * 
 * @author (your name) Jiayu
 * @version (a version number or a date) 06/10/25
 */
public class NPC2 extends Base {
    private GreenfootImage[] npcAnimation = new GreenfootImage[4];
    private int animationFrame = 0;
    private int animationTimer = 0;
    private final int ANIMATION_SPEED = 10;
    private final int TARGET_WIDTH = 80;
    private String facing;

    private boolean shopOpened = false;
    private ShopManager shopManager;

    public NPC2(String directory, int npcSize, String facing) {
        this.facing = facing;

        for (int i = 0; i < 4; i++) {
            GreenfootImage img = new GreenfootImage("images/shopkeeper/" + i + ".png");
            scaleImage(img, TARGET_WIDTH);
            if (facing.equals("left")) img.mirrorHorizontally();
            npcAnimation[i] = img;
        }

        setImage(npcAnimation[0]);
    }

    public void act() {
        animate();
    
        if (!ShopManager.shopOpen && Greenfoot.mouseClicked(this)) {
            World currentWorld = getWorld();
            shopManager = new ShopManager(currentWorld);
            shopManager.initializeShop();
            ShopManager.shopOpen = true;
        }
    }

    private void animate() {
        animationTimer++;
        if (animationTimer >= ANIMATION_SPEED) {
            animationTimer = 0;
            animationFrame = (animationFrame + 1) % npcAnimation.length;
            setImage(npcAnimation[animationFrame]);
        }
    }

    private void scaleImage(GreenfootImage img, int targetWidth) {
        int targetHeight = (int)(img.getHeight() * ((double) targetWidth / img.getWidth()));
        img.scale(targetWidth, targetHeight);
    }
}