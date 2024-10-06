package com.acsn1.enchantapi;

import com.acsn1.enchantapi.enchant.Enchant;
import com.acsn1.enchantapi.event.EnchantListener;
import com.acsn1.enchantapi.util.Pair;
import de.tr7zw.nbtapi.NBT;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

@Getter
public final class EnchantAPI extends JavaPlugin {

    @Override
    public void onEnable() {
        new EnchantListener(this);
    }

    private static final Set<Enchant> enchantments = new HashSet<>();

    public static List<Pair<Enchant, Integer>> getEnchantments(ItemStack item) {
        List<Pair<Enchant, Integer>> list = new ArrayList<>();
        enchantments.forEach(enchant-> {
            NBT.get(item, nbt-> {
                if(!nbt.hasTag(enchant.getId())) return;
                list.add(new Pair<>(enchant, nbt.getInteger(enchant.getId())));
            });
        });
        return list;
    }

    public static List<Pair<Enchant, Integer>> getEnchantments(Player player) {
        List<Pair<Enchant, Integer>> list = new ArrayList<>();

        for(ItemStack armor : player.getInventory().getArmorContents()) {
            if(armor==null || armor.getType() == Material.AIR) continue;
            list.addAll(getEnchantments(armor));
        }
        ItemStack hand = player.getItemInHand();
        if(hand==null || hand.getType() == Material.AIR) return list;
        list.addAll(getEnchantments(hand));
        return list;

    }


    public static void registerEnchantment(Enchant... enchant) {
        Collections.addAll(enchantments, enchant);
    }

    public static void unregisterEnchantment(Enchant... enchant) {
        Arrays.stream(enchant).forEach(enchantments::remove);
    }

}
