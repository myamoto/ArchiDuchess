package org.toolup.archi.business.archimate.business;


public class BusinessProduct extends AbstractElementBusiness{
	
	public static final String XSITYPE = "archimate:Product";

	public BusinessProduct() {
		super();
	}


	public String getXSIType() {
		return XSITYPE;
	}
	
}
