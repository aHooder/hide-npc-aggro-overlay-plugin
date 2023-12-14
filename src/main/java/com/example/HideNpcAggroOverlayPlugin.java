package com.example;

import com.google.inject.Provides;
import javax.inject.Inject;
import javax.swing.SwingUtilities;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.PluginChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.npcunaggroarea.NpcAggroAreaPlugin;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Hide NPC Aggro Overlay"
)
@PluginDependency(NpcAggroAreaPlugin.class)
public class HideNpcAggroOverlayPlugin extends Plugin
{
	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ExampleConfig config;

	@Override
	protected void startUp()
	{
		SwingUtilities.invokeLater(this::hideOverlay);
	}

	@Override
	protected void shutDown()
	{

	}

	private void hideOverlay() {
		overlayManager.removeIf(o -> o.getClass().getSimpleName().equals("NpcAggroAreaNotWorkingOverlay"));
	}

	@Subscribe
	public void onPluginChanged(PluginChanged pluginChanged)
	{
		if (pluginChanged.isLoaded() && pluginChanged.getPlugin() instanceof NpcAggroAreaPlugin)
			SwingUtilities.invokeLater(this::hideOverlay);
	}

	@Provides
	ExampleConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ExampleConfig.class);
	}
}
