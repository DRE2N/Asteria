package de.erethon.asteria.commands;

import de.erethon.bedrock.chat.MessageUtil;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.command.CommandSender;

public class HelpCommand extends CommandAPICommand {

    public HelpCommand() {
        super("help");
        withShortDescription("Shows the help");
        withPermission("asteria.help");
        executes((sender, args) -> {
            onExecute(args, sender);
        });

    }

    public void onExecute(CommandArguments args, CommandSender commandSender) {
        MessageUtil.sendMessage(commandSender, "&9Asteria Help");
        MessageUtil.sendMessage(commandSender, "&8- &6/asteria help &8- &7Shows this help");
        MessageUtil.sendMessage(commandSender, "&8- &6/asteria create &8- &7Creates a decoration from the current item");
        MessageUtil.sendMessage(commandSender, "&8- &6/asteria billboard &8- &7Sets the billboard mode");
        MessageUtil.sendMessage(commandSender, "&8- &6/asteria list &8- &7Lists all decorations");
        MessageUtil.sendMessage(commandSender, "&8- &6/asteria info &8- &7Shows info about a decoration");
        MessageUtil.sendMessage(commandSender, "&8- &6/asteria item &8- &7Sets the display item");
        MessageUtil.sendMessage(commandSender, "&8- &6/asteria select &8- &7Selects a decoration");
        MessageUtil.sendMessage(commandSender, "&8- &6/asteria scale &8- &7Scales a decoration");
        MessageUtil.sendMessage(commandSender, "&8- &6/asteria rotate &8- &7Rotates a decoration");
        MessageUtil.sendMessage(commandSender, "&8- &6/asteria translate &8- &7Translates a decoration");
        MessageUtil.sendMessage(commandSender, "&8- &6/asteria spawn &8- &7Spawns a saved decoration");
        MessageUtil.sendMessage(commandSender, "&8- &6/asteria save &8- &7Saves a decoration");
        MessageUtil.sendMessage(commandSender, "&8- &6/asteria delete &8- &7Deletes a decoration");
        MessageUtil.sendMessage(commandSender, "&8- &6/asteria pickup &8- &7Picks up a decoration");
        MessageUtil.sendMessage(commandSender, "&8- &6/asteria move &8- &7Moves a decoration");
        MessageUtil.sendMessage(commandSender, "");
        MessageUtil.sendMessage(commandSender, "&9&oTransformation visualizer: &6&o<click:open_url:'https://misode.github.io/transformation/'>Click me</click>");
    }
}
