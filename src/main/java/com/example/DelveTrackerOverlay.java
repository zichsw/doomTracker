package com.example;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;

import javax.inject.Inject;
import java.awt.*;
import java.util.List;


public class DelveTrackerOverlay extends OverlayPanel {
    private DelveTrackerPlugin plugin;
    private Client client;
    private DelveTrackerConfig config;


    @Inject
    public DelveTrackerOverlay(DelveTrackerPlugin plugin, Client client, DelveTrackerConfig config) {
        super(plugin);
        this.client = client;
        this.config = config;
        setPosition(OverlayPosition.TOP_LEFT);
    }

    @Override
    public Dimension render(Graphics2D graphics){
        panelComponent.getChildren().clear();

        if (!plugin.inRegion()){
            return null;  //don't render if not in room
        }

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Levels :")
                .right(String.valueOf(plugin.getLevels()))
                .build());

        return super.render(graphics);
    }
}
