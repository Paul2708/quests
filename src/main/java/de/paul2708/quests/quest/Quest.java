package de.paul2708.quests.quest;

import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Quest {

    private final String identifier;
    private final String name;
    private final List<String> description;
    private final List<Condition> conditions;

    public Quest(String identifier, String name, List<String> description, List<Condition> conditions) {
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.conditions = conditions;
    }

    public boolean fulfillsConditions(Player player) {
        return conditions.stream()
                .allMatch(cond -> cond.fulfills(player));
    }

    public List<String> getConditionDescription() {
        List<String> lore = new LinkedList<>();

        if (!conditions.isEmpty()) {
            lore.add("§6Anforderungen:");
            for (Condition condition : conditions) {
                lore.add("  §8- §7" + condition.description());
            }

            lore.add(" ");
        }

        return lore;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public List<String> getDescription() {
        return Collections.unmodifiableList(description);
    }

    public List<Condition> getConditions() {
        return Collections.unmodifiableList(conditions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Quest quest = (Quest) o;
        return Objects.equals(identifier, quest.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}