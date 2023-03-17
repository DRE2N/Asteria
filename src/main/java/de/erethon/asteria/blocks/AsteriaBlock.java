package de.erethon.asteria.blocks;


import net.minecraft.world.level.block.Block;

public interface AsteriaBlock {

    String getName();
    String getID();
    int getHardness();
    AsteriaBlockBaseType getType();
}
