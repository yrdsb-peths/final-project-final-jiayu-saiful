import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class OptionPage here.
 * 
 * @author (your name) Jiayu Chen
 * @version (a version number or a date) 05/31
 */
public class OptionPage extends World
{
    private Label title = new Label("Options", 60);
    private Label change1 = new Label("V > _", 40);
    private Label change2 = new Label("C > _", 40);
    private Label text1 = new Label("Click to change the attack keybind:", 35);
    private Label text2 = new Label("Click to change the defend keybind:", 35);
    
    public OptionPage(int width, int height)
    {    
        super(900, 540, 1);
        
        GreenfootImage bg = new GreenfootImage("images/menu.png");
        setBackground(bg);
        
        addObject(new BackButton(), 100, 100);
        addObject(new KeyBindButton(), 700, 200);
        addObject(new KeyBindButton(), 700, 400);
        addLabel(title, 450, 100);
        addLabel(change1, 700, 190);
        addLabel(change2, 700, 390);
        addLabel(text1, 300, 200);
        addLabel(text2, 300, 400);
    }
    
    private void styleText(Label label) {
        label.setFillColor(new Color(255, 215, 0));
        label.setLineColor(new Color(0, 0, 0, 0));
        label.setShadowColor(new Color(0, 0, 0, 150));
        label.setShadowOffset(4, 4);
    }
    
    private void addLabel(Label label, int x, int y) {
        styleText(label);
        addObject(label, x, y);
    }
}
