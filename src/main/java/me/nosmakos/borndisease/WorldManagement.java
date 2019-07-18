package me.nosmakos.borndisease;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class WorldManagement {

    private final Set<UUID> disabledWorlds = new HashSet<>();

    private BornDisease plugin;
    private FileConfiguration config;

    public WorldManagement(BornDisease plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public void loadDisabledWorlds() {
        this.disabledWorlds.clear();

        String b = config.getString("enabled-worlds") != null ? "enable " : "disable ";

        for (World world : Bukkit.getServer().getWorlds()) {
            plugin.getLogger().info(world.getName());
        }

        for (String worldsList : config.getStringList(getPath())) {
            if (worldsList.isEmpty()) continue;

            World world = Bukkit.getWorld(worldsList);

            if (world == null) {
                plugin.getLogger().info("Could not " + b + worldsList + ". Unknown world found - Ignoring...");
                continue;
            }

            this.disabledWorlds.add(world.getUID());
        }
    }

    public int size() {
        return config.getStringList(getPath()).size();
    }

    public boolean isDisabledInWorld(World world) {
        Preconditions.checkArgument(world != null, "Cannot check state of deadsociety in null world");
        return config.getString("enabled-worlds") != null ? !disabledWorlds.contains(world.getUID()) : disabledWorlds.contains(world.getUID());
    }

    public void clearDisabledWorlds() {
        this.disabledWorlds.clear();
    }

    private String getPath() {
        return config.getString("enabled-worlds") != null ? "enabled-worlds" : "disabled-worlds";
    }
}