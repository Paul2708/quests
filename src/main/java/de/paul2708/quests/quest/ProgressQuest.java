package de.paul2708.quests.quest;

import java.util.Arrays;
import java.util.List;

public class ProgressQuest extends Quest {

    private static final String PROGRESS_BAR = "||||||||||||||||||||||||||||||||||||||||||||||||||";
    private static final int PROGRESS_BAR_LENGTH = ProgressQuest.PROGRESS_BAR.length();

    private final int requiredProgress;

    public ProgressQuest(String identifier, String name, List<String> description, List<Condition> conditions,
                         int requiredProgress) {
        super(identifier, name, description, conditions);

        this.requiredProgress = requiredProgress;
    }

    public List<String> buildDescription(int progress) {
        int correctProgress = Math.min(progress, requiredProgress);
        int position = (int) (correctProgress * (PROGRESS_BAR_LENGTH / requiredProgress * 1.0));

        if (correctProgress >= requiredProgress) {
            position = PROGRESS_BAR_LENGTH;
        }

        String progressBar = PROGRESS_BAR;
        progressBar = "§a" + progressBar.substring(0, position) + "§7" + progressBar.substring(position);

        return Arrays.asList(
                progressBar,
                "§6(" + (int) (correctProgress * 100.0 / requiredProgress) + "%)",
                "");
    }

    public int getRequiredProgress() {
        return requiredProgress;
    }
}