package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.bedrock.chat.MessageUtil;
import de.erethon.bedrock.command.ECommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;

public class CreateCommand extends ECommand {

    public CreateCommand() {
        setCommand("create");
        setAliases("c");
        setMinArgs(0);
        setMaxArgs(1);
        setHelp("Creates a decoration");
        setPermission("asteria.create");
        setPlayerCommand(true);
    }

    @Override
    public void onExecute(String[] strings, CommandSender commandSender) {
        Player player = (Player) commandSender;
        player.getWorld().spawn(player.getLocation(), ItemDisplay.class, itemDisplay -> {
            itemDisplay.setItemStack(player.getInventory().getItemInMainHand());
            Asteria.getInstance().select(player, itemDisplay);
            MessageUtil.sendMessage(player, "&9Created and selected item display.");
        });
    }
}
