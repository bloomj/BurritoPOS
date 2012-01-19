/**
 * 
 */
package com.burritopos.presentation;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.*;
import java.util.Date;
import org.springframework.context.*;
import org.springframework.context.support.*;

import com.burritopos.business.OrderManager;
import com.burritopos.domain.Order;
import com.burritopos.exception.ServiceLoadException;


/**
 * @author james.bloom
 *
 */
public class OrderViewUI extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1496373611427777792L;
    private static Logger dLog = Logger.getLogger(OrderViewUI.class);
	private JLabel neatoLbl = new JLabel("Neato Burrito Order History");
	private JLabel priceLbl = new JLabel("Total Sales: $0.00");
	private JButton deleteOrderBtn = new JButton("Delete Order");
	private JButton exitBtn = new JButton("Exit");
	private JTable orderList;
	private DefaultTableModel model;
	private Container orderContainer;
	
	/**
	 * Business Layer variables
	 */
	private OrderManager oManager;
	private ArrayList<Order> tOrders;
	
	// Order View constructor
	public OrderViewUI (String name) throws ServiceLoadException, Exception {
		super(name);

		//initialize OrderManager
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring.cfg.xml"});
		oManager = (OrderManager)context.getBean("OrderManager");
		//oManager = new OrderManager();
		
		// layout the Login UI look & feel here
		// and register any event handlers
		
		// use an anonymous inner class as an event handler
		// and register it with the buttons
		deleteOrderBtn.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent event) 
					{deleteOrderBtnOnClick();}
			}
		);
		
		exitBtn.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent event) 
					{exitBtnOnClick();}
			}
		);	
		
		Container container = getContentPane();
		container.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,10,10,10);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		neatoLbl.setHorizontalTextPosition(JLabel.CENTER);
		neatoLbl.setHorizontalAlignment(JLabel.CENTER);
		neatoLbl.setBorder(new LineBorder(Color.blue, 3));
		neatoLbl.setOpaque(true);
		neatoLbl.setBackground(Color.WHITE);
		container.add(neatoLbl, c);

		Object columnNames[] = { "#", "Date", "# of Burritos", "Submitted", "Complete", "Total Cost" };
		Object rowData[][] = { };
                model = new DefaultTableModel(rowData, columnNames);
		
		// add all Ordering orders
		try {
			dLog.trace(new Date() + " | getting history");
			tOrders = oManager.getOrderHistories();
			for(int n=0; n<tOrders.size(); n++) {
				dLog.trace(new Date() + " | order: " + n);
				model.addRow(new Object[]{model.getRowCount()+1,tOrders.get(n).getOrderDate(),tOrders.get(n).getBurritos().size(),tOrders.get(n).getIsSubmitted(),tOrders.get(n).getIsComplete(),tOrders.get(n).getTotalCost()});
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Error in getting history: " + e.getMessage());
		}
		
		updateTotalSales();
	    orderList = new JTable(model) { 	      
			/**
			 * Ensure that our JTable burritoList cannot be edited
			 */
			private static final long serialVersionUID = -724278995898299137L;

			public boolean isCellEditable(int rowIndex, int colIndex) {
	        return false;   //Disallow the editing of any cell
	      }};
		// only allow one edit or delete from order at a time
	    orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    orderList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {  
					dLog.trace(new Date() + " | Currently selected Row: " + orderList.getSelectedRow());
				}
			}
			
		});
		
		c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.ipadx = 400;
		c.ipady = 50;
		c.weightx = 0.0;
		c.gridy = 1;
		container.add(new JScrollPane(orderList), c);
		
		// add/remove burrito buttons
		orderContainer = new Container();
		orderContainer.setLayout(new FlowLayout());
		orderContainer.add(deleteOrderBtn);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 2;
		container.add(orderContainer, c);
		
		// total price
		c.gridx = 0;
		c.gridy = 3;
		priceLbl.setHorizontalTextPosition(JLabel.CENTER);
		priceLbl.setHorizontalAlignment(JLabel.CENTER);
		container.add(priceLbl, c);
		
		// submit/cancel order buttons
		orderContainer = new Container();
		orderContainer.setLayout(new FlowLayout());
		orderContainer.add(exitBtn);
		//c.insets = new Insets(10,5,5,5);
		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.PAGE_END;
		container.add(orderContainer, c);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public void deleteOrderBtnOnClick() {
		dLog.trace(new Date() + " | Delete Order button has been clicked");
		
		try {
			if(orderList.getSelectedRow() != -1) {
				if(oManager.cancelOrder(tOrders.get(orderList.getSelectedRow()))) {
					model.removeRow(orderList.getSelectedRow());
					updateTotalSales();
				}
				else
					dLog.trace(new Date() + " | Unable to remove burrito");
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in deleteOrderBtnOnClick: "+e.getMessage());
		}
	}
	
	public void exitBtnOnClick() {
		dLog.trace(new Date() + " | Exit button has been clicked");
		
		try {
			
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in exitBtnOnClick: "+e.getMessage());
		}
		
		dLog.trace(new Date() + " | Closing Order History Form");
		dispose();
	}
	
	private void updateTotalSales() {
		dLog.trace(new Date() + " | Updating Total Sales");
		
		try {
			BigDecimal totalSales = new BigDecimal("0");
			
			tOrders = oManager.getOrderHistories();
			for(int n=0; n<tOrders.size(); n++) {
				dLog.trace(new Date() + " | Order: " + n + " | Total Sales: $" + tOrders.get(n).getTotalCost());
				totalSales = totalSales.add(tOrders.get(n).getTotalCost());
			}
			
			dLog.trace(new Date() + " | Total Sales: $" + totalSales);
			priceLbl.setText("Total Sales: $" + totalSales);
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in updateTotalCost: "+e.getMessage());
		}
	}
}
