package net.tiagofar78.moneyanalyzer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gamingmesh.jobs.api.JobsPaymentEvent;
import com.gamingmesh.jobs.container.CurrencyType;

import net.brcdev.shopgui.event.ShopPostTransactionEvent;
import net.brcdev.shopgui.shop.ShopManager.ShopAction;
import net.brcdev.shopgui.shop.ShopTransactionResult;
import net.tiagofar78.moneyanalyzer.managers.EconomyLogger;

public class Events implements Listener {
	
	private static final String SHOP_NAME = "ShopGui";
	private static final String JOBS_NAME = "Jobs";

	@EventHandler
	public void onShopGUITransaction(ShopPostTransactionEvent e){
		ShopTransactionResult result = e.getResult();
		TransactionType type = result.getShopAction() == ShopAction.BUY ? TransactionType.SPENT : TransactionType.GAINED;
	    EconomyLogger.registerMoneyTransaction(type, SHOP_NAME, result.getPrice());
	}
	
	@EventHandler
	public void onJobsPayment(JobsPaymentEvent event) {
		double price = event.get(CurrencyType.MONEY);
		EconomyLogger.registerMoneyTransaction(TransactionType.GAINED, JOBS_NAME, price);
	}
	
}
