package me.nosmakos.borndisease.items.types;

import me.nosmakos.borndisease.BornDisease;
import me.nosmakos.borndisease.items.AbstractItem;
import me.nosmakos.borndisease.utilities.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Syringe extends AbstractItem implements Listener {

    private BornDisease plugin;

    private static final CureItem ITEM = CureItem.SYRINGE;
    private static final int COOLDOWN = 1;
    private static final Permission PERMISSION = Permission.SYRINGE;
    private static final boolean CANCELLED = false;

    private final int[] slots = {3, 4, 5, 6, 7, 8};

    public Syringe(BornDisease plugin) {
        super(plugin, ITEM, COOLDOWN, PERMISSION, CANCELLED);
        this.plugin = plugin;
    }

    @Override
    protected void interact(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, Lang.SYRINGE_TITLE.get());

        for (int slot : slots) {
            inv.setItem(slot, Item.GUI_GLASS);
        }

        inv.setItem(0, Item.getItem(CureItem.ALTEPLASE, 1));
        inv.setItem(1, Item.getItem(CureItem.NITROGLYCERIN, 1));
        inv.setItem(2, Item.getItem(CureItem.INSULIN, 1));

        player.openInventory(inv);
    }

    @EventHandler
    public void onRadio(InventoryClickEvent event) {
        if ((event.getClickedInventory() != null) && (event.getView().getTitle().contains(Lang.SYRINGE_TITLE.get()))) {
            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();
            if ((event.getClick().isShiftClick() || (item == null) || (item.getType() == Material.AIR) || item.getType() == XMaterial.GRAY_STAINED_GLASS_PANE.pM())) {
                return;
            }
            Player player = (Player) event.getWhoClicked();

            if (!player.getInventory().containsAtLeast(event.getCurrentItem(), 1)){
                player.sendMessage("not available");
                player.closeInventory();
                return;
            }

            switch (event.getSlot()) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
            player.closeInventory();
        }
    }
}
