package de.erethon.asteria.blocks;

import de.erethon.bedrock.chat.MessageUtil;
import net.minecraft.world.level.block.Block;
import org.bukkit.Instrument;
import org.bukkit.Note;

public record AsteriaNoteblock(String id, Instrument instrument, Note note, String name, int hardness) implements AsteriaBlock {

        public AsteriaNoteblock {
            if (hardness < 0) {
                throw new IllegalArgumentException("Hardness must be positive.");
            }
            MessageUtil.log("Created block " + name + " with hardness " + hardness + " and note " + note);
        }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getID() {
        return null;
    }

    @Override
    public int getHardness() {
        return hardness;
    }

    @Override
    public AsteriaBlockBaseType getType() {
        return AsteriaBlockBaseType.NOTE_BLOCK;
    }
}
