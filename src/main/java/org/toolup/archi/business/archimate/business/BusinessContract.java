package org.toolup.archi.business.archimate.business;


public class BusinessContract extends AbstractElementBusiness{
	
	public static final String XSITYPE = "archimate:Contract";

	public BusinessContract() {
		super();
	}


	public String getXSIType() {
		return XSITYPE;
	}
	
}
