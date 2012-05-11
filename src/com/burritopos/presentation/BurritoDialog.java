/**
 * 
 */
package com.burritopos.presentation;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
//import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

import org.apache.log4j.*;
import java.util.Date;
import java.util.Random;
import org.springframework.context.*;
import org.springframework.context.support.*;

import com.burritopos.business.BurritoManager;
import com.burritopos.domain.Burrito;
import com.burritopos.domain.Inventory;



/**
 * @author james.bloom
 *
 */
public class BurritoDialog extends JDialog {

	/**
	 * 
	 */
	public static int CANCELLED = -1;
	public int ADD = 1;
	private static final long serialVersionUID = 655601216090289917L;
    private static Logger dLog = Logger.getLogger(BurritoDialog.class);
	private JLabel burritoLbl = new JLabel("New Burrito");
	private JLabel typeLbl = new JLabel("Type");
	private JLabel tortLbl = new JLabel("Tortilla");
	private JLabel riceLbl = new JLabel("Rice");
	private JLabel beansLbl = new JLabel("Beans");
	private JLabel salsaLbl = new JLabel("Salsa");
	private JLabel guacLbl = new JLabel("Guacamole");
	private JLabel extrasLbl = new JLabel("Extras");
	private JLabel priceLbl = new JLabel("Total Price: $0.00");
	private JCheckBoxMenuItem beefChk = new JCheckBoxMenuItem("Beef");
	private JCheckBoxMenuItem chickenChk = new JCheckBoxMenuItem("Chicken");
	private JCheckBoxMenuItem hummusChk = new JCheckBoxMenuItem("Hummus");
	private JCheckBoxMenuItem flourTortChk = new JCheckBoxMenuItem("Flour");
	private JCheckBoxMenuItem wheatTortChk = new JCheckBoxMenuItem("Wheat");
	private JCheckBoxMenuItem chiliTortChk = new JCheckBoxMenuItem("Chili");
	private JCheckBoxMenuItem herbGarlicTortChk = new JCheckBoxMenuItem("Herb Garlic");
	private JCheckBoxMenuItem jalapenoTortChk = new JCheckBoxMenuItem("Jalapeno Cheddar");
	private JCheckBoxMenuItem tomatoTortChk = new JCheckBoxMenuItem("Tomato Basil");
	private JCheckBoxMenuItem whiteRiceChk = new JCheckBoxMenuItem("White Rice");
	private JCheckBoxMenuItem brownRiceChk = new JCheckBoxMenuItem("Brown Rice");
	private JCheckBoxMenuItem blackBeansChk = new JCheckBoxMenuItem("Black Beans");
	private JCheckBoxMenuItem pintoBeansChk = new JCheckBoxMenuItem("Pinto Beans");
	private JCheckBoxMenuItem salsaPicoChk = new JCheckBoxMenuItem("Pico de Gallo");
	private JCheckBoxMenuItem salsaVerdeChk = new JCheckBoxMenuItem("Salsa Verde");
	private JCheckBoxMenuItem salsaSpecialChk = new JCheckBoxMenuItem("Salsa of the Day");
	private JCheckBoxMenuItem guacChk = new JCheckBoxMenuItem("Guacamole");
	private JCheckBoxMenuItem lettuceChk = new JCheckBoxMenuItem("Lettuce");
	private JCheckBoxMenuItem cucumbersChk = new JCheckBoxMenuItem("Cucumbers");
	private JCheckBoxMenuItem jalapenoChk = new JCheckBoxMenuItem("Jalapenos");
	private JCheckBoxMenuItem onionChk = new JCheckBoxMenuItem("Onions");
	private JCheckBoxMenuItem tomatoChk = new JCheckBoxMenuItem("Tomatoes");
	private JButton addBurritoBtn = new JButton("Add to Order");
	private JButton remBurritoBtn = new JButton("Cancel");
	private Container typeContainer, buttonContainer;
	private BurritoManager bManager;
	private Burrito newBurrito;
	private int addResult;
	private Inventory curInventory;
    private Random rand;
	
	// Order constructor
	public BurritoDialog (Frame owner, String name, boolean modal, Inventory i) throws Exception {
		super(owner, name, modal);

        rand = new Random();
        
        //Spring Framework IoC
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring.cfg.xml"});
		bManager = (BurritoManager)context.getBean("BurritoManager");
       
		//bManager = new BurritoManager();
		newBurrito = new Burrito(rand.nextInt(),false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,new BigDecimal("0.00"));
		addResult = CANCELLED;
		
		//initialize Inventory
		curInventory = i;
		
		// add our listeners
		beefChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setBeef(beefChk.getState());
							updateBurritoCost();
						}
				}
			);

		chickenChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setChicken(chickenChk.getState());
							updateBurritoCost();
						}
				}
			);
		
		hummusChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setHummus(hummusChk.getState());
							updateBurritoCost();
						}
				}
			);
		
		flourTortChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setFlourTortilla(flourTortChk.getState());
							constrainTort("Flour");updateBurritoCost();
						}
				}
			);

		wheatTortChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setWheatTortilla(wheatTortChk.getState());
							constrainTort("Wheat");updateBurritoCost();
						}
				}
			);

		herbGarlicTortChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setHerbGarlicTortilla(herbGarlicTortChk.getState());
							constrainTort("HerbGarlic");updateBurritoCost();
						}
				}
			);
		
		chiliTortChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setChiliTortilla(chiliTortChk.getState());
							constrainTort("Chili");updateBurritoCost();
						}
				}
			);
		
		jalapenoTortChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setJalapenoCheddarTortilla(jalapenoTortChk.getState());
							constrainTort("Jalapeno");updateBurritoCost();
						}
				}
			);
		
		tomatoTortChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setTomatoBasilTortilla(tomatoTortChk.getState());
							constrainTort("Tomato");updateBurritoCost();
						}
				}
			);
		
		whiteRiceChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setWhiteRice(whiteRiceChk.getState());
							updateBurritoCost();
						}
				}
			);		

		brownRiceChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setBrownRice(brownRiceChk.getState());
							updateBurritoCost();
						}
				}
			);
	
		blackBeansChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setBlackBeans(blackBeansChk.getState());
							updateBurritoCost();
						}
				}
			);
		
		pintoBeansChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setPintoBeans(pintoBeansChk.getState());
							updateBurritoCost();
						}
				}
			);
	
		salsaPicoChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setSalsaPico(salsaPicoChk.getState());
							updateBurritoCost();
						}
				}
			);
		
		salsaVerdeChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setSalsaVerde(salsaVerdeChk.getState());
							updateBurritoCost();
						}
				}
			);
		
		salsaSpecialChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setSalsaSpecial(salsaSpecialChk.getState());
							updateBurritoCost();
						}
				}
			);
	
		guacChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setGuacamole(guacChk.getState());
							updateBurritoCost();
						}
				}
			);

		lettuceChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setLettuce(lettuceChk.getState());
							updateBurritoCost();
						}
				}
			);
		
		cucumbersChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setCucumber(cucumbersChk.getState());
							updateBurritoCost();
						}
				}
			);
		
		jalapenoChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setJalapenos(jalapenoChk.getState());
							updateBurritoCost();
						}
				}
			);
		
		tomatoChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setTomatoes(tomatoChk.getState());
							updateBurritoCost();
						}
				}
			);
		
		onionChk.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							newBurrito.setOnion(onionChk.getState());
							updateBurritoCost();
						}
				}
			);
	
		addBurritoBtn.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{
							if(newBurrito.getPrice().compareTo(new BigDecimal("0")) != 1) {
								JOptionPane.showMessageDialog(BurritoDialog.this, "Please select a meat type to add a burrito.", "Warning", JOptionPane.OK_OPTION);
							}
							else {
								addResult = ADD; 
								setVisible(false);								
							}
						}
				}
			);
		
		remBurritoBtn.addActionListener (
				new ActionListener () {
					public void actionPerformed (ActionEvent event) 
						{clearState(curInventory);setVisible(false);}
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
		burritoLbl.setHorizontalTextPosition(JLabel.CENTER);
		burritoLbl.setHorizontalAlignment(JLabel.CENTER);
		burritoLbl.setBorder(new LineBorder(Color.blue, 3));
		burritoLbl.setOpaque(true);
		burritoLbl.setBackground(Color.WHITE);
		container.add(burritoLbl, c);

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
		typeContainer = new Container();
		typeContainer.setLayout(new FlowLayout());
		typeContainer.add(beefChk);
		typeContainer.add(chickenChk);
		typeContainer.add(hummusChk);
		container.add(typeContainer, c);

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
		typeContainer = new Container();
		typeContainer.setLayout(new FlowLayout());
		typeContainer.add(flourTortChk);
		typeContainer.add(wheatTortChk);
		typeContainer.add(herbGarlicTortChk);
		typeContainer.add(chiliTortChk);
		typeContainer.add(jalapenoTortChk);
		typeContainer.add(tomatoTortChk);
		container.add(typeContainer, c);

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
		typeContainer = new Container();
		typeContainer.setLayout(new FlowLayout());
		typeContainer.add(whiteRiceChk);
		typeContainer.add(brownRiceChk);
		container.add(typeContainer, c);
		
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
		typeContainer = new Container();
		typeContainer.setLayout(new FlowLayout());
		typeContainer.add(blackBeansChk);
		typeContainer.add(pintoBeansChk);
		container.add(typeContainer, c);
		
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
		typeContainer = new Container();
		typeContainer.setLayout(new FlowLayout());
		typeContainer.add(salsaPicoChk);
		typeContainer.add(salsaVerdeChk);
		typeContainer.add(salsaSpecialChk);
		container.add(typeContainer, c);
		
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
		typeContainer = new Container();
		typeContainer.setLayout(new FlowLayout());
		typeContainer.add(guacChk);
		container.add(typeContainer, c);
		
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
		typeContainer = new Container();
		typeContainer.setLayout(new FlowLayout());
		typeContainer.add(lettuceChk);
		typeContainer.add(cucumbersChk);
		typeContainer.add(jalapenoChk);
		typeContainer.add(onionChk);
		typeContainer.add(tomatoChk);
		container.add(typeContainer, c);
		
		c.insets = new Insets(15,15,15,15);
		c.gridx = 0;
		c.gridy = 15;
		priceLbl.setHorizontalTextPosition(JLabel.RIGHT);
		priceLbl.setHorizontalAlignment(JLabel.RIGHT);
		container.add(priceLbl, c);
		
		// add/remove burrito buttons
		buttonContainer = new Container();
		buttonContainer.setLayout(new FlowLayout());
		buttonContainer.add(addBurritoBtn);
		buttonContainer.add(remBurritoBtn);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 16;
		container.add(buttonContainer, c);
		
		//initialize options based on inventory
		initAvailableOptions();
		
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		pack();
		//setVisible(true);
	}
	
	private void initAvailableOptions() {
		try {

			if(curInventory.getBeefQty() == 0)
				beefChk.setEnabled(false);
			if(curInventory.getBlackBeansQty() == 0)
				blackBeansChk.setEnabled(false);
			if(curInventory.getBrownRiceQty() == 0)
				brownRiceChk.setEnabled(false);
			if(curInventory.getChickenQty() == 0)
				chickenChk.setEnabled(false);
			if(curInventory.getChiliTortillaQty() == 0)
				chiliTortChk.setEnabled(false);
			if(curInventory.getCucumberQty() == 0)
				cucumbersChk.setEnabled(false);
			if(curInventory.getFlourTortillaQty() == 0)
				flourTortChk.setEnabled(false);
			if(curInventory.getGuacamoleQty() == 0)
				guacChk.setEnabled(false);
			if(curInventory.getHerbGarlicTortillaQty() == 0)
				herbGarlicTortChk.setEnabled(false);
			if(curInventory.getHummusQty() == 0)
				hummusChk.setEnabled(false);
			if(curInventory.getJalapenoCheddarTortillaQty() == 0)
				jalapenoTortChk.setEnabled(false);
			if(curInventory.getJalapenosQty() == 0)
				jalapenoChk.setEnabled(false);
			if(curInventory.getLettuceQty() == 0)
				lettuceChk.setEnabled(false);
			if(curInventory.getOnionQty() == 0)
				onionChk.setEnabled(false);
			if(curInventory.getPintoBeansQty() == 0)
				pintoBeansChk.setEnabled(false);
			if(curInventory.getSalsaPicoQty() == 0)
				salsaPicoChk.setEnabled(false);
			if(curInventory.getSalsaSpecialQty() == 0)
				salsaSpecialChk.setEnabled(false);
			if(curInventory.getSalsaVerdeQty() == 0)
				salsaVerdeChk.setEnabled(false);
			if(curInventory.getTomatoBasilTortillaQty() == 0)
				tomatoTortChk.setEnabled(false);
			if(curInventory.getTomatoesQty() == 0)
				tomatoChk.setEnabled(false);
			if(curInventory.getWheatTortillaQty() == 0)
				wheatTortChk.setEnabled(false);
			if(curInventory.getWhiteRiceQty() == 0)
				whiteRiceChk.setEnabled(false);
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in initAvailableOptions: "+e.getMessage());
		}
	}
	
	private void constrainTort(String type) {
		try {
			dLog.trace(new Date() + " | constraining tortilla | type " + type);
			
			if(type == "Flour" && flourTortChk.getState()) {
				wheatTortChk.setEnabled(false);
				herbGarlicTortChk.setEnabled(false);
				chiliTortChk.setEnabled(false);
				jalapenoTortChk.setEnabled(false);
				tomatoTortChk.setEnabled(false);				
			}
			else if(type == "Wheat" && wheatTortChk.getState()) {
				flourTortChk.setEnabled(false);
				herbGarlicTortChk.setEnabled(false);
				chiliTortChk.setEnabled(false);
				jalapenoTortChk.setEnabled(false);
				tomatoTortChk.setEnabled(false);				
			}
			else if(type == "HerbGarlic" && herbGarlicTortChk.getState()) {
				flourTortChk.setEnabled(false);
				wheatTortChk.setEnabled(false);
				chiliTortChk.setEnabled(false);
				jalapenoTortChk.setEnabled(false);
				tomatoTortChk.setEnabled(false);				
			}
			else if(type == "Chili" && chiliTortChk.getState()) {
				flourTortChk.setEnabled(false);
				wheatTortChk.setEnabled(false);
				herbGarlicTortChk.setEnabled(false);
				jalapenoTortChk.setEnabled(false);
				tomatoTortChk.setEnabled(false);				
			}
			else if(type == "Jalapeno" && jalapenoTortChk.getState()) {
				flourTortChk.setEnabled(false);
				wheatTortChk.setEnabled(false);
				herbGarlicTortChk.setEnabled(false);
				chiliTortChk.setEnabled(false);
				tomatoTortChk.setEnabled(false);				
			}
			else if(type == "Tomato" && tomatoTortChk.getState()) {
				flourTortChk.setEnabled(false);
				wheatTortChk.setEnabled(false);
				herbGarlicTortChk.setEnabled(false);
				chiliTortChk.setEnabled(false);
				jalapenoTortChk.setEnabled(false);			
			}
			else {
				// nothing's checked, enable all checkboxes
				if(curInventory.getFlourTortillaQty() > 0)
					flourTortChk.setEnabled(true);
				if(curInventory.getWheatTortillaQty() > 0)
					wheatTortChk.setEnabled(true);
				if(curInventory.getHerbGarlicTortillaQty() > 0)
					herbGarlicTortChk.setEnabled(true);
				if(curInventory.getChiliTortillaQty() > 0)
					chiliTortChk.setEnabled(true);
				if(curInventory.getJalapenoCheddarTortillaQty() > 0)
					jalapenoTortChk.setEnabled(true);
				if(curInventory.getTomatoBasilTortillaQty() > 0)
					tomatoTortChk.setEnabled(true);
			}
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in constrainTort: "+e.getMessage());
		}
	}
	
	private void updateBurritoCost() {
		try {
			dLog.trace(new Date() + " | updating burrito cost");
			newBurrito.setPrice(bManager.calculatePrice(newBurrito));
			priceLbl.setText("Total Price: $" + newBurrito.getPrice());
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in updateBurritoCost: "+e.getMessage());
		}
	}
	
	public void setBurrito(Burrito b) {
		try {
			newBurrito = b;
			
			//set burrito Items
			if(newBurrito.isBeef())
				beefChk.setState(true);
			if(newBurrito.isChicken())
				chickenChk.setState(true);
			if(newBurrito.isHummus())
				hummusChk.setState(true);
			
			if(newBurrito.isChiliTortilla()) {
				chiliTortChk.setState(true);
				constrainTort("Chili");
			}
			else if(newBurrito.isHerbGarlicTortilla()) {
				herbGarlicTortChk.setState(true);
				constrainTort("HerbGarlic");
			}
			else if(newBurrito.isJalapenoCheddarTortilla()) {
				jalapenoTortChk.setState(true);
				constrainTort("Jalapeno");
			}
			else if(newBurrito.isTomatoBasilTortilla()) {
				tomatoTortChk.setState(true);
				constrainTort("Tomato");
			}
			else if(newBurrito.isWheatTortilla()) {
				wheatTortChk.setState(true);
				constrainTort("Wheat");
			}
			else if(newBurrito.isFlourTortilla()) {
				flourTortChk.setState(true);
				constrainTort("Flour");
			}
			
			if(newBurrito.isWhiteRice())
				whiteRiceChk.setState(true);
			if(newBurrito.isBrownRice())
				brownRiceChk.setState(true);
			
			if(newBurrito.isBlackBeans())
				blackBeansChk.setState(true);
			if(newBurrito.isPintoBeans())
				pintoBeansChk.setState(true);
			
			if(newBurrito.isSalsaPico())
				salsaPicoChk.setState(true);
			if(newBurrito.isSalsaVerde())
				salsaVerdeChk.setState(true);
			if(newBurrito.isSalsaSpecial())
				salsaSpecialChk.setState(true);
			
			if(newBurrito.isGuacamole())
				guacChk.setState(true);
			
			if(newBurrito.isCucumber())
				cucumbersChk.setState(true);
			if(newBurrito.isJalapenos())
				jalapenoChk.setState(true);
			if(newBurrito.isLettuce())
				lettuceChk.setState(true);
			if(newBurrito.isOnion())
				onionChk.setState(true);
			if(newBurrito.isTomatoes())
				tomatoChk.setState(true);
				
			updateBurritoCost();
		}
		catch(Exception e) {
			dLog.error(new Date() + " | Exception in setBurrito: "+e.getMessage());
		}
	}
	
	public void clearState(Inventory i) {
		curInventory = i;
		addResult = CANCELLED;
		newBurrito = new Burrito(rand.nextInt(),false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,new BigDecimal("0.00"));
		
		// reset UI
		beefChk.setState(false);
		chickenChk.setState(false);
		hummusChk.setState(false);
		flourTortChk.setState(false);
		herbGarlicTortChk.setState(false);
		wheatTortChk.setState(false);
		chiliTortChk.setState(false);
		jalapenoTortChk.setState(false);
		tomatoTortChk.setState(false);
		whiteRiceChk.setState(false);
		brownRiceChk.setState(false);
		blackBeansChk.setState(false);
		pintoBeansChk.setState(false);
		salsaPicoChk.setState(false);
		salsaVerdeChk.setState(false);
		salsaSpecialChk.setState(false);
		guacChk.setState(false);
		lettuceChk.setState(false);
		cucumbersChk.setState(false);
		jalapenoChk.setState(false);
		onionChk.setState(false);
		tomatoChk.setState(false);
		
		flourTortChk.setEnabled(true);
		herbGarlicTortChk.setEnabled(true);
		wheatTortChk.setEnabled(true);
		chiliTortChk.setEnabled(true);
		jalapenoTortChk.setEnabled(true);
		tomatoTortChk.setEnabled(true);	
		
		priceLbl.setText("Total Price: $0.00");
		
		//initialize options based on inventory
		initAvailableOptions();
	}
	
	public Burrito getNewBurrito() {
		return newBurrito;
	}
	
	public int getAddResult() {
		return addResult;
	}
	
	public String getBurritoType() {
		return bManager.getBurritoType(newBurrito);
	}
}
