package org.toolup.archi.business.archimate.business;


public class BusinessRepresentation extends AbstractElementBusiness{
	
	public static final String XSITYPE = "archimate:Representation";

	public BusinessRepresentation() {
		super();
	}


	public String getXSIType() {
		return XSITYPE;
	}
	
}
