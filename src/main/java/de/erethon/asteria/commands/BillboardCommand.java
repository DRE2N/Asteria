package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.PlacedDecorationWrapper;
import de.erethon.bedrock.chat.MessageUtil;
import de.erethon.bedrock.command.ECommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BillboardCommand extends ECommand {

    public BillboardCommand() {
        setCommand("billboard");
        setAliases("bb");
        setMinArgs(0);
        setMaxArgs(1);
        setHelp("Sets the billboard mode");
        setPermission("asteria.billboard");
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
        if (args.length < 2) {
            MessageUtil.sendMessage(player, "&cPlease specify a mode. /asteria billboard <mode>");
            return;
        }
        try {
            Display.Billboard billboard = Display.Billboard.valueOf(args[1].toUpperCase());
            wrapper.billboard(billboard);
            MessageUtil.sendMessage(player, "&9Billboard mode set to &6" + billboard);
        } catch (IllegalArgumentException exception) {
            MessageUtil.sendMessage(player, "&cInvalid mode. Try " + Arrays.toString(Display.Billboard.values()));
        }
    }
}
