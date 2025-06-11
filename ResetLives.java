import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ResetLives here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import greenfoot.*;

public class ResetLives extends Actor {
    private GreenfootImage baseImage;
    private int fadeLevel = 0;
    private final int maxFade = 80;
    private final int fadeSpeed = 3;
    private final int cost = 15;

    public ResetLives() {
        baseImage = new GreenfootImage("images/reset_hearts.png");
        int targetWidth = 175;
        int targetHeight = (int)(baseImage.getHeight() * ((double) targetWidth / baseImage.getWidth()));
        baseImage.scale(targetWidth, targetHeight);
        setImage(new GreenfootImage(baseImage));
    }

    public void act() {
        boolean mouseOver = isMouseOverAccurate();
        if (mouseOver && fadeLevel < maxFade) fadeLevel += fadeSpeed;
        else if (!mouseOver && fadeLevel > 0) fadeLevel -= fadeSpeed;
        fadeLevel = Math.max(0, Math.min(maxFade, fadeLevel));
        updateImageWithFade(fadeLevel);

        if (Greenfoot.mouseClicked(this) && UI.goldCoinsCounter >= cost) {
            getWorld().removeObject(this);
            UI.goldCoinsCounter -= cost;
            UI.playerLives = 5;
            World world = getWorld();
            if (world != null) {
                UI.fillToFullHearts(world);
            }
        }
    }

    private void updateImageWithFade(int alpha) {
        GreenfootImage img = new GreenfootImage(baseImage);
        if (alpha > 0) {
            img.setColor(new Color(0, 0, 0, alpha));
            img.fillRect(0, 0, img.getWidth(), img.getHeight());
        }
        setImage(img);
    }

    private boolean isMouseOverAccurate() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse == null) return false;
        int mx = mouse.getX(), my = mouse.getY();
        int x = getX(), y = getY();
        int halfW = getImage().getWidth() / 2, halfH = getImage().getHeight() / 2;
        return (mx >= x - halfW && mx <= x + halfW && my >= y - halfH && my <= y + halfH);
    }
}