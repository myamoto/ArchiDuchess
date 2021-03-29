package org.toolup.archi.business.archimate.technology;

import org.toolup.archi.business.archimate.AbstractArchimateElement;

public abstract class AbstractElementTechnology extends AbstractArchimateElement {
	public AbstractElementTechnology() {
		super();
	}
	
	public String getDefaultFontColor() {
		return "black";
	}
	
	public String getDefaultFillColor() {
		return "#c9e7b7";
	}
}
