package com.utc.cards.model;

public class TraditionnalCard extends Card {

	private Color _color;

	public TraditionnalCard(String name, Color color, int value, int resourceId) {
		super(name, value, resourceId);
		this._color = color;
	}

	public Color getColor() {
		return _color;
	}

	public void setColor(Color color) {
		this._color = color;
	}

	@Override
	public String toString() {
		return _color.toString() + getName();
	}

}
