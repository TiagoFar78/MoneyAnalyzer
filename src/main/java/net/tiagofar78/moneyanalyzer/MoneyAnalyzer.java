package net.tiagofar78.moneyanalyzer;

import java.util.Collection;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class MoneyAnalyzer extends JavaPlugin {
	
	@Override
	public void onEnable() {
		ServicesManager sm = getServer().getServicesManager();
		
		Collection<RegisteredServiceProvider<Economy>> economies = this.getServer().getServicesManager().getRegistrations(Economy.class);
		
		for (RegisteredServiceProvider<Economy> econ : economies) {
			sm.unregister(econ);
			
			Economy changedProvider = econ.getProvider();
			
			sm.register(Economy.class, changedProvider, this, econ.getPriority());
		}
	}
	
	private Economy createMoneyRegisterEconomy(Economy economy) {
		
	}
	
}
