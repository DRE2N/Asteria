package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.PlacedDecorationWrapper;
import de.erethon.bedrock.chat.MessageUtil;
import de.erethon.bedrock.command.ECommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScaleCommand extends ECommand {

    public ScaleCommand() {
        setCommand("scale");
        setAliases("s");
        setMinArgs(0);
        setMaxArgs(3);
        setHelp("Scales the selected decoration");
        setPermission("asteria.scale");
        setPlayerCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender commandSender) {
        Player player = (Player) commandSender;
        PlacedDecorationWrapper wrapper = Asteria.getInstance().getSelected(player);
        if (wrapper == null) {
            MessageUtil.sendMessage(player, "&cNo entity selected.");
            return;
        }
        if (args.length < 4) {
            MessageUtil.sendMessage(player, "&cPlease specify a scale. /asteria scale <x> <y> <z>");
            return;
        }
        float xScale = Float.parseFloat(args[1]);
        float yScale = Float.parseFloat(args[2]);
        float zScale = Float.parseFloat(args[3]);
        wrapper.scale(xScale, yScale, zScale);
        MessageUtil.sendMessage(player, "&9Scale set to &6" + xScale + " &9/ &6" + yScale + " &9/ &6" + zScale);
    }
}
