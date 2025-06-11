import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;
import java.util.List;
import java.awt.Rectangle;

/**
 * Player character that scrolls the world and plays animations
 * @author By Saiful Shaik
 * @version May 28, 2025
 */
public class Player extends Actor {
    private GreenfootImage[] walkImagesRight, walkImagesLeft;
    private GreenfootImage[] idleImagesRight, idleImagesLeft;
    private GreenfootImage[] jumpImagesRight, jumpImagesLeft;
    private GreenfootImage[][] attackImagesRight, attackImagesLeft;
    private GreenfootImage[] defendImagesRight, defendImagesLeft;
    private GreenfootImage[] deathImagesRight, deathImagesLeft;
    private GreenfootImage currentImage;
    private GreenfootSound deathSound = new GreenfootSound("death.mp3");
    private GreenfootSound attackSound = new GreenfootSound("attack.mp3");
    
    private final int GRAVITY = 1;
    private final int MAX_FALL_SPEED = 10;
    public static int MOVE_SPEED = 3;
    private final int JUMP_STRENGTH = -15;
    private final int PLAYER_BOTTOM_OFFSET = 34;

    private int vSpeed = 0;
    private boolean onGround = false;
    private boolean facingRight = true;
    public boolean isAttacking = false;
    private boolean attackHitRegistered = false;
    private boolean isDefending = false;
    public boolean isHit = false;

    private int animationFrame = 0;
    private int jumpAnimationFrame = 0;
    private int animationTimer = 0;
    private final int ANIMATION_SPEED = 6;

    private int attackType = 0;
    private int attackFrame = 0;
    private final int[] attackFrameCounts = {5, 4, 5};

    private Random random = new Random();
    
    private boolean isDead = false;
    private int deathFrame = 0;

    public Player(int tWidth) {
        int targetWidth = tWidth;

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

        defendImagesRight = loadAnimation("defend", 5, targetWidth);
        defendImagesLeft = flipImagesHorizontally(defendImagesRight);
        
        deathImagesRight = loadAnimation("death", 11, targetWidth);
        deathImagesLeft = flipImagesHorizontally(deathImagesRight);

        currentImage = idleImagesRight[0];
        setImage(currentImage);
    }

    public void act() {
        if (isDead) {
            playDeathAnimation();
            MusicManager.setSFXVolume(deathSound);
            deathSound.play();
            return;
        }
    
        handleInput();
        applyGravity();
        checkGroundCollision();
        updateAnimationState();
        //drawHitbox();
    }

    private void handleInput() {
        World world = getWorld();
        if ((Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")) && world instanceof Level0) {
            ((Level0) world).scrollWorld(-MOVE_SPEED);
            facingRight = true;
        } else if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")) {
            setLocation(getX() + MOVE_SPEED, getY());
            facingRight = true;
        }

        if ((Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")) && world instanceof Level0) {
            ((Level0) world).scrollWorld(MOVE_SPEED);
            facingRight = false;
        } else if (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")) {
            setLocation(getX() - MOVE_SPEED, getY());
            facingRight = false;
        }

        if (Greenfoot.isKeyDown(KeybindManager.getAttackKey()) && !isAttacking) {
            MusicManager.setSFXVolume(attackSound);
            attackSound.play();
            isAttacking = true;
            attackHitRegistered = false;
            attackType = random.nextInt(3);
            attackFrame = 0;
        }
        
        if (Greenfoot.isKeyDown(KeybindManager.getDefendKey())) {
            isDefending = true;
            animationFrame = 0;
        } else {
            isDefending = false;
        }

        if (onGround && (Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("space"))) {
            vSpeed = JUMP_STRENGTH;
            onGround = false;
        }
    }

    private void updateAnimationState() {
        boolean moving = Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("d");
        animationTimer++;

        if (isAttacking) {
            if (animationTimer >= ANIMATION_SPEED) {
                animationTimer = 0;
                GreenfootImage[] attackSet = facingRight ? attackImagesRight[attackType] : attackImagesLeft[attackType];
                setImage(attackSet[attackFrame]);

                if (attackFrame == 2 && !attackHitRegistered) {
                    int attackRange = 100;
                    List<Enemy> enemies = getObjectsInRange(attackRange, Enemy.class);

                    for (Enemy enemy : enemies) {
                        if (enemy != null && !enemy.getIsDead()) {
                            enemy.takeDamage();
                            attackHitRegistered = true;
                            break;
                        }
                    }
                }

                attackFrame++;
                if (attackFrame >= attackSet.length) {
                    isAttacking = false;
                    attackHitRegistered = false;
                }
            }
            return;
        }

        if (isDefending) {
            if (isHit) {
                if (animationTimer >= ANIMATION_SPEED) {
                    animationTimer = 0;
                    GreenfootImage[] defendSet = facingRight ? defendImagesRight : defendImagesLeft;
                    animationFrame = (animationFrame + 1) % defendSet.length;
                    setImage(defendSet[animationFrame]);
                }
            } else {
                int staticFrame = Math.min(1, defendImagesRight.length - 1);
                GreenfootImage defendFrame = facingRight ? defendImagesRight[staticFrame] : defendImagesLeft[staticFrame];
                setImage(defendFrame);
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
        int footY = getImage().getHeight() / 2 - PLAYER_BOTTOM_OFFSET;
        int checkDistance = getImage().getWidth() / 2 - 50;
    
        Actor groundLeft = getOneObjectAtOffset(-checkDistance, footY, Grass.class);
        Actor groundRight = getOneObjectAtOffset(checkDistance, footY, Grass.class);
        Actor ground = (groundLeft != null) ? groundLeft : groundRight;
    
        if (ground == null) {
            groundLeft = getOneObjectAtOffset(-checkDistance, footY, Stone.class);
            groundRight = getOneObjectAtOffset(checkDistance, footY, Stone.class);
            ground = (groundLeft != null) ? groundLeft : groundRight;
        }
    
        if (ground != null && vSpeed >= 0) {
            int groundTopY = ground.getY() - ground.getImage().getHeight() / 2;
            int playerHeight = getImage().getHeight();
            setLocation(getX(), groundTopY - playerHeight / 2 + PLAYER_BOTTOM_OFFSET);
            vSpeed = 0;
            onGround = true;
        } else {
            onGround = false;
        }
    }

    private GreenfootImage[] loadAnimation(String folderName, int frameCount, int targetWidth) {
        GreenfootImage[] images = new GreenfootImage[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = "images/knight/knightAnimations/" + folderName + "/" + i + ".png";
            GreenfootImage img = new GreenfootImage(path);
            if (img == null) {
                System.out.println("Missing image: " + path);
            }
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
    
    public int getAttackId() {
        return attackType;
    }

    public boolean isAttacking() {
        return isAttacking && attackFrame < attackFrameCounts[attackType];
    }
    
    public void takeDamage() {
        if (UI.playerLives > 0 && !isDefending) {
            ((Level1)getWorld()).ui.decreaseLife(getWorld());
            isHit = true;
            isDefending = true;
            animationFrame = 0;
    
            if (UI.playerLives == 0) {
                deathAnimation();
                return;
            }
        }
    }
    
    private void deathAnimation() {
        isDead = true;
        deathFrame = 0;
        animationTimer = 0;
    }
    
    private void playDeathAnimation() {
        if (animationTimer >= ANIMATION_SPEED) {
            animationTimer = 0;
    
            GreenfootImage[] deathSet = facingRight ? deathImagesRight : deathImagesLeft;
            if (deathFrame < deathSet.length) {
                setImage(deathSet[deathFrame]);
                deathFrame++;
            } else {
                Level0.resetUIState();
                Greenfoot.setWorld(new Level0(900, 540));
                Greenfoot.stop();
            }
        } else {
            animationTimer++;
        }
    }
    
    public Rectangle getHitbox() {
        return new Rectangle(getX() - 35, getY() - 35, 60, 70);
    }
    
    private void drawHitbox() {
        GreenfootImage img = new GreenfootImage(getImage());
        Rectangle hitbox = getHitbox();
        
        int relX = hitbox.x - (getX() - img.getWidth() / 2);
        int relY = hitbox.y - (getY() - img.getHeight() / 2);
    
        img.setColor(Color.RED);
        img.drawRect(relX, relY, hitbox.width, hitbox.height);
        
        setImage(img);
    }
}