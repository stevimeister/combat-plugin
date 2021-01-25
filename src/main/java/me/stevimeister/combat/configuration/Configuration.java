package me.stevimeister.combat.configuration;

import me.stevimeister.combat.CombatPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.Field;

/**
 * @author stevimeister on 05/01/2021
 **/
public abstract class Configuration {

    private final File file;

    public Configuration(final String fileName) {
        this.file = new File(CombatPlugin.getInstance().getDataFolder(), fileName);
    }

    public void load() {
        try {
            if (!CombatPlugin.getInstance().getDataFolder().exists() && !CombatPlugin.getInstance().getDataFolder().mkdir())
                return;

            if (!this.file.exists())
                CombatPlugin.getInstance().saveResource(this.file.getName(), true);

            final YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(this.file);
            for (final Field field : this.getClass().getDeclaredFields()) {
                if (!field.isAnnotationPresent(ConfigurationOption.class))
                    continue;

                if (!field.isAccessible())
                    field.setAccessible(true);

                final ConfigurationOption configurationOption = field.getDeclaredAnnotation(ConfigurationOption.class);
                field.set(this, yamlConfiguration.get(configurationOption.value().isEmpty() ?
                        field.getName().replace("_", ".") :
                        configurationOption.value()));
            }
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
}

