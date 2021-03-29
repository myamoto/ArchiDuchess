package org.toolup.archi.business.archimate;

import java.util.List;

public interface IFolder<T extends IElement> extends IArchimateObject {
	List<IFolder<T>> getFolderList();
	
	List<T> getElementList();
}
