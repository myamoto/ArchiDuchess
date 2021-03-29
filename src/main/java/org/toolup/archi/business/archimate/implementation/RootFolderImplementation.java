package org.toolup.archi.business.archimate.implementation;

import org.toolup.archi.business.archimate.AbstractRootFolder;

public class RootFolderImplementation extends AbstractRootFolder<AbstractElementImplementation> {

	public static final  String TYPE = "implementation_migration";
	
	@Override
	public String getName() {
		return "Implementation & Migration";
	}
	
	@Override
	public String getType() {
		return TYPE;
	}
}
