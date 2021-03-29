package org.toolup.archi.business.archimate.application;

import org.toolup.archi.business.archimate.AbstractRootFolder;

public class RootFolderApplication extends AbstractRootFolder<AbstractElementApplication> {
	
	public static final  String TYPE = "application";
	
	@Override
	public String getName() {
		return "Application";
	}
	
	@Override
	public String getType() {
		return TYPE;
	}

}
