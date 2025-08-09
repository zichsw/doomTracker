package com.example;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;

import javax.inject.Inject;
import java.awt.*;


public class DelveTrackerOverlay extends OverlayPanel {
    private DelveTrackerPlugin plugin;
    private Client client;
    private DelveTrackerConfig config;


    @Inject
    public DelveTrackerOverlay(DelveTrackerPlugin plugin, Client client, DelveTrackerConfig config) {
        this.plugin = plugin;
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
                .left("Floors tracked :")
                .right(String.valueOf(plugin.getTotalFloors()))
                .build());

        panelComponent.getChildren().add(LineComponent.builder()
                .left("Chance of seeing unique : %").
                right(String.valueOf(plugin.getUniqueRolls()))
                .build());


        return super.render(graphics);
    }


}
