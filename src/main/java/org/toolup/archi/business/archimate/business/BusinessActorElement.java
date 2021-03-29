package org.toolup.archi.business.archimate.business;

import org.toolup.archi.business.mxgraph.IMxElemArchi;
import org.toolup.archi.business.mxgraph.MxEleBusinessActor;

public class BusinessActorElement extends AbstractElementBusiness{
	
	public static final String XSITYPE = "archimate:BusinessActor";

	public BusinessActorElement() {
		super();
	}


	public String getXSIType() {
		return XSITYPE;
	}
	

	@Override
	public IMxElemArchi getMxGraphDecorateElement(int x, int y, int width, int padding, String fillColor, String fontColor) {
		int laptopWidth = 18;
		MxEleBusinessActor res = new MxEleBusinessActor()
				.setId(getIdMxgraph())
				.setX(x + width - laptopWidth - padding)
				.setY(y + padding)
				.setW(laptopWidth)
				.setH(laptopWidth);
		res.setArchiElem(this);
		return res;
	}
	
}
