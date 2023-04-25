package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.bedrock.chat.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;

public class CreateCommand extends CommandAPICommand {

    public CreateCommand() {
        super("create");
        withPermission("asteria.create");
        withShortDescription("Creates a new item display");
        withRequirement((sender) -> sender instanceof Player);
        executesPlayer(((sender, args) -> {
            onExecute(args, sender);
        }));
    }

    public void onExecute(CommandArguments args, Player player) {
        player.getWorld().spawn(player.getLocation(), ItemDisplay.class, itemDisplay -> {
            itemDisplay.setItemStack(player.getInventory().getItemInMainHand());
            Asteria.getInstance().select(player, itemDisplay);
            MessageUtil.sendMessage(player, "&9Created and selected item display.");
        })
        ;
    }
}
