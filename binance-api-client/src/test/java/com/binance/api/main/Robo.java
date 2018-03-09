package com.binance.api.main;

import java.util.ArrayList;
import java.util.List;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.AssetBalance;
import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.account.request.AllOrdersRequest;
import com.binance.api.client.domain.account.request.OrderRequest;

/**
 * Examples on how to place orders, cancel them, and query account information.
 */
public class Robo {
	
	private static BinanceApiRestClient client;
	private static Account account;
	
	private void getInstance(){
		if (client == null){
			BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();
			client = factory.newRestClient();
			account = client.getAccount(6000000L, System.currentTimeMillis());
		}
	}
	
	
	
	public static void main(String[] args) {
		
		Robo robo = new Robo();
		robo.getInstance();
//		robo.getBalances(null);
//		robo.getOpenOrders("NANOBTC");
		robo.getAllOrders("GTOBTC");

		
		//
		// // Get status of a particular order
		// Order order = client.getOrderStatus(new OrderStatusRequest("LINKETH",
		// 751698L));
		// System.out.println(order);
		//
		// // Canceling an order
		// try {
		// client.cancelOrder(new CancelOrderRequest("LINKETH", 756762l));
		// } catch (BinanceApiException e) {
		// System.out.println(e.getError().getMsg());
		// }
		//
		// // Placing a test LIMIT order
		// client.newOrderTest(limitBuy("LINKETH", TimeInForce.GTC, "1000",
		// "0.0001"));
		//
		// // Placing a test MARKET order
		// client.newOrderTest(marketBuy("LINKETH", "1000"));
		//
		// // Placing a real LIMIT order
		// NewOrderResponse newOrderResponse =
		// client.newOrder(limitBuy("LINKETH", TimeInForce.GTC, "1000",
		// "0.0001"));
		// System.out.println(newOrderResponse);
	}
	
	private void getBalances(String symbol){
		List<AssetBalance> list = new ArrayList<AssetBalance>();
		if (symbol==null){
			list = account.getBalances();
		}else{
			list.add(account.getAssetBalance(symbol));
		}
		for (AssetBalance obj : list){
			System.out.println(obj.toString());
		}
	}
	
	private void getOpenOrders(String symbol){
		List<Order> list = new ArrayList<Order>();
		if (symbol==null){
			List<Order> listAux = new ArrayList<Order>();
			for (AssetBalance balance : account.getBalances()){
				listAux = client.getOpenOrders(new OrderRequest(balance.getAsset()+"BTC"));
			}
			list.addAll(listAux);
		}else{
			list = client.getOpenOrders(new OrderRequest(symbol));
		}
		for (Order obj : list){
			System.out.println(obj.toString());
		}
	}
	
	private void getAllOrders(String symbol){
		List<Order> list = new ArrayList<Order>();
		if (symbol==null){
			List<Order> listAux = new ArrayList<Order>();
			for (AssetBalance balance : account.getBalances()){
				listAux = client.getAllOrders(new AllOrdersRequest(balance.getAsset()+"BTC").limit(10));
			}
			list.addAll(listAux);
		}else{
			list = client.getAllOrders(new AllOrdersRequest(symbol).limit(10));
		}
		for (Order obj : list){
			System.out.println(obj.toString());
		}
	}
	

}
