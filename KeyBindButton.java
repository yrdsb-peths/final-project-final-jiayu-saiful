import greenfoot.*;

public class KeyBindButton extends Actor {
    private GreenfootImage baseImage;
    private int fadeLevel = 0;
    private final int maxFade = 80;
    private final int fadeSpeed = 5;

    private boolean keyButton;

    private final int hitboxX = 20;
    private final int hitboxY = 10;
    private final int hitboxWidth = 170;
    private final int hitboxHeight = 40;

    public KeyBindButton(boolean keyButton) {
        this.keyButton = keyButton;

        baseImage = new GreenfootImage("images/button0.png");
        int targetWidth = 210;
        int targetHeight = (int)(baseImage.getHeight() * ((double) targetWidth / baseImage.getWidth()));
        baseImage.scale(targetWidth, targetHeight);

        setImage(new GreenfootImage(baseImage));
    }

    public void act() {
        boolean mouseOver = isMouseOverAccurate();

        if (mouseOver && fadeLevel < maxFade) {
            fadeLevel += fadeSpeed;
        } else if (!mouseOver && fadeLevel > 0) {
            fadeLevel -= fadeSpeed;
        }

        fadeLevel = Math.max(0, Math.min(maxFade, fadeLevel));
        updateImageWithFade(fadeLevel);

        if (Greenfoot.mouseClicked(null) && isClickedInHitbox()) {
            String prompt = keyButton ? "Enter new ATTACK key:" : "Enter new DEFEND key:";
            String input = Greenfoot.ask(prompt);

            if (input != null && input.length() > 0) {
                input = input.toLowerCase();

                String currentAttack = KeybindManager.getAttackKey();
                String currentDefend = KeybindManager.getDefendKey();

                // Check for duplicates
                if ((keyButton && input.equals(currentDefend)) ||
                    (!keyButton && input.equals(currentAttack))) {
                    Greenfoot.ask("Key '" + input + "' is already used. Press OK to continue.");
                    return;
                }

                if (keyButton) {
                    KeybindManager.setAttackKey(input);
                } else {
                    KeybindManager.setDefendKey(input);
                }
            }
        }
    }

    private void updateImageWithFade(int alpha) {
        GreenfootImage img = new GreenfootImage(baseImage);
        if (alpha > 0) {
            img.setColor(new Color(0, 0, 0, alpha));
            img.fillRect(0, 0, img.getWidth(), img.getHeight());
        }
        setImage(img);
    }

    private boolean isMouseOverAccurate() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse == null) return false;

        int mx = mouse.getX();
        int my = mouse.getY();
        int x = getX();
        int y = getY();
        int halfW = getImage().getWidth() / 2;
        int halfH = getImage().getHeight() / 2;

        return (mx >= x - halfW && mx <= x + halfW &&
                my >= y - halfH && my <= y + halfH);
    }

    private boolean isClickedInHitbox() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse == null) return false;

        int imgX = getX() - getImage().getWidth() / 2;
        int imgY = getY() - getImage().getHeight() / 2;

        int mouseX = mouse.getX();
        int mouseY = mouse.getY();

        return (mouseX >= imgX + hitboxX && mouseX <= imgX + hitboxX + hitboxWidth &&
                mouseY >= imgY + hitboxY && mouseY <= imgY + hitboxY + hitboxHeight);
    }
}