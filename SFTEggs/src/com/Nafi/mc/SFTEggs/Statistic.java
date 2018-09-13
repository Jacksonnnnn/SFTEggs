package com.Nafi.mc.SFTEggs;

import org.bukkit.entity.EntityType;

public enum Statistic {
    Distance_By_Walking(org.bukkit.Statistic.WALK_ONE_CM,
            "DISTANCE_WALKING",
            "Distance Walked",
            "m"),
    Distance_By_Elytra_Flight(org.bukkit.Statistic.AVIATE_ONE_CM,
            "DISTANCE_ELYTRA",
            "Distance by Elytra",
            "m"),
    Distance_By_Horse(org.bukkit.Statistic.HORSE_ONE_CM,
            "DISTANCE_HORSE",
            "Distance by horse",
            "m"),
    Distance_By_Boat(org.bukkit.Statistic.BOAT_ONE_CM,
            "DISTANCE_BOAT",
            "Distance by boat",
            "m"),
    Distance_By_Pig(org.bukkit.Statistic.PIG_ONE_CM,
            "DISTANCE_PIG",
            "Distance by Pig",
            "m"),
    Distance_By_Swimming(org.bukkit.Statistic.SWIM_ONE_CM,
            "DISTANCE_SWIMMING",
            "Distance swam",
            "m"),
    Items_Enchanted(org.bukkit.Statistic.ITEM_ENCHANTED,
            "ITEMS_ENCHANTED",
            "Items enchanted",
            "items"),
    Sneak_Time(org.bukkit.Statistic.SNEAK_TIME,
            "SNEAK_TIME",
            "Time in sneak",
            "sec"),
    Distance_By_Sneaking(org.bukkit.Statistic.CROUCH_ONE_CM,
            "DISTANCE_CROUCH",
            "Distance in sneak",
            "m"),
    Distance_By_SPRINT(org.bukkit.Statistic.SPRINT_ONE_CM,
            "DISTANCE_SPRINT",
            "Distance in Sprint",
            "m"),
    Distance_By_Falling(org.bukkit.Statistic.FALL_ONE_CM,
            "DISTANCE_FALL",
            "Distance by fall",
            "m"),
    Distance_By_Climbing(org.bukkit.Statistic.CLIMB_ONE_CM,
            "DISTANCE_CLIMB",
            "Distance by climbing",
            "m"),
    Jumps(org.bukkit.Statistic.JUMP,
            "JUMPS",
            "Jumps",
            "jumps"),
    Damage_Dealt(org.bukkit.Statistic.DAMAGE_DEALT,
            "DAMAGE_DEALT",
            "Damage dealt",
            "health"),
    Damage_Taken(org.bukkit.Statistic.DAMAGE_TAKEN,
            "DAMAGE_TAKEN",
            "Damage taken",
            "Damage"),
    Deaths(org.bukkit.Statistic.DEATHS,
            "DEATHS",
            "Deaths",
            "deaths"),
    Animals_Bread(org.bukkit.Statistic.ANIMALS_BRED,
            "ANIMALS_BRED",
            "Animals bred",
            "animals"),
    Fish_Caught(org.bukkit.Statistic.FISH_CAUGHT,
            "FISH_CAUGHT",
            "Fish caught",
            "fish"),
    Cake_Eaten(org.bukkit.Statistic.CAKE_SLICES_EATEN,
            "CAKE_EATEN",
            "Cake slices eaten",
            "slices"),
    SLEPT(org.bukkit.Statistic.SLEEP_IN_BED,
            "SLEPT",
            "Slept in Bed",
            "fish"),
    Creeper_Kills(org.bukkit.Statistic.KILL_ENTITY,
            "CREEPER_KILLS",
            "Creepers Killed",
            "creepers", EntityType.CREEPER),
    Skeleton_Kills(org.bukkit.Statistic.KILL_ENTITY,
            "SKELETON_KILLS",
            "Skeletons Killed",
            "Skeletons", EntityType.SKELETON),
    Zombie_Kills(org.bukkit.Statistic.KILL_ENTITY,
            "ZOMBIE_KILLS",
            "Zombies Killed",
            "zombies", EntityType.ZOMBIE),
    Chicken_Kills(org.bukkit.Statistic.KILL_ENTITY,
            "CHICKEN_KILLS",
            "Chickens Killed",
            "chickens", EntityType.CHICKEN),
    Cow_Kills(org.bukkit.Statistic.KILL_ENTITY,
            "COW_KILLS",
            "Cows Killed",
            "cows", EntityType.COW),
    Sheep_Kills(org.bukkit.Statistic.KILL_ENTITY,
            "SHEEP_KILLS",
            "Sheep Killed",
            "sheep", EntityType.SHEEP),
    Slime_Kills(org.bukkit.Statistic.KILL_ENTITY,
            "SLIME_KILLS",
            "Slimes Killed",
            "slimes", EntityType.SLIME),
    Squid_Kills(org.bukkit.Statistic.KILL_ENTITY,
            "SQUID_KILLS",
            "Squid Killed",
            "squid", EntityType.SQUID),
    Witch_Kills(org.bukkit.Statistic.KILL_ENTITY,
            "WITCH_KILLS",
            "Witches Killed",
            "witches", EntityType.WITCH),
    Spider_Kills(org.bukkit.Statistic.KILL_ENTITY,
            "SPIDER_KILLS",
            "Spiders Killed",
            "spiders", EntityType.SPIDER);

    private final String Name;
    private final String Unit;
    private final org.bukkit.Statistic Stat;
    private final String toString;
    private final EntityType entity;
    private boolean isMobKills;


    Statistic(org.bukkit.Statistic stat, String tostring, String name, String unit) {
        this(stat, tostring, name, unit, EntityType.UNKNOWN);
    }


    Statistic(org.bukkit.Statistic stat, String tostring, String name, String unit, EntityType ent) {
        this.Name = name;
        this.Unit = unit;
        this.Stat = stat;
        this.toString = tostring;
        this.entity = ent;
        this.isMobKills = ent != EntityType.UNKNOWN;
    }


    public EntityType getEntity() {
        return this.entity;
    }

    public boolean isMobKill() {
        return this.isMobKills;
    }

    public String getUsableString() {
        return this.Name;
    }

    public String getUnit() {
        return this.Unit;
    }

    public org.bukkit.Statistic getStatistic() {
        return this.Stat;
    }

    public String toString() {
        return this.toString;
    }

    public Statistic parsefromString(String s) {
        for (Statistic stat : Statistic.values()) {
            if (stat.toString == s.toUpperCase()) {
                return stat;
            }
        }

        return null;
    }

    public static class parse {
        public static Statistic fromString(String s) {
            for (Statistic stat : Statistic.values()) {
                if (stat.toString.equalsIgnoreCase(s)) {
                    return stat;
                }
            }
            return null;
        }

        public static Statistic fromUsableString(String s) {
            for (Statistic stat : Statistic.values()) {
                if (stat.getUsableString().equalsIgnoreCase(s)) {
                    return stat;
                }
            }
            return null;
        }
    }


}
