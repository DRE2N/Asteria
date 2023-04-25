package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.PlacedDecorationWrapper;
import de.erethon.bedrock.chat.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ListArgumentBuilder;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;

public class BillboardCommand extends CommandAPICommand {

    public BillboardCommand() {
        super("billboard");
        withAliases("bb");
        withPermission("asteria.billboard");
        withShortDescription("Sets the billboard mode of the selected entity");
        withPermission("asteria.billboard");
        withRequirement((sender) -> sender instanceof Player);
        withArguments(new ListArgumentBuilder<Display.Billboard>("billboardmode")
                .withList(Display.Billboard.values())
                .withMapper(mode -> mode.name().toLowerCase())
                .buildText());
        executesPlayer(((sender, args) -> {
            onExecute(args, sender);
        }));
    }

    public void onExecute(CommandArguments args, Player player) {
        PlacedDecorationWrapper wrapper = Asteria.getInstance().getSelected(player);
        if (wrapper == null) {
            MessageUtil.sendMessage(player, "&cNo entity selected.");
            return;
        }
        wrapper.billboard((Display.Billboard) args.get(0));
        MessageUtil.sendMessage(player, "&9Billboard mode set to &6" + wrapper.getDisplay().getBillboard());
    }
}

