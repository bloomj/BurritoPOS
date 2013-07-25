/**
 * 
 */
package com.burritopos.domain;

import java.io.Serializable;
//import java.util.UUID;
import org.apache.log4j.*;

import com.burritopos.exception.InsufficientInventoryException;

/**
 * @author james.bloom
 * This class should be thread safe so Inventory can be tracked without error.  To that
 * affect, all variables are defined as volatile and all functions are synchronized.
 */
public class Inventory implements Serializable {
	// members are static and refer to quantity of servings still left in inventory (for simplicity)
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3284121516050642084L;
    private static Logger dLog = Logger.getLogger(Inventory.class);
	//id
	private Integer id;
        
	// tortillas
	private volatile Integer FlourTortillaQty;
	private volatile Integer ChiliTortillaQty;
	private volatile Integer JalapenoCheddarTortillaQty;
	private volatile Integer TomatoBasilTortillaQty;
	private volatile Integer HerbGarlicTortillaQty;
	private volatile Integer WheatTortillaQty;
	
	// rice
	private volatile Integer WhiteRiceQty;
	private volatile Integer BrownRiceQty;
	
	// beans
	private volatile Integer BlackBeansQty;
	private volatile Integer PintoBeansQty;
	
	// meat or meat substitute
	private volatile Integer ChickenQty;
	private volatile Integer BeefQty;
	private volatile Integer HummusQty;
	
	// salsas
	private volatile Integer SalsaPicoQty;
	private volatile Integer SalsaVerdeQty;
	private volatile Integer SalsaSpecialQty;
	
	// guacamole
	private volatile Integer GuacamoleQty;
	
	// toppings
	private volatile Integer LettuceQty;
	private volatile Integer JalapenosQty;
	private volatile Integer TomatoesQty;
	private volatile Integer CucumberQty;
	private volatile Integer OnionQty;
	
	//constructors
	/**
	 * default constructor
	 */
	public Inventory() {
		//not initializing these variables to correct JUnit Tests
		/*setFlourTortillaQty(0);
		setChiliTortillaQty(0);
		setJalapenoCheddarTortillaQty(0);
		setTomatoBasilTortillaQty(0);
		setHerbGarlicTortillaQty(0);
		setWheatTortillaQty(0);
		setWhiteRiceQty(0);
		setBrownRiceQty(0);
		setBlackBeansQty(0);
		setPintoBeansQty(0);
		setChickenQty(0);
		setBeefQty(0);
		setHummusQty(0);
		setSalsaPicoQty(0);
		setSalsaVerdeQty(0);
		setSalsaSpecialQty(0);
		setGuacamoleQty(0);
		setLettuceQty(0);
		setJalapenosQty(0);
		setTomatoesQty(0);
		setCucumberQty(0);
		setOnionQty(0);	*/	
	}
	
	/**
	 * 
	 * @param FlourTortillaQty
	 * @param ChiliTortillaQty
	 * @param JalapenoCheddarTortillaQty
	 * @param TomatoBasilTortillaQty
	 * @param HerbGarlicTortillaQty
	 * @param WheatTortillaQty
	 * @param WhiteRiceQty
	 * @param BrownRiceQty
	 * @param BlackBeansQty
	 * @param PintoBeansQty
	 * @param ChickenQty
	 * @param BeefQty
	 * @param HummusQty
	 * @param SalsaPicoQty
	 * @param SalsaVerdeQty
	 * @param SalsaSpecialQty
	 * @param GuacamoleQty
	 * @param LettuceQty
	 * @param JalapenosQty
	 * @param TomatoesQty
	 * @param CucumberQty
	 * @param OnionQty
	 */
	public Inventory(Integer id,Integer FlourTortillaQty,Integer ChiliTortillaQty,Integer JalapenoCheddarTortillaQty,
			Integer TomatoBasilTortillaQty,Integer HerbGarlicTortillaQty,Integer WheatTortillaQty,Integer WhiteRiceQty,
			Integer BrownRiceQty,Integer BlackBeansQty,Integer PintoBeansQty,Integer ChickenQty,Integer BeefQty,Integer HummusQty,
			Integer SalsaPicoQty,Integer SalsaVerdeQty,Integer SalsaSpecialQty,Integer GuacamoleQty,Integer LettuceQty,Integer JalapenosQty,
			Integer TomatoesQty,Integer CucumberQty,Integer OnionQty) {
		setId(id);
		try {
			// ensure we have good values
			if(FlourTortillaQty < 0) {FlourTortillaQty=0;}
			if(ChiliTortillaQty < 0) {ChiliTortillaQty=0;}
			if(JalapenoCheddarTortillaQty < 0) {JalapenoCheddarTortillaQty=0;}
			if(TomatoBasilTortillaQty < 0) {TomatoBasilTortillaQty=0;}
			if(HerbGarlicTortillaQty < 0) {HerbGarlicTortillaQty=0;}
			if(WheatTortillaQty < 0) {WheatTortillaQty=0;}
			if(WhiteRiceQty < 0) {WhiteRiceQty=0;}
			if(BrownRiceQty < 0) {BrownRiceQty=0;}
			if(BlackBeansQty < 0) {BlackBeansQty=0;}
			if(PintoBeansQty < 0) {PintoBeansQty=0;}
			if(ChickenQty < 0) {ChickenQty=0;}
			if(BeefQty < 0) {BeefQty=0;}
			if(HummusQty < 0) {HummusQty=0;}
			if(SalsaPicoQty < 0) {SalsaPicoQty=0;}
			if(SalsaVerdeQty < 0) {SalsaVerdeQty=0;}
			if(SalsaSpecialQty < 0) {SalsaSpecialQty=0;}
			if(GuacamoleQty < 0) {GuacamoleQty=0;}
			if(LettuceQty < 0) {LettuceQty=0;}
			if(JalapenosQty < 0) {JalapenosQty=0;}
			if(TomatoesQty < 0) {TomatoesQty=0;}
			if(CucumberQty < 0) {CucumberQty=0;}
			if(OnionQty < 0) {OnionQty=0;}
			
			// initialize Inventory
			setFlourTortillaQty(FlourTortillaQty);
			setChiliTortillaQty(ChiliTortillaQty);
			setJalapenoCheddarTortillaQty(JalapenoCheddarTortillaQty);
			setTomatoBasilTortillaQty(TomatoBasilTortillaQty);
			setHerbGarlicTortillaQty(HerbGarlicTortillaQty);
			setWheatTortillaQty(WheatTortillaQty);
			setWhiteRiceQty(WhiteRiceQty);
			setBrownRiceQty(BrownRiceQty);
			setBlackBeansQty(BlackBeansQty);
			setPintoBeansQty(PintoBeansQty);
			setChickenQty(ChickenQty);
			setBeefQty(BeefQty);
			setHummusQty(HummusQty);
			setSalsaPicoQty(SalsaPicoQty);
			setSalsaVerdeQty(SalsaVerdeQty);
			setSalsaSpecialQty(SalsaSpecialQty);
			setGuacamoleQty(GuacamoleQty);
			setLettuceQty(LettuceQty);
			setJalapenosQty(JalapenosQty);
			setTomatoesQty(TomatoesQty);
			setCucumberQty(CucumberQty);
			setOnionQty(OnionQty);	
		}
		catch(InsufficientInventoryException e) {
			dLog.error("Exception in Inventory", e);
		}
	}

	/**
	 * validates the object
	 * @return success or failure
	 */
	public boolean validate() {
		if(this.FlourTortillaQty != null && this.ChiliTortillaQty != null && this.JalapenoCheddarTortillaQty != null && this.TomatoBasilTortillaQty != null && 
				this.HerbGarlicTortillaQty != null && this.WheatTortillaQty != null && this.WhiteRiceQty != null && this.BrownRiceQty != null && 
				this.BlackBeansQty != null && this.PintoBeansQty != null && this.ChickenQty != null && this.BeefQty != null && this.HummusQty != null && this.SalsaPicoQty != null &&
				this.SalsaVerdeQty != null&& this.SalsaSpecialQty != null && this.GuacamoleQty != null && this.LettuceQty != null && this.JalapenosQty != null && this.TomatoesQty != null &&
				this.CucumberQty != null && this.OnionQty != null)
		{
			if(getFlourTortillaQty() >= 0 && getChiliTortillaQty() >= 0 && getJalapenoCheddarTortillaQty() >= 0 && getTomatoBasilTortillaQty() >= 0 && 
					getHerbGarlicTortillaQty() >= 0 && getWheatTortillaQty() >= 0 && getWhiteRiceQty() >= 0 && getBrownRiceQty() >= 0 && 
					getBlackBeansQty() >= 0 && getPintoBeansQty() >= 0 && getChickenQty() >= 0 && getBeefQty() >= 0 && getHummusQty() >= 0 && getSalsaPicoQty() >= 0 &&
					getSalsaVerdeQty() >= 0 && getSalsaSpecialQty() >= 0 && getGuacamoleQty() >= 0 && getLettuceQty() >= 0 && getJalapenosQty() >= 0 && getTomatoesQty() >= 0 &&
					getCucumberQty() >= 0 && getOnionQty() >= 0)
				return true;
			else
				return false;
		}
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
		
	    final Inventory other = (Inventory) obj;
	    if (getFlourTortillaQty() != other.getFlourTortillaQty() || getChiliTortillaQty() != other.getChiliTortillaQty() || getJalapenoCheddarTortillaQty() != other.getJalapenoCheddarTortillaQty() && getTomatoBasilTortillaQty() != other.getTomatoBasilTortillaQty() && 
				getHerbGarlicTortillaQty() != other.getHerbGarlicTortillaQty() && getWheatTortillaQty() != other.getWheatTortillaQty() && getWhiteRiceQty() != other.getWhiteRiceQty() && getBrownRiceQty() != other.getBrownRiceQty() && 
				getBlackBeansQty() != other.getBlackBeansQty() && getPintoBeansQty() != other.getPintoBeansQty() && getChickenQty() != other.getChickenQty() && getBeefQty() != other.getBeefQty() && getHummusQty() != other.getHummusQty() && getSalsaPicoQty() != other.getSalsaPicoQty() &&
				getSalsaVerdeQty() != other.getSalsaVerdeQty() && getSalsaSpecialQty() != other.getSalsaSpecialQty() && getGuacamoleQty() != other.getGuacamoleQty() && getLettuceQty() != other.getLettuceQty() && getJalapenosQty() != other.getJalapenosQty() && 
				getTomatoesQty() != other.getTomatoesQty() && getCucumberQty() != other.getCucumberQty() && getOnionQty() != other.getOnionQty())
	    	return false;
	    
	    return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Inventory [toString()=" + super.toString() + "]";
	}

	/**
	 * @return the id
	 */
	public synchronized Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public synchronized void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the flourTortillaQty
	 */
	public synchronized Integer getFlourTortillaQty() {
		return FlourTortillaQty;
	}

	/**
	 * 
	 * @param flourTortillaQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setFlourTortillaQty(Integer flourTortillaQty) throws InsufficientInventoryException {
		if(flourTortillaQty < 0)
			throw new InsufficientInventoryException("Insufficient flour tortillas in Inventory to complete request");
		
		FlourTortillaQty = flourTortillaQty;
	}

	/**
	 * @return the chiliTortillaQty
	 */
	public synchronized Integer getChiliTortillaQty() {
		return ChiliTortillaQty;
	}

	/**
	 * 
	 * @param chiliTortillaQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setChiliTortillaQty(Integer chiliTortillaQty) throws InsufficientInventoryException {
		if(chiliTortillaQty < 0)
			throw new InsufficientInventoryException("Insufficient chili tortillas in Inventory to complete request");
		
		ChiliTortillaQty = chiliTortillaQty;
	}

	/**
	 * @return the jalapenoCheddarTortillaQty
	 */
	public synchronized Integer getJalapenoCheddarTortillaQty() {
		return JalapenoCheddarTortillaQty;
	}

	/**
	 * 
	 * @param jalapenoCheddarTortillaQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setJalapenoCheddarTortillaQty(
			Integer jalapenoCheddarTortillaQty) throws InsufficientInventoryException {
		if(jalapenoCheddarTortillaQty < 0)
			throw new InsufficientInventoryException("Insufficient jalapeno tortillas in Inventory to complete request");
		
		JalapenoCheddarTortillaQty = jalapenoCheddarTortillaQty;
	}

	/**
	 * @return the tomatoBasilTortillaQty
	 */
	public synchronized Integer getTomatoBasilTortillaQty() {
		return TomatoBasilTortillaQty;
	}

	/**
	 * 
	 * @param tomatoBasilTortillaQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setTomatoBasilTortillaQty(
			Integer tomatoBasilTortillaQty) throws InsufficientInventoryException {
		if(tomatoBasilTortillaQty < 0)
			throw new InsufficientInventoryException("Insufficient tomato basil tortillas in Inventory to complete request");
		
		TomatoBasilTortillaQty = tomatoBasilTortillaQty;
	}

	/**
	 * @return the herbGarlicTortillaQty
	 */
	public synchronized Integer getHerbGarlicTortillaQty() {
		return HerbGarlicTortillaQty;
	}

	/**
	 * 
	 * @param herbGarlicTortillaQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setHerbGarlicTortillaQty(
			Integer herbGarlicTortillaQty) throws InsufficientInventoryException {
		if(herbGarlicTortillaQty < 0)
			throw new InsufficientInventoryException("Insufficient herb garlic tortillas in Inventory to complete request");
		
		HerbGarlicTortillaQty = herbGarlicTortillaQty;
	}

	/**
	 * @return the wheatTortillaQty
	 */
	public synchronized Integer getWheatTortillaQty() {
		return WheatTortillaQty;
	}

	/**
	 * 
	 * @param wheatTortillaQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setWheatTortillaQty(Integer wheatTortillaQty) throws InsufficientInventoryException {
		if(wheatTortillaQty < 0)
			throw new InsufficientInventoryException("Insufficient wheat tortillas in Inventory to complete request");
		
		WheatTortillaQty = wheatTortillaQty;
	}

	/**
	 * @return the whiteRiceQty
	 */
	public synchronized Integer getWhiteRiceQty() {
		return WhiteRiceQty;
	}

	/**
	 * 
	 * @param whiteRiceQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setWhiteRiceQty(Integer whiteRiceQty) throws InsufficientInventoryException {
		if(whiteRiceQty < 0)
			throw new InsufficientInventoryException("Insufficient white rice in Inventory to complete request");
		
		WhiteRiceQty = whiteRiceQty;
	}

	/**
	 * @return the brownRiceQty
	 */
	public synchronized Integer getBrownRiceQty() {
		return BrownRiceQty;
	}

	/**
	 * 
	 * @param brownRiceQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setBrownRiceQty(Integer brownRiceQty) throws InsufficientInventoryException {
		if(brownRiceQty < 0)
			throw new InsufficientInventoryException("Insufficient brown rice in Inventory to complete request");
		
		BrownRiceQty = brownRiceQty;
	}

	/**
	 * @return the blackBeansQty
	 */
	public synchronized Integer getBlackBeansQty() {
		return BlackBeansQty;
	}

	/**
	 * 
	 * @param blackBeansQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setBlackBeansQty(Integer blackBeansQty) throws InsufficientInventoryException {
		if(blackBeansQty < 0)
			throw new InsufficientInventoryException("Insufficient black beans in Inventory to complete request");
		
		BlackBeansQty = blackBeansQty;
	}

	/**
	 * @return the pintoBeansQty
	 */
	public synchronized Integer getPintoBeansQty() {
		return PintoBeansQty;
	}

	/**
	 * 
	 * @param pintoBeansQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setPintoBeansQty(Integer pintoBeansQty) throws InsufficientInventoryException {
		if(pintoBeansQty < 0)
			throw new InsufficientInventoryException("Insufficient pinto beans in Inventory to complete request");
		
		PintoBeansQty = pintoBeansQty;
	}

	/**
	 * @return the chickenQty
	 */
	public synchronized Integer getChickenQty() {
		return ChickenQty;
	}

	/**
	 * 
	 * @param chickenQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setChickenQty(Integer chickenQty) throws InsufficientInventoryException {
		if(chickenQty < 0)
			throw new InsufficientInventoryException("Insufficient chicken in Inventory to complete request");
		
		ChickenQty = chickenQty;
	}

	/**
	 * @return the beefQty
	 */
	public synchronized Integer getBeefQty() {
		return BeefQty;
	}

	/**
	 * 
	 * @param beefQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setBeefQty(Integer beefQty) throws InsufficientInventoryException {
		if(beefQty < 0)
			throw new InsufficientInventoryException("Insufficient beef in Inventory to complete request");
		
		BeefQty = beefQty;
	}

	/**
	 * @return the hummusQty
	 */
	public synchronized Integer getHummusQty() {
		return HummusQty;
	}

	/**
	 * 
	 * @param hummusQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setHummusQty(Integer hummusQty) throws InsufficientInventoryException {
		if(hummusQty < 0)
			throw new InsufficientInventoryException("Insufficient hummus in Inventory to complete request");
		
		HummusQty = hummusQty;
	}

	/**
	 * @return the salsaPicoQty
	 */
	public synchronized Integer getSalsaPicoQty() {
		return SalsaPicoQty;
	}

	/**
	 * 
	 * @param salsaPicoQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setSalsaPicoQty(Integer salsaPicoQty) throws InsufficientInventoryException {
		if(salsaPicoQty < 0)
			throw new InsufficientInventoryException("Insufficient salsa pico in Inventory to complete request");
		
		SalsaPicoQty = salsaPicoQty;
	}

	/**
	 * @return the salsaVerdeQty
	 */
	public synchronized Integer getSalsaVerdeQty() {
		return SalsaVerdeQty;
	}

	/**
	 * 
	 * @param salsaVerdeQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setSalsaVerdeQty(Integer salsaVerdeQty) throws InsufficientInventoryException {
		if(salsaVerdeQty < 0)
			throw new InsufficientInventoryException("Insufficient salsa verde in Inventory to complete request");
		
		SalsaVerdeQty = salsaVerdeQty;
	}

	/**
	 * @return the salsaSpecialQty
	 */
	public synchronized Integer getSalsaSpecialQty() {
		return SalsaSpecialQty;
	}

	/**
	 * 
	 * @param salsaSpecialQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setSalsaSpecialQty(Integer salsaSpecialQty) throws InsufficientInventoryException {
		if(salsaSpecialQty < 0)
			throw new InsufficientInventoryException("Insufficient salsa special in Inventory to complete request");
		
		SalsaSpecialQty = salsaSpecialQty;
	}

	/**
	 * @return the guacamoleQty
	 */
	public synchronized Integer getGuacamoleQty() {
		return GuacamoleQty;
	}

	/**
	 * 
	 * @param guacamoleQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setGuacamoleQty(Integer guacamoleQty) throws InsufficientInventoryException {
		if(guacamoleQty < 0)
			throw new InsufficientInventoryException("Insufficient guacamole in Inventory to complete request");
		
		GuacamoleQty = guacamoleQty;
	}

	/**
	 * @return the lettuceQty
	 */
	public synchronized Integer getLettuceQty() {
		return LettuceQty;
	}

	/**
	 * 
	 * @param lettuceQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setLettuceQty(Integer lettuceQty) throws InsufficientInventoryException {
		if(lettuceQty < 0)
			throw new InsufficientInventoryException("Insufficient lettuce in Inventory to complete request");
		
		LettuceQty = lettuceQty;
	}

	/**
	 * @return the jalapenosQty
	 */
	public synchronized Integer getJalapenosQty() {
		return JalapenosQty;
	}

	/**
	 * 
	 * @param jalapenosQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setJalapenosQty(Integer jalapenosQty) throws InsufficientInventoryException {
		if(jalapenosQty < 0)
			throw new InsufficientInventoryException("Insufficient jalapenos in Inventory to complete request");
		
		JalapenosQty = jalapenosQty;
	}

	/**
	 * @return the tomatoesQty
	 */
	public synchronized Integer getTomatoesQty() {
		return TomatoesQty;
	}

	/**
	 * 
	 * @param tomatoesQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setTomatoesQty(Integer tomatoesQty) throws InsufficientInventoryException {
		if(tomatoesQty < 0)
			throw new InsufficientInventoryException("Insufficient tomatoes in Inventory to complete request");
		
		TomatoesQty = tomatoesQty;
	}

	/**
	 * @return the cucumberQty
	 */
	public synchronized Integer getCucumberQty() {
		return CucumberQty;
	}

	/**
	 * 
	 * @param cucumberQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setCucumberQty(Integer cucumberQty) throws InsufficientInventoryException {
		if(cucumberQty < 0)
			throw new InsufficientInventoryException("Insufficient cucumber in Inventory to complete request");
		
		CucumberQty = cucumberQty;
	}

	/**
	 * @return the onionQty
	 */
	public synchronized Integer getOnionQty() {
		return OnionQty;
	}

	/**
	 * 
	 * @param onionQty
	 * @throws InsufficientInventory
	 */
	public synchronized void setOnionQty(Integer onionQty)
	 throws InsufficientInventoryException {
		if(onionQty < 0)
			throw new InsufficientInventoryException("Insufficient onion in Inventory to complete request");
		
		OnionQty = onionQty;
	}
	
}
