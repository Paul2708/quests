package de.paul2708.quests;

import de.paul2708.quests.database.Database;
import de.paul2708.quests.quest.Condition;
import de.paul2708.quests.quest.ProgressQuest;
import de.paul2708.quests.quest.Quest;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class DefaultQuestSystem implements QuestSystem {

    private Database database;

    private final Map<String, Quest> quests;
    private BiConsumer<Player, Quest> completionAction;

    public DefaultQuestSystem() {
        this.quests = new LinkedHashMap<>();
        this.completionAction = new FallbackCompletionAction();
    }

    @Override
    public void init(Database database) {
        this.database = database;

        this.database.setup();
    }

    @Override
    public void setCompletionAction(BiConsumer<Player, Quest> completedQuestAction) {
        this.completionAction = completedQuestAction;
    }

    @Override
    public void register(Quest quest) {
        if (quests.containsKey(quest.getIdentifier())) {
            throw new IllegalArgumentException(String.format("You already registered a quest with the identifier '%s'",
                    quest.getIdentifier()));
        }

        this.quests.put(quest.getIdentifier(), quest);
    }

    @Override
    public void complete(Player player, String questIdentifier) {
        if (database.hasCompleted(player.getUniqueId(), questIdentifier)) {
            return;
        }

        Quest quest = quests.get(questIdentifier);
        if (quest.fulfillsConditions(player)) {
            completionAction.accept(player, quest);
            database.complete(player.getUniqueId(), questIdentifier);
        }
    }

    @Override
    public void progress(Player player, String questIdentifier) {
        this.progress(player, questIdentifier, 1);
    }

    @Override
    public void progress(Player player, String questIdentifier, int progress) {
        if (!(quests.get(questIdentifier) instanceof ProgressQuest)) {
            throw new IllegalArgumentException(String.format(
                    "The quest with id '%s' is not an instance of ProgressQuest.", questIdentifier));
        }

        if (database.hasCompleted(player.getUniqueId(), questIdentifier)) {
            return;
        }

        ProgressQuest quest = (ProgressQuest) quests.get(questIdentifier);
        if (quest.fulfillsConditions(player)) {
            int currentProgress = database.getProgress(player.getUniqueId(), questIdentifier);

            database.storeProgress(player.getUniqueId(), questIdentifier, currentProgress + progress);

            if (currentProgress + progress >= quest.getRequiredProgress()) {
                completionAction.accept(player, quest);
                database.complete(player.getUniqueId(), questIdentifier);
            }
        }
    }

    @Override
    public List<ItemStack> buildQuestItems(Player player) {
        List<ItemStack> items = new LinkedList<>();

        for (Quest quest : quests.values()) {
            ItemStack item = new ItemStack(Material.INK_SACK, 1);
            ItemMeta meta = item.getItemMeta();

            List<String> lore = new LinkedList<>();

            lore.addAll(quest.getDescription());
            lore.add(" ");
            lore.addAll(quest.getConditionDescription());

            if (quest instanceof ProgressQuest) {
                lore.addAll(((ProgressQuest) quest)
                        .buildDescription(database.getProgress(player.getUniqueId(), quest.getIdentifier())));
            }

            if (database.hasCompleted(player.getUniqueId(), quest.getIdentifier())) {
                item.setDurability((short) 10);
                meta.setDisplayName("§a" + quest.getName());
                lore.add("§aerledigt");
            } else {
                item.setDurability((short) 8);
                lore.add("§cnicht erledigt");
                meta.setDisplayName("§c" + quest.getName());
            }

            meta.setLore(lore);
            item.setItemMeta(meta);

            items.add(item);
        }

        return items;
    }

    public static class FallbackCompletionAction implements BiConsumer<Player, Quest> {

        @Override
        public void accept(Player player, Quest quest) {
            player.sendMessage("[Quests] Congratulation!");
            player.sendMessage(String.format("[Quests] You completed the quest '%s'.", quest.getName()));
        }
    }
}