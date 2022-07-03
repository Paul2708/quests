package de.paul2708.quests.quest;

import java.util.Arrays;
import java.util.List;

public class ProgressQuest extends Quest {
    private final int requiredProgress;

    public ProgressQuest(String identifier, String name, List<String> description, List<Condition> conditions,
                         int requiredProgress) {
        super(identifier, name, description, conditions);

        this.requiredProgress = requiredProgress;
    }

    public List<String> buildDescription(int progress) {
        if (progress >= requiredProgress) {
            progress = requiredProgress;
        }

        String progressBar = "||||||||||||||||||||||||||||||||||||||||||||||||||";
        int length = progressBar.length();

        /*
        20 / 250     * (length/250)
           / length
         */

        int position = (int) (progress * (length / requiredProgress * 1.0));
        if (progress >= requiredProgress) {
            position = length;
        }

        progressBar = "§a" + progressBar.substring(0, position) + "§7" + progressBar.substring(position, progressBar.length());

        return Arrays.asList(
                progressBar,
                "§6(" + (int) (progress * 100.0 / requiredProgress) + "%)",
                "");
    }

    public int getRequiredProgress() {
        return requiredProgress;
    }
}