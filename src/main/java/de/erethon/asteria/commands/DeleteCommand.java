package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.PlacedDecorationWrapper;
import de.erethon.bedrock.chat.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.entity.Player;

public class DeleteCommand extends CommandAPICommand {

    public DeleteCommand() {
        super("delete");
        withPermission("asteria.delete");
        withShortDescription("Deletes the selected entity");
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
        wrapper.getDisplay().remove();
        Asteria.getInstance().deselect(player);
        MessageUtil.sendMessage(player, "&9Entity deleted.");
    }
}
