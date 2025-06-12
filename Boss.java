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
    private static final int DAMAGE_COOLDOWN_TIME = 30;  // cooldown duration in act cycles

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

        checkHitByPlayer();
        handleMovement();
        handleAttack();
    }

    private void checkHitByPlayer() {
        if (damageCooldown > 0) return;  // Still in cooldown, don't take damage again yet

        World world = getWorld();
        if (world == null) return;

        Player player = (Player) world.getObjects(Player.class).stream().findFirst().orElse(null);
        if (player == null) return;

        if (player.isAttacking()) {
            int attackRange = 100;
            int dx = getX() - player.getX();
            int dy = getY() - player.getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance <= attackRange) {
                receiveDamage(1);
                damageCooldown = DAMAGE_COOLDOWN_TIME;  // start cooldown
            }
        }
    }

    private void handleMovement() {
        moveTimer++;
        if (moveTimer >= 60) {
            direction = rand.nextInt(4);
            moveTimer = 0;
        }

        int dx = 0, dy = 0;
        switch (direction) {
            case 0: dy = -2; break;
            case 1: dy = 2; break;
            case 2: dx = -2; break;
            case 3: dx = 2; break;
        }

        setLocation(getX() + dx, getY() + dy);
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