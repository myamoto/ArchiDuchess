package org.toolup.archi.business.archimate.other;

import org.toolup.archi.business.archimate.AbstractRootFolder;

public class RootFolderOther extends AbstractRootFolder<AbstractElementOther>{

	public static final  String TYPE = "other";

	@Override
	public String getName() {
		return "Other";
	}

	@Override
	public String getType() {
		return TYPE;
	}

}
