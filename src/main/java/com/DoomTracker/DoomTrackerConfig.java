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
			name = "Show total floor completions",
			description = "Resets upon receiving any unique item."
	)
	default boolean showFloorsSinceUnique()
	{
		return true;
	}

	@ConfigItem(
			position = 1,
			keyName = "showChanceOfUnique",
			name = "Show chance of unique",
			description = "Show cumulative chance of unique. Resets upon receiving any unique item."
	)
	default boolean showChanceOfUnique()
	{
		return true;
	}

	// Pet
	@ConfigItem(
			position = 2,
			keyName = "showFloorsSincePet",
			name = "Show floors without pet",
			description = "Show floors without pet in the overlay. Resets upon receiving a pet."
	)
	default boolean showFloorsSincePet()
	{
		return false;
	}

	@ConfigItem(
			position = 3,
			keyName = "showChanceOfPet",
			name = "Show chance of pet ",
			description = "Show cumulative chance of pet. Resets upon receiving a pet."
	)
	default boolean showChanceOfPet()
	{
		return true;
	}

	// Cloth
	@ConfigItem(
			position = 4,
			keyName = "showFloorsSinceCloth",
			name = "Show floors without pet",
			description = "Show floors completed without Mokhaiotl Cloth. Resets upon receiving the cloth. "
	)
	default boolean showFloorsSinceCloth()
	{
		return false;
	}

	@ConfigItem(
			position = 5,
			keyName = "showChanceOfCloth",
			name = "Show chance of cloth",
			description = "Show cumulative chance for Mokhaiotl Cloth. Resets upon receiving the cloth."
	)
	default boolean showChanceOfCloth()
	{
		return false;
	}

	// Eye
	@ConfigItem(
			position = 6,
			keyName = "showFloorsSinceEye",
			name = "Show floors without eye",
			description = "Show floors completed without Eye of Ayak. Resets upon receiving the eye."
	)
	default boolean showFloorsSinceEye()
	{
		return false;
	}

	@ConfigItem(
			position = 7,
			keyName = "showChanceOfEye",
			name = "Show chance of eye",
			description = "Show cumulative chance of Eye of Ayak.Resets upon receiving the eye."
	)
	default boolean showChanceOfEye()
	{
		return false;
	}

	// Treads
	@ConfigItem(
			position = 8,
			keyName = "showFloorsSinceTreads",
			name = "Show floors without treads",
			description = "Show floors completed without Avernic Treads. Resets upon receiving treads."
	)
	default boolean showFloorsSinceTreads()
	{
		return false;
	}

	@ConfigItem(
			position = 9,
			keyName = "showChanceOfTreads",
			name = "Show chance of treads",
			description = "Show cumulative chance of Avernic Treads. Resets upon receiving treads."
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
