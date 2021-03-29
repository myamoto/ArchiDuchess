package org.toolup.archi.business.archimate.view;

import java.util.ArrayList;
import java.util.List;

public class DiagramModelView extends AbstractElementView {

	private List<AbstractChildElemView> childList;

	public static final String XSITYPE = "archimate:ArchimateDiagramModel";

	public DiagramModelView() {
		childList = new ArrayList<>();
	}

	@Override
	public String getXSIType() {
		return XSITYPE;
	}

	public boolean addElement(AbstractChildElemView element) {
		return childList.add(element);
	}

	public boolean removeElement(AbstractChildElemView element) {
		return childList.remove(element);		
	}

	public List<AbstractChildElemView> getChildList(){
		return childList;
	}

	public AbstractChildElemView findChildRecur(String childId) {
		for (AbstractChildElemView child : childList) {
			if(child.getId().equals(childId))
				return child;
			if(child instanceof IViewParent) {
				AbstractChildElemView result = ((IViewParent)child).findChild(childId);
				if(result != null)
					return result;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return getName();
	}
}
