package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.AsteriaDecoration;
import de.erethon.asteria.decorations.DecorationManager;
import de.erethon.bedrock.chat.MessageUtil;
import de.erethon.bedrock.command.ECommand;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;

public class SpawnCommand extends ECommand {

    DecorationManager decorationManager = Asteria.getInstance().getDecorationManager();

    public SpawnCommand() {
        setCommand("spawn");
        setAliases("s");
        setMinArgs(1);
        setMaxArgs(1);
        setHelp("Spawns a decoration");
        setPermission("asteria.spawn");
        setPlayerCommand(true);
        setConsoleCommand(false);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        AsteriaDecoration decoration = decorationManager.getDecoration(args[1]);
        Player player = (Player) sender;
        if (decoration == null) {
            MessageUtil.sendMessage(player, "&cDecoration not found.");
            return;
        }
        Block targetBlock = player.getTargetBlockExact(16);
        if (targetBlock == null) {
            MessageUtil.sendMessage(player, "&cNo block in range.");
            return;
        }
        Display display = decoration.spawn(player, targetBlock.getLocation()).getDisplay();
        Asteria.getInstance().select(player, display);
        MessageUtil.sendMessage(player, "&9Spawned and selected decoration &6" + decoration.getName() + "&a.");
    }
}
