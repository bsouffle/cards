package com.utc.cards.model.game;

public class Info
{
    private InfoType type;
    private String json;

    public Info()
    {
	super();
    }

    public Info(InfoType type, String json)
    {
	super();
	this.type = type;
	this.json = json;
    }

    public InfoType getType()
    {
	return type;
    }

    public void setType(InfoType type)
    {
	this.type = type;
    }

    public String getJson()
    {
	return json;
    }

    public void setJson(String string)
    {
	this.json = string;
    }

}
