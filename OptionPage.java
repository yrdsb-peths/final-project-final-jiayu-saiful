import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class OptionPage here.
 * 
 * @author (your name) Jiayu Chen
 * @version (a version number or a date) 05/31
 */
public class OptionPage extends World
{
    private Label sound = new Label("Options:", 60);
    
    public OptionPage(int width, int height)
    {    
        super(900, 540, 1);
        
        GreenfootImage bg = new GreenfootImage("images/menu.png");
        setBackground(bg);
        
        addLabel(sound, 200);
    }
    
    private void styleText(Label label) {
        label.setFillColor(new Color(255, 215, 0));
        label.setLineColor(new Color(0, 0, 0, 0));
        label.setShadowColor(new Color(0, 0, 0, 150));
        label.setShadowOffset(4, 4);
    }
    
    private void addLabel(Label label, int y) {
        styleText(label);
        addObject(label, 450, y);
    }
}
