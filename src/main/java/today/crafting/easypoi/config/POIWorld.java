package today.crafting.easypoi.config;

import lombok.Value;
import org.bukkit.configuration.ConfigurationSection;

import java.nio.file.Path;
import java.nio.file.Paths;

@Value
public class POIWorld {
    private final String name;
    private final Path poiFile;
    private final Path overviewerMap;
    private final Path overviewerConfig;

    public static POIWorld create(ConfigurationSection section) {
        return new POIWorld(section.getName(), Paths.get(section.getString("poi-file")),
                Paths.get("overviewer-map"), Paths.get(section.getString("overviewer-configuration")));
    }
}
