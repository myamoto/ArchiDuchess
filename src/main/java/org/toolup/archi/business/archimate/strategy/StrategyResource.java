package org.toolup.archi.business.archimate.strategy;


public class StrategyResource extends AbstractElementStrategy{
	
	public static final String XSITYPE = "archimate:Resource";

	
	public StrategyResource() {
		super();
	}


	public String getXSIType() {
		return XSITYPE;
	}

}
