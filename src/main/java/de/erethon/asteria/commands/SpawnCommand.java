package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.AsteriaDecoration;
import de.erethon.bedrock.chat.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.MultiLiteralArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;

public class SpawnCommand extends CommandAPICommand {

    public SpawnCommand() {
        super("spawn");
        withPermission("asteria.spawn");
        withShortDescription("Spawns a decoration");
        withRequirement((sender) -> sender instanceof Player);
        withArguments(new StringArgument("<name").replaceSuggestions(ArgumentSuggestions.strings(Asteria.getInstance().getDecorationManager().getDecorationNames())));
        executesPlayer(((sender, args) -> {
            onExecute(args, sender);
        }));
    }

    public void onExecute(CommandArguments args, Player player) {
        AsteriaDecoration decoration = Asteria.getInstance().getDecorationManager().getDecoration((String) args.get(0));
        Location location = player.getLocation();
        Block targetBlock = player.getTargetBlockExact(7);
        if (targetBlock != null) {
            location = targetBlock.getLocation();
        }
        Display display = decoration.spawn(player, location).getDisplay();
        Asteria.getInstance().select(player, display);
        MessageUtil.sendMessage(player, "&9Spawned and selected decoration &6" + decoration.getName() + "&a.");
    }
}
