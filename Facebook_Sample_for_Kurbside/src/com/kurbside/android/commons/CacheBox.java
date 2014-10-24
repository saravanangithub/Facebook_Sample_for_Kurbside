package com.kurbside.android.commons;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CacheBox
{
	private static final Map<String,Object> CACHE_BOX = Collections.synchronizedMap(new HashMap<String,Object>());

	public static void put(String key, Object value) 
	{
		if(value == null)
		{
			remove(key);
		}else 
		{
			CACHE_BOX.put(key, value);
		}
	}

	public static Object get(String key) 
	{
		Object obj = CACHE_BOX.get(key);
		return obj;
	}

	public static void remove(String key) 
	{
		CACHE_BOX.remove(key);
	}

	public static boolean contains(String key)
	{
		return CACHE_BOX.containsKey(key);
	}

}