import greenfoot.*;
import java.util.ArrayList;
import java.util.List;

public class ShopManager {
    private World world;
    private List<Actor> uiElements = new ArrayList<>();
    private Actor backgroundImage;
    private Actor foregroundImage;

    // Global shop state
    public static boolean shopOpen = false;

    // Layout constants
    private static final int UI_HEADER_WIDTH = 750;
    private static final int UI_HEADER_HEIGHT = 60;
    private static final int UI_HEADER_Y = 45;

    private static final int SHOP_ITEM_Y = 300;
    private static final int SHOP_ITEM_SPACING = 220;

    private static final int EXIT_BUTTON_X_OFFSET = 60;
    private static final int EXIT_BUTTON_Y = 60;

    private static final double SHOP_BG_SCALE = 0.9;
    private static final int BG_OFFSET = 50;
    private static final int FG_OFFSET = 55;
    private static final int BACK_OFFSET = 20;
    private static final int BACK_WIDTH = 175;

    public ShopManager(World world) {
        this.world = world;

        ShopManager.shopOpen = true; // <<< Mark shop as open

        // Create background layer
        backgroundImage = new Actor() {};
        GreenfootImage bg = new GreenfootImage("images/UI/BackShopUI.png");
        int bgWidth = (int)(bg.getWidth() * SHOP_BG_SCALE) - BG_OFFSET;
        int bgHeight = (int)(bg.getHeight() * SHOP_BG_SCALE) - BG_OFFSET;
        bg.scale(bgWidth + BACK_WIDTH, bgHeight);
        backgroundImage.setImage(bg);
        world.addObject(backgroundImage, world.getWidth() / 2, world.getHeight() / 2 + BACK_OFFSET);

        // Create foreground layer
        foregroundImage = new Actor() {};
        GreenfootImage fg = new GreenfootImage("images/UI/ShopUI.png");
        fg.scale(bgWidth - FG_OFFSET + BACK_WIDTH, bgHeight - FG_OFFSET);
        foregroundImage.setImage(fg);
        world.addObject(foregroundImage, world.getWidth() / 2, world.getHeight() / 2 + BACK_OFFSET);
    }

    public void initializeShop() {
        int worldWidth = world.getWidth();

        UIBackground UIbg = new UIBackground(world, UI_HEADER_WIDTH, UI_HEADER_HEIGHT);
        addToUI(UIbg, worldWidth / 2, UI_HEADER_Y);

        UI ui = new UI(world);

        addToUI(new EnhanceAttack(), worldWidth / 2 - SHOP_ITEM_SPACING, SHOP_ITEM_Y);
        addToUI(new EnhanceSpeed(),  worldWidth / 2, SHOP_ITEM_Y);
        addToUI(new ResetLives(),    worldWidth / 2 + SHOP_ITEM_SPACING, SHOP_ITEM_Y);

        ExitShopButton exitButton = new ExitShopButton(this);
        addToUI(exitButton, worldWidth - EXIT_BUTTON_X_OFFSET - 65, EXIT_BUTTON_Y + 80);
    }

    private void addToUI(Actor actor, int x, int y) {
        world.addObject(actor, x, y);
        uiElements.add(actor);
    }

    public void closeShopUI() {
        for (Actor actor : uiElements) {
            world.removeObject(actor);
        }
        uiElements.clear();

        if (foregroundImage != null) {
            world.removeObject(foregroundImage);
            foregroundImage = null;
        }

        if (backgroundImage != null) {
            world.removeObject(backgroundImage);
            backgroundImage = null;
        }

        ShopManager.shopOpen = false;
    }
}