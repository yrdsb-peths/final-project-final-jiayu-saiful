import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HelpPage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HelpPage extends World
{
    private Label controls = new Label("Player Controls", 60);
    private Label label3 = new Label("Use C to Defend and D to Attack", 40);
    private Label label2 = new Label("Use \u2191 or space to Jump", 40);
    private Label label1 = new Label("Use \u2190 and \u2192 OR A and D to Move", 40);
    
    public HelpPage(int width, int height)
    {    
        super(900, 540, 1);
        
        GreenfootImage bg = new GreenfootImage("images/menu.png");
        setBackground(bg);
        
        addLabel(controls, 100);
        addLabel(label1, 200);
        addLabel(label2, 300);
        addLabel(label3, 400);
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
