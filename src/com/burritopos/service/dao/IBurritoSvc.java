package com.burritopos.service.dao;

//import java.util.UUID;

import com.burritopos.domain.Burrito;
import com.burritopos.service.IService;

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
