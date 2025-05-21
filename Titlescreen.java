import greenfoot.*;

/**
 * Write a description of class Titlescreen here.
 * 
 * @author Saiful Shaik 
 * @version May, 21, 2025
 */

public class Titlescreen extends World {
    private static int screenWidth = 900;
    private static int screenHeight = 540;
    private static int fontSize = 20;
    private static int titleSize = 80;
    private static int titleSpacing = 10;
    private static String titleFont = "TimesRoman";

    private Label titleTop = new Label("The Hollow", titleSize);
    private Label titleBottom = new Label("Quest", titleSize);

    private Label playTitle = new Label("Play Game", fontSize);
    private Label optionTitle = new Label("Options", fontSize);
    private Label helpTitle = new Label("Help", fontSize);
    private Label quitTitle = new Label("Quit", fontSize);
    
    private int buttonOffset = 65;
    private int verticalOffset = -20;

    public Titlescreen() {    
        super(screenWidth, screenHeight, 1);

        // Style and place the main title
        styleTitleLabel(titleTop);
        styleTitleLabel(titleBottom);

        addObject(titleTop, getWidth() / 2, 80);
        addObject(titleBottom, getWidth() / 2, 80 + titleSize + titleSpacing);

        // Play Button
        addMenuButton(new PlayButton(), playTitle, 0);
        
        // Options Button
        addMenuButton(new OptionButton(), optionTitle, 1);
        
        // Help Button
        addMenuButton(new HelpButton(), helpTitle, 2);
        
        // Quit Button
        addMenuButton(new QuitButton(), quitTitle, 3);
    }

    private void styleTitleLabel(Label label) {
        label.setFillColor(new Color(255, 215, 0));
        label.setLineColor(new Color(0, 0, 0, 0));
        label.setShadowColor(new Color(0, 0, 0, 150));
        label.setShadowOffset(4, 4);
    }

    private void styleMenuLabel(Label label) {
        label.setFillColor(new Color(255, 215, 0));
        label.setLineColor(new Color(0, 0, 0, 0));
        label.setShadowColor(new Color(0, 0, 0, 150));
        label.setShadowOffset(3, 3);
    }

    private void addMenuButton(Actor button, Label label, int index) {
        int y = (getHeight() / 2) - verticalOffset + (buttonOffset * index);
        addObject(button, getWidth() / 2, y);
        styleMenuLabel(label);
        addObject(label, getWidth() / 2, y);
    }
    
    public int getScreenWidth() {
        return screenWidth;
    }
    
    public int getScreenHeight() {
        return screenHeight;
    }
}