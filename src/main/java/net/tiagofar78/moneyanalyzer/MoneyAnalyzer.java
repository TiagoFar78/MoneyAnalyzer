package net.tiagofar78.moneyanalyzer;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.tiagofar78.moneyanalyzer.commands.AnalyzeEconomyCommand;

public class MoneyAnalyzer extends JavaPlugin {
	
	public static final String ANALYZE_ECONOMY_PERMISSION = "MoneyAnalyzer.AnalyzeEconomy";
	
	@Override
	public void onEnable() {		
		if (!new File(getDataFolder(), "config.yml").exists()) {
			saveDefaultConfig();
		}
		
		File dataFile = new File(getDataFolder(), "data.yml");
		if (!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		
		getCommand("AnalyzeEconomy").setExecutor(new AnalyzeEconomyCommand());
	}
	
	public static YamlConfiguration getYamlConfiguration() {
		return YamlConfiguration.loadConfiguration(configFile());
	}
	
	public static YamlConfiguration getDataFile() {
		return YamlConfiguration.loadConfiguration(dataFile());
	}
	
	private static File configFile() {
		return new File(getMoneyAnalyzer().getDataFolder(), "config.yml");
	}
	
	private static File dataFile() {
		return new File(getMoneyAnalyzer().getDataFolder(), "data.yml");
	}
	
	public static MoneyAnalyzer getMoneyAnalyzer() {
		return (MoneyAnalyzer)Bukkit.getServer().getPluginManager().getPlugin("TF_MoneyAnalyzer");
	}
	
	public static void saveFile(YamlConfiguration fileInfo, boolean isDataFile) {
		File file = isDataFile ? dataFile() : configFile();
		
		try {
			fileInfo.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
}
