import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.Color;

public class OptionPage extends World {
    private int optionChoice = 0;

    // === Layout Constants ===
    private static final int WORLD_WIDTH = 900;
    private static final int WORLD_HEIGHT = 540;

    private static final int HORIZONTAL_PADDING = 40;
    private static final int VERTICAL_PADDING = 30;

    private static final int CENTER_X = WORLD_WIDTH - HORIZONTAL_PADDING - 125;
    private static final int LABEL_LEFT_X = HORIZONTAL_PADDING + 150;

    private static final int TITLE_Y = 80 + VERTICAL_PADDING;
    private static final int ATTACK_Y = 220 + VERTICAL_PADDING;
    private static final int VERTICAL_SPACING = 100;
    private static final int DEFEND_Y = ATTACK_Y + VERTICAL_SPACING;

    // === Font Sizes ===
    private static final int TITLE_FONT_SIZE = 60;
    private static final int LABEL_FONT_SIZE = 25;
    private static final int KEY_LABEL_FONT_SIZE = 25;

    // === UI Components ===
    private Label title = new Label("Options", TITLE_FONT_SIZE);
    private Label attackText = new Label("Attack Key:", LABEL_FONT_SIZE);
    private Label defendText = new Label("Defend Key:", LABEL_FONT_SIZE);
    private Label attackKeyLabel;
    private Label defendKeyLabel;
    private KeyBindButton attackButton;
    private KeyBindButton defendButton;

    // === Clickable buttons ===
    private Actor volumeButton;
    private Actor graphicsButton;
    private Actor keybindButton;

    // === Button labels ===
    private Label volumeLabel;
    private Label graphicsLabel;
    private Label keybindLabel;

    // === Button layout constants ===
    private final int buttonWidth = 180;
    private final int buttonHeight = 60;
    private final int buttonY = 140;
    private final int spacing = 50;
    private final int totalWidth = buttonWidth * 3 + spacing * 2;
    private final int startX = (WORLD_WIDTH - totalWidth) / 2 + buttonWidth / 2;
    private final int labelY = buttonY + buttonHeight / 2 - 25;

    public OptionPage(int width, int height) {
        super(WORLD_WIDTH, WORLD_HEIGHT, 1);
        setBackground(new GreenfootImage("images/menu.png"));

        // Load and scale images
        GreenfootImage volImg = new GreenfootImage("images/OptionsBar.png");
        GreenfootImage gfxImg = new GreenfootImage("images/OptionsBar.png");
        GreenfootImage keysImg = new GreenfootImage("images/OptionsBar.png");

        volImg.scale(buttonWidth, buttonHeight);
        gfxImg.scale(buttonWidth, buttonHeight);
        keysImg.scale(buttonWidth, buttonHeight);

        // Create Actor buttons
        volumeButton = new Actor() {{ setImage(volImg); }};
        graphicsButton = new Actor() {{ setImage(gfxImg); }};
        keybindButton = new Actor() {{ setImage(keysImg); }};

        // Create text labels
        volumeLabel = new Label("Volume", LABEL_FONT_SIZE);
        graphicsLabel = new Label("Graphics", LABEL_FONT_SIZE);
        keybindLabel = new Label("Keybinds", LABEL_FONT_SIZE);
        styleText(volumeLabel);
        styleText(graphicsLabel);
        styleText(keybindLabel);

        // Add buttons and labels to world
        addObject(volumeButton, startX, buttonY);
        addObject(graphicsButton, startX + buttonWidth + spacing, buttonY);
        addObject(keybindButton, startX + 2 * (buttonWidth + spacing), buttonY);

        addObject(volumeLabel, startX, labelY);
        addObject(graphicsLabel, startX + buttonWidth + spacing, labelY);
        addObject(keybindLabel, startX + 2 * (buttonWidth + spacing), labelY);

        reloadOptionsMenu();
    }

    public void act() {
        updateKeybindLabels();

        if (Greenfoot.mouseClicked(null)) {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if (mouse != null) {
                int mx = mouse.getX();
                int my = mouse.getY();

                if (isPointInActor(mx, my, volumeButton)) {
                    MusicManager.buttonClicked();
                    setOptionChoice(0);
                } else if (isPointInActor(mx, my, graphicsButton)) {
                    MusicManager.buttonClicked();
                    setOptionChoice(1);
                } else if (isPointInActor(mx, my, keybindButton)) {
                    MusicManager.buttonClicked();
                    setOptionChoice(2);
                }
            }
        }
    }

    private boolean isPointInActor(int x, int y, Actor actor) {
        if (actor == null) return false;
        int ax = actor.getX();
        int ay = actor.getY();
        GreenfootImage img = actor.getImage();
        if (img == null) return false;

        int width = img.getWidth();
        int height = img.getHeight();

        return x >= ax - width / 2 && x <= ax + width / 2 &&
               y >= ay - height / 2 && y <= ay + height / 2;
    }

    private void setOptionChoice(int choice) {
        if (choice != optionChoice) {
            optionChoice = choice;
            reloadOptionsMenu();
        }
    }

    private void reloadOptionsMenu() {
        removeObjects(getObjects(null));
        setBackground(new GreenfootImage("images/menu.png"));

        noUiChanged();

        switch (optionChoice) {
            case 0 -> addVolumeMenu();
            case 1 -> addGraphicsMenu();
            case 2 -> addKeybindsMenu();
        }
    }

    private void noUiChanged() {
        addObject(new BackButton(), 80, 80);
        addLabel(title, WORLD_WIDTH / 2, TITLE_Y - 50);

        addObject(volumeButton, startX, buttonY);
        addObject(graphicsButton, startX + buttonWidth + spacing, buttonY);
        addObject(keybindButton, startX + 2 * (buttonWidth + spacing), buttonY);

        addObject(volumeLabel, startX, labelY);
        addObject(graphicsLabel, startX + buttonWidth + spacing, labelY);
        addObject(keybindLabel, startX + 2 * (buttonWidth + spacing), labelY);
    }

    private void addKeybindsMenu() {
        attackButton = new KeyBindButton(true);
        defendButton = new KeyBindButton(false);
        attackKeyLabel = new Label("", KEY_LABEL_FONT_SIZE);
        defendKeyLabel = new Label("", KEY_LABEL_FONT_SIZE);

        addObject(attackButton, CENTER_X, ATTACK_Y);
        addObject(defendButton, CENTER_X, DEFEND_Y);
        addObject(attackKeyLabel, CENTER_X, ATTACK_Y);
        addObject(defendKeyLabel, CENTER_X, DEFEND_Y);

        addLabel(attackText, LABEL_LEFT_X, ATTACK_Y);
        addLabel(defendText, LABEL_LEFT_X, DEFEND_Y);

        updateKeybindLabels();
    }

    private void addVolumeMenu() {
        addLabel(new Label("Volume Settings Coming Soon", LABEL_FONT_SIZE), WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
    }

    private void addGraphicsMenu() {
        addLabel(new Label("Graphics Settings Coming Soon", LABEL_FONT_SIZE), WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
    }

    private void updateKeybindLabels() {
        if (attackKeyLabel != null && defendKeyLabel != null) {
            attackKeyLabel.setValue("[" + KeybindManager.getAttackKey().toUpperCase() + "]");
            defendKeyLabel.setValue("[" + KeybindManager.getDefendKey().toUpperCase() + "]");
            styleText(attackKeyLabel);
            styleText(defendKeyLabel);
        }
    }

    private void styleText(Label label) {
        label.setFillColor(new Color(255, 215, 0));
        label.setLineColor(new Color(0, 0, 0, 0));
        label.setShadowColor(new Color(0, 0, 0, 150));
        label.setShadowOffset(3, 3);
    }

    private void addLabel(Label label, int x, int y) {
        styleText(label);
        addObject(label, x, y);
    }
}