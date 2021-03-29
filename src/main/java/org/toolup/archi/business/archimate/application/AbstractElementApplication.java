package org.toolup.archi.business.archimate.application;

import org.toolup.archi.business.archimate.AbstractArchimateElement;

public abstract class AbstractElementApplication extends AbstractArchimateElement{
	
	public static final String FILL_COLOR = "#b5ffff";
	public String getDefaultFontColor() {
		return "black";
	}
	
	public String getDefaultFillColor() {
		return FILL_COLOR;
	}
}

