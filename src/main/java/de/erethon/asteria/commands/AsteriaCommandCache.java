package de.erethon.asteria.commands;

import de.erethon.bedrock.command.ECommandCache;
import de.erethon.bedrock.plugin.EPlugin;

public class AsteriaCommandCache extends ECommandCache {


    public static final String LABEL = "asteria";

    EPlugin plugin;

    public AsteriaCommandCache(EPlugin plugin) {
        super(LABEL, plugin);
        this.plugin = plugin;
        addCommand(new BillboardCommand());
        addCommand(new CreateCommand());
        addCommand(new DeleteCommand());
        addCommand(new HelpCommand());
        addCommand(new InfoCommand());
        addCommand(new ItemCommand());
        addCommand(new ListCommand());
        addCommand(new PickupCommand());
        addCommand(new RotateCommand());
        addCommand(new SaveCommand());
        addCommand(new ScaleCommand());
        addCommand(new SelectCommand());
        addCommand(new SpawnCommand());
        addCommand(new TranslateCommand());
    }
}
