package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.PlacedDecorationWrapper;
import de.erethon.bedrock.chat.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.util.Transformation;

public class InfoCommand extends CommandAPICommand {

    public InfoCommand() {
        super("info");
        withAliases("i");
        withPermission("asteria.info");
        withShortDescription("Shows information about the selected entity");
        withFullDescription("Shows information about the selected entity");
        withRequirement((sender) -> sender instanceof Player);
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
        Display display = wrapper.getDisplay();
        MessageUtil.sendMessage(player, "&9UUID: &6" + display.getUniqueId());
        MessageUtil.sendMessage(player, "&9Type: &6" + display.getType());
        MessageUtil.sendMessage(player, "&9Location: &6" + Math.round(display.getLocation().x() * 100.0) / 100.0 + "&8/&6" + Math.round(display.getLocation().y() * 100.0) + "&8/&6" + Math.round(display.getLocation().z() * 100.0));
        MessageUtil.sendMessage(player, " ");
        MessageUtil.sendMessage(player, "&9Transform: ");
        Transformation transformation = display.getTransformation();
        MessageUtil.sendMessage(player, Asteria.transformationToString(transformation));
    }
}
