package org.toolup.archi.business.archimate.business;

import org.toolup.archi.business.archimate.AbstractRootFolder;

public class RootFolderBusiness extends AbstractRootFolder<AbstractElementBusiness> {
	
	public static final  String TYPE = "business";
	
	public String getName() {
		return "Business";
	}
	
	@Override
	public String getType() {
		return TYPE;
	}

}
