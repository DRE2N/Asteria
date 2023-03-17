package de.erethon.asteria;

import de.erethon.asteria.blocks.AsteriaBlockManager;
import de.erethon.asteria.commands.AsteriaCommandCache;
import de.erethon.asteria.decorations.DecorationManager;
import de.erethon.asteria.listeners.AsteriaBlockListener;
import de.erethon.bedrock.compatibility.Internals;
import de.erethon.bedrock.plugin.EPlugin;
import de.erethon.bedrock.plugin.EPluginSettings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class Asteria extends EPlugin implements Listener {

    private static Asteria instance;
    private AsteriaCommandCache commandCache;
    public static NamespacedKey asteriaTypeKey = new NamespacedKey(Asteria.getInstance(), "asteria_type");
    private AsteriaBlockListener blockListener = new AsteriaBlockListener();
    private DecorationManager decorationManager;
    private AsteriaBlockManager blockManager;

    public Asteria() {
        settings = EPluginSettings.builder()
                .economy(false)
                .forcePaper(true)
                .internals(Internals.NEW)
                .build();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(blockListener, this);
        File decorationFile = new File(getDataFolder(), "decorations.yml");
        if (!decorationFile.exists()) {
            try {
                decorationFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        decorationManager = new DecorationManager(decorationFile);
        File blocksFile = new File(getDataFolder(), "blocks.yml");
        if (!blocksFile.exists()) {
            try {
                blocksFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        blockManager = new AsteriaBlockManager(blocksFile);
        commandCache = new AsteriaCommandCache(this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        try {
            decorationManager.save();
            blockManager.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Asteria getInstance() {
        return instance;
    }

    public DecorationManager getDecorationManager() {
        return decorationManager;
    }

}
