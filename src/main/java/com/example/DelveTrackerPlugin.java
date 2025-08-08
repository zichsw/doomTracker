package com.example;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.util.Arrays;
import java.util.Set;

@Slf4j
@PluginDescriptor(
	name = "Example"
)
public class DelveTrackerPlugin extends Plugin
{
	private static final Set<Integer> REGION_IDS = Set.of(5269, 13668, 14180);
	private int levels = 0;

	@Inject
	private Client client;

	@Inject
	private DelveTrackerConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private DelveTrackerOverlay overlay;


	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage){
		if (chatMessage.getType() == ChatMessageType.GAMEMESSAGE && inRegion()){
			String message = chatMessage.getMessage();
			if (message.contains("Delve level") && message.contains("duration")){
				addLevel();
			}
		}
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


	public void addLevel(){
		levels += 1;
	}

	public int getLevels() {
		return levels;
	}

	@Provides
	DelveTrackerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(DelveTrackerConfig.class);
	}

}
