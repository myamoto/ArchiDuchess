package org.toolup.archi.business.archimate.relation;

import org.toolup.archi.business.archimate.AbstractRootFolder;

public class RootFolderRelation extends AbstractRootFolder<AbstractElementRelation> {

	public static final  String TYPE = "relations";

	@Override
	public String getName() {
		return "Relations";
	}

	@Override
	public String getType() {
		return TYPE;
	}

}
