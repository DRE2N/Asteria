package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.PlacedDecorationWrapper;
import de.erethon.bedrock.chat.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.FloatArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.entity.Player;

public class ScaleCommand extends CommandAPICommand {

    public ScaleCommand() {
        super("scale");
        withPermission("asteria.scale");
        withShortDescription("Scales the selected entity");
        withRequirement((sender) -> sender instanceof Player);
        withArguments(new FloatArgument("x"), new FloatArgument("y"), new FloatArgument("z"));
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
        float xScale = (float) args.get(0);
        float yScale = (float) args.get(1);
        float zScale = (float) args.get(2);
        wrapper.scale(xScale, yScale, zScale);
        MessageUtil.sendMessage(player, "&9Scale set to &6" + xScale + " &9/ &6" + yScale + " &9/ &6" + zScale);
    }
}
