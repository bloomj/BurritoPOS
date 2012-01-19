/**
 * 
 */
package com.burritopos.domain;

import java.io.Serializable;
import java.math.BigDecimal;
//import java.util.UUID;
//import org.apache.log4j.*;
//import java.util.Date;

/**
 * @author james.bloom
 * This class represents a single delicious Burrito to be part of an Order.
 */
public class Burrito implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5350639002805741487L;
    //private static Logger dLog = Logger.getLogger(Burrito.class);
	// UUID
	private Integer id;
    private Integer orderID;
	
	// tortillas
	private Boolean FlourTortilla;
	private Boolean ChiliTortilla;
	private Boolean JalapenoCheddarTortilla;
	private Boolean TomatoBasilTortilla;
	private Boolean HerbGarlicTortilla;
	private Boolean WheatTortilla;
	
	// rice
	private Boolean WhiteRice;
	private Boolean BrownRice;
	
	// beans
	private Boolean BlackBeans;
	private Boolean PintoBeans;
	
	// meat or meat substitute
	private Boolean Chicken;
	private Boolean Beef;
	private Boolean Hummus;
	
	// salsas
	private Boolean SalsaPico;
	private Boolean SalsaVerde;
	private Boolean SalsaSpecial;
	
	// guacamole
	private Boolean Guacamole;
	
	// toppings
	private Boolean Lettuce;
	private Boolean Jalapenos;
	private Boolean Tomatoes;
	private Boolean Cucumber;
	private Boolean Onion;
	
	//price
	private BigDecimal Price;
	
	/**
	 * default constructor
	 */
	public Burrito(){
		//not initializing these variables to correct JUnit Tests
		/*this.FlourTortilla = false;
		this.ChiliTortilla = false;
		this.JalapenoCheddarTortilla = false;
		this.TomatoBasilTortilla = false;
		this.HerbGarlicTortilla = false;
		this.WheatTortilla = false;
		this.WhiteRice = false;
		this.BrownRice = false;
		this.BlackBeans = false;
		this.PintoBeans = false;
		this.Chicken = false;
		this.Beef = false;
		this.Hummus = false;
		this.SalsaPico = false;
		this.SalsaVerde = false;
		this.SalsaSpecial = false;
		this.Guacamole = false;
		this.Lettuce = false;
		this.Jalapenos = false;
		this.Tomatoes = false;
		this.Cucumber = false;
		this.Onion = false;
		this.Price = new BigDecimal("0.0");*/
	}
	
	/**
	 * 
	 * @param flourTortilla
	 * @param chiliTortilla
	 * @param jalapenoCheddarTortilla
	 * @param tomatoBasilTortilla
	 * @param herbGarlicTortilla
	 * @param wheatTortilla
	 * @param whiteRice
	 * @param brownRice
	 * @param blackBeans
	 * @param pintoBeans
	 * @param chicken
	 * @param beef
	 * @param hummus
	 * @param salsaPico
	 * @param salsaVerde
	 * @param salsaSpecial
	 * @param guacamole
	 * @param lettuce
	 * @param jalapenos
	 * @param tomatoes
	 * @param cucumber
	 * @param onion
	 * @param price
	 */
	public Burrito(Integer id, Boolean flourTortilla, Boolean chiliTortilla,
			Boolean jalapenoCheddarTortilla, Boolean tomatoBasilTortilla,
			Boolean herbGarlicTortilla, Boolean wheatTortilla,
			Boolean whiteRice, Boolean brownRice, Boolean blackBeans,
			Boolean pintoBeans, Boolean chicken, Boolean beef, Boolean hummus,
			Boolean salsaPico, Boolean salsaVerde, Boolean salsaSpecial,
			Boolean guacamole, Boolean lettuce, Boolean jalapenos,
			Boolean tomatoes, Boolean cucumber, Boolean onion, BigDecimal price) {
		super();
		this.id = id;
		this.FlourTortilla = flourTortilla;
		this.ChiliTortilla = chiliTortilla;
		this.JalapenoCheddarTortilla = jalapenoCheddarTortilla;
		this.TomatoBasilTortilla = tomatoBasilTortilla;
		this.HerbGarlicTortilla = herbGarlicTortilla;
		this.WheatTortilla = wheatTortilla;
		this.WhiteRice = whiteRice;
		this.BrownRice = brownRice;
		this.BlackBeans = blackBeans;
		this.PintoBeans = pintoBeans;
		this.Chicken = chicken;
		this.Beef = beef;
		this.Hummus = hummus;
		this.SalsaPico = salsaPico;
		this.SalsaVerde = salsaVerde;
		this.SalsaSpecial = salsaSpecial;
		this.Guacamole = guacamole;
		this.Lettuce = lettuce;
		this.Jalapenos = jalapenos;
		this.Tomatoes = tomatoes;
		this.Cucumber = cucumber;
		this.Onion = onion;
		this.Price = price;
	}

	/**
	 * validates the object
	 * @return success or failure
	 */
	public boolean validate() {
		if(this.id != null && this.FlourTortilla != null && this.ChiliTortilla != null && this.JalapenoCheddarTortilla != null &&
	    		this.TomatoBasilTortilla != null && this.HerbGarlicTortilla != null && this.WheatTortilla != null &&
	    		this.WhiteRice != null && this.BrownRice != null && this.BlackBeans != null && this.PintoBeans != null && 
	    		this.Chicken != null && this.Beef != null && this.Hummus != null && this.SalsaPico != null && 
	    		this.SalsaVerde != null && this.SalsaSpecial != null && this.Guacamole != null && this.Lettuce != null && 
	    		this.Jalapenos != null && this.Tomatoes != null && this.Cucumber != null && this.Onion != null && this.Price != null)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if the objects are equal
	 * @return success or failure
	 */
	@Override 
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		
		if(obj == null || getClass() != obj.getClass())
			return false;
		
	    final Burrito other = (Burrito) obj;
	    if (this.id != other.id || this.FlourTortilla != other.FlourTortilla || this.ChiliTortilla != other.ChiliTortilla || this.JalapenoCheddarTortilla != other.JalapenoCheddarTortilla ||
	    		this.TomatoBasilTortilla != other.TomatoBasilTortilla || this.HerbGarlicTortilla != other.HerbGarlicTortilla || this.WheatTortilla != other.WheatTortilla ||
	    		this.WhiteRice != other.WhiteRice || this.BrownRice != other.BrownRice || this.BlackBeans != other.BlackBeans || this.PintoBeans != other.PintoBeans || 
	    		this.Chicken != other.Chicken || this.Beef != other.Beef || this.Hummus != other.Hummus || this.SalsaPico != other.SalsaPico || 
	    		this.SalsaVerde != other.SalsaVerde || this.SalsaSpecial != other.SalsaSpecial || this.Guacamole != other.Guacamole || this.Lettuce != other.Lettuce || 
	    		this.Jalapenos != other.Jalapenos || this.Tomatoes != other.Tomatoes || this.Cucumber != other.Cucumber || this.Onion != other.Onion || this.Price != other.Price)
	    	return false;
	    
	    return true;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

        /**
	 * @return the orderID
	 */
	public Integer getOrderId() {
		return orderID;
	}

	/**
	 * @param id the orderID to set
	 */
	public void setOrderId(Integer orderID) {
		this.orderID = orderID;
	}

	/**
	 * @return the flourTortilla
	 */
	public Boolean isFlourTortilla() {
		return FlourTortilla;
	}

	/**
	 * @param flourTortilla the flourTortilla to set
	 */
	public void setFlourTortilla(Boolean flourTortilla) {
		FlourTortilla = flourTortilla;
	}

	/**
	 * @return the chiliTortilla
	 */
	public Boolean isChiliTortilla() {
		return ChiliTortilla;
	}

	/**
	 * @param chiliTortilla the chiliTortilla to set
	 */
	public void setChiliTortilla(Boolean chiliTortilla) {
		ChiliTortilla = chiliTortilla;
	}

	/**
	 * @return the jalapenoCheddarTortilla
	 */
	public Boolean isJalapenoCheddarTortilla() {
		return JalapenoCheddarTortilla;
	}

	/**
	 * @param jalapenoCheddarTortilla the jalapenoCheddarTortilla to set
	 */
	public void setJalapenoCheddarTortilla(Boolean jalapenoCheddarTortilla) {
		JalapenoCheddarTortilla = jalapenoCheddarTortilla;
	}

	/**
	 * @return the tomatoBasilTortilla
	 */
	public Boolean isTomatoBasilTortilla() {
		return TomatoBasilTortilla;
	}

	/**
	 * @param tomatoBasilTortilla the tomatoBasilTortilla to set
	 */
	public void setTomatoBasilTortilla(Boolean tomatoBasilTortilla) {
		TomatoBasilTortilla = tomatoBasilTortilla;
	}

	/**
	 * @return the herbGarlicTortilla
	 */
	public Boolean isHerbGarlicTortilla() {
		return HerbGarlicTortilla;
	}

	/**
	 * @param herbGarlicTortilla the herbGarlicTortilla to set
	 */
	public void setHerbGarlicTortilla(Boolean herbGarlicTortilla) {
		HerbGarlicTortilla = herbGarlicTortilla;
	}

	/**
	 * @return the wheatTortilla
	 */
	public Boolean isWheatTortilla() {
		return WheatTortilla;
	}

	/**
	 * @param wheatTortilla the wheatTortilla to set
	 */
	public void setWheatTortilla(Boolean wheatTortilla) {
		WheatTortilla = wheatTortilla;
	}

	/**
	 * @return the whiteRice
	 */
	public Boolean isWhiteRice() {
		return WhiteRice;
	}

	/**
	 * @param whiteRice the whiteRice to set
	 */
	public void setWhiteRice(Boolean whiteRice) {
		WhiteRice = whiteRice;
	}

	/**
	 * @return the brownRice
	 */
	public Boolean isBrownRice() {
		return BrownRice;
	}

	/**
	 * @param brownRice the brownRice to set
	 */
	public void setBrownRice(Boolean brownRice) {
		BrownRice = brownRice;
	}

	/**
	 * @return the blackBeans
	 */
	public Boolean isBlackBeans() {
		return BlackBeans;
	}

	/**
	 * @param blackBeans the blackBeans to set
	 */
	public void setBlackBeans(Boolean blackBeans) {
		BlackBeans = blackBeans;
	}

	/**
	 * @return the pintoBeans
	 */
	public Boolean isPintoBeans() {
		return PintoBeans;
	}

	/**
	 * @param pintoBeans the pintoBeans to set
	 */
	public void setPintoBeans(Boolean pintoBeans) {
		PintoBeans = pintoBeans;
	}

	/**
	 * @return the chicken
	 */
	public Boolean isChicken() {
		return Chicken;
	}

	/**
	 * @param chicken the chicken to set
	 */
	public void setChicken(Boolean chicken) {
		Chicken = chicken;
	}

	/**
	 * @return the beef
	 */
	public Boolean isBeef() {
		return Beef;
	}

	/**
	 * @param beef the beef to set
	 */
	public void setBeef(Boolean beef) {
		Beef = beef;
	}

	/**
	 * @return the hummus
	 */
	public Boolean isHummus() {
		return Hummus;
	}

	/**
	 * @param hummus the hummus to set
	 */
	public void setHummus(Boolean hummus) {
		Hummus = hummus;
	}

	/**
	 * @return the salsaPico
	 */
	public Boolean isSalsaPico() {
		return SalsaPico;
	}

	/**
	 * @param salsaPico the salsaPico to set
	 */
	public void setSalsaPico(Boolean salsaPico) {
		SalsaPico = salsaPico;
	}

	/**
	 * @return the salsaVerde
	 */
	public Boolean isSalsaVerde() {
		return SalsaVerde;
	}

	/**
	 * @param salsaVerde the salsaVerde to set
	 */
	public void setSalsaVerde(Boolean salsaVerde) {
		SalsaVerde = salsaVerde;
	}

	/**
	 * @return the salsaSpecial
	 */
	public Boolean isSalsaSpecial() {
		return SalsaSpecial;
	}

	/**
	 * @param salsaSpecial the salsaSpecial to set
	 */
	public void setSalsaSpecial(Boolean salsaSpecial) {
		SalsaSpecial = salsaSpecial;
	}

	/**
	 * @return the guacamole
	 */
	public Boolean isGuacamole() {
		return Guacamole;
	}

	/**
	 * @param guacamole the guacamole to set
	 */
	public void setGuacamole(Boolean guacamole) {
		Guacamole = guacamole;
	}

	/**
	 * @return the lettuce
	 */
	public Boolean isLettuce() {
		return Lettuce;
	}

	/**
	 * @param lettuce the lettuce to set
	 */
	public void setLettuce(Boolean lettuce) {
		Lettuce = lettuce;
	}

	/**
	 * @return the jalapenos
	 */
	public Boolean isJalapenos() {
		return Jalapenos;
	}

	/**
	 * @param jalapenos the jalapenos to set
	 */
	public void setJalapenos(Boolean jalapenos) {
		Jalapenos = jalapenos;
	}

	/**
	 * @return the tomatoes
	 */
	public Boolean isTomatoes() {
		return Tomatoes;
	}

	/**
	 * @param tomatoes the tomatoes to set
	 */
	public void setTomatoes(Boolean tomatoes) {
		Tomatoes = tomatoes;
	}

	/**
	 * @return the cucumber
	 */
	public Boolean isCucumber() {
		return Cucumber;
	}

	/**
	 * @param cucumber the cucumber to set
	 */
	public void setCucumber(Boolean cucumber) {
		Cucumber = cucumber;
	}

	/**
	 * @return the onion
	 */
	public Boolean isOnion() {
		return Onion;
	}

	/**
	 * @param onion the onion to set
	 */
	public void setOnion(Boolean onion) {
		Onion = onion;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return Price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		Price = price;
	}
}
