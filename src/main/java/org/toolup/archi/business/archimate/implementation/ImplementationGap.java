package org.toolup.archi.business.archimate.implementation;


public class ImplementationGap extends AbstractElementImplementation{
	
	public static final String XSITYPE = "archimate:Gap";

	
	public ImplementationGap() {
		super();
	}


	public String getXSIType() {
		return XSITYPE;
	}

	public String getDefaultFillColor() {
		return "#e0ffe0";
	}
}
