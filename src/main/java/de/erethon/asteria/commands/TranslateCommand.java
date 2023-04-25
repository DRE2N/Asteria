package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.asteria.decorations.PlacedDecorationWrapper;
import de.erethon.bedrock.chat.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.FloatArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.entity.Player;

public class TranslateCommand extends CommandAPICommand {

    public TranslateCommand() {
        super("translate");
        withPermission("asteria.translate");
        withShortDescription("Translates the selected entity");
        withRequirement((sender) -> sender instanceof Player);
        withArguments(new FloatArgument("x"), new FloatArgument("y"), new FloatArgument("z"));
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
        float xTrans = (Float) args.get(0);
        float yTrans = (Float) args.get(1);
        float zTrans = (Float) args.get(2);
        wrapper.translate(xTrans, yTrans, zTrans);
        MessageUtil.sendMessage(player, "&9Translation set to &6" + xTrans + " &9/ &6" + yTrans + " &9/ &6" + zTrans);
    }
}
