package me.stevimeister.combat.helper;

import com.google.common.base.Strings;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;

import java.util.concurrent.TimeUnit;

/**
 * @author stevimeister on 05/01/2021
 **/
public final class CommonHelper {

    private CommonHelper() {

    }

    public static String timeToString(final long time) {
        if (time < 1L)
            return "< 1s";

        final long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
        return seconds > 0L ? seconds + "s" : time + "ms";
    }

    public static String repeat(final int current, final int max, final char symbol, final String colorOne, final String colorTwo) {
        return Strings.repeat(colorOne + symbol, max - current)
                + Strings.repeat(colorTwo + symbol, current);
    }

    public static Player getPlayer(final Entity entity) {
        if (entity instanceof Player)
            return (Player) entity;

        if (entity instanceof Projectile) {
            final ProjectileSource projectileSource = ((Projectile) entity).getShooter();

            if (!(projectileSource instanceof Player))
                return null;

            return (Player) projectileSource;
        }

        return null;
    }
}

