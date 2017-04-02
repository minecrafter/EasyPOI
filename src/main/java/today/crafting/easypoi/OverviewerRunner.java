package today.crafting.easypoi;

import org.bukkit.Bukkit;
import today.crafting.easypoi.config.POIWorld;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class OverviewerRunner implements Runnable {
    private final String overviewerPath;
    private final POIWorld world;

    public OverviewerRunner(String overviewerPath, POIWorld world) {
        this.overviewerPath = overviewerPath;
        this.world = world;
    }

    @Override
    public void run() {
        ProcessBuilder builder = new ProcessBuilder(overviewerPath, "--genpoi",
                "--config=" + world.getOverviewerConfig().toAbsolutePath().toString(),
                world.getOverviewerMap().toAbsolutePath().toString());
        Process process;
        try {
            process = builder.start();
        } catch (IOException e) {
            EasyPOI.getPlugin().getLogger().log(Level.SEVERE, "Unable to start Overviewer", e);
            return;
        }

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            EasyPOI.getPlugin().getLogger().log(Level.SEVERE, "Interrupted while waiting on Overviewer to complete", e);
        }
    }
}
