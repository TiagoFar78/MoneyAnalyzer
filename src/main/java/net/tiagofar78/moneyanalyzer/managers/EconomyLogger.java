package net.tiagofar78.moneyanalyzer.managers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

import net.tiagofar78.moneyanalyzer.MoneyAnalyzer;
import net.tiagofar78.moneyanalyzer.TransactionType;
import net.tiagofar78.moneyanalyzer.objects.EconomyDetails;

public class EconomyLogger {
	
	private static final String DATE_FORMAT = "dd-MM-yyyy";
	private static final String GAINED_STRING = "Gained";
	private static final String SPENT_STRING = "Spent";
	private static final String TOTAL_STRING = "Total";
	
	public static void registerMoneyTransaction(TransactionType type, String source, double amount) {
		ConfigManager config = ConfigManager.getInstance();
		
		YamlConfiguration data = MoneyAnalyzer.getDataFile();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		String dateString = formatter.format(LocalDate.now());
		String typeString = type == TransactionType.GAINED ? GAINED_STRING : SPENT_STRING;
		
		if (config.doSourcesCountForTotal() || source == null) {
			writeOnFile(dateString, typeString, TOTAL_STRING, data, amount);
		}
		
		if (source != null) {
			writeOnFile(dateString, typeString, source, data, amount);
		}
		
		MoneyAnalyzer.saveFile(data, true);
	}
	
	private static void writeOnFile(String dateString, String typeString, String source, YamlConfiguration data, double amount) {
		String key = dateString + "." + typeString + "." + source;
		
		double currentAmount = data.getDouble(key);
		data.set(key, currentAmount + amount);
	}
	
	public static EconomyDetails getEconomyLogs(int lastDaysSearch) {
		if (lastDaysSearch < 0) {
			return null;
		}
		
		YamlConfiguration data = MoneyAnalyzer.getDataFile();
		
		EconomyDetails details = new EconomyDetails(lastDaysSearch);
		
		Set<String> keys = data.getKeys(true);
		List<String> keysList = new ArrayList<String>(keys);		
		Collections.reverse(keysList);
		
		if (isOlderDate(lastDaysSearch, keysList.get(0).substring(0, DATE_FORMAT.length()))) {
			return null;
		}
		
		String oldDateString = null;
		for (String key : keysList) {			
			int indexOfDot = key.lastIndexOf(".");
			if (indexOfDot == DATE_FORMAT.length() || indexOfDot == -1) {				
				continue;
			}
			
			String currentDateString = key.substring(0, DATE_FORMAT.length());
			if (oldDateString == null || !oldDateString.equals(currentDateString)) {
				oldDateString = currentDateString;
				
				if (isOlderDate(lastDaysSearch, currentDateString)) {
					return details;
				}
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
		}
		
		return details;
	}
	
	private static boolean isOlderDate(int daysDifference, String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		LocalDate currentDate = LocalDate.parse(dateString, formatter);
		
		LocalDate dateLimit = LocalDate.now().minusDays(daysDifference);
		
		return currentDate.isBefore(dateLimit);
	}

}
