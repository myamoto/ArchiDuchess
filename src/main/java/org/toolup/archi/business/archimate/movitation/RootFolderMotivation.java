package org.toolup.archi.business.archimate.movitation;

import org.toolup.archi.business.archimate.AbstractRootFolder;

public class RootFolderMotivation extends AbstractRootFolder<AbstractElementMotivation> {
	public static final  String TYPE = "motivation";

	@Override
	public String getName() {
		return "Motivation";
	}

	@Override
	public String getType() {
		return TYPE;
	}

}
