import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;
import greenfoot.Color;

/**
 * Enemy character that moves, tracks the player, attacks, and dies with animations.
 * 
 * @author Saiful Shaik 
 * @version Updated May 30, 2025
 */
public class Enemy extends Base {
    private GreenfootImage[] movingImagesRight, movingImagesLeft;
    private GreenfootImage[] idleImagesRight, idleImagesLeft;
    private GreenfootImage[] attackImagesRight, attackImagesLeft;
    private GreenfootImage[] deathRight, deathLeft;

    private final int GRAVITY = 1;
    private final int MAX_FALL_SPEED = 10;
    private int moveSpeed;
    private final int PLAYER_BOTTOM_OFFSET = 40;

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
<<<<<<< Updated upstream
=======

    // Attack cooldown
    private int attackCooldown = 0;
    private final int ATTACK_DELAY = 120; // 1 second delay

    public int defeated = 0;
>>>>>>> Stashed changes
    
    private enum EnemyState { MOVING, ATTACKING, IDLE, DEAD }
    private EnemyState enemyState = EnemyState.IDLE;

    public Enemy(int tWidth) {
        int targetWidth = tWidth;
        moveSpeed = 1 + Greenfoot.getRandomNumber(4);
        System.out.println(moveSpeed);
        
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
        if (enemyState == EnemyState.DEAD) {
            playDeathAnimation();
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
            case IDLE:
                moveRandomly();
                break;
            case MOVING:
                followPlayer();
                break;
            case ATTACKING:
                attackPlayer();
                break;
            default:
                break;
        }

        applyGravity();
        checkGroundCollision();
        updateAnimationState();
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

            attackCooldown = ATTACK_DELAY; // Set cooldown here
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
    
        Actor obstacle = getOneObjectAtOffset(direction * (getImage().getWidth() / 2 + 1), 0, Grass.class);
        if (obstacle == null) {
            obstacle = getOneObjectAtOffset(direction * (getImage().getWidth() / 2 + 1), 0, Stone.class);
        }
    
        boolean groundAhead = true;
        if (getY() < 430) {  // Only do ledge check if above Y=430
            int checkX = getX() + direction * (getImage().getWidth() / 2);
            int checkY = getY() + getImage().getHeight() / 2 + 5;
    
            groundAhead = !getWorld().getObjectsAt(checkX, checkY, Stone.class).isEmpty()
                       || !getWorld().getObjectsAt(checkX, checkY, Grass.class).isEmpty();
        }
    
        if (obstacle == null && groundAhead) {
            setLocation(getX() + dx, getY());
        } else {
            direction *= -1;
            moveTimer = 0;
            MOVE_CHANGE_INTERVAL = 60 + Greenfoot.getRandomNumber(120);
        }
    
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
            setLocation(getX() + direction * moveSpeed, getY());
        }
    }

    private void updateAnimationState() {
        animationTimer++;

        if (isAttacking) {
            Player player = getPlayer();
            if (player != null) {
                facingRight = player.getX() > getX();
            }

            GreenfootImage[] attackSet = facingRight ? attackImagesRight : attackImagesLeft;

            if (animationTimer >= ANIMATION_SPEED) {
                animationTimer = 0;
                if (animationFrame < attackSet.length) {
                    setImage(attackSet[animationFrame]);

                    if (animationFrame == bulletSpawn && !missileFired) {
                        launchMissile();
                        missileFired = true;
                    }

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
            setImage(flashImage);
        } else {
            setImage(currentImage);
        }
    }

    private void launchMissile() {
        int missileOffsetX = facingRight ? 40 : -40;
        int missileOffsetY = 5;
        getWorld().addObject(new EnemyMissile(facingRight), getX() + missileOffsetX, getY() + missileOffsetY);
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

    private void playDeathAnimation() {
        deathTimer += 2;
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