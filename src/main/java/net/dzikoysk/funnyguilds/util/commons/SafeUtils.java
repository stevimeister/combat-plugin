package net.dzikoysk.funnyguilds.util.commons;

import me.stevimeister.combat.CombatPlugin;

public final class SafeUtils {

    private static void reportUnsafe(Throwable th) {
        CombatPlugin.getInstance().getLogger().info("Something went wront while handling unsafe");
    }

    public static <T> T safeInit(SafeInitializer<T> initializer) {
        try {
            return initializer.initialize();
        } catch (Exception e) {
            reportUnsafe(e);
            return null;
        }
    }

    @FunctionalInterface
    public interface SafeInitializer<T> {
        T initialize() throws Exception;
    }

    private SafeUtils() {}
}