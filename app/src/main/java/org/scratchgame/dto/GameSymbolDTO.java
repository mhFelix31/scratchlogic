package org.scratchgame.dto;

public class GameSymbolDTO{
        private double reward_multiplier;
        private String type;
        private String impact;  // Optional field, only for bonus symbols
        private Integer extra;  // Optional field, only for extra_bonus symbols

        public GameSymbolDTO(double reward_multiplier, String type, Integer extra, String impact) {
            this.reward_multiplier = reward_multiplier;
            this.type = type;
            this.extra = extra;
            this.impact = impact;
        }

        // Getters and Setters
        public double getRewardMultiplier() {
            return reward_multiplier;
        }
    
        public void setRewardMultiplier(double rewardMultiplier) {
            this.reward_multiplier = rewardMultiplier;
        }
    
        public String getType() {
            return type;
        }
    
        public void setType(String type) {
            this.type = type;
        }
    
        public String getImpact() {
            return impact;
        }
    
        public void setImpact(String impact) {
            this.impact = impact;
        }
    
        public Integer getExtra() {
            return extra;
        }
    
        public void setExtra(Integer extra) {
            this.extra = extra;
        }
    
}
