package de.paul2708.quests.quest;

import org.bukkit.entity.Player;
public interface Condition {

    String description();

    boolean fulfills(Player player);
}