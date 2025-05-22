import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Player character that scrolls the world and plays animations
 * @author Jiayu
 * @Modified By Saiful Shaik
 * @version May 22, 2025
 */

public class Player extends Actor {
    private GreenfootImage[] walkImagesRight, walkImagesLeft;
    private GreenfootImage[] idleImagesRight, idleImagesLeft;
    private GreenfootImage[] jumpImagesRight, jumpImagesLeft;
    private GreenfootImage currentImage;

    private final int GRAVITY = 1;
    private final int MAX_FALL_SPEED = 10;
    private final int MOVE_SPEED = 3;
    private final int JUMP_STRENGTH = -15;
    private final int PLAYER_BOTTOM_OFFSET = 32;

    private int vSpeed = 0;
    private boolean onGround = false;
    private boolean facingRight = true;

    private int animationFrame = 0;
    private int jumpAnimationFrame = 0;
    private int animationTimer = 0;
    private final int ANIMATION_SPEED = 6;

    public Player() {
        int targetWidth = 150;

        // Load walk animation frames
        walkImagesRight = new GreenfootImage[7];
        for (int i = 0; i < walkImagesRight.length; i++) {
            walkImagesRight[i] = new GreenfootImage("images/knight/knightAnimations/walk/0" + i + ".png");
            scaleImage(walkImagesRight[i], targetWidth);
        }
        walkImagesLeft = flipImagesHorizontally(walkImagesRight);

        // Load idle animation frames
        idleImagesRight = new GreenfootImage[6];
        for (int i = 0; i < idleImagesRight.length; i++) {
            idleImagesRight[i] = new GreenfootImage("images/knight/knightAnimations/idle/0" + i + ".png");
            scaleImage(idleImagesRight[i], targetWidth);
        }
        idleImagesLeft = flipImagesHorizontally(idleImagesRight);

        // Load jump animation frames
        jumpImagesRight = new GreenfootImage[4];
        for (int i = 0; i < jumpImagesRight.length; i++) {
            jumpImagesRight[i] = new GreenfootImage("images/knight/knightAnimations/jump/0" + i + ".png");
            scaleImage(jumpImagesRight[i], targetWidth);
        }
        jumpImagesLeft = flipImagesHorizontally(jumpImagesRight);

        currentImage = idleImagesRight[0];
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
            ((Level0)getWorld()).scrollWorld(-MOVE_SPEED);
            facingRight = true;
        }

        if (Greenfoot.isKeyDown("left")) {
            ((Level0)getWorld()).scrollWorld(MOVE_SPEED);
            facingRight = false;
        }

        if (onGround && Greenfoot.isKeyDown("up")) {
            vSpeed = JUMP_STRENGTH;
            onGround = false;
        }
    }

    private void updateAnimationState() {
        boolean moving = Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("right");

        animationTimer++;

        // Jump animation
        if (!onGround) {
            if (animationTimer >= ANIMATION_SPEED) {
                animationTimer = 0;
                jumpAnimationFrame = (jumpAnimationFrame + 1) % jumpImagesRight.length;
            }
            setImage(facingRight ? jumpImagesRight[jumpAnimationFrame] : jumpImagesLeft[jumpAnimationFrame]);
            return;
        }

        // Reset jump frame on landing
        jumpAnimationFrame = 0;

        if (animationTimer >= ANIMATION_SPEED) {
            animationTimer = 0;
            animationFrame++;
        }

        if (moving) {
            animationFrame %= walkImagesRight.length;
            setImage(facingRight ? walkImagesRight[animationFrame] : walkImagesLeft[animationFrame]);
        } else {
            animationFrame %= idleImagesRight.length;
            setImage(facingRight ? idleImagesRight[animationFrame] : idleImagesLeft[animationFrame]);
        }
    }

    private void applyGravity() {
        if (!onGround) {
            vSpeed = Math.min(vSpeed + GRAVITY, MAX_FALL_SPEED);
            setLocation(getX(), getY() + vSpeed);
        }
    }

    private void checkGroundCollision() {
        Actor ground = getOneObjectAtOffset(0, getImage().getHeight() / 2 - PLAYER_BOTTOM_OFFSET, Grass.class);

        if (ground != null && vSpeed >= 0) {
            int groundY = ground.getY() - ground.getImage().getHeight() / 2;
            int playerHeight = getImage().getHeight();
            setLocation(getX(), groundY - playerHeight / 2 + PLAYER_BOTTOM_OFFSET);
            vSpeed = 0;
            onGround = true;
        } else {
            onGround = false;
        }
    }

    private GreenfootImage[] flipImagesHorizontally(GreenfootImage[] originals) {
        GreenfootImage[] flipped = new GreenfootImage[originals.length];
        for (int i = 0; i < originals.length; i++) {
            flipped[i] = new GreenfootImage(originals[i]);
            flipped[i].mirrorHorizontally();
        }
        return flipped;
    }

    public void scaleImage(GreenfootImage img, int targetWidth) {
        int targetHeight = (int)(img.getHeight() * ((double) targetWidth / img.getWidth()));
        img.scale(targetWidth, targetHeight);
    }
}