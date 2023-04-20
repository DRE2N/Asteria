package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.PlacedDecorationWrapper;
import de.erethon.bedrock.chat.MessageUtil;
import de.erethon.bedrock.command.ECommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;

public class ItemCommand extends ECommand {

    public ItemCommand() {
        setCommand("item");
        setAliases("it");
        setMinArgs(0);
        setMaxArgs(0);
        setHelp("Sets the display item");
        setPermission("asteria.item");
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
        if (wrapper.getDisplay() instanceof ItemDisplay itemDisplay) {
            itemDisplay.setItemStack(player.getInventory().getItemInMainHand());
            MessageUtil.sendMessage(player, "&9Item set.");
            return;
        }
        MessageUtil.sendMessage(player, "&cThis is not an item display.");
    }
}
