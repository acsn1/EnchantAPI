package com.acsn1.enchantapi.enchant;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Map;
import java.util.UUID;

public interface Enchant {

    String getId();
    String getName();
    int getMaximumLevel();
    long getCooldown();
    Map<UUID, Long> getCooldowns();
    void execute(Player damager, Player entity, int level, EntityDamageByEntityEvent event);

}
