package me.nosmakos.borndisease.items.types;

import me.nosmakos.borndisease.BornDisease;
import me.nosmakos.borndisease.items.AbstractItem;
import me.nosmakos.borndisease.utilities.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RescueInhaler extends AbstractItem {

    private BornDisease plugin;

    private static final CureItem ITEM = CureItem.RESCUE_INHALER;
    private static final int COOLDOWN = 1;
    private static final Permission PERMISSION = Permission.RESCUE_INHALER;
    private static final boolean CANCELLED = false;


    private List<UUID> control = new ArrayList<>();

    public RescueInhaler(BornDisease plugin) {
        super(plugin, ITEM, COOLDOWN, PERMISSION, CANCELLED);
        this.plugin = plugin;
    }

    @Override
    protected void interact(Player player) {
        /*if (!hasDisease(player, DiseaseType.ASTHMA)){
            player.sendMessage("You do not have asthma or any crisis for using a rescue inhaler at the moment.");
            return;
        }
*/

        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 250));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 250));

        if (control.contains(player.getUniqueId())) return;
        control.add(player.getUniqueId());

        final int maxTime = 20;
        final ItemStack rescueInhaler = player.getInventory().getItemInMainHand();

        new BukkitRunnable() {
            private int i = 0;

            @Override
            public void run() {
                if (i > maxTime || !player.getInventory().getItemInMainHand().isSimilar(rescueInhaler)) {
                    control.remove(player.getUniqueId());

                    player.removePotionEffect(PotionEffectType.SLOW);
                    player.removePotionEffect(PotionEffectType.JUMP);
                    plugin.sendActionBar(player, "");
                    cancel();
                    return;
                }
                plugin.sendActionBar(player, RDUtils.progress(i, 20, 20));
                ++i;
            }

        }.runTaskTimer(plugin, 0, 1);
    }
}
