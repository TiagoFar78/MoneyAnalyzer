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
		_totalGainedMoney += amount;
	}
	
	public void addSpentMoney(double amount) {
		_totalSpentMoney += amount;
	}
	
	public void addGainedMoney(String source, double amount) {
		if (_specificSourceGainedMoney == null) {
			_specificSourceGainedMoney = new Hashtable<String, Double>();
		}
		
		addToHashtableEntry(_specificSourceGainedMoney, source, amount);
	}
	
	public void addSpentMoney(String source, double amount) {
		if (_specificSourceSpentMoney == null) {
			_specificSourceSpentMoney = new Hashtable<String, Double>();
		}
		
		addToHashtableEntry(_specificSourceSpentMoney, source, amount);
	}
	
	private void addToHashtableEntry(Hashtable<String, Double> map, String source, double amount) {
		if (map.contains(source)) {
			map.put(source, map.get(source) + amount);
		}
		
		map.put(source, amount);
	}
	
	public int getDays() {
		return _lastDaysSearch;
	}
	
	private double getDivisor() {
		return _lastDaysSearch + 1;
	}
	
	public double getAverageGainedMoney() {
		return _totalGainedMoney / (double)getDivisor();
	}
	
	public double getAverageSpentMoney() {
		return _totalSpentMoney / (double)getDivisor();
	}
	
	public Set<String> getGainedMoneySources() {
		return _specificSourceGainedMoney == null ? null : _specificSourceGainedMoney.keySet();
	}
	
	public Set<String> getSpentMoneySource() {
		return _specificSourceSpentMoney == null ? null : _specificSourceSpentMoney.keySet();
	}
	
	public double getAverageGainedMoney(String source) {
		return _specificSourceGainedMoney.get(source) / (double)getDivisor();
	}
	
	public double getAverageSpentMoney(String source) {
		return _specificSourceSpentMoney.get(source) / (double)getDivisor();
	}
	
//	Debug function only
	public void print() {
		System.out.println("Total ganho: " + getAverageGainedMoney());
		if (getGainedMoneySources() != null)
		for (String source : getGainedMoneySources()) {
			System.out.println(source + " " + getAverageGainedMoney(source));
		}
		
		System.out.println();
		
		System.out.println("Total gasto: " + getAverageSpentMoney());
		if (getSpentMoneySource() != null) 
		for (String source : getSpentMoneySource()) {
			System.out.println(source + " " + getAverageSpentMoney(source));
		}
	}

}
