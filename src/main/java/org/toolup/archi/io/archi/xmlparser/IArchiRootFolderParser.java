package org.toolup.archi.io.archi.xmlparser;

import org.w3c.dom.Element;

import org.toolup.archi.business.archimate.ArchimateModel;
import org.toolup.archi.business.archimate.ArchimateModelException;

public interface IArchiRootFolderParser <AbstractRootFolder>{

	String getType();
	AbstractRootFolder parse(Element rootFolderEle, ArchimateModel model) throws ArchimateModelException;
}
