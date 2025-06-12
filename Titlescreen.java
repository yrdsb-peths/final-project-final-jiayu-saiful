import greenfoot.*;
import java.awt.Font;

/**
 * Title screen world for the game.
 * Provides interactive menu buttons for navigating to the main game, options, and help pages.
 * 
 * @author Saiful Shaik 
 * @version May 21, 2025
 */
public class Titlescreen extends World {
    // Screen configuration constants
    private static final int SCREEN_WIDTH = 900;
    private static final int SCREEN_HEIGHT = 540;
    private static final int FONT_SIZE = 20;
    private static final int TITLE_SIZE = 80;
    private static final int TITLE_SPACING = 10;
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_OFFSET = 80;
    private static final int VERTICAL_OFFSET = -50;

    // UI components
    private Label version = new Label("V 0.0.1", FONT_SIZE);
    private Label titleTop = new Label("The Hollow", TITLE_SIZE);
    private Label titleBottom = new Label("Quest", TITLE_SIZE);

    private Actor playHitbox, optionHitbox, helpHitbox;
    private Label playLabel, optionLabel, helpLabel;
    private GreenfootImage baseButtonImage;

    public Titlescreen() {    
        super(SCREEN_WIDTH, SCREEN_HEIGHT, 1);

        resetWorld(); // Reset player stats and upgrades when starting from title

        // Load and scale the base button image
        baseButtonImage = new GreenfootImage("images/buttonImages/button0.png");
        int targetHeight = (int)(baseButtonImage.getHeight() * ((double) BUTTON_WIDTH / baseButtonImage.getWidth()));
        baseButtonImage.scale(BUTTON_WIDTH, targetHeight);

        setBackground(new GreenfootImage("images/titlescreen.png"));

        // Add title labels
        styleTitleLabel(titleTop);
        styleTitleLabel(titleBottom);
        addObject(titleTop, getWidth() / 2, 110);
        addObject(titleBottom, getWidth() / 2, 110 + TITLE_SIZE + TITLE_SPACING);

        // Create interactive hitboxes for each button
        playHitbox = createButtonActor();
        optionHitbox = createButtonActor();
        helpHitbox = createButtonActor();

        // Create and style text labels
        playLabel = new Label("Play Game", FONT_SIZE);
        optionLabel = new Label("Options", FONT_SIZE);
        helpLabel = new Label("Help", FONT_SIZE);
        styleMenuLabel(playLabel);
        styleMenuLabel(optionLabel);
        styleMenuLabel(helpLabel);

        // Add all buttons and labels to the world
        addMenuButton(playHitbox, playLabel, 0);
        addMenuButton(optionHitbox, optionLabel, 1);
        addMenuButton(helpHitbox, helpLabel, 2);

        // Version text at bottom-left
        version.setFillColor(new Color(255, 255, 255));
        version.setLineColor(new Color(255, 255, 255, 0));
        addObject(version, 40, getHeight() - 15);
    }

    /**
     * Creates a transparent red-outlined rectangle actor used for detecting clicks on menu items.
     */
    private Actor createButtonActor() {
        Actor hitbox = new Actor() {};
        GreenfootImage img = new GreenfootImage(baseButtonImage);

        // Optionally scale for slightly larger clickable area
        int newWidth = img.getWidth() + 20;
        int newHeight = img.getHeight() + 10;
        img.scale(newWidth, newHeight);

        img.setColor(new Color(255, 0, 0, 0)); // Semi-transparent red
        img.drawRect(0, 0, img.getWidth() - 1, img.getHeight() - 1);
        hitbox.setImage(img);
        return hitbox;
    }

    /**
     * Adds a clickable menu button and its associated label to the screen.
     */
    private void addMenuButton(Actor hitbox, Label label, int index) {
        int y = (getHeight() / 2) - VERTICAL_OFFSET + (BUTTON_OFFSET * index);
        int x = getWidth() / 2;
        addObject(hitbox, x, y);
        addObject(label, x, y);
    }

    /**
     * Applies a gold color and drop shadow to the main title labels.
     */
    private void styleTitleLabel(Label label) {
        label.setFillColor(new Color(255, 215, 0));
        label.setLineColor(new Color(0, 0, 0, 0));
        label.setShadowColor(new Color(0, 0, 0, 150));
        label.setShadowOffset(4, 4);
    }

    /**
     * Applies consistent visual styling to all menu option labels.
     */
    private void styleMenuLabel(Label label) {
        label.setFillColor(new Color(255, 215, 0));
        label.setLineColor(new Color(0, 0, 0, 0));
        label.setShadowColor(new Color(0, 0, 0, 150));
        label.setShadowOffset(3, 3);
    }

    /**
     * Checks for clicks on buttons or labels and transitions to the appropriate page.
     */
    @Override
    public void act() {
        if (Greenfoot.mouseClicked(playHitbox) || isLabelClicked(playLabel)) {
            MusicManager.setSFXVolume(new GreenfootSound("start.mp3"));
            new GreenfootSound("start.mp3").play();
            Greenfoot.setWorld(new Level0(SCREEN_WIDTH, SCREEN_HEIGHT));
        } else if (Greenfoot.mouseClicked(optionHitbox) || isLabelClicked(optionLabel)) {
            MusicManager.buttonClicked();
            Greenfoot.setWorld(new OptionPage(SCREEN_WIDTH, SCREEN_HEIGHT));
        } else if (Greenfoot.mouseClicked(helpHitbox) || isLabelClicked(helpLabel)) {
            MusicManager.buttonClicked();
            Greenfoot.setWorld(new HelpPage(SCREEN_WIDTH, SCREEN_HEIGHT));
        }
    }

    /**
     * Returns true if the mouse is currently over the given label.
     */
    private boolean isLabelClicked(Label label) {
        if (!Greenfoot.mouseClicked(null)) return false;
        MouseInfo mouse = Greenfoot.getMouseInfo();
        return mouse != null && isOverLabel(mouse.getX(), mouse.getY(), label);
    }

    /**
     * Checks if a coordinate is within the bounds of a label's image.
     */
    private boolean isOverLabel(int mx, int my, Label label) {
        GreenfootImage img = label.getImage();
        int labelX = label.getX();
        int labelY = label.getY();
        int halfW = img.getWidth() / 2;
        int halfH = img.getHeight() / 2;

        return mx >= labelX - halfW && mx <= labelX + halfW &&
               my >= labelY - halfH && my <= labelY + halfH;
    }

    public int getScreenWidth() { return SCREEN_WIDTH; }
    public int getScreenHeight() { return SCREEN_HEIGHT; }

    public void started() { MusicManager.playMusic(); }
    public void stopped() { MusicManager.stopMusic(); }

    /**
     * Resets all game progress and upgrades before entering the game from title screen.
     */
    public void resetWorld() {
        KeybindManager.resetToDefaults();
        UI.goldCoinsCounter = 0;
        UI.playerLives = 5;
        Level0.setting = 0;
        Player.MOVE_SPEED = 3;
        Player.ATTACK_RANGE = 100;
        EnhanceSpeed.isBought = false;
        EnhanceAttack.isBought = false;
        ResetLives.isBought = false;
    }
}