package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.PlacedDecorationWrapper;
import de.erethon.bedrock.chat.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.LocationArgument;
import dev.jorel.commandapi.arguments.LocationType;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MoveCommand extends CommandAPICommand {

    public MoveCommand() {
        super("move");
        withPermission("asteria.move");
        withShortDescription("Moves the selected entity");
        withRequirement((sender) -> sender instanceof Player);
        withOptionalArguments(new LocationArgument("location", LocationType.PRECISE_POSITION));
        executesPlayer(((sender, args) -> {
            onExecute(args, sender);
        }));
    }

    public void onExecute(CommandArguments args, Player player) {
        PlacedDecorationWrapper wrapper = Asteria.getInstance().getSelected(player);
        if (wrapper == null) {
            MessageUtil.sendMessage(player, "&cNo entity selected.");
            return;
        }
        Location playerLoc = player.getLocation();
        Location def = new Location(player.getWorld(), playerLoc.getBlockX() + 0.5f, playerLoc.getBlockY(), playerLoc.getBlockZ() + 0.5f);
        wrapper.getDisplay().teleport(((Location) args.getOrDefault(0, def)));
        MessageUtil.sendMessage(player, "&9Moved decoration.");


    }
}
