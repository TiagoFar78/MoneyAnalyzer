package net.tiagofar78.moneyanalyzer.managers;

public class ConfigManager {
	
	private static ConfigManager instance = new ConfigManager();
	
	public static ConfigManager getInstance() {
		return instance;
	}
	
	private String _notAllowedMessage;
	private String _invalidDaysErrorMessage;
	
	private String _usageMessage;
	
	private ConfigManager() {
		
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
