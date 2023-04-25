package de.erethon.asteria.commands;

import dev.jorel.commandapi.CommandAPICommand;

public class AsteriaCommandCache {


    public static final String LABEL = "asteria";

    public void register() {
        CommandAPICommand baseCommand = new CommandAPICommand(LABEL);
        baseCommand.withSubcommands(new BillboardCommand(),
                new CreateCommand(),
                new DeleteCommand(),
                new HelpCommand(),
                new InfoCommand(),
                new ItemCommand(),
                new ListCommand(),
                new MoveCommand(),
                new PickupCommand(),
                new RotateCommand(),
                new SaveCommand(),
                new ScaleCommand(),
                new SelectCommand(),
                new SpawnCommand(),
                new TranslateCommand());
        baseCommand.withAliases("as");
        baseCommand.register();
    }
}
