package me.expertmac2.rpg.util;

import java.util.HashMap;

public final class DualKeyMap<K1, K2, VAL> {
	
	private final HashMap<K1, VAL> map1 = new HashMap<K1, VAL>();
	private final HashMap<K2, VAL> map2 = new HashMap<K2, VAL>();

	public void put(K1 key1, K2 key2, VAL value) {
		map1.put(key1, value);
		map2.put(key2, value);
	}

	public VAL get(Object key) {
		VAL k1Result = map1.get(key);
		VAL k2Result = map2.get(key);
		if (k1Result != null) {
			return k1Result;
		} else if (k2Result != null) {
			return k2Result;
		} else {
			return null;
		}
	}

}
