package org.toolup.archi.business.archimate.other;

import org.toolup.archi.business.mxgraph.IMxElemArchi;
import org.toolup.archi.business.mxgraph.MxEleBusinessActor;
import org.toolup.archi.business.mxgraph.MxEleJunction;

public class JunctionElement extends AbstractElementOther{
	
	public static final String XSITYPE = "archimate:Junction";

	public enum JunctionType {or, and};
	
	private JunctionType type;
	
	public JunctionElement() {
		super();
	}


	public String getXSIType() {
		return XSITYPE;
	}


	public JunctionType getType() {
		return type;
	}


	public JunctionElement type(JunctionType type) {
		this.type = type;
		return this;
	}
	
	@Override
	public IMxElemArchi getMxGraphDecorateElement(int x, int y, int width, int padding, String fillColor, String fontColor) {
		MxEleJunction res = new MxEleJunction()
				.setId(getIdMxgraph())
				.setX(x)
				.setY(y)
				.setDiameter(width);
		res.setArchiElem(this);
		return res;
	}
	
	@Override
	public boolean isOverrideMxDraw() {
		return true;
	}	
}
