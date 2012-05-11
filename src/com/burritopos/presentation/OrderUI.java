/**
 * 
 */
package com.burritopos.presentation;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;


import org.apache.log4j.*;
import java.util.Date;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.Random;
import org.springframework.context.*;
import org.springframework.context.support.*;

import com.burritopos.business.InventoryManager;
import com.burritopos.business.OrderManager;
import com.burritopos.domain.Burrito;
import com.burritopos.domain.Inventory;
import com.burritopos.domain.Order;
import com.burritopos.exception.ServiceLoadException;

/**
 * @author james.bloom
 *
 */
//week 7
//week 6
@SuppressWarnings("unused")
//public class OrderUI extends JFrame {
public class OrderUI extends JInternalFrame {
	/**
	 * Presentation Layer variables
	 */
	private static final long serialVersionUID = 8078784057760508094L;
    private static Logger dLog = Logger.getLogger(OrderUI.class);
	private JLabel neatoLbl = new JLabel("Neato Burrito Order System");
	private JLabel priceLbl = new JLabel("Total Price: $0.00");
	private JButton addBurritoBtn = new JButton("Add Burrito");
	private JButton editBurriotBtn = new JButton("Edit Burrito");
	private JButton remBurritoBtn = new JButton("Remove Burrito");
	private JButton submitBtn = new JButton("Submit Order");
	private JButton cancelBtn = new JButton("Cancel Order");
	private JTable burritoList;
	private DefaultTableModel model;
	private Container buttonContainer, buttonContainer2;
	private BurritoDialog bDialog;
	
	/**
	 * Business Layer variables
	 */
	private OrderManager oManager;
	private Order newOrder;
	private InventoryManager iManager;
	private Inventory curInventory;
	
	// Order constructor
	public OrderUI (String name, Inventory i) throws ServiceLoadException, Exception {
		super(name);

		//initialize InventoryManager
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring.cfg.xml"});
        iManager = (InventoryManager)context.getBean("InventoryManager");
		//iManager = new InventoryManager();
		curInventory = i;
		
		//initialize OrderManager
        Random rand = new Random();
        oManager = (OrderManager)context.getBean("OrderManager");
		//oManager = new OrderManager();
		newOrder = new Order(rand.nextInt(),new ArrayList<Burrito>(),new Date(),false,false,new BigDecimal("0.00"));
		bDialog = new BurritoDialog(javax.swing.JOptionPane.getFrameForComponent(this), "New Burrito", true, curInventory);
		
		// layout the Login UI look & feel here
		// and register any event handlers
		
		// use an anonymous inner class as an event handler
		// and register it with the buttons
		editBurriotBtn.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent event) 
					{editBurritoBtnOnClick();}
			}
		);
		
		addBurritoBtn.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent event) 
					{addBurritoBtnOnClick();}
			}
		);		

		remBurritoBtn.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent event) 
					{remBurritoBtnOnClick();}
			}
		);
		
		submitBtn.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent event) 
					{submitBtnOnClick();}
			}
		);

		cancelBtn.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent event) 
					{cancelBtnOnClick();}
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

		Object columnNames[] = { "#", "Type", "Price" };
		Object rowData[][] = { };
        model = new DefaultTableModel(rowData, columnNames);
		burritoList = new JTable(model) { 	      
			/**
			 * Ensure that our JTable burritoList cannot be edited
			 */
			private static final long serialVersionUID = -724278995898299137L;

			public boolean isCellEditable(int rowIndex, int colIndex) {
	        return false;   //Disallow the editing of any cell
	      }};
		// only allow one edit or delete from order at a time
		burritoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		burritoList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {  
					dLog.trace(new Date() + " | Currently selected Row: " + burritoList.getSelectedRow());
				}
			}
			
		});
		
		c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.ipadx = 200;
		c.ipady = 50;
		c.weightx = 0.0;
		c.gridy = 1;
		container.add(new JScrollPane(burritoList), c);
		
		// add/remove burrito buttons
		buttonContainer2 = new Container();
		buttonContainer2.setLayout(new FlowLayout());
		buttonContainer2.add(addBurritoBtn);
		buttonContainer2.add(editBurriotBtn);
		buttonContainer2.add(remBurritoBtn);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 2;
		container.add(buttonContainer2, c);
		
		// total price
		c.gridx = 0;
		c.gridy = 3;
		priceLbl.setHorizontalTextPosition(JLabel.CENTER);
		priceLbl.setHorizontalAlignment(JLabel.CENTER);
		container.add(priceLbl, c);
		
		// submit/cancel order buttons
		buttonContainer = new Container();
		buttonContainer.setLayout(new FlowLayout());
		buttonContainer.add(submitBtn);
		buttonContainer.add(cancelBtn);
		//c.insets = new Insets(10,5,5,5);
		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.PAGE_END;
		container.add(buttonContainer, c);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	// private methods
	private void addBurritoBtnOnClick() {
		dLog.trace(new Date() + " | Add Burrito button has been clicked");
		
		try {
			bDialog.clearState(curInventory);
			bDialog.setVisible(true);
			if(bDialog.getAddResult() == bDialog.ADD) {
				if(oManager.addBurritoToOrder(newOrder, bDialog.getNewBurrito())) {
					model.addRow(new Object[]{model.getRowCount()+1,bDialog.getBurritoType(),bDialog.getNewBurrito().getPrice()});
					
					// remove ingredients from Inventory
					iManager.removeFromInventory(curInventory, bDialog.getNewBurrito());
					iManager.updateInventory(curInventory);
					
					//update cost
					updateTotalCost();
				}
				else
					dLog.trace(new Date() + " | Unable to add burrito");
			}	
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in addBurritoBtnOnClick: "+e.getMessage());
		}
	}

	private void editBurritoBtnOnClick() {
		dLog.trace(new Date() + " | Edit Burrito button has been clicked");
		
		try {
			if(burritoList.getSelectedRow() != -1) {
				bDialog.clearState(curInventory);
				
				//keep track of the burrito we are modifying
				Burrito tBurrito = newOrder.getBurritos().get(burritoList.getSelectedRow());
				
				bDialog.setBurrito(newOrder.getBurritos().get(burritoList.getSelectedRow()));
				bDialog.setVisible(true);
				if(bDialog.getAddResult() == bDialog.ADD) {
					if(oManager.updateBurritoInOrder(newOrder, bDialog.getNewBurrito())) {
						model.setValueAt(bDialog.getBurritoType(), burritoList.getSelectedRow(), 1);
						model.setValueAt(bDialog.getNewBurrito().getPrice(), burritoList.getSelectedRow(), 2);
						
						//update Inventory
						iManager.returnToInventory(curInventory, tBurrito);
						iManager.removeFromInventory(curInventory, bDialog.getNewBurrito());
						iManager.updateInventory(curInventory);
						
						//update cost
						updateTotalCost();
					}
					else
						dLog.trace(new Date() + " | Unable to edit burrito");
				}	
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in editBurritoBtnOnClick: "+e.getMessage());
		}
	}
	
	private void remBurritoBtnOnClick() {
		dLog.trace(new Date() + " | Remove Burrito button has been clicked");
		
		try {
			if(burritoList.getSelectedRow() != -1) {
				Burrito tBurrito = newOrder.getBurritos().get(burritoList.getSelectedRow());
				if(oManager.removeBurritoFromOrder(newOrder, tBurrito)) {
					model.removeRow(burritoList.getSelectedRow());
					
					//update all burrito cell numbers
					for(int n=0; n<model.getRowCount(); n++)
						model.setValueAt(n, n, 1);
					
					//return ingredients back to inventory
					iManager.returnToInventory(curInventory, tBurrito);
					iManager.updateInventory(curInventory);
					
					//update cost
					updateTotalCost();
				}
				else
					dLog.trace(new Date() + " | Unable to remove burrito");
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in remBurritoBtnOnClick: "+e.getMessage());
		}
	}
	
	private void submitBtnOnClick() {
		dLog.trace(new Date() + " | Order submit button has been clicked");
		
		try {
			if(newOrder.getTotalCost().compareTo(new BigDecimal("0")) != 1) {
				JOptionPane.showMessageDialog(OrderUI.this, "Please add a burrito to submit an order.", "Warning", JOptionPane.OK_OPTION);
			}
			else {
				newOrder.setOrderDate(new Date());
				newOrder.setIsSubmitted(true);
				
				if(oManager.updateOrder(newOrder)) { 
					dLog.trace(new Date() + " | Closing Order Creation Form");
					dispose();
				}
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in submitBtnOnClick: "+e.getMessage());
		}
	}
	
	private void cancelBtnOnClick() {
		dLog.trace(new Date() + " | Order cancel button has been clicked");

		if(JOptionPane.showConfirmDialog(OrderUI.this, "Are you sure you want to cancel this order?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
			try {
				//return ingredients back to inventory
				for(int n=0; n<newOrder.getBurritos().size(); n++)
					iManager.returnToInventory(curInventory, newOrder.getBurritos().get(n));
				iManager.updateInventory(curInventory);
				
				// cancel any order that was created up to this point
				if(oManager.cancelOrder(newOrder)) {
					dLog.trace(new Date() + " | Successfully cancelled order #: " + newOrder.getOrderID());
				}
				else {
					dLog.trace(new Date() + " | Failed to cancelled order #: " + newOrder.getOrderID());
				}
			}
			catch(Exception e) {
				dLog.error(new Date() + " | Exception in cancelBtnOnClick: "+e.getMessage());
			}
			
			dLog.trace(new Date() + " | Closing Order Creation Form");
			dispose();
		}
	}
	
	private void updateTotalCost() {
		try {
			newOrder.setTotalCost(oManager.calculateTotal(newOrder));
			priceLbl.setText("Total Price: $" + newOrder.getTotalCost());
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in updateTotalCost: "+e.getMessage());
		}
	}
}

