package today.crafting.easypoi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class POIManager {
    private static final Gson gson = new Gson();
    private final List<POI> pois = new ArrayList<>();
    private final Path destination;

    public POIManager(Path destination) {
        this.destination = destination;
    }

    public void addPOI(POI poi) {
        pois.add(poi);
    }

    public List<POI> getOwned(Player player) {
        return pois.stream()
                .filter(p -> p.getOwner().equals(player.getUniqueId()))
                .collect(Collectors.toList());
    }

    public void removePOI(POI poi) {
        pois.remove(poi);
    }

    public void save() throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(destination, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            gson.toJson(pois, w);
        }
    }

    public void load() throws IOException {
        try (BufferedReader r = Files.newBufferedReader(destination)) {
            List<POI> pois = gson.fromJson(r, new TypeToken<List<POI>>() {}.getType());
            if (pois != null) {
                this.pois.addAll(pois);
            }
        } catch (NoSuchFileException ignored) {} // because we can create it
    }
}
