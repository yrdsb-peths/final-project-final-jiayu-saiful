import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Coin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Coin extends Actor
{
    /**
     * Act - do whatever the Coin wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private GreenfootImage currentImage;
    GreenfootImage[] coinAnimation = new GreenfootImage[4];
    public Coin() {
        int targetWidth = 500;
        
        // Load coin animation frames
        for (int i = 0; i < coinAnimation.length; i++) {
            coinAnimation[i] = new GreenfootImage("images/coin_an/0" + i + ".png");
            scaleImage(coinAnimation[i], targetWidth);
        }
        
        setImage(currentImage);
    }
    
    public void act()
    {
        // Add your action code here.
    }
    
    private void scaleImage(GreenfootImage img, int targetWidth) {
        int targetHeight = (int)(img.getHeight() * ((double) targetWidth / img.getWidth()));
        img.scale(targetWidth, targetHeight);
    }
}
