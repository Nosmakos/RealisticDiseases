package me.nosmakos.borndisease;

import com.google.common.base.Charsets;
import me.nosmakos.borndisease.commands.CommandHandler;
import me.nosmakos.borndisease.data.UserData;
import me.nosmakos.borndisease.items.ItemHandler;
import me.nosmakos.borndisease.items.types.Syringe;
import me.nosmakos.borndisease.users.UserDataHandlers;
import me.nosmakos.borndisease.users.UserManagement;
import me.nosmakos.borndisease.utilities.Item;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BornDisease extends JavaPlugin {

    private WorldManagement worldManagement;
    private UserManagement userManagement;

    private File langFile;
    private FileConfiguration langConfig;

    @Override
    public void onEnable() {
        this.worldManagement = new WorldManagement(this);
        this.userManagement = new UserManagement();

        saveDefaultConfig();
        reloadConfig();
        createFiles();
        updateFiles();

        PluginManager pM = this.getServer().getPluginManager();
        pM.registerEvents(new UserDataHandlers(this), this);
        pM.registerEvents(new ItemHandler(this), this);
        pM.registerEvents(new Syringe(this), this);

        getCommand("realisticdiseases").setExecutor(new CommandHandler(this));

        this.worldManagement.loadDisabledWorlds();
        Item.initialize(this);
    }

    @Override
    public void onDisable() {
        UserData.removeAll();
        this.worldManagement.clearDisabledWorlds();
    }

    public UserManagement getUserManagement() {
        return this.userManagement;
    }

    public WorldManagement getWorldManagement() {
        return worldManagement;
    }


    public FileConfiguration getLanguageConfig() {
        return this.langConfig;
    }

    public void reloadLanguageConfig() {
        langConfig = YamlConfiguration.loadConfiguration(langFile);
        InputStream defItemsConfigStream = this.getResource("lang.yml");
        if (defItemsConfigStream != null) {
            langConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defItemsConfigStream, Charsets.UTF_8)));
        }
    }

    private void createFiles() {
        langFile = new File(getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            langFile.getParentFile().mkdirs();
            saveResource("lang.yml", false);
            getLogger().info("Creating lang.yml configuration file...");
        }

        langConfig = new YamlConfiguration();
        try {
            langConfig.load(langFile);
        } catch (IOException | InvalidConfigurationException e) {
            getLogger().warning("Cannot load lang.yml configuration file.");
        }
    }

    private void updateFiles() {
        boolean update = false;

        if (!getConfig().isSet("config-version") || !getConfig().getString("config-version").equals("1.0")) {
            saveResource("config.yml", true);
            update = true;
        }

        if (!getLanguageConfig().isSet("config-version") || !getLanguageConfig().getString("config-version").equals("1.0")) {
            saveResource("lang.yml", true);
            update = true;
        }

        if (update) getLogger().info("Updating configuration files ...");
    }

    public void sendActionBar(Player player, String string) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(string));
    }
}
