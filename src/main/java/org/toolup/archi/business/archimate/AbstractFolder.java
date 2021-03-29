package org.toolup.archi.business.archimate;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFolder<T extends IElement> extends AbstractArchimateObject implements IFolder<T>{
	
	private List<IFolder<T>> folderList;
	private List<T> elementList;
	
	public AbstractFolder() {
		super();
		this.folderList = new ArrayList<IFolder<T>>();
		this.elementList = new ArrayList<T>();
	}
	
	public List<IFolder<T>> getFolderList(){
		return this.folderList;
	}
	
	public List<T> getElementList(){
		return this.elementList;
	}
	
	public boolean addElement(T element) {
		return elementList.add(element);
	}
	
	public boolean removeElement(T element) {
		return elementList.remove(element);
	}
	
	public boolean addFolder(IFolder<T> folder) {
		return folderList.add(folder);
	}
	
	public boolean removeFolder(IFolder<T> folder) {
		return folderList.remove(folder);
	}

	@Override
	public String toString() {
		return "AbstractFolder [folderList=" + folderList + ", elementList=" + elementList + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elementList == null) ? 0 : elementList.hashCode());
		result = prime * result + ((folderList == null) ? 0 : folderList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		AbstractFolder other = (AbstractFolder) obj;
		if (elementList == null) {
			if (other.elementList != null)
				return false;
		} else if (!elementList.equals(other.elementList))
			return false;
		if (folderList == null) {
			if (other.folderList != null)
				return false;
		} else if (!folderList.equals(other.folderList))
			return false;
		return true;
	}
	
	
	
}
