import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Player character that scrolls the world and plays animations
 * @author Jiayu
 * @Modified By Saiful Shaik
 * @version May 22, 2025
 */

public class Player extends Actor {
    private GreenfootImage[] walkImages;
    private GreenfootImage[] idleImages;
    private GreenfootImage[] jumpImages;
    private GreenfootImage currentImage;

    private final int GRAVITY = 1;
    private final int MAX_FALL_SPEED = 10;
    private final int MOVE_SPEED = 3;
    private final int JUMP_STRENGTH = -10;

    private int vSpeed = 0;
    private boolean onGround = false;

    private int animationFrame = 0;
    private int jumpAnimationFrame = 0;
    private int animationTimer = 0;
    private final int ANIMATION_SPEED = 6;

    public Player() {
        int targetWidth = 150;

        // Load walk animation frames
        walkImages = new GreenfootImage[7]; // Adjust to your actual frame count
        for (int i = 0; i < walkImages.length; i++) {
            walkImages[i] = new GreenfootImage("images/knight/knightAnimations/walk/0" + i + ".png");
            scaleImage(walkImages[i], targetWidth);
        }

        // Load idle animation frames
        idleImages = new GreenfootImage[6]; // Adjust to your actual frame count
        for (int i = 0; i < idleImages.length; i++) {
            idleImages[i] = new GreenfootImage("images/knight/knightAnimations/idle/0" + i + ".png");
            scaleImage(idleImages[i], targetWidth);
        }

        // Load jump animation frames
        jumpImages = new GreenfootImage[4]; // Adjust to your actual frame count
        for (int i = 0; i < jumpImages.length; i++) {
            jumpImages[i] = new GreenfootImage("images/knight/knightAnimations/jump/0" + i + ".png");
            scaleImage(jumpImages[i], targetWidth);
        }

        currentImage = idleImages[0];
        setImage(currentImage);
    }

    public void act() {
        handleInput();
        applyGravity();
        checkGroundCollision();
        updateAnimationState();
    }

    private void handleInput() {
        if (Greenfoot.isKeyDown("right")) {
            ((Level0)getWorld()).scrollWorld(-MOVE_SPEED); // Scroll terrain left
        }

        if (Greenfoot.isKeyDown("left")) {
            ((Level0)getWorld()).scrollWorld(MOVE_SPEED); // Scroll terrain right
        }

        if (onGround && Greenfoot.isKeyDown("up")) {
            vSpeed = JUMP_STRENGTH;
            onGround = false;
        }
    }
    
    private void loadWalkAnimation() {
    
    }

    private void updateAnimationState() {
        boolean moving = Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("right");

        animationTimer++;

        if (!onGround) {
            if (animationTimer >= ANIMATION_SPEED) {
                animationTimer = 0;
                jumpAnimationFrame = (jumpAnimationFrame + 1) % jumpImages.length;
                setImage(jumpImages[jumpAnimationFrame]);
            }
            return;
        }

        // Reset jump animation when landing
        jumpAnimationFrame = 0;

        if (moving) {
            if (animationTimer >= ANIMATION_SPEED) {
                animationTimer = 0;
                animationFrame = (animationFrame + 1) % walkImages.length;
                setImage(walkImages[animationFrame]);
            }
        } else {
            if (animationTimer >= ANIMATION_SPEED) {
                animationTimer = 0;
                animationFrame = (animationFrame + 1) % idleImages.length;
                setImage(idleImages[animationFrame]);
            }
        }
    }

    private void applyGravity() {
        if (!onGround) {
            vSpeed = Math.min(vSpeed + GRAVITY, MAX_FALL_SPEED);
            setLocation(getX(), getY() + vSpeed);
        }
    }

    private void checkGroundCollision() {
        Actor ground = getOneIntersectingObject(Grass.class);
        if (ground != null && vSpeed >= 0) {
            while (isTouching(Grass.class)) {
                setLocation(getX(), getY() - 1);
            }
            vSpeed = 0;
            onGround = true;
        } else {
            onGround = false;
        }
    }

    private void scaleImage(GreenfootImage img, int targetWidth) {
        int targetHeight = (int)(img.getHeight() * ((double) targetWidth / img.getWidth()));
        img.scale(targetWidth, targetHeight);
    }
}