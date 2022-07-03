package de.paul2708.quests.example;

import de.paul2708.quests.database.Database;

import java.util.*;

public class MemoryDatabase implements Database {

    private Map<UUID, List<String>> completionDatabase;
    private Map<Map.Entry<UUID, String>, Integer> progressDatabase;

    @Override
    public void setup() {
        this.completionDatabase = new HashMap<>();
        this.progressDatabase = new HashMap<>();
    }

    @Override
    public void complete(UUID uuid, String questIdentifier) {
        List<String> quests = completionDatabase.getOrDefault(uuid, new LinkedList<>());
        quests.add(questIdentifier);

        completionDatabase.put(uuid, quests);
    }

    @Override
    public int getProgress(UUID uuid, String questIdentifier) {
        return progressDatabase.getOrDefault(new AbstractMap.SimpleImmutableEntry<>(uuid, questIdentifier), 0);
    }

    @Override
    public void storeProgress(UUID uuid, String questIdentifier, int newProgress) {
        progressDatabase.put(new AbstractMap.SimpleImmutableEntry<>(uuid, questIdentifier), newProgress);
    }

    @Override
    public boolean hasCompleted(UUID uuid, String questIdentifier) {
        return completionDatabase.containsKey(uuid) && completionDatabase.get(uuid).contains(questIdentifier);
    }
}