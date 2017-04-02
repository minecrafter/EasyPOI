package today.crafting.easypoi;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class EasyPOIListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        for (POIManager manager : EasyPOI.getPlugin().getWorldPOIManagers().values()) {
            List<POI> pois = manager.getOwned(event.getPlayer());
            // update player username
            for (POI poi : pois) {
                poi.setOwnerUsername(event.getPlayer().getName());
            }
        }
    }
}
