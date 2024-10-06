package com.acsn1.enchantapi.enchant;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Map;
import java.util.UUID;

public interface Enchant {

    String getId();
    String getName();
    EnchantType getEnchantType();
    int getMaximumLevel();
    long getCooldown();
    Map<UUID, Long> getCooldowns();
    void execute(Player damager, Player entity, int level, EntityDamageByEntityEvent event);
    void executeDefense(Player player, int level, EntityDamageEvent event);
}
