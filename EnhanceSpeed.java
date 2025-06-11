import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EnhanceSpeed here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EnhanceSpeed extends Actor
{
    public GreenfootImage baseImage;
    private int fadeLevel = 0;
    private final int maxFade = 80;
    private final int fadeSpeed = 3;

    public EnhanceSpeed() {
        baseImage = new GreenfootImage("images/speed.png");

        int targetWidth = 175;
        int targetHeight = (int)(baseImage.getHeight() * ((double) targetWidth / baseImage.getWidth()));
        baseImage.scale(targetWidth, targetHeight);

        setImage(new GreenfootImage(baseImage));
    }

    public void act() {
        boolean mouseOver = isMouseOverAccurate();

        if (mouseOver && fadeLevel < maxFade) {
            fadeLevel += fadeSpeed;
        } else if (!mouseOver && fadeLevel > 0) {
            fadeLevel -= fadeSpeed;
        }

        fadeLevel = Math.max(0, Math.min(maxFade, fadeLevel));
        updateImageWithFade(fadeLevel);

        if (Greenfoot.mouseClicked(this) && UI.goldCoinsCounter >= 10) {
            getWorld().removeObject(this);
            Player.MOVE_SPEED = 5;
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

        int mx = mouse.getX();
        int my = mouse.getY();
        int x = getX();
        int y = getY();
        int halfW = getImage().getWidth() / 2;
        int halfH = getImage().getHeight() / 2;

        return (mx >= x - halfW && mx <= x + halfW &&
                my >= y - halfH && my <= y + halfH);
    }
}
