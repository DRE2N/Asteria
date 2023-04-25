package de.erethon.asteria;

import de.erethon.asteria.blocks.AsteriaBlockManager;
import de.erethon.asteria.commands.AsteriaCommandCache;
import de.erethon.asteria.decorations.DecorationManager;
import de.erethon.asteria.decorations.PlacedDecorationWrapper;
import de.erethon.asteria.listeners.AsteriaBlockListener;
import de.erethon.bedrock.chat.MessageUtil;
import de.erethon.bedrock.compatibility.Internals;
import de.erethon.bedrock.plugin.EPlugin;
import de.erethon.bedrock.plugin.EPluginSettings;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class Asteria extends EPlugin implements Listener {

    private static Asteria instance;
    private AsteriaCommandCache commandCache;
    private NamespacedKey asteriaTypeKey = new NamespacedKey(this, "asteria_type");

    private HashMap<Player, PlacedDecorationWrapper> selected = new HashMap<>();
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
    public void onLoad() {
        CommandAPIBukkitConfig config = new CommandAPIBukkitConfig(this);
        config.shouldHookPaperReload(true);
        config.silentLogs(true);
        CommandAPI.onLoad(config);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        CommandAPI.onEnable();
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

        new AsteriaCommandCache().register();
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        selected.remove(event.getPlayer());
    }

    @Override
    public void onDisable() {
        try {
            MessageUtil.log("Saving decorations and blocks...");
            decorationManager.save();
            blockManager.save();
            MessageUtil.log("Saved decorations and blocks.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.onDisable();
    }

    public static Asteria getInstance() {
        return instance;
    }

    public AsteriaBlockManager getBlockManager() {
        return blockManager;
    }

    public NamespacedKey getAsteriaKey() {
        return asteriaTypeKey;
    }

    public DecorationManager getDecorationManager() {
        return decorationManager;
    }

    public PlacedDecorationWrapper select(Player player, Display entity) {
        PlacedDecorationWrapper wrapper = new PlacedDecorationWrapper(player, entity);
        selected.put(player, wrapper);
        return wrapper;
    }

    public void deselect(Player player) {
        selected.remove(player);
    }

    public PlacedDecorationWrapper getSelected(Player player) {
        return selected.get(player);
    }

    public static String transformationToString(Transformation transformation) {
        return "&9Scale: &6" + transformation.getScale().x + "&8/&6" + transformation.getScale().y + "&8/&6" + transformation.getScale().z + "\n"
                + "&9LeftRotation: &6" + transformation.getLeftRotation().x + "&8/&6" + transformation.getLeftRotation().y + "&8/&6" + transformation.getLeftRotation().z + "&8/&6" + transformation.getLeftRotation().w + "\n"
                + "&9RightRotation: &6" + transformation.getRightRotation().x + "&8/&6" + transformation.getRightRotation().y + "&8/&6" + transformation.getRightRotation().z + "&8/&6" + transformation.getRightRotation().w + "\n"
                + "&9Translation: &6" + transformation.getTranslation().x + "&8/&6" + transformation.getTranslation().y + "&8/&6" + transformation.getTranslation().z;
    }

}
