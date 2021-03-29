package org.toolup.archi.business.archimate.view;

import java.util.List;

public interface IViewParent {
	List<AbstractChildElemView> getChildList();
	List<SourceConnectionElementView> getSourceConList();
	 AbstractChildElemView findChild(String childId);
	 String getId();
	int getNbSourceConRecur();
	int getNbChildrenRecur();
}
