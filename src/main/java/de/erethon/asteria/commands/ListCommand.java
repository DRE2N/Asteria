package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.AsteriaDecoration;
import de.erethon.asteria.decorations.DecorationManager;
import de.erethon.bedrock.chat.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.command.CommandSender;

public class ListCommand extends CommandAPICommand {

    public ListCommand() {
        super("list");
        withShortDescription("Lists all decorations");
        withPermission("asteria.list");
        executes((sender, args) -> {
            onExecute(sender);
        });
    }

    public void onExecute(CommandSender commandSender) {
        DecorationManager decorationManager = Asteria.getInstance().getDecorationManager();
        MessageUtil.sendMessage(commandSender, "&9Decorations:");
        for (AsteriaDecoration decoration : decorationManager.getDecorations().values()) {
            MessageUtil.sendMessage(commandSender, "&8- <hover:show_text:'&9Item: &6" + decoration.getMaterial() + "\n &9ModelData: &6" + decoration.getCustomModelData() + "\n" + Asteria.transformationToString(decoration.getDefaultTransformation()) + "'>&6" + decoration.getName() + "</hover>");
        }
    }
}
