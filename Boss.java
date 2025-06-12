import greenfoot.*;  
import java.util.Random;

public class Boss extends Base {
    public static boolean isBossDead = false;

    private int health = 15;
    private int damage = 1;

    private int moveTimer = 0;
    private int attackTimer = 0;
    private int direction = 0;

    private Random rand = new Random();

    private Level2 level2World;

    // Cooldown for taking damage to avoid multiple hits during attack animation
    private int damageCooldown = 0;
    private static final int DAMAGE_COOLDOWN_TIME = 60;  // cooldown duration in act cycles

    public Boss() {
        GreenfootImage bossImage = new GreenfootImage("images/golem/idle/0.png");
        bossImage.scale(bossImage.getWidth() * 3, bossImage.getHeight() * 3);
        setImage(bossImage);
    }

    @Override
    protected void addedToWorld(World world) {
        super.addedToWorld(world);
        if (world instanceof Level2) {
            level2World = (Level2) world;
        }
    }

    public void act() {
        super.act();

        // Decrement damage cooldown timer
        if (damageCooldown > 0) {
            damageCooldown--;
        }

        if (health <= 0 && !isBossDead) {
            isBossDead = true;
            Greenfoot.setWorld(new Gameover());
            return;
        }

        handleMovement();
        handleAttack();
    }

    private void handleMovement() {
        moveTimer++;
        if (moveTimer >= 60) {
            // Weighted direction: [0, 1, 1, 1, 2, 3] => 50% down, 16.7% up, 33.3% horizontal
            int[] biasedDirections = {0, 1, 1, 1, 2, 3};
            direction = biasedDirections[rand.nextInt(biasedDirections.length)];
            moveTimer = 0;
        }
    
        int dx = 0, dy = 0;
        switch (direction) {
            case 0: dy = -2; break;
            case 1: dy = 2; break;
            case 2: dx = -2; break;
            case 3: dx = 2; break;
        }
    
        int newX = getX() + dx;
        int newY = getY() + dy;
    
        // Prevent moving above Y=350
        if (newY < 350) newY = 350;
    
        setLocation(newX, newY);
    }

    private void handleAttack() {
        attackTimer++;
        if (attackTimer >= 120) {
            attackMissiles();
            attackTimer = 0;
        }
    }

    private void attackMissiles() {
        World world = getWorld();
        if (world == null) return;

        int x = getX();
        int y = getY();

        world.addObject(new EnemyMissile(false), x - 50, y);
        world.addObject(new EnemyMissile(true), x, y - 50);
        world.addObject(new EnemyMissile(true), x + 50, y);
    }

    public int getHealth() {
        return health;
    }

    public void receiveDamage(int amount) {
        health -= amount;
        if (level2World != null && level2World.ui != null) {
            level2World.ui.updateBossHearts(health);
        }
    }

    public int getDamage() {
        return damage;
    }
}