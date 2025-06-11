import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ShopSpeech here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShopSpeech extends Base {
    private GreenfootImage baseImage;
    private ShopManager shopManager;

    public ShopSpeech(int speechSize) {
        baseImage = new GreenfootImage("images/shopSpeech.png");

        int targetWidth = speechSize;
        int targetHeight = (int)(baseImage.getHeight() * ((double) targetWidth / baseImage.getWidth()));
        baseImage.scale(targetWidth, targetHeight);

        setImage(new GreenfootImage(baseImage));
    }

    public void act() {
        if (!ShopManager.shopOpen && Greenfoot.mouseClicked(this)) {
            World currentWorld = getWorld();
            shopManager = new ShopManager(currentWorld);
            shopManager.initializeShop();
            ShopManager.shopOpen = true;
        }
    }
}
