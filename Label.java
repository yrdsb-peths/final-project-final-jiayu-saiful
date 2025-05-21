import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Font;

/**
 * A Label class that allows you to display a textual value on screen.
 * A customizable Label with optional shadow and styled text.
 * 
 * The Label is an actor, so you will need to create it, and then add it to the world
 * in Greenfoot.  If you keep a reference to the Label then you can change the text it
 * displays.  
 *
 * @author Amjad Altadmri and Saiful Shaik
 * @version 1.2
 * Author: Amjad Altadmri + polished By Saiful Shaik
 */
public class Label extends Actor {
    private String value;
    private int fontSize;
    private int fontStyle = Font.PLAIN;
    private Color lineColor = Color.BLACK;
    private Color fillColor = Color.WHITE;
    private boolean shadowEnabled = true;
    private Color shadowColor = new Color(50, 50, 50, 180);
    private int shadowOffsetX = 2;
    private int shadowOffsetY = 2;

    private static final Color transparent = new Color(0, 0, 0, 0);

    public Label(String value, int fontSize) {
        this.value = value;
        this.fontSize = fontSize;
        updateImage();
    }

    public Label(int value, int fontSize) {
        this(Integer.toString(value), fontSize);
    }

    public void setValue(String value) {
        this.value = value;
        updateImage();
    }

    public void setValue(int value) {
        this.value = Integer.toString(value);
        updateImage();
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        updateImage();
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        updateImage();
    }

    public void setShadow(boolean enabled) {
        shadowEnabled = enabled;
        updateImage();
    }

    public void setShadowColor(Color color) {
        shadowColor = color;
        updateImage();
    }

    public void setShadowOffset(int x, int y) {
        shadowOffsetX = x;
        shadowOffsetY = y;
        updateImage();
    }

    private void updateImage() {
        GreenfootImage textImage = new GreenfootImage(value, fontSize, fillColor, transparent, lineColor);
        int width = textImage.getWidth() + shadowOffsetX;
        int height = textImage.getHeight() + shadowOffsetY;

        GreenfootImage finalImage = new GreenfootImage(width, height);
        
        if (shadowEnabled) {
            GreenfootImage shadow = new GreenfootImage(value, fontSize, shadowColor, transparent);
            finalImage.drawImage(shadow, shadowOffsetX, shadowOffsetY);
        }

        finalImage.drawImage(textImage, 0, 0);
        setImage(finalImage);
    }
}