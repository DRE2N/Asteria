package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.AsteriaDecoration;
import de.erethon.asteria.decorations.DecorationManager;
import de.erethon.bedrock.chat.MessageUtil;
import de.erethon.bedrock.command.ECommand;
import org.bukkit.command.CommandSender;
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
        decoration.spawn(player, player.getTargetBlockExact(16).getLocation());
        MessageUtil.sendMessage(player, "&aSpawned decoration &6" + decoration.getName() + "&a.");
    }
}
