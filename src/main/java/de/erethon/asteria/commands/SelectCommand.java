package de.erethon.asteria.commands;

import de.erethon.asteria.Asteria;
import de.erethon.bedrock.chat.MessageUtil;
import de.erethon.bedrock.command.ECommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Set;

public class SelectCommand extends ECommand {

    public SelectCommand() {
        setCommand("select");
        setAliases("s");
        setMinArgs(0);
        setMaxArgs(1);
        setPlayerCommand(true);
        setHelp("Selects an entity.");
        setPermission("asteria.select");
    }

    @Override
    public void onExecute(String[] args, CommandSender commandSender) {
        Player player = (Player) commandSender;
        Block block = player.getTargetBlockExact(8);
        RayTraceResult result = player.getWorld().rayTraceEntities(player.getEyeLocation(), player.getEyeLocation().getDirection(), 8, 5);
        if (result == null) {
            MessageUtil.sendMessage(player, "&cNothing targeted.");
            return;
        }
        if (result != null && result.getHitEntity() != null && result.getHitEntity() instanceof Display entity && args.length < 2/* Allow selecting all even if hit */) {
            Asteria.getInstance().select(player, entity);
            MessageUtil.sendMessage(player, "&9Selected &6" + entity.getUniqueId());
            return;
        }
        if (block == null) {
            MessageUtil.sendMessage(player, "&cNothing targeted.");
            return;
        }
        Collection<Display> displays = block.getLocation().getNearbyEntitiesByType(Display.class, 7);
        if (displays.size() == 0) {
            MessageUtil.sendMessage(player, "&cNo displays in range.");
            return;
        }
        if (displays.size() == 1) {
            Display display = displays.iterator().next();
            Asteria.getInstance().select(player, display);
            MessageUtil.sendMessage(player, "&9Selected &6" + display.getUniqueId());
            return;
        }
        MessageUtil.sendMessage(player, "&6" + displays.size() + "&9 displays in range. &8- &7&oHover for info, click to select.");
        for (Display display : displays) {
            player.sendMessage(Component.text("- " + display.getUniqueId()).hoverEvent(display.asHoverEvent()).color(NamedTextColor.GRAY).clickEvent(ClickEvent.callback(f -> {
                MessageUtil.sendMessage(player, "&9Selected &6" + display.getUniqueId());
                Asteria.getInstance().select(player, display);
            })));
        }
    }

}
