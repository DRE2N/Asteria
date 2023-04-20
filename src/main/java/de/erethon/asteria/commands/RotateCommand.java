package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.PlacedDecorationWrapper;
import de.erethon.bedrock.chat.MessageUtil;
import de.erethon.bedrock.command.ECommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RotateCommand extends ECommand {

    public RotateCommand() {
        setCommand("rotate");
        setAliases("r");
        setMinArgs(0);
        setMaxArgs(5);
        setHelp("Rotates the selected decoration");
        setPermission("asteria.rotate");
        setPlayerCommand(true);
        setConsoleCommand(false);
    }

    @Override
    public void onExecute(String[] args, CommandSender commandSender) {
        Player player = (Player) commandSender;
        PlacedDecorationWrapper wrapper = Asteria.getInstance().getSelected(player);
        if (wrapper == null) {
            MessageUtil.sendMessage(player, "&cNo entity selected. /asteria select");
            return;
        }
        if (args.length < 6) {
            MessageUtil.sendMessage(player, "&c/asteria rotate <left/right> <x> <y> <z> <w>");
            return;
        }
        if (args[1].equalsIgnoreCase("left")) {
            wrapper.rotateLeft(Float.parseFloat(args[2]), Float.parseFloat(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]));
            MessageUtil.sendMessage(player, "&9Left Rotation set to &6" + args[2] + " &9/ &6" + args[3] + " &9/ &6" + args[4] + " &9/ &6" + args[5]);
        } else if (args[1].equalsIgnoreCase("right")) {
            wrapper.rotateRight(Float.parseFloat(args[2]), Float.parseFloat(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]));
            MessageUtil.sendMessage(player, "&9Right Rotation set to &6" + args[2] + " &9/ &6" + args[3] + " &9/ &6" + args[4] + " &9/ &6" + args[5]);
        } else {
            MessageUtil.sendMessage(player, "&c/asteria rotate <left/right> <x> <y> <z> <w>");
            return;
        }
    }
}
