import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * @author Saiful Shaik
 * @version June 11, 2025
 */
public class WorldText extends Base {
    private int alpha = 0;
    private int fadeSpeed = 6;
    private int visibleTime = 100;
    private int timer = 0;
    private boolean fadingIn = true;
    private boolean fadingOut = false;
    private GreenfootImage finalImage;

    public WorldText() {
        String message = "Walk forward and begin your quest";
        Font font = new Font("Arial", true, false, 28);

        GreenfootImage text = new GreenfootImage(message, 28, new Color(255, 215, 0), new Color(0, 0, 0, 0));

        GreenfootImage shadow = new GreenfootImage(message, 28, new Color(0, 0, 0, 100), new Color(0, 0, 0, 0));

        finalImage = new GreenfootImage(text.getWidth() + 2, text.getHeight() + 2);

        finalImage.drawImage(shadow, 1, 1);

        finalImage.drawImage(text, 0, 0);

        setImage(new GreenfootImage(finalImage));
        updateTransparency();
    }

    public void act() {
        if (fadingIn) {
            if (alpha < 255) {
                alpha = Math.min(255, alpha + fadeSpeed);
                updateTransparency();
            } else {
                fadingIn = false;
                timer = 0;
            }
        } else if (!fadingOut) {
            timer++;
            if (timer >= visibleTime) {
                fadingOut = true;
            }
        } else if (fadingOut) {
            if (alpha > 0) {
                alpha = Math.max(0, alpha - fadeSpeed);
                updateTransparency();
            } else {
                getWorld().removeObject(this);
            }
        }
    }

    private void updateTransparency() {
        GreenfootImage img = new GreenfootImage(finalImage);
        img.setTransparency(alpha);
        setImage(img);
    }
}