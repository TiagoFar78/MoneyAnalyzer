package net.tiagofar78.moneyanalyzer.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

import net.tiagofar78.moneyanalyzer.MoneyAnalyzer;
import net.tiagofar78.moneyanalyzer.TransactionType;
import net.tiagofar78.moneyanalyzer.objects.EconomyDetails;

public class EconomyLogger {
	
	private static final String DATE_FORMAT = "dd-mm-yyyy";
	private static final String GAINED_STRING = "Gained";
	private static final String SPENT_STRING = "Spent";
	private static final String TOTAL_STRING = "Total";
	
	public static void registerMoneyTransaction(TransactionType type, String source, double amount) {
		
	}
	
	public static EconomyDetails getEconomyLogs(int lastDaysSearch) {
		if (lastDaysSearch <= 0) {
			return null;
		}
		
		YamlConfiguration data = MoneyAnalyzer.getDataFile();
		if (lastDaysSearch > data.getKeys(false).size()) {
			return null;
		}
		
		EconomyDetails details = new EconomyDetails(lastDaysSearch);
		
		Set<String> keys = data.getKeys(true);
		List<String> keysList = new ArrayList<String>(keys);
		Collections.reverse(keysList);
		
		int daysCounted = 0;
		for (int i = 0; i < keys.size(); i++) {
			String key = keysList.get(i);
			
			if (!key.contains(".")) {
				daysCounted++;
				continue;
			}
			
			if (key.lastIndexOf(".") == DATE_FORMAT.length()) {
				continue;
			}
			
			if (key.contains(GAINED_STRING)) {
				if (key.contains(TOTAL_STRING)) {
					details.addGainedMoney(data.getDouble(key));
				}
				else {
					details.addGainedMoney(key.substring(DATE_FORMAT.length() + 1 + GAINED_STRING.length() + 1), data.getDouble(key));
				}
			}
			else if (key.contains(SPENT_STRING)) {
				if (key.contains(TOTAL_STRING)) {
					details.addSpentMoney(data.getDouble(key));
				}
				else {
					details.addSpentMoney(key.substring(DATE_FORMAT.length() + 1 + SPENT_STRING.length() + 1), data.getDouble(key));
				}
			}
			
			if (daysCounted == lastDaysSearch) {
				break;
			}
			
			daysCounted++;
		}
		
		return details;
	}

}
