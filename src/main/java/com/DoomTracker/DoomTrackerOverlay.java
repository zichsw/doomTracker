package com.DoomTracker;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;

import javax.inject.Inject;
import java.awt.*;

public class DoomTrackerOverlay extends OverlayPanel
{
    private final DoomTrackerPlugin plugin;
    private final Client client;
    private final DoomTrackerConfig config;

    @Inject
    public DoomTrackerOverlay(DoomTrackerPlugin plugin, Client client, DoomTrackerConfig config)
    {
        super(plugin);
        this.plugin = plugin;
        this.client = client;
        this.config = config;
        setPosition(OverlayPosition.TOP_LEFT);
        setPreferredSize(new Dimension(150, 0));
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        panelComponent.getChildren().clear();

        if (!plugin.inRegion())
        {
            return null; // don't render if not in room
        }

        // === Unique ===
        if (config.showFloorsSinceUnique())
        {
            panelComponent.getChildren().add(LineComponent.builder()
                    .left("Floors without unique:")
                    .right(String.valueOf(plugin.getTotalFloors(plugin.getFloorsSinceUnique())))
                    .build());
        }

        if (config.showChanceOfUnique())
        {
            panelComponent.getChildren().add(LineComponent.builder()
                    .left("Unique chance:")
                    .right(String.format("%.2f%%", plugin.getUniqueRolls() * 100))
                    .build());
        }

        // === Pet ===
        if (config.showFloorsSincePet())
        {
            panelComponent.getChildren().add(LineComponent.builder()
                    .left("Floors without pet:")
                    .right(String.valueOf(plugin.getTotalFloors(plugin.getFloorsSincePet())))
                    .build());
        }

        if (config.showChanceOfPet())
        {
            panelComponent.getChildren().add(LineComponent.builder()
                    .left("Pet chance:")
                    .right(String.format("%.2f%%", plugin.getPetRolls() * 100))
                    .build());
        }

        // === Cloth ===
        if (config.showFloorsSinceCloth())
        {
            panelComponent.getChildren().add(LineComponent.builder()
                    .left("Floors without cloth:")
                    .right(String.valueOf(plugin.getTotalFloors(plugin.getFloorsSinceCloth())))
                    .build());
        }

        if (config.showChanceOfCloth())
        {
            panelComponent.getChildren().add(LineComponent.builder()
                    .left("Cloth chance:")
                    .right(String.format("%.2f%%", plugin.getClothRolls() * 100))
                    .build());
        }

        // === Eye ===
        if (config.showFloorsSinceEye())
        {
            panelComponent.getChildren().add(LineComponent.builder()
                    .left("Floors without eye:")
                    .right(String.valueOf(plugin.getTotalFloors(plugin.getFloorsSinceEye())))
                    .build());
        }

        if (config.showChanceOfEye())
        {
            panelComponent.getChildren().add(LineComponent.builder()
                    .left("Eye chance:")
                    .right(String.format("%.2f%%", plugin.getEyeRolls() * 100))
                    .build());
        }

        // === Treads ===
        if (config.showFloorsSinceTreads())
        {
            panelComponent.getChildren().add(LineComponent.builder()
                    .left("Floors without treads:")
                    .right(String.valueOf(plugin.getTotalFloors(plugin.getFloorsSinceTreads())))
                    .build());
        }

        if (config.showChanceOfTreads())
        {
            panelComponent.getChildren().add(LineComponent.builder()
                    .left("Treads chance:")
                    .right(String.format("%.2f%%", plugin.getTreadsRolls() * 100))
                    .build());
        }

        return super.render(graphics);
    }
}
