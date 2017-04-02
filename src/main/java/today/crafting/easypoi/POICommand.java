package today.crafting.easypoi;

import com.google.common.base.Joiner;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class POICommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "Usage:");
            sender.sendMessage(ChatColor.GREEN + "/poi create <description>" + ChatColor.GRAY + ": create POI marker");
            sender.sendMessage(ChatColor.GREEN + "/poi list" + ChatColor.GRAY + ": list your POI markers");
            sender.sendMessage(ChatColor.GREEN + "/poi delete <num>" + ChatColor.GRAY + ": delete POI marker");
            sender.sendMessage(ChatColor.GREEN + "/poi refresh" + ChatColor.GRAY + ": refresh Overviewer POI markers");
            return true;
        }

        Player player = null;
        POIManager currentManager = null;
        if (sender instanceof Player) {
            player = (Player) sender;
            currentManager = EasyPOI.getPlugin().getWorldPOIManagers().get(player.getWorld().getName());
        }

        switch (args[0]) {
            case "create":
                if (args.length <= 1) {
                    sender.sendMessage(ChatColor.RED + "/poi create <description>");
                    return true;
                }

                if (player == null) {
                    sender.sendMessage(ChatColor.RED + "You must be a player.");
                    return true;
                }

                if (currentManager == null) {
                    sender.sendMessage(ChatColor.RED + "You're not in a POI-enabled world.");
                    return true;
                }

                POI poi1 = new POI(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(),
                        player.getUniqueId(), Joiner.on(' ').join(Arrays.copyOfRange(args, 1, args.length)));
                poi1.setOwnerUsername(player.getName());
                currentManager.addPOI(poi1);
                sender.sendMessage(ChatColor.GREEN + "POI added.");
                return true;
            case "delete":
                if (args.length <= 1) {
                    sender.sendMessage(ChatColor.RED + "/poi delete <num>");
                    return true;
                }

                if (player == null) {
                    sender.sendMessage(ChatColor.RED + "You must be a player.");
                    return true;
                }

                if (currentManager == null) {
                    sender.sendMessage(ChatColor.RED + "You're not in a POI-enabled world.");
                    return true;
                }

                int num;
                try {
                    num = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "You didn't specify a number.");
                    return true;
                }

                List<POI> delPoi = currentManager.getOwned(player);
                if (num < 1 || num > delPoi.size()) {
                    sender.sendMessage(ChatColor.RED + "That number is out of range.");
                    return true;
                }

                currentManager.removePOI(delPoi.get(num - 1));
                sender.sendMessage(ChatColor.RED + "POI removed.");
                return true;
            case "list":
                if (args.length != 1) {
                    sender.sendMessage(ChatColor.RED + "/poi list");
                    return true;
                }

                if (player == null) {
                    sender.sendMessage(ChatColor.RED + "You must be a player.");
                    return true;
                }

                if (currentManager == null) {
                    sender.sendMessage(ChatColor.RED + "You're not in a POI-enabled world.");
                    return true;
                }

                List<POI> listPoi = currentManager.getOwned(player);
                sender.sendMessage(ChatColor.YELLOW + "Your POIs for this world:");
                for (int i = 0; i < listPoi.size(); i++) {
                    POI lpoi = listPoi.get(i);
                    sender.sendMessage((i + 1) + ": " + lpoi.getX() + ", " + lpoi.getY() + ", " + lpoi.getZ() + ": " + lpoi.getDescription());
                }
                return true;
            case "refresh":
                // TODO
                return true;
        }
        return true;
    }
}
