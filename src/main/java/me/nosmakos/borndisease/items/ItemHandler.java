package me.nosmakos.borndisease.items;

import me.nosmakos.borndisease.BornDisease;
import me.nosmakos.borndisease.WorldManagement;
import me.nosmakos.borndisease.items.types.RescueInhaler;
import me.nosmakos.borndisease.items.types.Syringe;
import me.nosmakos.borndisease.utilities.CureItem;
import me.nosmakos.borndisease.utilities.Item;
import me.nosmakos.borndisease.utilities.Lang;
import me.nosmakos.borndisease.utilities.Permission;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ItemHandler implements Listener {

    private BornDisease plugin;
    private WorldManagement manager;

    private List<AbstractItem> items = new ArrayList<>();

    public ItemHandler(BornDisease plugin) {
        this.plugin = plugin;
        this.manager = plugin.getWorldManagement();

        AbstractItem[] bases = {
                new Syringe(plugin),
                new RescueInhaler(plugin)
        };

        Collections.addAll(items, bases);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        for (AbstractItem base : items) {
            if (event.getHand() != EquipmentSlot.OFF_HAND && Item.hasItem(event.getPlayer(), base.getItemType())) {
                if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
                    continue;
                }
                base.use(event.getPlayer());
                event.setCancelled(base.isCancelled());
            }
        }
    }

   /* @EventHandler
    public void onInteractEntity(PlayerInteractAtEntityEvent event) {
        for (AbstractItemUse base : items) {
            if (event.getHand() != EquipmentSlot.OFF_HAND && Item.hasItem(event.getPlayer(), base.getItemType())) {
                if (event.getRightClicked() == null || event.getRightClicked().getType() != base.getEntityType()) {
                    continue;
                }
                base.use(event.getPlayer(), event.getRightClicked());
            }
        }
    }*/

    @EventHandler
    public void onDropItemHologram(PlayerDropItemEvent event) {
        Boolean enabled = plugin.getConfig().getBoolean("enable-custom-item-holograms");
        if (manager.isDisabledInWorld(event.getPlayer().getWorld()) || !enabled) return;

        ItemStack item = event.getItemDrop().getItemStack();

        if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;
        Item.createItemHologram(event.getItemDrop(), item.getItemMeta().getDisplayName());
    }

    @EventHandler
    public void onPlayerContentDropHologram(PlayerDeathEvent event) {
        Boolean enabled = plugin.getConfig().getBoolean("enable-custom-item-holograms");
        if (manager.isDisabledInWorld(event.getEntity().getWorld()) || !enabled) return;

        Iterator<ItemStack> it = event.getDrops().iterator();

        while (it.hasNext()) {
            ItemStack item = it.next();
            if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
                continue;
            }
            it.remove();
            Item.dropItemHologram(event.getEntity().getLocation(), item);
        }
    }
}
