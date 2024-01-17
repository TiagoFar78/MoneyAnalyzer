package net.tiagofar78.moneyanalyzer.managers;

import org.bukkit.configuration.file.YamlConfiguration;

import net.tiagofar78.moneyanalyzer.MoneyAnalyzer;

public class ConfigManager {
	
	private static ConfigManager instance = new ConfigManager();
	
	public static ConfigManager getInstance() {
		return instance;
	}
	
	private boolean _doSourcesCountForTotal;
	
	private String _notAllowedMessage;
	private String _invalidDaysErrorMessage;
	
	private String _usageMessage;
	
	private ConfigManager() {
		YamlConfiguration config = MoneyAnalyzer.getYamlConfiguration();
		
		_doSourcesCountForTotal = config.getBoolean("SourcesCountForTotal");
		
		_notAllowedMessage = config.getString("Messages.Errors.NotAllowed").replace("&", "§");
		_invalidDaysErrorMessage = config.getString("Messages.Errors.NotAllowed").replace("&", "§");
		
		_usageMessage = config.getString("Messages.Usage.AnalyzeEconomy").replace("&", "§");
	}
	
	public boolean doSourcesCountForTotal() {
		return _doSourcesCountForTotal;
	}
	
	public String getNotAllowedMessage() {
		return _notAllowedMessage;
	}
	
	public String getInvalidDaysErrorMessage() {
		return _invalidDaysErrorMessage;
	}
	
	public String getUsageMessage() {
		return _usageMessage;
	}

}
