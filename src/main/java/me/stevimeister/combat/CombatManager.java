package me.stevimeister.combat;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Player;

/**
 * @author stevimeister on 05/01/2021
 **/
public final class CombatManager {

    private final Map<UUID, Combat> combatMap = new ConcurrentHashMap<>();

    public void addCombat(final Player player, final Combat combat) {
        this.combatMap.put(player.getUniqueId(), combat);
    }

    public void removeCombat(final Player player) {
        if (!this.combatMap.containsKey(player.getUniqueId()))
            return;

        this.combatMap.remove(player.getUniqueId());
    }

    public Combat findCombat(final Player player) {
        return this.combatMap.get(player.getUniqueId());
    }
}


