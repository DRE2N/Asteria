package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.PlacedDecorationWrapper;
import de.erethon.bedrock.chat.MessageUtil;
import de.erethon.bedrock.command.ECommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteCommand extends ECommand {

    public DeleteCommand() {
        setCommand("delete");
        setAliases("d");
        setMinArgs(0);
        setMaxArgs(0);
        setHelp("Deletes the current decoration");
        setPermission("asteria.delete");
        setPlayerCommand(true);
    }

    @Override
    public void onExecute(String[] strings, CommandSender commandSender) {
        Player player = (Player) commandSender;
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
