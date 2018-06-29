package com.enjin.enjincoin.spigot_framework.commands.subcommands;

import com.enjin.enjincoin.spigot_framework.BasePlugin;
import com.enjin.enjincoin.spigot_framework.player.MinecraftPlayer;
import com.enjin.enjincoin.spigot_framework.util.MessageUtils;
import com.enjin.enjincoin.spigot_framework.util.UuidUtils;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.logging.Level;

/**
 * <p>Link command handler.</p>
 */
public class LinkCommand {

    /**
     * <p>The spigot plugin.</p>
     */
    private BasePlugin plugin;

    /**
     * <p>Link command handler constructor.</p>
     *
     * @param plugin the Spigot plugin
     */
    public LinkCommand(BasePlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * <p>Executes and performs operations defined for the command.</p>
     *
     * @param sender the command sender
     * @param args   the command arguments
     *
     * @since 1.0
     */
    public void execute(CommandSender sender, String[] args) {
        UUID uuid = null;

        if (sender instanceof Player) {
            Player player = (Player) sender;
            uuid = player.getUniqueId();
        } else {
            if (args.length >= 1) {
                try {
                    uuid = UuidUtils.stringToUuid(args[0]);
                } catch (IllegalArgumentException e) {
                    errorInvalidUuid(sender);
                }
            } else {
                final TextComponent text = TextComponent.of("UUID argument required.")
                        .color(TextColor.RED);
                MessageUtils.sendMessage(sender, text);
            }
        }

        if (uuid != null) {
            MinecraftPlayer minecraftPlayer = this.plugin.getBootstrap().getPlayerManager().getPlayer(uuid);
            if (minecraftPlayer != null) {
                if (minecraftPlayer.isLoaded()) {
                    handleCode(sender, minecraftPlayer.getIdentityData().getLinkingCode());
                } else {
                    // TODO: Warn sender that the online player has not fully loaded
                }
            } else {
                // TODO: Fetch Identity for the provided UUID
                // Only fetch, do not create new Identity instances
            }
        } else {
            errorInvalidUuid(sender);
        }
    }

    private void errorInvalidUuid(CommandSender sender) {
        final TextComponent text = TextComponent.of("The UUID provided is invalid.")
                .color(TextColor.RED);
        MessageUtils.sendMessage(sender, text);
    }

    private void handleCode(CommandSender sender, String code) {
        if (code == null || code.isEmpty()) {
            final TextComponent text = TextComponent.of("Could not acquire a player identity code: ")
                    .color(TextColor.GREEN)
                    .append(TextComponent.of("code not present.")
                            .color(TextColor.GOLD));
            MessageUtils.sendMessage(sender, text);
        } else {
            final TextComponent text = TextComponent.of("Identity Code: ")
                    .color(TextColor.GREEN)
                    .append(TextComponent.of(code)
                            .color(TextColor.GOLD));
            MessageUtils.sendMessage(sender, text);
        }
    }

}
