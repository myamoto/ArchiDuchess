package org.toolup.archi.business.mxgraph;

import org.toolup.archi.business.archimate.IElement;

public interface IMxElemArchi {
	IElement getArchiElem();
	String toXmlString();
	int getId();
}
