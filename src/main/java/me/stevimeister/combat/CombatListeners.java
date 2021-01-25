package me.stevimeister.combat;

import me.stevimeister.combat.helper.ChatHelper;
import me.stevimeister.combat.helper.CommonHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

/**
 * @author stevimeister on 05/01/2021
 **/
public final class CombatListeners implements Listener {

    private final CombatPlugin plugin;

    public CombatListeners(final CombatPlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDamage(final EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        final Player player = CommonHelper.getPlayer(event.getDamager());
        final Player target = (Player) event.getEntity();

        if (Objects.isNull(player))
            return;

        final long combatTime = System.currentTimeMillis() + this.plugin.getMainConfiguration().getCombatTime();
        final Combat combatOne = this.plugin.getCombatManager().findCombat(player);
        final Combat combatTwo = this.plugin.getCombatManager().findCombat(target);

        if (Objects.isNull(combatOne) || Objects.isNull(combatTwo)) {
            this.plugin.getCombatManager().addCombat(player, new Combat(combatTime));
            this.plugin.getCombatManager().addCombat(target, new Combat(combatTime));

            ChatHelper.sendMessage(player, this.plugin.getMainConfiguration().getMessageStarted());
            ChatHelper.sendMessage(target, this.plugin.getMainConfiguration().getMessageStarted());
            return;
        }

        combatOne.setTime(combatTime);
        combatTwo.setTime(combatTime);
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        if (player.hasPermission(this.plugin.getMainConfiguration().getCombatBypassPermission()))
            return;

        if (Objects.nonNull(this.plugin.getCombatManager().findCombat(player))) {
            player.setHealth(0.0D);
            ChatHelper.sendMessage(Bukkit.getOnlinePlayers(), this.plugin.getMainConfiguration().getMessageCombatLogged().replace("{NAME}", player.getName()));
        }
    }

    @EventHandler
    public void onDeath(final PlayerDeathEvent event) {
        this.plugin.getCombatManager().removeCombat(event.getEntity());
    }

    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        if (player.hasPermission(this.plugin.getMainConfiguration().getCombatBypassPermission()) || Objects.isNull(this.plugin.getCombatManager().findCombat(player)))
            return;

        final String message = event.getMessage().split(" ")[0];
        if (this.plugin.getMainConfiguration().getCombatCommands().contains(message.toLowerCase()))
            return;

        ChatHelper.sendMessage(player, this.plugin.getMainConfiguration().getMessageCommandBlocked().replace("{COMMAND}", message));
        event.setCancelled(true);
    }
}
