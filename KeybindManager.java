public class KeybindManager {
    private static String attackKey = "v";
    private static String defendKey = "c";

    public static String getAttackKey() {
        return attackKey;
    }

    public static String getDefendKey() {
        return defendKey;
    }

    public static void setAttackKey(String newKey) {
        if (newKey != null && !newKey.isEmpty()) {
            attackKey = newKey.toLowerCase();
        }
    }

    public static void setDefendKey(String newKey) {
        if (newKey != null && !newKey.isEmpty()) {
            defendKey = newKey.toLowerCase();
        }
    }
}