package org.toolup.archi.business.archimate.strategy;

import org.toolup.archi.business.archimate.AbstractRootFolder;

public class RootFolderStrategy extends AbstractRootFolder<AbstractElementStrategy>{

	public static final  String TYPE = "strategy";

	@Override
	public String getName() {
		return "Strategy";
	}

	@Override
	public String getType() {
		return TYPE;
	}

}