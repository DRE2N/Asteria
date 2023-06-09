package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.PlacedDecorationWrapper;
import de.erethon.bedrock.chat.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class PickupCommand extends CommandAPICommand implements Listener {

    private final Map<Player, Display> selected = new HashMap<>();

    public PickupCommand() {
        super("pickup");
        withPermission("asteria.pickup");
        withShortDescription("Moves the selected entity");
        withRequirement((sender) -> sender instanceof Player);
        executesPlayer(((sender, args) -> {
            onExecute(args, sender);
        }));
    }

    public void onExecute(CommandArguments args, Player player) {
        Bukkit.getServer().getPluginManager().registerEvents(this, Asteria.getInstance());
        PlacedDecorationWrapper wrapper = Asteria.getInstance().getSelected(player);
        if (wrapper == null) {
            MessageUtil.sendMessage(player, "&cNo entity selected.");
            return;
        }
        if (selected.containsKey(player)) {
            selected.remove(player);
            MessageUtil.sendMessage(player, "&9No longer moving entity");
            return;
        }
        selected.put(player, wrapper.getDisplay());
        MessageUtil.sendMessage(player, "&9Now moving entity\n&7&oRun /asteria pickup again to stop moving it.");
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!selected.containsKey(event.getPlayer())) {
            return;
        }
        Display display = selected.get(event.getPlayer());
        Vector rotation = display.getLocation().getDirection();
        Location loc = event.getTo().clone().add(0, 1.5, 0);
        Location newLoc = loc.add(event.getPlayer().getLocation().getDirection().multiply(1.2));
        newLoc.setDirection(rotation);
        display.teleport(newLoc);
    }
}
