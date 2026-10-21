public class GamificationEngine {
    
    package com.example.fitnotesrpg.engine;

import com.example.fitnotesrpg.database.UserCharacterEntity;

public class GamificationEngine {

    public static class RewardResult {
        public final int xpEarned;
        public final int goldEarned;
        public final boolean leveledUp;

        public RewardResult(int xpEarned, int goldEarned, boolean leveledUp) {
            this.xpEarned = xpEarned;
            this.goldEarned = goldEarned;
            this.leveledUp = leveledUp;
        }
    }

    // Calculates XP and updates character progression
    public static RewardResult processWorkoutSet(UserCharacterEntity character, int weight, int reps) {
        // Simple RPG math: Volume (Weight * Reps) / 10 = XP earned
        int volume = weight * reps;
        int xpEarned = Math.max(5, volume / 10); // Minimum 5 XP per set
        int goldEarned = Math.max(1, weight / 20); // Scale gold with weight lifted

        // Add to character totals
        int currentXp = character.getCurrentXp() + xpEarned;
        character.setGold(character.getGold() + goldEarned);

        boolean leveledUp = false;

        // Check for level up
        if (currentXp >= character.getXpToNextLevel()) {
            currentXp -= character.getXpToNextLevel();
            character.setLevel(character.getLevel() + 1);
            // Increase next level requirement by 50%
            character.setXpToNextLevel((int) (character.getXpToNextLevel() * 1.5));
            leveledUp = true;
        }

        character.setCurrentXp(currentXp);

        return new RewardResult(xpEarned, goldEarned, leveledUp);
    }
} 
}
