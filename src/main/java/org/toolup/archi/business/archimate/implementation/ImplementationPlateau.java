package org.toolup.archi.business.archimate.implementation;


public class ImplementationPlateau extends AbstractElementImplementation{
	
	public static final String XSITYPE = "archimate:Plateau";

	
	public ImplementationPlateau() {
		super();
	}


	public String getXSIType() {
		return XSITYPE;
	}

	public String getDefaultFillColor() {
		return "#e0ffe0";
	}
}
