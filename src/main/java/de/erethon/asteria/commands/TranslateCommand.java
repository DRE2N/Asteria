package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.PlacedDecorationWrapper;
import de.erethon.bedrock.chat.MessageUtil;
import de.erethon.bedrock.command.ECommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TranslateCommand extends ECommand {

    public TranslateCommand() {
        setCommand("translate");
        setAliases("t");
        setMinArgs(0);
        setMaxArgs(4);
        setHelp("Translates a decoration");
        setPermission("asteria.translate");
        setPlayerCommand(true);
        setConsoleCommand(false);
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
            MessageUtil.sendMessage(player, "&cPlease specify a translation. /asteria translate <x> <y> <z>");
            return;
        }
        float xTrans = Float.parseFloat(args[1]);
        float yTrans = Float.parseFloat(args[2]);
        float zTrans = Float.parseFloat(args[3]);
        wrapper.translate(xTrans, yTrans, zTrans);
        MessageUtil.sendMessage(player, "&9Translation set to &6" + xTrans + " &9/ &6" + yTrans + " &9/ &6" + zTrans);
    }
}
