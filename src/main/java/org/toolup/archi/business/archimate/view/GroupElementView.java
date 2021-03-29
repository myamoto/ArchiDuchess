package org.toolup.archi.business.archimate.view;

import java.util.ArrayList;
import java.util.List;

public class GroupElementView extends AbstractChildElemView implements IViewParent{

	public static final String XSITYPE = "archimate:Group";

	private List<AbstractChildElemView> childList;
	
	public GroupElementView(String name, Integer x, Integer y, Integer width, Integer height) {
		super(x, y, width, height);

		this.setName(name);

		this.childList = new ArrayList<>();
	}

	@Override
	public String getXSIType() {
		return XSITYPE;
	}
	

	public AbstractChildElemView findChild(String childId) {
		if (childId.equals(this.getId()))
			return this;
		for (AbstractChildElemView child : childList) {
			if(child instanceof IViewParent) {
				AbstractChildElemView result = ((IViewParent)child).findChild(childId);
				if (result != null)
					return result;
			}
		}
		return null;
	}

	public boolean addElement(AbstractChildElemView element) {
		return childList.add(element);
	}

	public boolean removeElement(AbstractChildElemView element) {
		return childList.remove(element);
	}

	public List<AbstractChildElemView> getChildList() {
		List<AbstractChildElemView> result = new ArrayList<>();
		result.addAll(childList);
		return result;
	}

	public int getNbChildrenRecur() {
		int result = getChildList().size();
		for (AbstractChildElemView child : childList) {
			if(child instanceof IViewParent)
				result += ((IViewParent)child).getNbChildrenRecur();
		}
		return result;
	}

	public int getNbSourceConRecur() {
		int result = getSourceConList().size();
		for (AbstractChildElemView child : childList) {
			if(child instanceof IViewParent)
				result += ((IViewParent)child).getNbSourceConRecur();
		}
		return result;
	}

	@Override
	public String getMxGraphElementContent() {
		return getName();
	}

	@Override
	public String getDefaultFontColor() {
		return null;
	}

	@Override
	public String getDefaultFillColor() {
		return "#d2d7d7";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((childList == null) ? 0 : childList.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "GroupElementView [childList=" + childList + ", getXSIType()=" + getXSIType() + ", getChildList()="
				+ getChildList() + ", getxRect()=" + getxRect() + ", getyRect()=" + getyRect() + ", getWidthRect()="
				+ getWidthRect() + ", getHeightRect()=" + getHeightRect() + ", getSourceConList()=" + getSourceConList()
				+ ", getType()=" + getType() + ", getxMGraph()=" + getxMGraph() + ", getyMGraph()=" + getyMGraph()
				+ ", getDocumentation()=" + getDocumentation() + ", getId()=" + getId() + ", getName()=" + getName()+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupElementView other = (GroupElementView) obj;
		if (childList == null) {
			if (other.childList != null)
				return false;
		} else if (!childList.equals(other.childList))
			return false;
		return true;
	}

}
