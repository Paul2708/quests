package de.paul2708.quests.database;

import java.util.UUID;

public interface Database {

    void setup();

    void complete(UUID uuid, String questIdentifier);

    int getProgress(UUID uuid, String questIdentifier);

    void storeProgress(UUID uuid, String questIdentifier, int newProgress);
    boolean hasCompleted(UUID uuid, String questIdentifier);
}