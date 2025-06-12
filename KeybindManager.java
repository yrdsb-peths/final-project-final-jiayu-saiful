public class KeybindManager {
    public enum Action {
        ATTACK, DEFEND, JUMP
    }

    private static String attackKey = "v";
    private static String defendKey = "c";
    private static String jumpKey = "space";

    public static String getKey(Action action) {
        switch (action) {
            case ATTACK: return attackKey;
            case DEFEND: return defendKey;
            case JUMP: return jumpKey;
            default: return "";
        }
    }

    public static void setKey(Action action, String newKey) {
        if (newKey == null || newKey.isEmpty()) return;

        newKey = newKey.toLowerCase();
        switch (action) {
            case ATTACK: attackKey = newKey; break;
            case DEFEND: defendKey = newKey; break;
            case JUMP: jumpKey = newKey; break;
        }
    }

    public static void resetToDefaults() {
        attackKey = "v";
        defendKey = "c";
        jumpKey = "space";
    }
}