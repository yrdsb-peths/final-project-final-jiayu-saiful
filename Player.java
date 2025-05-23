import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;

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
    private GreenfootImage[][] attackImagesRight, attackImagesLeft;
    private GreenfootImage[] defendImagesRight, defendImagesLeft;
    private GreenfootImage currentImage;

    private final int GRAVITY = 1;
    private final int MAX_FALL_SPEED = 10;
    private final int MOVE_SPEED = 3;
    private final int JUMP_STRENGTH = -15;
    private final int PLAYER_BOTTOM_OFFSET = 32;

    private int vSpeed = 0;
    private boolean onGround = false;
    private boolean facingRight = true;
    private boolean isAttacking = false;
    private boolean isDefending = false;

    private int animationFrame = 0;
    private int jumpAnimationFrame = 0;
    private int animationTimer = 0;
    private final int ANIMATION_SPEED = 6;

    private int attackType = 0;
    private int attackFrame = 0;

    private final int[] attackFrameCounts = {5, 4, 5};

    private Random random = new Random();

    public Player() {
        int targetWidth = 150;

        walkImagesRight = loadAnimation("walk", 7, targetWidth);
        walkImagesLeft = flipImagesHorizontally(walkImagesRight);

        idleImagesRight = loadAnimation("idle", 6, targetWidth);
        idleImagesLeft = flipImagesHorizontally(idleImagesRight);

        jumpImagesRight = loadAnimation("jump", 4, targetWidth);
        jumpImagesLeft = flipImagesHorizontally(jumpImagesRight);

        attackImagesRight = new GreenfootImage[3][];
        attackImagesLeft = new GreenfootImage[3][];
        attackImagesRight[0] = loadAnimation("attack1", 5, targetWidth);
        attackImagesLeft[0] = flipImagesHorizontally(attackImagesRight[0]);
        attackImagesRight[1] = loadAnimation("attack2", 4, targetWidth);
        attackImagesLeft[1] = flipImagesHorizontally(attackImagesRight[1]);
        attackImagesRight[2] = loadAnimation("attack3", 5, targetWidth);
        attackImagesLeft[2] = flipImagesHorizontally(attackImagesRight[2]);

        defendImagesRight = loadAnimation("defend", 3, targetWidth);
        defendImagesLeft = flipImagesHorizontally(defendImagesRight);

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
            ((Level0) getWorld()).scrollWorld(-MOVE_SPEED);
            facingRight = true;
        }

        if (Greenfoot.isKeyDown("left")) {
            ((Level0) getWorld()).scrollWorld(MOVE_SPEED);
            facingRight = false;
        }

        if (Greenfoot.isKeyDown("a") && !isAttacking) {
            isAttacking = true;
            attackType = random.nextInt(3);
            attackFrame = 0;
        }

        if (Greenfoot.isKeyDown("d") && !isDefending) {
            isDefending = true;
            animationFrame = 0;
        } else if (!Greenfoot.isKeyDown("d")) {
            isDefending = false;
        }

        if (onGround && Greenfoot.isKeyDown("up")) {
            vSpeed = JUMP_STRENGTH;
            onGround = false;
        }
    }

    private void updateAnimationState() {
        boolean moving = Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("right");
        animationTimer++;

        if (isAttacking) {
            if (animationTimer >= ANIMATION_SPEED) {
                animationTimer = 0;
                GreenfootImage[] attackSet = facingRight ? attackImagesRight[attackType] : attackImagesLeft[attackType];
                setImage(attackSet[attackFrame]);
                attackFrame++;
                if (attackFrame >= attackSet.length) {
                    isAttacking = false;
                }
            }
            return;
        }

        if (isDefending) {
            if (animationTimer >= ANIMATION_SPEED) {
                animationTimer = 0;
                GreenfootImage[] defendSet = facingRight ? defendImagesRight : defendImagesLeft;
                animationFrame = (animationFrame + 1) % defendSet.length;
                setImage(defendSet[animationFrame]);
            }
            return;
        }

        if (!onGround) {
            if (animationTimer >= ANIMATION_SPEED) {
                animationTimer = 0;
                jumpAnimationFrame = (jumpAnimationFrame + 1) % jumpImagesRight.length;
            }
            setImage(facingRight ? jumpImagesRight[jumpAnimationFrame] : jumpImagesLeft[jumpAnimationFrame]);
            return;
        }

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

    private GreenfootImage[] loadAnimation(String folderName, int frameCount, int targetWidth) {
        GreenfootImage[] images = new GreenfootImage[frameCount];
        for (int i = 0; i < frameCount; i++) {
            images[i] = new GreenfootImage("images/knight/knightAnimations/" + folderName + "/0" + i + ".png");
            scaleImage(images[i], targetWidth);
        }
        return images;
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