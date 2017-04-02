package today.crafting.easypoi;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import today.crafting.easypoi.config.POIWorld;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public final class EasyPOI extends JavaPlugin {

    @Getter
    private static EasyPOI plugin;
    @Getter
    private final Map<String, POIManager> worldPOIManagers = new HashMap<>();
    @Getter
    private final Map<String, POIWorld> worldConfiguration = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        // Load configuration
        saveDefaultConfig();
        ConfigurationSection worldsSec = getConfig().getConfigurationSection("worlds");
        for (String s : worldsSec.getKeys(false)) {
            ConfigurationSection worldSec = worldsSec.getConfigurationSection(s);
            POIWorld pw = POIWorld.create(worldSec);
            worldConfiguration.put(s, pw);

            POIManager poiManager = new POIManager(pw.getPoiFile());
            try {
                poiManager.load();
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "Unable to load POI file " + pw.getPoiFile() + " for world " + s, e);
            }
        }

        getServer().getPluginManager().registerEvents(new EasyPOIListener(), this);
        getCommand("poi").setExecutor(this); // todo
    }

    @Override
    public void onDisable() {
        for (POIManager poiManager : worldPOIManagers.values()) {
            try {
                poiManager.save();
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "Unable to save POI file", e);
            }
        }
    }
}
