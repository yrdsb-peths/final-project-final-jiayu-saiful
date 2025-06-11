import greenfoot.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Author: Saiful Shaik
 * Version June 11, 2025
 */

public class ShopManager {
    private World world;
    private List<Actor> uiElements = new ArrayList<>();
    private Actor backgroundImage;

    public ShopManager(World world) {
        this.world = world;

        backgroundImage = new Actor() {};
        GreenfootImage bgImage = new GreenfootImage("images/shopbg.png");
        backgroundImage.setImage(bgImage);
        world.addObject(backgroundImage, world.getWidth() / 2, world.getHeight() / 2);
    }

    public void initializeShop() {
        UIBackground UIbg = new UIBackground(world, 900, 70);
        addToUI(UIbg, 450, 35);

        UI ui = new UI(world);

        addToUI(new EnhanceAttack(), 160, 330);
        addToUI(new EnhanceSpeed(), 450, 330);
        addToUI(new ResetLives(), 740, 330);

        ExitShopButton exitButton = new ExitShopButton(this);
        addToUI(exitButton, 820, 120);
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

        if (backgroundImage != null) {
            world.removeObject(backgroundImage);
            backgroundImage = null;
        }
    }
}