package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Order>{
	
	public static final Logger LOGGER = LogManager.getLogger();
	private Utils utils;
	private OrderDAO orderDAO;
	private ItemDAO itemDAO;
	
	public OrderController(OrderDAO orderDAO, Utils utils, ItemDAO itemDAO) {
		this.utils = utils;
		this.orderDAO = orderDAO;
		this.itemDAO = itemDAO;
	}

	@Override
	public List<Order> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order create() {
		
		
		LOGGER.info("Would you like to add an 'ITEM' to an order or create a new 'ORDER'?");
		String response = utils.getString();
		response = response.toLowerCase();
		switch(response) {
		  case "order":
				
				
				LOGGER.info("Which customer would you like to create an order for?");
				Long custID = utils.getLong();
				LOGGER.info("Please choose an initial item to add to the order");
				Long itemID = utils.getLong();
				
				// checks that the item exists
				if (itemDAO.read(itemID)!= null) {
					Order passin = new Order(custID);
					//creates a new order using the customer ID as a foreign key
					orderDAO.create(passin);
					//gets the ID of the order it just created
					long orderID = orderDAO.readLatest(custID).getOrderID();
					//adds an item to that order
					orderDAO.productAdder(orderID, itemID);
				}
				else {
					LOGGER.info("The item ID does not exist");
				}
				break;
				
		  case "item":
		    
				LOGGER.info("Which order would you like to add the item to?");
				Long order = utils.getLong();
				LOGGER.info("Which item would you like to add?");
				Long prod = utils.getLong();
				orderDAO.productAdder(order, prod);
				
			  
		    break;
		  default:
		    LOGGER.info("Invalid response");
		}
		return null;
		

	}

	@Override
	public Order update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}