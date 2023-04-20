package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.AsteriaDecoration;
import de.erethon.asteria.decorations.PlacedDecorationWrapper;
import de.erethon.bedrock.chat.MessageUtil;
import de.erethon.bedrock.command.ECommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;

public class SaveCommand extends ECommand {

    public SaveCommand() {
        setCommand("save");
        setAliases("s");
        setMinArgs(0);
        setMaxArgs(1);
        setHelp("Saves the current decoration");
        setPermission("asteria.save");
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
            MessageUtil.sendMessage(player, "&cPlease specify a name.");
            return;
        }
        if (wrapper.getDisplay() instanceof ItemDisplay itemDisplay) {
            Asteria.getInstance().getDecorationManager().registerDecoration(new AsteriaDecoration(itemDisplay, args[1]));
            MessageUtil.sendMessage(player, "&9Decoration saved as &6" + args[1] + "&9.");
        } else {
            MessageUtil.sendMessage(player, "&cOnly item displays can be saved currently.");
        }
    }
}
