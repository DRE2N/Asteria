package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.PlacedDecorationWrapper;
import de.erethon.bedrock.chat.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.FloatArgument;
import dev.jorel.commandapi.arguments.MultiLiteralArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.entity.Player;

public class RotateCommand extends CommandAPICommand {

    public RotateCommand() {
        super("rotate");
        withPermission("asteria.rotate");
        withShortDescription("Rotates the selected entity");
        withRequirement((sender) -> sender instanceof Player);
        withArguments(new MultiLiteralArgument("left", "right"),
                new FloatArgument("x", -1, 1),
                new FloatArgument("y", -1, 1),
                new FloatArgument("z", -1, 1),
                new FloatArgument("w", -1, 1));
        executesPlayer(((sender, args) -> {
            onExecute(args, sender);
        }));
    }

    public void onExecute(CommandArguments args, Player player) {
        PlacedDecorationWrapper wrapper = Asteria.getInstance().getSelected(player);
        if (wrapper == null) {
            MessageUtil.sendMessage(player, "&cNo entity selected. /asteria select");
            return;
        }
        if (args.get(0).equals("left")) {
            wrapper.rotateLeft((Float) args.get(1), (Float) args.get(2), (Float) args.get(3), (Float) args.get(4));
            MessageUtil.sendMessage(player, "&9Left Rotation set to &6" + args.get(1) + " &9/ &6" + args.get(2) + " &9/ &6" + args.get(3) + " &9/ &6" + args.get(4));
        }
        if (args.get(0).equals("right")) {
            wrapper.rotateRight((Float) args.get(1), (Float) args.get(2), (Float) args.get(3), (Float) args.get(4));
            MessageUtil.sendMessage(player, "&9Right Rotation set to &6" + args.get(1) + " &9/ &6" + args.get(2) + " &9/ &6" + args.get(3) + " &9/ &6" + args.get(4));
        }
    }
}
