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

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.apache.log4j.*;
import org.springframework.context.support.*;

import com.burritopos.business.InventoryManager;
import com.burritopos.domain.Inventory;
import com.burritopos.exception.ServiceLoadException;

/**
 * @author james.bloom
 *
 */
public class InventoryUI extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7548567081410890074L;
    private static Logger dLog = Logger.getLogger(InventoryUI.class);
	private JLabel neatoLbl = new JLabel("Neato Burrito Current Inventory");
	private JLabel typeLbl = new JLabel("Meat");
	private JLabel tortLbl = new JLabel("Tortilla");
	private JLabel riceLbl = new JLabel("Rice");
	private JLabel beansLbl = new JLabel("Beans");
	private JLabel salsaLbl = new JLabel("Salsa");
	private JLabel guacLbl = new JLabel("Guacamole");
	private JLabel extrasLbl = new JLabel("Extras");
	private JLabel beefLbl = new JLabel("Beef");
	private JLabel chickenLbl = new JLabel("Chicken");
	private JLabel hummusLbl = new JLabel("Hummus");
	private JLabel flourTortLbl = new JLabel("Flour");
	private JLabel wheatTortLbl = new JLabel("Wheat");
	private JLabel chiliTortLbl = new JLabel("Chili");
	private JLabel herbGarlicTortLbl = new JLabel("Herb Garlic");
	private JLabel jalapenoTortLbl = new JLabel("Jalapeno Cheddar");
	private JLabel tomatoTortLbl = new JLabel("Tomato Basil");
	private JLabel whiteRiceLbl = new JLabel("White Rice");
	private JLabel brownRiceLbl = new JLabel("Brown Rice");
	private JLabel blackBeansLbl = new JLabel("Black Beans");
	private JLabel pintoBeansLbl = new JLabel("Pinto Beans");
	private JLabel salsaPicoLbl = new JLabel("Pico de Gallo");
	private JLabel salsaVerdeLbl = new JLabel("Salsa Verde");
	private JLabel salsaSpecialLbl = new JLabel("Salsa of the Day");
	private JLabel lettuceLbl = new JLabel("Lettuce");
	private JLabel cucumbersLbl = new JLabel("Cucumbers");
	private JLabel jalapenoLbl = new JLabel("Jalapenos");
	private JLabel onionLbl = new JLabel("Onions");
	private JLabel tomatoLbl = new JLabel("Tomatoes");
	private JTextField beefTxt = new JTextField("Beef");
	private JTextField chickenTxt = new JTextField("Chicken");
	private JTextField hummusTxt = new JTextField("Hummus");
	private JTextField flourTortTxt = new JTextField("Flour");
	private JTextField wheatTortTxt = new JTextField("Wheat");
	private JTextField chiliTortTxt = new JTextField("Chili");
	private JTextField herbGarlicTortTxt = new JTextField("Herb Garlic");
	private JTextField jalapenoTortTxt = new JTextField("Jalapeno Cheddar");
	private JTextField tomatoTortTxt = new JTextField("Tomato Basil");
	private JTextField whiteRiceTxt = new JTextField("White Rice");
	private JTextField brownRiceTxt = new JTextField("Brown Rice");
	private JTextField blackBeansTxt = new JTextField("Black Beans");
	private JTextField pintoBeansTxt = new JTextField("Pinto Beans");
	private JTextField salsaPicoTxt = new JTextField("Pico de Gallo");
	private JTextField salsaVerdeTxt = new JTextField("Salsa Verde");
	private JTextField salsaSpecialTxt = new JTextField("Salsa of the Day");
	private JTextField guacTxt = new JTextField("Guacamole");
	private JTextField lettuceTxt = new JTextField("Lettuce");
	private JTextField cucumbersTxt = new JTextField("Cucumbers");
	private JTextField jalapenoTxt = new JTextField("Jalapenos");
	private JTextField onionTxt = new JTextField("Onions");
	private JTextField tomatoTxt = new JTextField("Tomatoes");
	private JButton updateInventoryBtn = new JButton("Update Inventory");
	private JButton exitBtn = new JButton("Exit");
	private Container inventoryContainer;
	
	/**
	 * Business Layer variables
	 */
	private InventoryManager iManager;
	private Inventory curInventory;
    
	// Spring configuration
    private static final String SPRING_CONFIG_DEFAULT = "applicationContext.xml";
	
	// Inventory constructor
	public InventoryUI (String name, Inventory i) throws ServiceLoadException, Exception {
		super(name);
        
        //Spring Framework IoC
        ClassPathXmlApplicationContext beanfactory = null;
        try {
            beanfactory = new ClassPathXmlApplicationContext(SPRING_CONFIG_DEFAULT);
            iManager = (InventoryManager)beanfactory.getBean("InventoryManager");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (beanfactory != null) {
                beanfactory.close();
            }
        }
        
        //iManager = new InventoryManager();
		curInventory = i;
		
		// layout the Login UI look & feel here
		// and register any event handlers
		
		// use an anonymous inner class as an event handler
		// and register it with the buttons
		updateInventoryBtn.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent event) 
					{updateInventoryBtnOnClick();}
			}
		);
		
		exitBtn.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent event) 
					{exitBtnOnClick();}
			}
		);	
		
		setDefaultValues();
		
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

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 1;
		typeLbl.setHorizontalTextPosition(JLabel.CENTER);
		typeLbl.setHorizontalAlignment(JLabel.CENTER);
		typeLbl.setBorder(new LineBorder(Color.blue, 1));
		typeLbl.setOpaque(true);
		typeLbl.setBackground(Color.WHITE);
		container.add(typeLbl, c);
		
		c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		inventoryContainer = new Container();
		inventoryContainer.setLayout(new FlowLayout());
		inventoryContainer.add(beefLbl);
		inventoryContainer.add(beefTxt);
		inventoryContainer.add(chickenLbl);
		inventoryContainer.add(chickenTxt);
		inventoryContainer.add(hummusLbl);
		inventoryContainer.add(hummusTxt);
		container.add(inventoryContainer, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 3;
		tortLbl.setHorizontalTextPosition(JLabel.CENTER);
		tortLbl.setHorizontalAlignment(JLabel.CENTER);
		tortLbl.setBorder(new LineBorder(Color.blue, 1));
		tortLbl.setOpaque(true);
		tortLbl.setBackground(Color.WHITE);
		container.add(tortLbl, c);
		
		c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		inventoryContainer = new Container();
		inventoryContainer.setLayout(new FlowLayout());
		inventoryContainer.add(flourTortLbl);
		inventoryContainer.add(flourTortTxt);
		inventoryContainer.add(wheatTortLbl);
		inventoryContainer.add(wheatTortTxt);
		inventoryContainer.add(herbGarlicTortLbl);
		inventoryContainer.add(herbGarlicTortTxt);
		inventoryContainer.add(chiliTortLbl);
		inventoryContainer.add(chiliTortTxt);
		inventoryContainer.add(jalapenoTortLbl);
		inventoryContainer.add(jalapenoTortTxt);
		inventoryContainer.add(tomatoTortLbl);
		inventoryContainer.add(tomatoTortTxt);
		container.add(inventoryContainer, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 5;
		riceLbl.setHorizontalTextPosition(JLabel.CENTER);
		riceLbl.setHorizontalAlignment(JLabel.CENTER);
		riceLbl.setBorder(new LineBorder(Color.blue, 1));
		riceLbl.setOpaque(true);
		riceLbl.setBackground(Color.WHITE);
		container.add(riceLbl, c);

		c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 6;
		inventoryContainer = new Container();
		inventoryContainer.setLayout(new FlowLayout());
		inventoryContainer.add(whiteRiceLbl);
		inventoryContainer.add(whiteRiceTxt);
		inventoryContainer.add(brownRiceLbl);
		inventoryContainer.add(brownRiceTxt);
		container.add(inventoryContainer, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 7;
		beansLbl.setHorizontalTextPosition(JLabel.CENTER);
		beansLbl.setHorizontalAlignment(JLabel.CENTER);
		beansLbl.setBorder(new LineBorder(Color.blue, 1));
		beansLbl.setOpaque(true);
		beansLbl.setBackground(Color.WHITE);
		container.add(beansLbl, c);

		c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 8;
		inventoryContainer = new Container();
		inventoryContainer.setLayout(new FlowLayout());
		inventoryContainer.add(blackBeansLbl);
		inventoryContainer.add(blackBeansTxt);
		inventoryContainer.add(pintoBeansLbl);
		inventoryContainer.add(pintoBeansTxt);
		container.add(inventoryContainer, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 9;
		salsaLbl.setHorizontalTextPosition(JLabel.CENTER);
		salsaLbl.setHorizontalAlignment(JLabel.CENTER);
		salsaLbl.setBorder(new LineBorder(Color.blue, 1));
		salsaLbl.setOpaque(true);
		salsaLbl.setBackground(Color.WHITE);
		container.add(salsaLbl, c);

		c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 10;
		inventoryContainer = new Container();
		inventoryContainer.setLayout(new FlowLayout());
		inventoryContainer.add(salsaPicoLbl);
		inventoryContainer.add(salsaPicoTxt);
		inventoryContainer.add(salsaVerdeLbl);
		inventoryContainer.add(salsaVerdeTxt);
		inventoryContainer.add(salsaSpecialLbl);
		inventoryContainer.add(salsaSpecialTxt);
		container.add(inventoryContainer, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 11;
		guacLbl.setHorizontalTextPosition(JLabel.CENTER);
		guacLbl.setHorizontalAlignment(JLabel.CENTER);
		guacLbl.setBorder(new LineBorder(Color.blue, 1));
		guacLbl.setOpaque(true);
		guacLbl.setBackground(Color.WHITE);
		container.add(guacLbl, c);

		c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 12;
		inventoryContainer = new Container();
		inventoryContainer.setLayout(new FlowLayout());
		inventoryContainer.add(guacTxt);
		container.add(inventoryContainer, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 13;
		extrasLbl.setHorizontalTextPosition(JLabel.CENTER);
		extrasLbl.setHorizontalAlignment(JLabel.CENTER);
		extrasLbl.setBorder(new LineBorder(Color.blue, 1));
		extrasLbl.setOpaque(true);
		extrasLbl.setBackground(Color.WHITE);
		container.add(extrasLbl, c);

		c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 14;
		inventoryContainer = new Container();
		inventoryContainer.setLayout(new FlowLayout());
		inventoryContainer.add(lettuceLbl);
		inventoryContainer.add(lettuceTxt);
		inventoryContainer.add(cucumbersLbl);
		inventoryContainer.add(cucumbersTxt);
		inventoryContainer.add(jalapenoLbl);
		inventoryContainer.add(jalapenoTxt);
		inventoryContainer.add(onionLbl);
		inventoryContainer.add(onionTxt);
		inventoryContainer.add(tomatoLbl);
		inventoryContainer.add(tomatoTxt);
		container.add(inventoryContainer, c);
		
		// submit/cancel order buttons
		inventoryContainer = new Container();
		inventoryContainer.setLayout(new FlowLayout());
		inventoryContainer.add(updateInventoryBtn);
		inventoryContainer.add(exitBtn);
		//c.insets = new Insets(10,5,5,5);
		c.gridx = 0;
		c.gridy = 15;
		c.anchor = GridBagConstraints.PAGE_END;
		container.add(inventoryContainer, c);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public void setDefaultValues() {
		dLog.trace("Updating Default Values");
		
		try {
			beefTxt.setText(curInventory.getBeefQty().toString());
			chickenTxt.setText(curInventory.getChickenQty().toString());
			hummusTxt.setText(curInventory.getHummusQty().toString());
			flourTortTxt.setText(curInventory.getFlourTortillaQty().toString());
			wheatTortTxt.setText(curInventory.getWheatTortillaQty().toString());
			chiliTortTxt.setText(curInventory.getChiliTortillaQty().toString());
			herbGarlicTortTxt.setText(curInventory.getHerbGarlicTortillaQty().toString());
			jalapenoTortTxt.setText(curInventory.getJalapenoCheddarTortillaQty().toString());
			tomatoTortTxt.setText(curInventory.getTomatoBasilTortillaQty().toString());
			whiteRiceTxt.setText(curInventory.getWhiteRiceQty().toString());
			brownRiceTxt.setText(curInventory.getBrownRiceQty().toString());
			blackBeansTxt.setText(curInventory.getBlackBeansQty().toString());
			pintoBeansTxt.setText(curInventory.getPintoBeansQty().toString());
			salsaPicoTxt.setText(curInventory.getSalsaPicoQty().toString());
			salsaVerdeTxt.setText(curInventory.getSalsaVerdeQty().toString());
			salsaSpecialTxt.setText(curInventory.getSalsaSpecialQty().toString());
			guacTxt.setText(curInventory.getGuacamoleQty().toString());
			lettuceTxt.setText(curInventory.getLettuceQty().toString());
			cucumbersTxt.setText(curInventory.getCucumberQty().toString());
			jalapenoTxt.setText(curInventory.getJalapenosQty().toString());
			onionTxt.setText(curInventory.getOnionQty().toString());
			tomatoTxt.setText(curInventory.getTomatoesQty().toString());
		}
		catch(Exception e) {
			dLog.error("Exception in setDefaultValues: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void updateInventoryBtnOnClick() {
		dLog.trace("Update Inventory button has been clicked");
		
		try {
			//set updated values
			if(Integer.decode(beefTxt.getText()) >= 0) {
				curInventory.setBeefQty(Integer.decode(beefTxt.getText()));
			}
			if(Integer.decode(chickenTxt.getText()) >= 0) {
				curInventory.setChickenQty(Integer.decode(chickenTxt.getText()));
			}
			if(Integer.decode(hummusTxt.getText()) >= 0) {
				curInventory.setHummusQty(Integer.decode(hummusTxt.getText()));
			}
			if(Integer.decode(flourTortTxt.getText()) >= 0) {
				curInventory.setFlourTortillaQty(Integer.decode(flourTortTxt.getText()));
			}
			if(Integer.decode(wheatTortTxt.getText()) >= 0) {
				curInventory.setWheatTortillaQty(Integer.decode(wheatTortTxt.getText()));
			}
			if(Integer.decode(chiliTortTxt.getText()) >= 0) {
				curInventory.setChiliTortillaQty(Integer.decode(chiliTortTxt.getText()));
			}
			if(Integer.decode(herbGarlicTortTxt.getText()) >= 0) {
				curInventory.setHerbGarlicTortillaQty(Integer.decode(herbGarlicTortTxt.getText()));
			}
			if(Integer.decode(jalapenoTortTxt.getText()) >= 0) {
				curInventory.setJalapenoCheddarTortillaQty(Integer.decode(jalapenoTortTxt.getText()));
			}
			if(Integer.decode(tomatoTortTxt.getText()) >= 0) {
				curInventory.setTomatoBasilTortillaQty(Integer.decode(tomatoTortTxt.getText()));
			}
			if(Integer.decode(whiteRiceTxt.getText()) >= 0) {
				curInventory.setWhiteRiceQty(Integer.decode(whiteRiceTxt.getText()));
			}
			if(Integer.decode(brownRiceTxt.getText()) >= 0) {
				curInventory.setBrownRiceQty(Integer.decode(brownRiceTxt.getText()));
			}
			if(Integer.decode(blackBeansTxt.getText()) >= 0) {
				curInventory.setBlackBeansQty(Integer.decode(blackBeansTxt.getText()));
			}
			if(Integer.decode(pintoBeansTxt.getText()) >= 0) {
				curInventory.setPintoBeansQty(Integer.decode(pintoBeansTxt.getText()));
			}
			if(Integer.decode(salsaPicoTxt.getText()) >= 0) {
				curInventory.setSalsaPicoQty(Integer.decode(salsaPicoTxt.getText()));
			}
			if(Integer.decode(salsaVerdeTxt.getText()) >= 0) {
				curInventory.setSalsaVerdeQty(Integer.decode(salsaVerdeTxt.getText()));
			}
			if(Integer.decode(salsaSpecialTxt.getText()) >= 0) {
				curInventory.setSalsaSpecialQty(Integer.decode(salsaSpecialTxt.getText()));
			}
			if(Integer.decode(lettuceTxt.getText()) >= 0) {
				curInventory.setLettuceQty(Integer.decode(lettuceTxt.getText()));
			}
			if(Integer.decode(cucumbersTxt.getText()) >= 0) {
				curInventory.setCucumberQty(Integer.decode(cucumbersTxt.getText()));
			}
			if(Integer.decode(jalapenoTxt.getText()) >= 0) {
				curInventory.setJalapenosQty(Integer.decode(jalapenoTxt.getText()));
			}
			if(Integer.decode(onionTxt.getText()) >= 0) {
				curInventory.setOnionQty(Integer.decode(onionTxt.getText()));
			}
			if(Integer.decode(tomatoTxt.getText()) >= 0) {
				curInventory.setTomatoesQty(Integer.decode(tomatoTxt.getText()));
			}
			
			if(curInventory.validate()) {
				iManager.updateInventory(curInventory);
			}
		}
		catch(Exception e) {
			dLog.error("Exception in updateInventoryBtnOnClick: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void exitBtnOnClick() {
		dLog.trace("Exit button has been clicked");
		
		try {
			if(curInventory.validate()) {
				iManager.updateInventory(curInventory);
			}
		}
		catch(Exception e) {
			dLog.error("Exception in exitBtnOnClick: "+e.getMessage());
		}
		
		dLog.trace("Closing Inventory Form");
		dispose();
	}
}
