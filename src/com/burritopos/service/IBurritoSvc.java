/**
 * 
 */
package com.burritopos.service;

//import java.util.UUID;

import com.burritopos.domain.Burrito;

/**
 * @author james.bloom
 *
 */
public interface IBurritoSvc extends IService {
	public final String NAME = "IBurritoSvc";
	
	public boolean storeBurrito(Burrito b) throws Exception;
	public Burrito getBurrito(Integer id) throws Exception;
	public boolean deleteBurrito(Integer id) throws Exception;
}
