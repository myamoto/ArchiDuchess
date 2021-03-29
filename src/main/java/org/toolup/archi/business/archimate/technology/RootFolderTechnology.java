package org.toolup.archi.business.archimate.technology;

import org.toolup.archi.business.archimate.AbstractRootFolder;

public class RootFolderTechnology extends AbstractRootFolder<AbstractElementTechnology> {

	public static final  String TYPE = "technology";

	@Override
	public String getName() {
		return "Technology & Physical";
	}

	@Override
	public String getType() {
		return TYPE;
	}

}
