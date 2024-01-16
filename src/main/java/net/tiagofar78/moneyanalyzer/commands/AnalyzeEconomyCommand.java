package net.tiagofar78.moneyanalyzer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.tiagofar78.moneyanalyzer.MoneyAnalyzer;
import net.tiagofar78.moneyanalyzer.managers.ConfigManager;
import net.tiagofar78.moneyanalyzer.managers.EconomyLogger;
import net.tiagofar78.moneyanalyzer.objects.EconomyDetails;

public class AnalyzeEconomyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		ConfigManager config = ConfigManager.getInstance();		
		
		if (!sender.hasPermission(MoneyAnalyzer.ANALYZE_ECONOMY_PERMISSION)) {
			sender.sendMessage(config.getNotAllowedMessage());
			return false;
		}
		
		if (args.length != 1) {
			sender.sendMessage(config.getUsageMessage());
			return false;
		}
		
		int lastDaysSearch = stringToInt(args[0]);
		if (lastDaysSearch == -1) {
			sender.sendMessage(config.getUsageMessage());
			return false;
		}
		
		EconomyDetails economyDetails = EconomyLogger.getEconomyLogs(lastDaysSearch);
		if (economyDetails == null) {
			sender.sendMessage(config.getInvalidDaysErrorMessage());
			return false;
		}
		
		showEconomyDetails(sender, economyDetails);
		
		return false;
	}
	
	private void showEconomyDetails(CommandSender sender, EconomyDetails economyDetails) {
		sender.sendMessage("");
		sender.sendMessage(">-------------------{ Economy Details }-------------------<");
		sender.sendMessage("");
		
		sender.sendMessage("§aTotal gained: " + economyDetails.getAverageGainedMoney());
		if (economyDetails.getGainedMoneySources() != null) {
			for (String source : economyDetails.getGainedMoneySources()) {
				sender.sendMessage(source + ": " + economyDetails.getAverageGainedMoney(source));
			}
		}

		sender.sendMessage("");
		
		sender.sendMessage("§cTotal spent: " + economyDetails.getAverageSpentMoney());
		if (economyDetails.getSpentMoneySource() != null) {
			for (String source : economyDetails.getSpentMoneySource()) {
				sender.sendMessage(source + ": " + economyDetails.getAverageSpentMoney(source));
			}
		}

		sender.sendMessage("");
		sender.sendMessage(">---------------------{ last " + economyDetails.getDays() + " days }---------------------<");
		sender.sendMessage("");
	}

	private int stringToInt(String s) {
		try {
			return Integer.parseInt(s);
		} 
		catch (Exception e) {
			return -1;
		}
	}

}
