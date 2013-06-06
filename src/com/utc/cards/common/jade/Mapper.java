package com.utc.cards.common.jade;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Mapper
{
    private static Mapper instance;

    private ObjectMapper objectMapper;

    private Mapper()
    {
	objectMapper = new ObjectMapper();
    }

    public static ObjectMapper getObjectMapper()
    {
	if (instance == null)
	{
	    instance = new Mapper();
	}
	return instance.objectMapper;
    }

}
