package de.erethon.asteria.decorations;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class DecorationManager {

    private HashMap<String, AsteriaDecoration> decorations = new HashMap<>();
    private File dataFile;

    public DecorationManager(File file) {
        dataFile = file;
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        load(cfg);
    }


    public void registerDecoration(AsteriaDecoration decoration) {
        decorations.put(decoration.getName(), decoration);
    }

    public AsteriaDecoration getDecoration(String name) {
        return decorations.get(name);
    }

    public HashMap<String, AsteriaDecoration> getDecorations() {
        return decorations;
    }

    private void load(YamlConfiguration cfg) {
        for (String key : cfg.getKeys(false)) {
            AsteriaDecoration decoration = new AsteriaDecoration(cfg.getConfigurationSection(key));
            registerDecoration(decoration);
        }
    }

    public void save() throws IOException {
        YamlConfiguration cfg = new YamlConfiguration();
        for (AsteriaDecoration decoration : decorations.values()) {
            cfg.set(decoration.getName(), decoration.save());
        }
        cfg.save(dataFile);
    }
}
