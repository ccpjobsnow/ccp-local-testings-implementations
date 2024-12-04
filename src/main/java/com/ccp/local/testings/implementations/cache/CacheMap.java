package com.ccp.local.testings.implementations.cache;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.decorators.CcpTimeDecorator;
import com.ccp.especifications.cache.CcpCache;

class CacheMap implements CcpCache {
	
	private static CcpJsonRepresentation expirations = CcpConstants.EMPTY_JSON;
	private static CcpJsonRepresentation localCache = CcpConstants.EMPTY_JSON;

	static {

		Thread purger = new Thread(() -> purgeExpiredKeys()); 
		purger.setPriority(Thread.MIN_PRIORITY);
		purger.start();
	}
	
	private static void purgeExpiredKeys() {

		CcpTimeDecorator ctd = new CcpTimeDecorator();
		while(true) {
			try {
				Set<String> expiredTimes = expirations.fieldSet().stream().filter(x -> System.currentTimeMillis() >= Long.valueOf(x)).collect(Collectors.toSet());

				List<String> expiredKeys = new ArrayList<String>();
				
				for (String expiredTime : expiredTimes) {
					List<String> keys = localCache.getAsStringList(expiredTime);
					expiredKeys.addAll(keys);
				}
				
				localCache = localCache.removeFields(expiredKeys);
				
				expirations = expirations.removeFields(expiredTimes);
				
			} catch (Exception e) {
				ctd.sleep(1);
				purgeExpiredKeys();
			}
			ctd.sleep(1000);
		}
		
	}
	@SuppressWarnings("unchecked")
	public Object get(String key) {
		
		boolean itIsMissingFields = localCache.containsAllFields(key) == false;
	
		if(itIsMissingFields) {
			return null;
		}
		
		Object object = localCache.get(key);

		if(object instanceof Map map) {
			CcpJsonRepresentation jr = new CcpJsonRepresentation(map);
			return jr;
		}
		return object;
	}


	public void put(String key, Object value, int secondsDelay) {
		
		if(value instanceof CcpJsonRepresentation json) {
			value = new LinkedHashMap<>(json.content);
		}
		localCache = localCache.put(key, value);
		long expiration = System.currentTimeMillis() + (secondsDelay * 1000);
		expirations = expirations.addToList("" + expiration, key);
	}

	@SuppressWarnings("unchecked")
	public <V> V delete(String key) {
		
		V t = (V) this.get(key);
		
		localCache = localCache.removeField(key);
		
		return t;
	}

}
