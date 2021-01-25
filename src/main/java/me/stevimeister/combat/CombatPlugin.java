package me.stevimeister.combat;

import me.stevimeister.combat.configuration.impl.MainConfiguration;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author stevimeister on 05/01/2021
 **/
public final class CombatPlugin extends JavaPlugin {

    private static CombatPlugin instance;

    private Metrics metrics;
    private MainConfiguration mainConfiguration;
    private CombatManager combatManager;

    @Override
    public void onEnable() {
        instance = this;

        this.metrics = new Metrics(this, 9894);
        this.mainConfiguration = new MainConfiguration();
        this.combatManager = new CombatManager();

        new CombatTask(this);
        new CombatListeners(this);
    }

    public static CombatPlugin getInstance() {
        return instance;
    }

    public Metrics getMetrics() {
        return this.metrics;
    }

    public MainConfiguration getMainConfiguration() {
        return this.mainConfiguration;
    }

    public CombatManager getCombatManager() {
        return this.combatManager;
    }
}
