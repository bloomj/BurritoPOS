/**
 * 
 */
package com.burritopos.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
//import java.util.UUID;
//import org.apache.log4j.*;
//import java.util.Date;

/**
 * @author james.bloom
 * This class represents an Order within the system.
 */
public class Order implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7591015289324685131L;
    //private static Logger dLog = Logger.getLogger(Order.class);
	private Integer orderID;
	//changing a generic array out for an ArrayList here to dynamically and easily add/subtract the burritos
	//don't need Vector because Orders will not need to by synchronized (i.e. only one person should be modifying at a time)
	//private Burrito[] burritos;
	private List<Burrito> burritos = new ArrayList<Burrito>();
	private Date orderDate;
	private Boolean isSubmitted;
	private Boolean isComplete;
	private BigDecimal totalCost;
	
	/**
	 * default constructor
	 */
	public Order() {
		//not initializing these variables to correct JUnit Tests
		/*this.orderID = UUID.randomUUID();
		this.burritos = new Burrito[1];
		this.isSubmitted = false;
		this.isComplete = false;
		this.totalCost = new BigDecimal("0.0");*/
	}
	
	/**
	 * @param orderID
	 * @param burritos
	 * @param isSubmitted
	 * @param isComplete
	 * @param totalCost
	 */
	public Order(Integer orderID, ArrayList<Burrito> burritos, Date orderDate, boolean isSubmitted,
			boolean isComplete, BigDecimal totalCost) {
		super();
		this.orderID = orderID;
		this.burritos = burritos;
		this.orderDate = orderDate;
		this.isSubmitted = isSubmitted;
		this.isComplete = isComplete;
		this.totalCost = totalCost;
	}

	/**
	 * validates the object
	 * @return success or failure
	 */
	public boolean validate() {
                System.out.println(new Date() + "[Burrito][Validate] | orderID: " + this.orderID + " | burritos: " + this.burritos.size() + " | isSubmitted: " + this.isSubmitted + " | isComplete: " + this.isComplete + " | totalCost: " + this.totalCost);
		if(this.orderID != null && this.burritos != null && this.orderDate != null && this.isSubmitted != null && this.isComplete != null && this.totalCost != null)
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
		
	    final Order other = (Order) obj;
	    if (this.orderID != other.orderID || this.burritos != other.burritos || this.orderDate != other.orderDate || this.isSubmitted != other.isSubmitted || this.isComplete != other.isComplete || this.totalCost != other.totalCost)
	    	return false;
	    
	    return true;
	}
	
	public String toString() {
		return orderID.toString();
	}
	/**
	 * @return the orderID
	 */
	public Integer getOrderID() {
		return orderID;
	}
	/**
	 * @param orderID the orderID to set
	 */
	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}
	/**
	 * @return the burritos
	 */
	public List<Burrito> getBurritos() {
		return burritos;
	}
	/**
	 * @param burritos the burritos to set
	 */
	public void setBurritos(List<Burrito> burritos) {
		this.burritos = burritos;
	}
	/**
	 * @return the isSubmitted
	 */
	public Boolean getIsSubmitted() {
		return isSubmitted;
	}
	/**
	 * @param isSubmitted the isSubmitted to set
	 */
	public void setIsSubmitted(Boolean isSubmitted) {
		this.isSubmitted = isSubmitted;
	}
	/**
	 * @return the isComplete
	 */
	public Boolean getIsComplete() {
		return isComplete;
	}
	/**
	 * @param isComplete the isComplete to set
	 */
	public void setIsComplete(Boolean isComplete) {
		this.isComplete = isComplete;
	}
	/**
	 * @return the totalCost
	 */
	public BigDecimal getTotalCost() {
		return totalCost;
	}
	/**
	 * @param totalCost the totalCost to set
	 */
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	/**
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
}
