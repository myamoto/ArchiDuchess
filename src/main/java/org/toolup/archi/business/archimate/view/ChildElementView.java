package org.toolup.archi.business.archimate.view;

import java.util.ArrayList;
import java.util.List;

import org.toolup.archi.business.archimate.AbstractArchimateObject;
import org.toolup.archi.business.archimate.IElement;
import org.toolup.archi.business.mxgraph.IMxElemArchi;
import org.toolup.archi.business.mxgraph.MxEleChildView;

public class ChildElementView extends AbstractChildElemView implements IViewParent {

	public static final String XSITYPE = "archimate:DiagramObject";

	private IElement archimateElement;
	

	private List<AbstractChildElemView> childList;

	public ChildElementView(IElement archimateElement, Integer x, Integer y, Integer width, Integer height) {
		super(x, y, width, height);

		this.archimateElement = archimateElement;
		this.setName(archimateElement == null ? null : archimateElement.getName());

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

	public IElement getArchimateElement() {
		return archimateElement;
	}

	@Override
	protected String getStyle() {
		String result = super.getStyle();
		return archimateElement == null ? result : archimateElement.getDecoratedStyle(result);

	}

	
	@Override
	public String getMxGraphElementContent() {
		return getArchimateElement() != null ? getArchimateElement().getName() : "";
	}

	@Override
	public String getDefaultFontColor() {
		return getArchimateElement() != null ? getArchimateElement().getDefaultFontColor() : null;
	}

	@Override
	public String getDefaultFillColor() {
		return getArchimateElement() != null ? getArchimateElement().getDefaultFillColor() : null;
	}

	@Override
	public IMxElemArchi createCell() {
		String result = ((MxEleChildView)super.createCell()).setDecorated(getDeco() != null).toXmlString();

		IMxElemArchi deco = getDeco();
		
		if(deco != null) {
			if(getArchimateElement() != null && getArchimateElement().isOverrideMxDraw()) result =  deco.toXmlString();
			else result +=  deco.toXmlString();
		}
		
		final String xmlString = result;
		return new IMxElemArchi() {
			@Override
			public String toXmlString() {
				return xmlString;
			}

			@Override
			public int getId() {
				return AbstractArchimateObject.nextId();
			}

			@Override
			public IElement getArchiElem() {
				return ChildElementView.this;
			}
		};
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((archimateElement == null) ? 0 : archimateElement.hashCode());
		result = prime * result + ((childList == null) ? 0 : childList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChildElementView other = (ChildElementView) obj;
		if (archimateElement == null) {
			if (other.archimateElement != null)
				return false;
		} else if (!archimateElement.equals(other.archimateElement))
			return false;
		if (childList == null) {
			if (other.childList != null)
				return false;
		} else if (!childList.equals(other.childList))
			return false;
		return true;
	}

	private IMxElemArchi getDeco() {
		return getArchimateElement() == null ? null : getArchimateElement().getMxGraphDecorateElement(getxMGraph(), getyMGraph(), getWidthRect(), 5, getFillColor(), getFontColor());
	}

	@Override
	public String toString() {
		return "ChildElementView [archimateElement=" + archimateElement + ", childList="
				+ childList + "]";
	}
	

}
