import greenfoot.*;

/**
 * Write a description of class Titlescreen here.
 * 
 * @author Saiful Shaik 
 * @version May, 21, 2025
 */
public class Titlescreen extends World {
    private static int fontSize = 25;
    Label playTitle = new Label("Play Game", fontSize);
    Label optionTitle = new Label("Options", fontSize);
    Label helpTitle = new Label("Help", fontSize);
    Label quitTitle = new Label("Quit", fontSize);
    
    private int buttonOffset = 75;
    private int verticalOffset = 10;
    public Titlescreen() {    
        super(960, 540, 1);
        
        // Play Button Label
        PlayButton playButton = new PlayButton();
        addObject(playButton, getWidth() / 2, (getHeight() / 2) - verticalOffset);
        playTitle.setFillColor(new Color(255, 215, 0));
        playTitle.setLineColor(new Color(0, 0, 0, 0));
        playTitle.setShadowColor(new Color(0, 0, 0, 150)); 
        playTitle.setShadowOffset(3, 3);
        addObject(playTitle, getWidth() / 2, (getHeight() / 2) - verticalOffset);
        
        
        
        // Option Button Label
        OptionButton optionButton = new OptionButton();
        addObject(optionButton, getWidth() / 2, (getHeight() / 2) - verticalOffset + buttonOffset);
        optionTitle.setFillColor(new Color(255, 215, 0));
        optionTitle.setLineColor(new Color(0, 0, 0, 0));
        optionTitle.setShadowColor(new Color(0, 0, 0, 150)); 
        optionTitle.setShadowOffset(3, 3);
        addObject(optionTitle, getWidth() / 2, (getHeight() / 2) - verticalOffset + buttonOffset);
        
        
        // Help Button Label
        HelpButton helpButton = new HelpButton();
        addObject(helpButton, getWidth() / 2, (getHeight() / 2) - verticalOffset + (buttonOffset*2));
        helpTitle.setFillColor(new Color(255, 215, 0));
        helpTitle.setLineColor(new Color(0, 0, 0, 0));
        helpTitle.setShadowColor(new Color(0, 0, 0, 150)); 
        helpTitle.setShadowOffset(3, 3);
        addObject(helpTitle, getWidth() / 2, (getHeight() / 2) - verticalOffset + (buttonOffset*2));
        
        
        // Quit Button Label
        QuitButton quitButton = new QuitButton();
        addObject(quitButton, getWidth() / 2, (getHeight() / 2) - verticalOffset + (buttonOffset*3));
        quitTitle.setFillColor(new Color(255, 215, 0));
        quitTitle.setLineColor(new Color(0, 0, 0, 0));
        quitTitle.setShadowColor(new Color(0, 0, 0, 150)); 
        quitTitle.setShadowOffset(3, 3);
        addObject(quitTitle, getWidth() / 2, (getHeight() / 2) - verticalOffset + (buttonOffset*3));
    }
}
