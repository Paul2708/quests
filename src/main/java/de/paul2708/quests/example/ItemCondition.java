package de.paul2708.quests.example;

import de.paul2708.quests.quest.Condition;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemCondition implements Condition {

    @Override
    public String description() {
        return "§8habe einen Dimanten im Inventar";
    }

    @Override
    public boolean fulfills(Player player) {
        return player.getInventory().contains(new ItemStack(Material.DIAMOND));
    }
}