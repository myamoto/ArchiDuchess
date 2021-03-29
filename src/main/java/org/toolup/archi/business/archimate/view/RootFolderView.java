package org.toolup.archi.business.archimate.view;

import org.toolup.archi.business.archimate.AbstractRootFolder;

public class RootFolderView extends AbstractRootFolder<AbstractElementView>{

	public static final  String TYPE = "diagrams";

	@Override
	public String getName() {
		return "Views";
	}

	@Override
	public String getType() {
		return TYPE;
	}

}
