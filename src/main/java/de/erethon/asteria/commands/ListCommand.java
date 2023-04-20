package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.AsteriaDecoration;
import de.erethon.asteria.decorations.DecorationManager;
import de.erethon.bedrock.chat.MessageUtil;
import de.erethon.bedrock.command.ECommand;
import org.bukkit.command.CommandSender;

public class ListCommand extends ECommand {

    public ListCommand() {
        setCommand("list");
        setAliases("l");
        setMinArgs(0);
        setMaxArgs(0);
        setHelp("Lists all decorations");
        setPermission("asteria.list");
        setPlayerCommand(true);
        setConsoleCommand(true);
    }

    @Override
    public void onExecute(String[] strings, CommandSender commandSender) {
        DecorationManager decorationManager = Asteria.getInstance().getDecorationManager();
        MessageUtil.sendMessage(commandSender, "&9Decorations:");
        for (AsteriaDecoration decoration : decorationManager.getDecorations().values()) {
            MessageUtil.sendMessage(commandSender, "&8- <hover:show_text:'&9Item: &6" + decoration.getMaterial() + "\n &9ModelData: &6" + decoration.getCustomModelData() + "\n" + Asteria.transformationToString(decoration.getDefaultTransformation()) + "'>&6" + decoration.getName() + "</hover>");
        }
    }
}
