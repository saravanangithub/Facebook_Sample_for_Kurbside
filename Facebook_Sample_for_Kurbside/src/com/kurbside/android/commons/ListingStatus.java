package com.kurbside.android.commons;

public enum ListingStatus
{
	UNCLAIMED("unclaimed"),
	CLAIMED("claimed");
	
	private String _name;
	
	private ListingStatus(String name) 
	{
		_name = name;
	}
	
	public String getName()
	{
		return _name;
	}
}