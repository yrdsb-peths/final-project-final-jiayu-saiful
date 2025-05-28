import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;
import greenfoot.Color;

/**
 * Write a description of class Enemy here.
 * 
 * @author Saiful Shaik 
 * @version May, 28, 2025
 */
public class Enemy extends Base {
    private GreenfootImage[] movingImagesRight, movingImagesLeft;
    private GreenfootImage[] idleImagesRight, idleImagesLeft;
    private GreenfootImage[] attackImagesRight, attackImagesLeft;
    private GreenfootImage[] deathRight, deathLeft;

    private final int GRAVITY = 1;
    private final int MAX_FALL_SPEED = 10;
    private final int MOVE_SPEED = 2;
    private final int PLAYER_BOTTOM_OFFSET = 34;

    private int vSpeed = 0;
    private boolean onGround = false;
    private boolean facingRight = true;
    private boolean isAttacking = false;
    public boolean isHit = false;

    private int animationFrame = 0;
    private int animationTimer = 0;
    private final int ANIMATION_SPEED = 6;

    private int direction = 1;
    private int moveTimer = 0;
    private int MOVE_CHANGE_INTERVAL;

    private Random random = new Random();
    private int health = 5;

    private boolean isDead = false;
    private int deathFrame = 0;
    private int deathTimer = 0;
    private final int DEATH_ANIMATION_SPEED = 6;

    // Flash effect
    private boolean isFlashing = false;
    private int flashTimer = 0;
    private final int FLASH_DURATION = 5;

    public Enemy() {
        int targetWidth = 150;

        movingImagesRight = loadAnimation("idle", 8, targetWidth);
        movingImagesLeft = flipImagesHorizontally(movingImagesRight);

        idleImagesRight = loadAnimation("idle", 8, targetWidth);
        idleImagesLeft = flipImagesHorizontally(idleImagesRight);

        attackImagesRight = loadAnimation("attack", 7, targetWidth);
        attackImagesLeft = flipImagesHorizontally(attackImagesRight);

        deathRight = loadAnimation("death", 13, targetWidth);
        deathLeft = flipImagesHorizontally(deathRight);

        setImage(idleImagesRight[0]);

        MOVE_CHANGE_INTERVAL = 60 + Greenfoot.getRandomNumber(120);
    }

    public void act() {
        if (isDead) {
            playDeathAnimation();
            return;
        }

        if (isFlashing) {
            flashTimer--;
            if (flashTimer <= 0) {
                isFlashing = false;
            }
        }
        moveRandomly();
        applyGravity();
        checkGroundCollision();
        updateAnimationState();
    }

    private void moveRandomly() {
        moveTimer++;
        if (moveTimer > MOVE_CHANGE_INTERVAL) {
            direction = Greenfoot.getRandomNumber(2) == 0 ? -1 : 1;
            MOVE_CHANGE_INTERVAL = 60 + Greenfoot.getRandomNumber(120);
            moveTimer = 0;
        }

        setLocation(getX() + direction * MOVE_SPEED, getY());
        facingRight = direction > 0;
    }

    private void updateAnimationState() {
        animationTimer++;

        if (isAttacking) {
            if (animationTimer >= ANIMATION_SPEED) {
                animationTimer = 0;
                GreenfootImage[] attackSet = facingRight ? attackImagesRight : attackImagesLeft;
                setImage(attackSet[animationFrame]);
                animationFrame++;
                if (animationFrame >= attackSet.length) {
                    isAttacking = false;
                }
            }
            return;
        }

        if (animationTimer >= ANIMATION_SPEED) {
            animationTimer = 0;
            animationFrame++;
        }

        GreenfootImage currentImage;
        if (direction != 0) {
            animationFrame %= movingImagesRight.length;
            currentImage = facingRight ? movingImagesRight[animationFrame] : movingImagesLeft[animationFrame];
        } else {
            animationFrame %= idleImagesRight.length;
            currentImage = facingRight ? idleImagesRight[animationFrame] : idleImagesLeft[animationFrame];
        }

        if (isFlashing) {
            GreenfootImage flashImage = new GreenfootImage(currentImage);
            flashImage.setColor(new Color(255, 100, 100));
            flashImage.fill();
            flashImage.setTransparency(128);
            setImage(flashImage);
        } else {
            setImage(currentImage);
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
        if (ground == null) {
            ground = getOneObjectAtOffset(0, getImage().getHeight() / 2 - PLAYER_BOTTOM_OFFSET, Stone.class);
        }

        if (ground != null && vSpeed >= 0) {
            int groundY = ground.getY() - ground.getImage().getHeight() / 2;
            int enemyHeight = getImage().getHeight();
            setLocation(getX(), groundY - enemyHeight / 2 + PLAYER_BOTTOM_OFFSET);
            vSpeed = 0;
            onGround = true;
        } else {
            onGround = false;
        }
    }

    private GreenfootImage[] loadAnimation(String folderName, int frameCount, int targetWidth) {
        GreenfootImage[] images = new GreenfootImage[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = "images/golem/" + folderName + "/" + i + ".png";
            GreenfootImage img = new GreenfootImage(path);
            scaleImage(img, targetWidth);
            images[i] = img;
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

    private void scaleImage(GreenfootImage img, int targetWidth) {
        int targetHeight = (int)(img.getHeight() * ((double) targetWidth / img.getWidth()));
        img.scale(targetWidth, targetHeight);
    }

    public void takeDamage() {
        if (isDead) return;

        health --;
        isFlashing = true;
        flashTimer = FLASH_DURATION;

        System.out.println("Enemy hit! Health: " + health);

        if (health <= 0) {
            isDead = true;
            deathFrame = 0;
            deathTimer = 0;
        }
    }

    private void playDeathAnimation() {
        deathTimer+=2;
        if (deathTimer >= DEATH_ANIMATION_SPEED) {
            deathTimer = 0;
            if (deathFrame < deathRight.length) {
                GreenfootImage[] deathSet = facingRight ? deathRight : deathLeft;
                setImage(deathSet[deathFrame]);
                deathFrame++;
            } else {
                getWorld().removeObject(this);
            }
        }
    }
}