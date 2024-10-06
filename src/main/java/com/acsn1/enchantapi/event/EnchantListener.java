package com.acsn1.enchantapi.event;

import com.acsn1.enchantapi.EnchantAPI;
import com.acsn1.enchantapi.enchant.Enchant;
import com.acsn1.enchantapi.enchant.EnchantType;
import com.acsn1.enchantapi.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EnchantListener implements Listener {

    private final EnchantAPI plugin;
    public EnchantListener(EnchantAPI plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamageTaken(EntityDamageEvent event) {
        if(event.isCancelled()) return;
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        for(ItemStack armor : player.getInventory().getArmorContents()) {
            if(armor==null || armor.getType() == Material.AIR) continue;
            List<Pair<Enchant, Integer>> enchants = EnchantAPI.getEnchantments(player);
            if(enchants.isEmpty()) continue;
            enchants.forEach(data->{
                Enchant enchant = data.getKey();
                int level = data.getValue();
                enchant.executeDefense(player, level, event);
            });
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEnchantment(EntityDamageByEntityEvent event) {
        if(event.isCancelled()) return;
        if(!(event.getEntity() instanceof Player)) return;
        Player entity = (Player) event.getEntity();

        if(event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            List<Pair<Enchant, Integer>> enchants = EnchantAPI.getEnchantments(damager);
            if(enchants.isEmpty()) return;
            enchants.forEach(data->{
                Enchant enchant = data.getKey();
                if(enchant.getEnchantType() != EnchantType.SWORD) return;
                int level = data.getValue();
                enchant.execute(damager, entity, level, event);
            });

            return;
        }

        if(event.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getDamager();
            if(!(arrow.getShooter() instanceof Player)) return;
            Player shooter = (Player) arrow.getShooter();
            List<Pair<Enchant, Integer>> enchants = EnchantAPI.getEnchantments(shooter);
            if(enchants.isEmpty()) return;
            enchants.forEach(data->{
                Enchant enchant = data.getKey();
                if(enchant.getEnchantType() != EnchantType.BOW) return;
                int level = data.getValue();
                enchant.execute(shooter, entity, level, event);
            });

        }

    }

}
