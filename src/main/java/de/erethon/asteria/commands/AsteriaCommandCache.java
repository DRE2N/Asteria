package de.erethon.asteria.commands;

import de.erethon.bedrock.command.ECommandCache;
import de.erethon.bedrock.plugin.EPlugin;

public class AsteriaCommandCache extends ECommandCache {


    public static final String LABEL = "asteria";

    EPlugin plugin;

    public AsteriaCommandCache(EPlugin plugin) {
        super(LABEL, plugin);
        this.plugin = plugin;
        addCommand(new SpawnCommand());
    }
}
