package me.nosmakos.borndisease.users;

import me.nosmakos.borndisease.utilities.DiseaseType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManagement {

    private Map<UUID, User> user = new HashMap<>();

    public UserManagement() {
    }

    public Map<UUID, User> user() {
        return this.user;
    }

    public void setInformation(Player player, User user) {
        this.user.put(player.getUniqueId(), user);
    }

    public boolean contains(Player player) {
        return this.user.containsKey(player.getUniqueId());
    }

    public void addDisease(Player player, DiseaseType diseaseType) {
        user.get(player.getUniqueId()).addDiseaseType(diseaseType);
    }

    public boolean hasDiseaseType(Player player, DiseaseType diseaseType) {
        return user.get(player.getUniqueId()).hasDiseaseType(diseaseType);
    }

}