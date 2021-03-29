package org.toolup.archi.business.archimate.business;


public class BusinessObject extends AbstractElementBusiness{
	
	public static final String XSITYPE = "archimate:BusinessObject";

	public BusinessObject() {
		super();
	}


	public String getXSIType() {
		return XSITYPE;
	}
	
}
