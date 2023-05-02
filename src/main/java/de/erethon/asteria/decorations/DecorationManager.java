package de.erethon.asteria.decorations;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DecorationManager {

    private HashMap<String, AsteriaDecoration> decorations = new HashMap<>();
    private List<String> decorationNames = new ArrayList<>();
    private File dataFile;

    public DecorationManager(File file) {
        dataFile = file;
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        load(cfg);
    }


    public void registerDecoration(AsteriaDecoration decoration) {
        decorations.put(decoration.getName().toLowerCase(), decoration);
        decorationNames.add(decoration.getName().toLowerCase());
    }

    public AsteriaDecoration getDecoration(String name) {
        return decorations.get(name);
    }

    public HashMap<String, AsteriaDecoration> getDecorations() {
        return decorations;
    }

    public List<String> getDecorationNames() {
        return decorationNames;
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
            cfg.set(decoration.getName().toLowerCase(), decoration.save());
        }
        cfg.save(dataFile);
    }
}
