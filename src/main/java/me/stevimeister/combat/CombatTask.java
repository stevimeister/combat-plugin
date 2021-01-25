package me.stevimeister.combat;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import me.stevimeister.combat.helper.ChatHelper;
import me.stevimeister.combat.helper.CommonHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author stevimeister on 05/01/2021
 **/
public final class CombatTask extends BukkitRunnable {

    private final CombatPlugin plugin;

    public CombatTask(final CombatPlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(this.plugin, 20L, 20L);
    }

    @Override
    public void run() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            final Combat combat = this.plugin.getCombatManager().findCombat(player);

            if (Objects.isNull(combat))
                continue;

            if (combat.getTime() > System.currentTimeMillis()) {
                ChatHelper.sendActionBar(player, this.plugin.getMainConfiguration().getActionBarMessage().replace("{TIME}", CommonHelper.timeToString(combat.getTime() - System.currentTimeMillis()))
                        .replace("{PATTERN}", CommonHelper.repeat((int) TimeUnit.MILLISECONDS.toSeconds(combat.getTime() - System.currentTimeMillis()),
                            this.plugin.getMainConfiguration().getActionBarMaxSymbols(), this.plugin.getMainConfiguration().getActionBarSymbol(), this.plugin.getMainConfiguration().getActionBarColourOne(), this.plugin.getMainConfiguration().getActionBarColourTwo())));
                continue;
            }

            ChatHelper.sendTitle(player, this.plugin.getMainConfiguration().getMessageFinishedTitle(), this.plugin.getMainConfiguration().getMessageFinishedSubTitle());
            this.plugin.getCombatManager().removeCombat(player);
        }
    }
}
