package de.paul2708.quests;

import de.paul2708.quests.database.Database;
import de.paul2708.quests.quest.Quest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.BiConsumer;

public interface QuestSystem {

    void init(Database database);

    void setCompletionAction(BiConsumer<Player, Quest> completedQuestAction);

    void register(Quest quest);

    void complete(Player player, String questIdentifier);

    void progress(Player player, String questIdentifier);

    void progress(Player player, String questIdentifier, int progress);

    List<ItemStack> buildQuestItems(Player player);
}