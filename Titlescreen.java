import greenfoot.*;

/**
 * Write a description of class Titlescreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Titlescreen extends World {
    Label playTitle = new Label("Play Game", 30);
    Label optionTitle = new Label("Options", 30);
    Label helpTitle = new Label("Help", 30);
    Label quitTitle = new Label("Quit", 30);
    
    private int buttonOffset = 80;
    private int verticleOffset = 10;
    public Titlescreen() {    
        super(960, 540, 1);
        
        // Play Button Label
        PlayButton playButton = new PlayButton();
        addObject(playButton, getWidth() / 2, (getHeight() / 2) - verticleOffset);
        addObject(playTitle, getWidth() / 2, (getHeight() / 2) - verticleOffset);
        
        
        // Option Button Label
        OptionButton optionButton = new OptionButton();
        addObject(optionButton, getWidth() / 2, (getHeight() / 2) - verticleOffset + buttonOffset);
        addObject(optionTitle, getWidth() / 2, (getHeight() / 2) - verticleOffset + buttonOffset);
        
        
        // Help Button Label
        HelpButton helpButton = new HelpButton();
        addObject(helpButton, getWidth() / 2, (getHeight() / 2) - verticleOffset + (buttonOffset*2));
        addObject(helpTitle, getWidth() / 2, (getHeight() / 2) - verticleOffset + (buttonOffset*2));
        
        
        // Quit Button Label
        QuitButton quitButton = new QuitButton();
        addObject(quitButton, getWidth() / 2, (getHeight() / 2) - verticleOffset + (buttonOffset*3));
        addObject(quitTitle, getWidth() / 2, (getHeight() / 2) - verticleOffset + (buttonOffset*3));
    }
}
