package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.PlacedDecorationWrapper;
import de.erethon.bedrock.chat.MessageUtil;
import de.erethon.bedrock.command.ECommand;
import io.netty.util.internal.MathUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.util.Transformation;

public class InfoCommand extends ECommand {

    public InfoCommand() {
        setCommand("info");
        setAliases("i");
        setMinArgs(0);
        setMaxArgs(0);
        setHelp("Shows information about the selected entity");
        setPermission("asteria.info");
        setPlayerCommand(true);
    }

    @Override
    public void onExecute(String[] strings, CommandSender commandSender) {
        Player player = (Player) commandSender;
        PlacedDecorationWrapper wrapper = Asteria.getInstance().getSelected(player);
        if (wrapper == null) {
            MessageUtil.sendMessage(player, "&cNo entity selected.");
            return;
        }
        Display display = wrapper.getDisplay();
        MessageUtil.sendMessage(player, "&9UUID: &6" + display.getUniqueId());
        MessageUtil.sendMessage(player, "&9Type: &6" + display.getType());
        MessageUtil.sendMessage(player, "&9Location: &6" + Math.round(display.getLocation().x() * 100.0) / 100.0 + "&8/&6" + Math.round(display.getLocation().y() * 100.0) + "&8/&6" + Math.round(display.getLocation().z() * 100.0));
        MessageUtil.sendMessage(player, " ");
        MessageUtil.sendMessage(player, "&9Transform: ");
        Transformation transformation = display.getTransformation();
        MessageUtil.sendMessage(player, Asteria.transformationToString(transformation));
    }
}
