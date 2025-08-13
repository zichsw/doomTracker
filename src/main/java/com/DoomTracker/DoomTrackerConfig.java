package com.DoomTracker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;


@ConfigGroup("DelveTrackerConfig")
public interface DoomTrackerConfig extends Config
{
	// Unique
	@ConfigItem(
			position = 0,
			keyName = "showFloorsSinceUnique",
			name = "Floors Since Unique ",
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
			description = "Show chance of unique"
	)
	default boolean showChanceOfUnique()
	{
		return true;
	}

	// Pet
	@ConfigItem(
			position = 2,
			keyName = "showFloorsSincePet",
			name = "Floors since pet ",
			description = "Show floors since pet in overlay"
	)
	default boolean showFloorsSincePet()
	{
		return true;
	}

	@ConfigItem(
			position = 3,
			keyName = "showChanceOfPet",
			name = "Chance of pet ",
			description = "Show chance of pet"
	)
	default boolean showChanceOfPet()
	{
		return true;
	}

	// Cloth
	@ConfigItem(
			position = 4,
			keyName = "showFloorsSinceCloth",
			name = "Floors since cloth ",
			description = "Show floors since cloth in overlay"
	)
	default boolean showFloorsSinceCloth()
	{
		return false;
	}

	@ConfigItem(
			position = 5,
			keyName = "showChanceOfCloth",
			name = "Chance of cloth ",
			description = "Show chance of cloth"
	)
	default boolean showChanceOfCloth()
	{
		return false;
	}

	// Eye
	@ConfigItem(
			position = 6,
			keyName = "showFloorsSinceEye",
			name = "Floors since eye ",
			description = "Show floors since eye in overlay"
	)
	default boolean showFloorsSinceEye()
	{
		return false;
	}

	@ConfigItem(
			position = 7,
			keyName = "showChanceOfEye",
			name = "Chance of eye ",
			description = "Show chance of eye"
	)
	default boolean showChanceOfEye()
	{
		return false;
	}

	// Treads
	@ConfigItem(
			position = 8,
			keyName = "showFloorsSinceTreads",
			name = "Floors since treads ",
			description = "Show floors since treads in overlay"
	)
	default boolean showFloorsSinceTreads()
	{
		return false;
	}

	@ConfigItem(
			position = 9,
			keyName = "showChanceOfTreads",
			name = "Chance of treads ",
			description = "Show chance of treads"
	)
	default boolean showChanceOfTreads()
	{
		return false;
	}


}
