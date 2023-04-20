package de.erethon.asteria.blocks;

import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class AsteriaBlockManager {

    private HashMap<String, AsteriaBlock> blocks = new HashMap<>();
    private File dataFile;

    public AsteriaBlockManager(File file) {
        dataFile = file;
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        load(cfg);
    }

    public void registerBlock(AsteriaBlock block) {
        blocks.put(block.getName(), block);
    }

    public AsteriaBlock getBlock(String name) {
        return blocks.get(name);
    }

    public HashMap<String, AsteriaBlock> getBlocks() {
        return blocks;
    }

    private void load(YamlConfiguration cfg) {
        for (String key : cfg.getKeys(false)) {
            String name = cfg.getString(key + ".name");
            AsteriaBlockBaseType type = AsteriaBlockBaseType.valueOf(cfg.getString(key + ".type").toUpperCase());
            int hardness = cfg.getInt(key + ".hardness");
            if (type == AsteriaBlockBaseType.NOTE_BLOCK) {
                AsteriaNoteblock block = new AsteriaNoteblock(
                        key,
                        Instrument.valueOf(cfg.getString(key + ".instrument", "piano").toUpperCase()),
                        new Note(cfg.getInt(key + ".note")),
                        name,
                        hardness
                );
                registerBlock(block);
            }
        }
    }

    public void test() {}

    public void save() throws IOException {
        YamlConfiguration cfg = new YamlConfiguration();
        for (AsteriaBlock block : blocks.values()) {
            cfg.set(block.getID() + ".name", block.getName());
            cfg.set(block.getID() + ".type", block.getType().name());
            cfg.set(block.getID() + ".hardness", block.getHardness());
            if (block.getType() == AsteriaBlockBaseType.NOTE_BLOCK) {
                AsteriaNoteblock noteBlock = (AsteriaNoteblock) block;
                cfg.set(block.getID() + ".instrument", noteBlock.instrument().name());
                cfg.set(block.getID() + ".note", noteBlock.note().getId());
            }
        }
        cfg.save(dataFile);
    }
}
