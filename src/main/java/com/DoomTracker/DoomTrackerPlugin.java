/*
 * Copyright (c) 2018, Psikoi <https://github.com/psikoi>
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * Copyright (c) 2025, Steve <https://github.com/zichsw>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.DoomTracker;
import com.google.inject.Provides;

import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.ItemContainer;
import net.runelite.api.ScriptID;
import com.google.common.base.Strings;

import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.ScriptPreFired;

import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.util.Set;
import java.util.HashMap;
import com.google.gson.Gson;

@Slf4j
@PluginDescriptor(
		name = "Doom Tracker"
)
public class DoomTrackerPlugin extends Plugin
{
	private static final Set<Integer> REGION_IDS = Set.of(5269, 13668, 14180);

	@Getter
	private Data data;

	@Inject
	private Client client;

	@Inject
	private DoomTrackerConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private DoomTrackerOverlay overlay;

	@Inject
	ConfigManager configManager;

	@Inject
	private Gson gson;

	private final HashMap<Integer, Double> uniqueChance = new HashMap<>();
	private final HashMap<Integer, Double> petChance = new HashMap<>();
	private final HashMap<Integer, Double> clothChance = new HashMap<>();
	private final HashMap<Integer, Double> eyeChance = new HashMap<>();
	private final HashMap<Integer, Double> treadsChance = new HashMap<>();

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);

		clothChance.put(1, 0.0);
		clothChance.put(2, 1.0/2500.00);
		clothChance.put(3, 1.0/2000.00);
		clothChance.put(4, 1.0/1350.00);
		clothChance.put(5, 1.0/810.00);
		clothChance.put(6, 1.0/765.00);
		clothChance.put(7, 1.0/720.00);
		clothChance.put(8, 1.0/630.00);
		clothChance.put(9, 1.0/540.00);

		eyeChance.put(1, 0.0);
		eyeChance.put(2, 0.0);
		eyeChance.put(3, 1.0/2000.00);
		eyeChance.put(4, 1.0/1350.00);
		eyeChance.put(5, 1.0/810.00);
		eyeChance.put(6, 1.0/765.00);
		eyeChance.put(7, 1.0/720.00);
		eyeChance.put(8, 1.0/630.00);
		eyeChance.put(9, 1.0/540.00);

		treadsChance.put(1, 0.0);
		treadsChance.put(2, 0.0);
		treadsChance.put(3, 0.0);
		treadsChance.put(4, 1.0/1350.00);
		treadsChance.put(5, 1.0/810.00);
		treadsChance.put(6, 1.0/765.00);
		treadsChance.put(7, 1.0/720.00);
		treadsChance.put(8, 1.0/630.00);
		treadsChance.put(9, 1.0/540.00);

		//chances of unique from each floor
		uniqueChance.put(1, 0.0);
		uniqueChance.put(2, 1.0/2500.00);
		uniqueChance.put(3, 1.0/1000.00);
		uniqueChance.put(4, 1.0/450.00);
		uniqueChance.put(5, 1.0/270.00);
		uniqueChance.put(6, 1.0/255.00);
		uniqueChance.put(7, 1.0/240.00);
		uniqueChance.put(8, 1.0/210.00);
		uniqueChance.put(9, 1.0/180.00);

		petChance.put(1, 0.0);
		petChance.put(2, 0.0);
		petChance.put(3, 0.0);
		petChance.put(4, 0.0);
		petChance.put(5, 0.0);
		petChance.put(6, 1.0/1000.00);
		petChance.put(7, 1.0/750.00);
		petChance.put(8, 1.0/500.00);
		petChance.put(9, 1.0/250.00);

		if (client.getGameState() == GameState.LOGGED_IN) {
			populate();
		}
	}


	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		save();
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event) {
		if (event.getGameState() == GameState.LOGGED_IN) {
			populate(); // Load once on login
		} else if (event.getGameState() == GameState.LOGIN_SCREEN) {
			save(); // Save once on logout
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage) {
		if (chatMessage.getType() != ChatMessageType.GAMEMESSAGE || !inRegion()) {
			return;
		}
		String message = chatMessage.getMessage();
		if (message.contains("Delve level") && message.contains("duration")) {
			int level;

			if (message.contains("8+")) {
				level = 9;
			}
			else{
				String [] parts = message.split(" ");
				level = Integer.parseInt(parts[2]);
			}
			addFloorCompletions(level);
		}
	}


	@Subscribe
	public void onScriptPreFired(ScriptPreFired event)
	{
		if (event.getScriptId() == ScriptID.DOM_LOOT_CLAIM)
		{
			ItemContainer inv = client.getItemContainer(InventoryID.DOM_LOOTPILE);

			if (inv == null) return;

			for (var item : inv.getItems()) {
				if (item.getId() == ItemID.AVERNIC_TREADS){
					getData().setFloorsSinceCloth(new int [9]);
					getData().setFloorsSinceUnique(new int [9]);
				}
				if (item.getId() == ItemID.DOMPET){
					getData().setFloorsSincePet(new int [9]);

				}
				if (item.getId() == ItemID.EYE_OF_AYAK){
					getData().setFloorsSinceEye(new int [9]);
					getData().setFloorsSinceUnique(new int [9]);
				}
				if (item.getId() == ItemID.MOKHAIOTL_CLOTH){
					getData().setFloorsSinceCloth(new int [9]);
					getData().setFloorsSinceUnique(new int [9]);
				}
			}
		}
	}

	private void populate() {
		String json = configManager.getRSProfileConfiguration(DoomTrackerConfig.CONFIG_GROUP, DoomTrackerConfig.CONFIG_TRACKED);
		if (Strings.isNullOrEmpty(json))
		{
			data = new Data();
		}
		else
		{
			data = gson.fromJson(json, Data.class);
		}
	}

	private void save() {
		configManager.setRSProfileConfiguration(DoomTrackerConfig.CONFIG_GROUP, DoomTrackerConfig.CONFIG_TRACKED, gson.toJson(data));
	}

	public int getTotalFloors(int [] counter){
		int floors = 0;
		for (int i = 0; i < counter.length; i++) {
			floors += counter[i];
		}
		return floors;
	}

	public boolean inRegion(){
		final var region = client.getTopLevelWorldView();
		if (!region.isInstance()){ return false; }

		for (var r : region.getMapRegions()){
			if (REGION_IDS.contains(r)){
				return true;
			}
		}
		return false;
	}

	private void addFloorCompletions(int level) {
		getData().incrementFloorClothCompletions(level);
		getData().incrementFloorEyeCompletions(level);
		getData().incrementFloorTreadCompletions(level);
		getData().incrementFloorUniqueCompletions(level);
		getData().incrementFloorPetCompletions(level);

		save(); // Right now, plugin saves immediately after updating. Hopefully doesn't cause issue with performance
	}


	private double calculateCumulativeRolls(int [] counter, HashMap<Integer, Double> chanceMap) {
		double noDropProb = 1.0;
		for (int i = 1; i <= counter.length; i++) {
			int count = counter[i - 1];
			double p = chanceMap.getOrDefault(i, 0.0);
			noDropProb *= Math.pow(1 - p, count);
		}
		return 1 - noDropProb;
	}

	public double getClothRolls() {
		return calculateCumulativeRolls(getData().getFloorsSinceCloth(), clothChance);
	}

	public double getEyeRolls() {
		return calculateCumulativeRolls(getData().getFloorsSinceEye(), eyeChance);
	}

	public double getTreadsRolls() {
		return calculateCumulativeRolls(getData().getFloorsSinceTreads(), treadsChance);
	}

	public double getUniqueRolls() {
		return calculateCumulativeRolls(getData().getFloorsSinceUnique(), uniqueChance);
	}

	public double getPetRolls() {
		return calculateCumulativeRolls(getData().getFloorsSincePet(), petChance);
	}


	@Provides
	DoomTrackerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(DoomTrackerConfig.class);
	}

	@Provides
	DoomTrackerOverlay provideOverlay(DoomTrackerPlugin plugin){
		return new DoomTrackerOverlay(plugin, client, config);
	}

	public static class Data{
		@Getter
		@Setter
		public int [] floorsSinceCloth;
		@Getter
		@Setter
		public int [] floorsSinceEye;
		@Getter
		@Setter
		public int [] floorsSinceTreads;
		@Getter
		@Setter
		public int [] floorsSincePet;
		@Getter
		@Setter
		public int [] floorsSinceUnique;

		public Data()
		{
			this.floorsSinceCloth = new int[9];
			this.floorsSinceEye = new int[9];
			this.floorsSinceTreads = new int[9];
			this.floorsSincePet = new int[9];
			this.floorsSinceUnique = new int[9];
		}

		public void incrementFloorClothCompletions(int level)
		{
			this.floorsSinceCloth[level - 1] += 1;
		}


		public void incrementFloorEyeCompletions(int level)
		{
			this.floorsSinceEye[level - 1] += 1;
		}

		public void incrementFloorTreadCompletions(int level)
		{
			this.floorsSinceTreads[level - 1] += 1;
		}


		public void incrementFloorPetCompletions(int level)
		{
			this.floorsSincePet[level - 1] += 1;
		}

		public void incrementFloorUniqueCompletions(int level)
		{
			this.floorsSinceUnique[level - 1] += 1;
		}
	}
}
