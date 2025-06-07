import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;
import greenfoot.Color;

/**
 * Enemy character that moves, tracks the player, attacks, and dies with animations.
 * Handles image offset to account for transparent spacing.
 * 
 * @author Saiful Shaik 
 * @version Updated June 7, 2025
 */
public class Enemy extends Base {
    private GreenfootImage[] movingImagesRight, movingImagesLeft;
    private GreenfootImage[] idleImagesRight, idleImagesLeft;
    private GreenfootImage[] attackImagesRight, attackImagesLeft;
    private GreenfootImage[] deathRight, deathLeft;

    private final int GRAVITY = 1;
    private final int MAX_FALL_SPEED = 10;
    private int moveSpeed;
    private final int PLAYER_BOTTOM_OFFSET = 0;
    private final int DETECTION_RANGE = 100;
    private final int ATTACK_RANGE = 150;

    private int vSpeed = 0;
    private boolean onGround = false;
    private boolean facingRight = true;
    private boolean isAttacking = false;
    private boolean missileFired = false;
    private boolean isFlashing = false;
    private boolean isDead = false;

    private int direction = 1;
    private int moveTimer = 0;
    private int MOVE_CHANGE_INTERVAL;
    private int health = 5;
    private int flashTimer = 0;
    private final int FLASH_DURATION = 5;

    private int animationFrame = 0;
    private int animationTimer = 0;
    private final int ANIMATION_SPEED = 6;

    private int deathFrame = 0;
    private int deathTimer = 0;
    private final int DEATH_ANIMATION_SPEED = 6;

    private int bulletSpawn = 2;
    private int attackCooldown = 0;
    private final int ATTACK_DELAY = 120;

    public int defeated = 0;
    private int imageOffsetX = -20;
    private int imageOffsetY = -30;

    private enum EnemyState { MOVING, ATTACKING, IDLE, DEAD }
    private EnemyState enemyState = EnemyState.IDLE;

    public Enemy(int tWidth) {
        moveSpeed = 1 + Greenfoot.getRandomNumber(3);

        movingImagesRight = loadAnimation("idle", 8, tWidth);
        movingImagesLeft = flipImagesHorizontally(movingImagesRight);
        idleImagesRight = loadAnimation("idle", 8, tWidth);
        idleImagesLeft = flipImagesHorizontally(idleImagesRight);
        attackImagesRight = loadAnimation("attack", 7, tWidth);
        attackImagesLeft = flipImagesHorizontally(attackImagesRight);
        deathRight = loadAnimation("death", 13, tWidth);
        deathLeft = flipImagesHorizontally(deathRight);

        setAdjustedImage(idleImagesRight[0]);
        MOVE_CHANGE_INTERVAL = 60 + Greenfoot.getRandomNumber(120);
    }

    public void act() {
        if (enemyState == EnemyState.DEAD) {
            playDeathAnimation();
            drawHitbox();
            return;
        }

        if (isFlashing) {
            flashTimer--;
            if (flashTimer <= 0) isFlashing = false;
        }

        if (attackCooldown > 0) {
            attackCooldown--;
        }

        double distanceToPlayer = rangeToPlayer();
        if (distanceToPlayer < ATTACK_RANGE) {
            enemyState = EnemyState.ATTACKING;
        } else if (distanceToPlayer < DETECTION_RANGE) {
            enemyState = EnemyState.MOVING;
        } else {
            enemyState = EnemyState.IDLE;
        }

        switch (enemyState) {
            case IDLE: moveRandomly(); break;
            case MOVING: followPlayer(); break;
            case ATTACKING: attackPlayer(); break;
        }

        applyGravity();
        checkGroundCollision();
        updateAnimationState();
        drawHitbox();
    }

    private void drawHitbox() {
        GreenfootImage base = getImage();
        GreenfootImage img = new GreenfootImage(base);
        img.setColor(Color.RED);
        img.drawRect(0, 0, img.getWidth() - 1, img.getHeight() - 1);
        setImage(img);
    }

    private double rangeToPlayer() {
        Player player = getPlayer();
        if (player != null) {
            int dx = player.getX() - getX();
            int dy = player.getY() - getY();
            return Math.sqrt(dx * dx + dy * dy);
        }
        return Double.MAX_VALUE;
    }

    private void attackPlayer() {
        if (!isAttacking && attackCooldown == 0) {
            Player player = getPlayer();
            if (player != null) {
                int dx = player.getX() - getX();
                facingRight = dx > 0;
            }
            isAttacking = true;
            animationFrame = 0;
            animationTimer = 0;
            missileFired = false;
            attackCooldown = ATTACK_DELAY;
        }
    }

    private void moveRandomly() {
        moveTimer++;
        if (moveTimer > MOVE_CHANGE_INTERVAL) {
            direction = Greenfoot.getRandomNumber(2) == 0 ? -1 : 1;
            MOVE_CHANGE_INTERVAL = 60 + Greenfoot.getRandomNumber(120);
            moveTimer = 0;
        }

        int dx = direction * moveSpeed;
        int verticalCheckRange = getImage().getHeight() / 2;
        int horizontalCheckDistance = (getImage().getWidth() / 2) + 1;

        for (int dy = -verticalCheckRange; dy <= verticalCheckRange; dy += 5) {
            Actor barrier = getOneObjectAtOffset(direction * horizontalCheckDistance, dy, Barrier.class);
            if (barrier != null) {
                direction *= -1;
                return;
            }
        }

        setLocation(getX() + dx, getY());
        facingRight = direction > 0;
        isAttacking = false;
    }

    private void followPlayer() {
        Player player = getPlayer();
        if (player == null) return;

        int dx = player.getX() - getX();
        direction = Integer.signum(dx);
        facingRight = direction > 0;

        if (Math.abs(dx) > ATTACK_RANGE) {
            Actor barrier = getOneObjectAtOffset(direction * (getImage().getWidth() / 2 + 1), 0, Barrier.class);
            if (barrier == null) {
                setLocation(getX() + direction * moveSpeed, getY());
            }
        }
    }

    private void updateAnimationState() {
        animationTimer++;

        if (isAttacking) {
            GreenfootImage[] attackSet = facingRight ? attackImagesRight : attackImagesLeft;
            if (animationTimer >= ANIMATION_SPEED) {
                animationTimer = 0;
                if (animationFrame < attackSet.length) {
                    if (animationFrame == bulletSpawn && !missileFired) {
                        launchMissile();
                        missileFired = true;
                    }
                    setAdjustedImage(attackSet[animationFrame]);
                    animationFrame++;
                } else {
                    isAttacking = false;
                    animationFrame = 0;
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
            setAdjustedImage(flashImage);
        } else {
            setAdjustedImage(currentImage);
        }
    }

    private void setAdjustedImage(GreenfootImage img) {
        int offsetX = imageOffsetX;
        int offsetY = imageOffsetY;
    
        int newWidth = img.getWidth() + offsetX * 2;
        int newHeight = img.getHeight() + offsetY * 2;
    
        GreenfootImage adjusted = new GreenfootImage(newWidth, newHeight);
        adjusted.drawImage(img, offsetX, offsetY);
    
        setImage(adjusted);
    }

    private void launchMissile() {
        Player player = getPlayer();
        int missileOffsetX = facingRight ? 40 : -40;
        int missileOffsetY = 5;

        EnemyMissile em = new EnemyMissile(facingRight);
        getWorld().addObject(em, getX() + missileOffsetX, getY() + missileOffsetY);
        em.setRotation((int) Math.toDegrees(Math.atan2(player.getY() - em.getY(), player.getX() - em.getX())));
    }

    private void applyGravity() {
        if (!onGround) {
            vSpeed = Math.min(vSpeed + GRAVITY, MAX_FALL_SPEED);
            setLocation(getX(), getY() + vSpeed);
        }
    }

    private void checkGroundCollision() {
        // Calculate offset where enemy’s feet actually are relative to center (getY)
        int feetOffsetY = getImage().getHeight() / 2 - PLAYER_BOTTOM_OFFSET;
    
        // Check for ground objects below the feet position
        Actor ground = getOneObjectAtOffset(0, feetOffsetY, Grass.class);
        if (ground == null) {
            ground = getOneObjectAtOffset(0, feetOffsetY, Stone.class);
        }
    
        if (ground != null && vSpeed >= 0) {
            // Get top Y of ground (ground center Y - half its height)
            int groundTopY = ground.getY() - ground.getImage().getHeight() / 2;
    
            // Position enemy so feet sit exactly on ground top
            int newEnemyY = groundTopY - (getImage().getHeight() / 2) + PLAYER_BOTTOM_OFFSET;
    
            setLocation(getX(), newEnemyY);
    
            vSpeed = 0;
            onGround = true;
        } else {
            onGround = false;
        }
    }

    private void playDeathAnimation() {
        deathTimer += 2;
        if (deathTimer >= DEATH_ANIMATION_SPEED) {
            deathTimer = 0;
            if (deathFrame < deathRight.length) {
                GreenfootImage[] deathSet = facingRight ? deathRight : deathLeft;
                setAdjustedImage(deathSet[deathFrame]);
                deathFrame++;
            } else {
                getWorld().removeObject(this);
            }
        }
    }

    public void takeDamage() {
        if (isDead) return;

        health--;
        isFlashing = true;
        flashTimer = FLASH_DURATION;

        if (health <= 0) {
            isDead = true;
            enemyState = EnemyState.DEAD;
            deathFrame = 0;
            deathTimer = 0;
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

    private Player getPlayer() {
        World world = getWorld();
        if (world instanceof Level1) {
            return ((Level1) world).getPlayer();
        }
        return null;
    }
}