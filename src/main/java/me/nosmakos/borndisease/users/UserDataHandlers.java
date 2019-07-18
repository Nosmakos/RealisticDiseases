package me.nosmakos.borndisease.users;

import me.nosmakos.borndisease.BornDisease;
import me.nosmakos.borndisease.WorldManagement;
import me.nosmakos.borndisease.utilities.DiseaseType;
import me.nosmakos.borndisease.data.UserData;
import me.nosmakos.borndisease.utilities.Lang;
import me.nosmakos.borndisease.utilities.Permission;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserDataHandlers implements Listener {

    private BornDisease plugin;
    private WorldManagement worldManagement;
    private UserManagement userManagement;

    public UserDataHandlers(BornDisease plugin) {
        this.plugin = plugin;
        this.worldManagement = plugin.getWorldManagement();
        this.userManagement = plugin.getUserManagement();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        UserData data = UserData.getConfig(plugin, event.getPlayer());
        data.setAccountInfo();

        if (data.getString("perm-disease") == null) return;
        List<DiseaseType> disease = new ArrayList<>();

        DiseaseType permanent = DiseaseType.valueOf(data.getString("perm-disease"));
        disease.add(permanent);

        User user = new User(disease);
        userManagement.setInformation(event.getPlayer(), user);

        if (permanent == DiseaseType.NONE) return;
        Bukkit.getScheduler().runTaskLater(plugin, () -> event.getPlayer().sendMessage(Lang.valueOf(permanent.name()).get().split("\\|")), 40);
    }

    @EventHandler
    public void onJoin(PlayerRespawnEvent event) {
        UserData data = UserData.getConfig(plugin, event.getPlayer());
        data.setAccountInfo();

        if (worldManagement.isDisabledInWorld(event.getPlayer().getWorld())) return;
        Player player = event.getPlayer();

        if (player.hasPermission(Permission.IMMUNE.get()) || player.hasPermission(Permission.OP.get())) return;

        DiseaseType[] diseaseTypes = {DiseaseType.DIABETES, DiseaseType.ASTHMA, DiseaseType.HEART_DISEASE, DiseaseType.NONE};
        DiseaseType permanent = diseaseTypes[new Random().nextInt(diseaseTypes.length)];

        List<DiseaseType> disease = new ArrayList<>();
        disease.add(permanent);

        User user = new User(disease);
        userManagement.setInformation(event.getPlayer(), user);

        data.set("perm-disease", permanent.name());
        data.save();

        if (permanent == DiseaseType.NONE) return;

        Bukkit.getScheduler().runTaskLater(plugin, () -> player.sendMessage(Lang.valueOf(permanent.name()).get().split("\\|")), 40);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UserData.remove(event.getPlayer());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (worldManagement.isDisabledInWorld(event.getEntity().getWorld())) return;

        Player player = event.getEntity();
        if (player.hasPermission(Permission.IMMUNE.get()) || player.hasPermission(Permission.OP.get())) return;

        UserData data = UserData.getConfig(plugin, event.getEntity());

        data.set("perm-disease", null);
        data.save();
    }
}