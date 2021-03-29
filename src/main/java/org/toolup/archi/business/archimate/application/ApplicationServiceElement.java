package org.toolup.archi.business.archimate.application;

public class ApplicationServiceElement extends AbstractElementApplication{

	public static final String XSITYPE = "archimate:ApplicationService";
	
	public String getXSIType() {
		return XSITYPE;
	}
	
	
	@Override
	public String getDecoratedStyle(String s) {
		return s.replace("rounded=0", "rounded=1");
	}
	
}
