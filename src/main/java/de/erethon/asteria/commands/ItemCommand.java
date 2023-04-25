package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.PlacedDecorationWrapper;
import de.erethon.bedrock.chat.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ItemStackArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemCommand extends CommandAPICommand {

    public ItemCommand() {
        super("item");
        withPermission("asteria.item");
        withShortDescription("Sets the item of the selected item display");
        withRequirement((sender) -> sender instanceof Player);
        withOptionalArguments(new ItemStackArgument("item"));
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
        if (wrapper.getDisplay() instanceof ItemDisplay itemDisplay) {
            itemDisplay.setItemStack((ItemStack) args.getOrDefault(0, player.getInventory().getItemInMainHand()));
            MessageUtil.sendMessage(player, "&9Item set.");
            return;
        }
        MessageUtil.sendMessage(player, "&cThis is not an item display.");
    }
}
