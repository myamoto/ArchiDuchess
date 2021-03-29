package org.toolup.archi.business.archimate.strategy;

import org.toolup.archi.business.archimate.AbstractArchimateElement;

public abstract class AbstractElementStrategy extends AbstractArchimateElement{
	public String getDefaultFontColor() {
		return null;
	}
	
	public String getDefaultFillColor() {
		return "#f5deaa";
	}
}

