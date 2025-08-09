package com.example;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.RuneLite;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

@Slf4j
@PluginDescriptor(
	name = "Example"
)
public class DelveTrackerPlugin extends Plugin
{
	private static final Set<Integer> REGION_IDS = Set.of(5269, 13668, 14180);
	private int [] floorCompletions  = new int[9];
	private int floorsSinceUnique = 0;
	private int floorsSinceEye = 0;
	private int floorsSinceCloth = 0;
	private int floorsSinceTreads = 0;
	private int florsSincePet = 0;

	private double uniqueRolls = 0;
	private double clothRolls = 0;
	private double eyeRolls = 0;
	private double treadsRolls = 0;
	private double petRolls = 0;

	private String rsn = null;
	private RLReadWrite fileRW;



	@Inject
	private Client client;

	@Inject
	private DelveTrackerConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private DelveTrackerOverlay overlay;

	protected File dataFolder = new File(RuneLite.RUNELITE_DIR, "delveTracker");


	private HashMap<Integer, Double> uniqueChance = new HashMap<>();
	private HashMap<Integer, Double> petChance = new HashMap<>();
	private HashMap<Integer, Double> clothChance = new HashMap<>();
	private HashMap<Integer, Double> eyeChance = new HashMap<>();
	private HashMap<Integer, Double> treadsChance = new HashMap<>();



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

	private void populate () {
		if (client.getGameState() == GameState.LOGGED_IN && client.getLocalPlayer().getName() != null) {
			rsn = client.getLocalPlayer().getName();
			fileRW = new RLReadWrite(dataFolder, rsn);
			RLReadWrite.Data playerData = fileRW.read();

			floorCompletions = playerData.floors;
		}
	}

	private void save() {
		if (fileRW != null && rsn != null) {
			try {
				RLReadWrite.Data playerData = new RLReadWrite.Data(floorCompletions);
				fileRW.write(playerData);
				log.info("Player data saved for {}", rsn);
			} catch (Exception e) {
				log.error("Failed to save player data for {}", rsn, e);
			}
		}
		else{
			log.error("Failed to save: fileRW or rsn is null");
		}
	}

	public int getTotalFloors(){
		int floors = 0;
		for (int i = 0; i < floorCompletions.length; i++) {
			floors += floorCompletions[i];
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


	public void addFloorCompletions(int level){
		floorCompletions[level-1] += 1;

		// add config options in the future to allow toggle
		floorsSinceCloth +=1;
		floorsSinceEye += 1;
		floorsSinceTreads += 1;
		floorsSinceUnique += 1;


		// make it toggleable
		clothRolls += clothChance.get(level);
		uniqueRolls += clothChance.get(level);

		eyeRolls += eyeChance.get(level);
		uniqueRolls += eyeChance.get(level);

		treadsRolls += treadsChance.get(level);
		uniqueRolls += treadsChance.get(level);

	}

	public int [] getFloorCompletions() {
		return floorCompletions;
	}

	public int getFloorsSinceCloth() {
		return floorsSinceCloth;
	}

	public int getFloorsSinceEye() {
		return floorsSinceEye;
	}

	public int getFloorsSinceTreads() {
		return floorsSinceTreads;
	}

	public int getFloorsSinceUnique() {
		return floorsSinceUnique;
	}

	public double getUniqueRolls() {
		return uniqueRolls;
	}

	public double getEyeRolls() {
		return eyeRolls;
	}

	public double getTreadsRolls() {
		return treadsRolls;
	}


	public double getClothRolls() {
		return clothRolls;
	}

	public double getPetRolls(){
		return petRolls;
	}


	@Provides
	DelveTrackerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(DelveTrackerConfig.class);
	}

	@Provides
	DelveTrackerOverlay provideOverlay(DelveTrackerPlugin plugin){
		return new DelveTrackerOverlay(plugin, client, config);
	}



}
