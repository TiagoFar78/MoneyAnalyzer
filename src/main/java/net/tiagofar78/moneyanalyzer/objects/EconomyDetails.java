package net.tiagofar78.moneyanalyzer.objects;

import java.util.Hashtable;
import java.util.Set;

public class EconomyDetails {
	
	private int _lastDaysSearch;
	private double _totalGainedMoney;
	private double _totalSpentMoney;
	private Hashtable<String, Double> _specificSourceGainedMoney;
	private Hashtable<String, Double> _specificSourceSpentMoney;
	
	public EconomyDetails(int lastDaysSearch) {
		_lastDaysSearch = lastDaysSearch;
	}
	
	public void addGainedMoney(double amount) {
		_totalGainedMoney = amount;
	}
	
	public void addSpentMoney(double amount) {
		_totalSpentMoney = amount;
	}
	
	public void addGainedMoney(String source, double amount) {
		addToHashtableEntry(_specificSourceGainedMoney, source, amount);
	}
	
	public void addSpentMoney(String source, double amount) {
		addToHashtableEntry(_specificSourceSpentMoney, source, amount);
	}
	
	private void addToHashtableEntry(Hashtable<String, Double> map, String source, double amount) {
		if (map.contains(source)) {
			map.put(source, map.get(source) + amount);
		}
		
		map.put(source, amount);
	}
	
	public double getAverageGainedMoney() {
		return _totalGainedMoney / (double)_lastDaysSearch;
	}
	
	public double getAverageSpentMoney() {
		return _totalSpentMoney / (double)_lastDaysSearch;
	}
	
	public Set<String> getGainedMoneySources() {
		return _specificSourceGainedMoney.keySet();
	}
	
	public Set<String> getAverageSpentMoneyFromSource() {
		return _specificSourceSpentMoney.keySet();
	}
	
	public double getAverageGainedMoney(String source) {
		return _specificSourceGainedMoney.get(source) / (double)_lastDaysSearch;
	}
	
	public double getAverageSpentMoney(String source) {
		return _specificSourceSpentMoney.get(source) / (double)_lastDaysSearch;
	}

}
