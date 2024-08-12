package dev.majek.simplehomes.data;

import dev.majek.simplehomes.SimpleHomes;
import dev.majek.simplehomes.data.struct.Home;
import dev.majek.simplehomes.data.struct.HomesPlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles PlaceholderAPI integration
 */
public class PAPI extends PlaceholderExpansion {

    private final SimpleHomes plugin;

    public PAPI(SimpleHomes plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getIdentifier() {
        return "home";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String identifier) {
        HomesPlayer homesPlayer = SimpleHomes.core().getHomesPlayer(player.getUniqueId());
        String[] identifiers = identifier.split("_");
        if (identifier.equals("count"))
            return Integer.toString(homesPlayer.getTotalHomes());
        if (identifiers.length == 2 && identifiers[0].equals("name")) {
            int index = Integer.parseInt(identifiers[1]);
            List<Home> sortedHomes = homesPlayer.getHomes().stream().sorted(Comparator.comparing(Home::name)).collect(Collectors.toList());
            return sortedHomes.get(index).name();
        }
        return null;
    }

    public static String applyPlaceholders(Player player, String message) {
        return PlaceholderAPI.setPlaceholders(player, message);
    }

}
