package com.ccp.local.testings.implementations.cache;

import com.ccp.especifications.cache.CcpCache;

class CacheMock implements CcpCache {

	public Object get(String key) {
		return null;
	}

	public void put(String key, Object value, int secondsDelay) {

	}

	public <V> V delete(String key) {
		return null;
	}

}
