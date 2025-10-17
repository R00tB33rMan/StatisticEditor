package me.datatags.statisticeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.tcoded.folialib.FoliaLib;
import com.tcoded.folialib.impl.PlatformScheduler;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class StatisticEditor extends JavaPlugin {

    private static StatisticEditor instance;
    private static PlatformScheduler scheduler;
	private final File messagesFile = new File(getDataFolder(), "messages.yml");
	private final YamlConfiguration messages = new YamlConfiguration();

	@Override
    public void onEnable() {
		instance = this;
        scheduler = new FoliaLib(this).getScheduler();
		saveDefaultConfig();
		StatisticManager.setVanillaNames(getConfig().getBoolean("use vanilla stat names", false));
		loadMessages();
		scheduler.runNextTick(task -> getCommand("statistic").setExecutor(new StatisticCommand()));
		scheduler.runNextTick(task -> getCommand("runifstat").setExecutor(new RunIfStatCommand()));
		if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
			getLogger().info("PlaceholderAPI found, registering hook");
			new PlaceholderAPIHook(this).register();
		}
	}

	private void loadMessages() {
		if (!messagesFile.exists()) {
            saveResource("messages.yml", false);
        }

		try {
			messages.load(messagesFile);
		} catch (FileNotFoundException e) {
			// shouldn't ever happen, we save the resource immediately before
			getLogger().warning("Couldn't find messages.yml");
			e.printStackTrace();
		} catch (IOException e) {
			getLogger().warning("Failed to load messages file:");
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			getLogger().warning("messages.yml is invalid! Please regenerate it or fix it!");
			e.printStackTrace();
		}
	}

	public String getMessage(String id) {
		return ChatColor.translateAlternateColorCodes('&', messages.getString(id, "&cMissing message: " + id));
	}

	public static StatisticEditor getInstance() {
	    return instance;
	}

    public static PlatformScheduler getScheduler() {
        return scheduler;
    }
}
