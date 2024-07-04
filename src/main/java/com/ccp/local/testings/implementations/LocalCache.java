package com.ccp.local.testings.implementations;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.cache.CcpCache;

class LocalCache implements CcpCache {
	private static CcpJsonRepresentation localCache = CcpConstants.EMPTY_JSON;

	@SuppressWarnings("unchecked")
	private Object get(String key) {
		
		boolean itIsMissingFields = localCache.containsAllFields(key) == false;
	
		if(itIsMissingFields) {
			return null;
		}
		
		Object object = localCache.get(key);

		boolean isNotMap = object instanceof Map == false;

		if (isNotMap) {
			return object;
		}

		Map<String, Object> map = (Map<String, Object>) object;

		CcpJsonRepresentation jr = new CcpJsonRepresentation(map);
		return jr;
	}

	@SuppressWarnings("unchecked")
	public <V> V get(String key, CcpJsonRepresentation json, Function<CcpJsonRepresentation, V> taskToGetValue, int cacheSeconds) {

		Object object = this.get(key);

		if (object != null) {
			return (V) object;
		}
		V value = taskToGetValue.apply(json);
		this.put(key, value, cacheSeconds);

		return value;
	}

	@SuppressWarnings("unchecked")
	
	public <V> V getOrDefault(String key, V defaultValue) {
		Object object = this.get(key);
		
		if(object == null) {
			return defaultValue;
		}
		return (V) object;
		
	}

	@SuppressWarnings("unchecked")
	
	public <V> V getOrThrowException(String key, RuntimeException e) {
		Object object = this.get(key);
		
		if(object == null) {
			throw e;
		}
		
		return (V) object;
	}

	
	public boolean isPresent(String key) {
		boolean isPresent = this.get(key) != null;
		return isPresent;
	}

	
	public void put(String key, Object value, int secondsDelay) {
		if(value instanceof CcpJsonRepresentation) {
			CcpJsonRepresentation jr = (CcpJsonRepresentation)value;
			value = new LinkedHashMap<>(jr.content);
		}
		localCache = localCache.put(key, value);

	}

	@SuppressWarnings("unchecked")
	
	public <V> V delete(String key) {
		
		V t = (V) this.get(key);
		
		localCache = localCache.removeField(key);
		
		return t;
	}

}
