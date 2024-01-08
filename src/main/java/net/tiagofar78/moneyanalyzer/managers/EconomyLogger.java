package net.tiagofar78.moneyanalyzer.managers;

import net.tiagofar78.moneyanalyzer.TransactionType;
import net.tiagofar78.moneyanalyzer.objects.EconomyDetails;

public class EconomyLogger {
	
	public static void registerMoneyTransaction(TransactionType type, String source, double amount) {
		
	}
	
	public static EconomyDetails getEconomyLogs(int lastDaysSearch) {
		if (lastDaysSearch <= 0) {
			return null;
		}
		
		return null; // TODO
	}

}
