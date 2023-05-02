package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.AsteriaDecoration;
import de.erethon.asteria.decorations.PlacedDecorationWrapper;
import de.erethon.bedrock.chat.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.arguments.TextArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;

public class SaveCommand extends CommandAPICommand {

    public SaveCommand() {
        super("save");
        withPermission("asteria.save");
        withShortDescription("Saves the selected entity as a decoration");
        withRequirement((sender) -> sender instanceof Player);
        withArguments(new TextArgument("name"), new GreedyStringArgument("greedyTest"));
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
            Asteria.getInstance().getDecorationManager().registerDecoration(new AsteriaDecoration(itemDisplay, (String) args.get(0)));
            MessageUtil.sendMessage(player, "&9Decoration saved as &6" + args.get(0) + "&9.");
        } else {
            MessageUtil.sendMessage(player, "&cOnly item displays can be saved currently.");
        }
    }
}
