package com.DoomTracker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;


@ConfigGroup("DoomTrackerConfig")
public interface DoomTrackerConfig extends Config
{
	// Unique
	@ConfigItem(
			position = 0,
			keyName = "showFloorsSinceUnique",
			name = "Floors Without Unique ",
			description = "Show floors since unique in overlay"
	)
	default boolean showFloorsSinceUnique()
	{
		return true;
	}

	@ConfigItem(
			position = 1,
			keyName = "showChanceOfUnique",
			name = "Chance of Unique ",
			description = "Show cumulative chance of unique"
	)
	default boolean showChanceOfUnique()
	{
		return true;
	}

	// Pet
	@ConfigItem(
			position = 2,
			keyName = "showFloorsSincePet",
			name = "Floors without pet ",
			description = "Show floors without pet in overlay"
	)
	default boolean showFloorsSincePet()
	{
		return true;
	}

	@ConfigItem(
			position = 3,
			keyName = "showChanceOfPet",
			name = "Chance of pet ",
			description = "Show cumulative chance of pet"
	)
	default boolean showChanceOfPet()
	{
		return true;
	}

	// Cloth
	@ConfigItem(
			position = 4,
			keyName = "showFloorsSinceCloth",
			name = "Floors without cloth ",
			description = "Show floors without cloth"
	)
	default boolean showFloorsSinceCloth()
	{
		return false;
	}

	@ConfigItem(
			position = 5,
			keyName = "showChanceOfCloth",
			name = "Chance of cloth ",
			description = "Show cumulative chance of cloth"
	)
	default boolean showChanceOfCloth()
	{
		return false;
	}

	// Eye
	@ConfigItem(
			position = 6,
			keyName = "showFloorsSinceEye",
			name = "Floors without eye ",
			description = "Show floors without Eye of Ayak"
	)
	default boolean showFloorsSinceEye()
	{
		return false;
	}

	@ConfigItem(
			position = 7,
			keyName = "showChanceOfEye",
			name = "Chance of eye ",
			description = "Show cumulative chance of Eye of Ayak"
	)
	default boolean showChanceOfEye()
	{
		return false;
	}

	// Treads
	@ConfigItem(
			position = 8,
			keyName = "showFloorsSinceTreads",
			name = "Floors without treads ",
			description = "Show floors without treads"
	)
	default boolean showFloorsSinceTreads()
	{
		return false;
	}

	@ConfigItem(
			position = 9,
			keyName = "showChanceOfTreads",
			name = "Chance of treads ",
			description = "Show cumulative chance of treads"
	)
	default boolean showChanceOfTreads()
	{
		return false;
	}

	@ConfigItem(
			keyName = "playerDataJson",
			name = "Player Data Json",
			description = "Serialized player data",
			hidden = true
	)

	default String playerDataJson(){
		return "";
	}

	@ConfigItem(
			keyName = "playerDataJson",
			name = "",
			description = "",
			hidden = true
	)
	void playerDataJson(String data);
}
