package me.stevimeister.combat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.stevimeister.combat.configuration.impl.MainConfiguration;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * @author stevimeister on 05/01/2021
 **/
public final class CombatPlugin extends JavaPlugin {

    private static CombatPlugin instance;

    private Gson gson;

    private Metrics metrics;
    private MainConfiguration mainConfiguration;
    private CombatManager combatManager;

    @Override
    public void onEnable() {
        instance = this;

        this.metrics = new Metrics(this, 9894);
        this.mainConfiguration = new MainConfiguration();
        this.combatManager = new CombatManager();
        this.gson = new Gson();

        new CombatTask(this);
        new CombatListeners(this);

        this.checkVersion(this.getDescription().getName(), this.getDescription().getVersion());
    }

    public void checkVersion(final String pluginName, final String pluginVersion) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL("https://raw.githubusercontent.com/stevimeister/" + pluginName + "/main/version.json").openStream()))) {
            final String onlineVersion = this.gson.fromJson(bufferedReader.lines().collect(Collectors.joining()), JsonObject.class).get("version").getAsString();
            if (Objects.equals(pluginVersion, onlineVersion))
                return;

            this.getLogger().log(Level.WARNING, "Wtyczka której używasz jest nieaktualna! Pobierz nową wersje z https://github.com/stevimeister/" + pluginName + "/releases/download/" + onlineVersion + "/" + pluginName + "-" + onlineVersion);
        } catch (final IOException e) {
            e.printStackTrace();
        }
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
