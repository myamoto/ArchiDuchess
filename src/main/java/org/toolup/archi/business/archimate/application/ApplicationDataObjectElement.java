package org.toolup.archi.business.archimate.application;

import org.toolup.archi.business.mxgraph.IMxElemArchi;
import org.toolup.archi.business.mxgraph.MxEleApplicationData;

public class ApplicationDataObjectElement extends AbstractElementApplication{

	public static final String XSITYPE = "archimate:DataObject";

	public String getXSIType() {
		return XSITYPE;
	}
	
	@Override
	public IMxElemArchi getMxGraphDecorateElement(int x, int y, int width, int padding, String fillColor, String fontColor) {
		int laptopWidth = 18;
		MxEleApplicationData res = new MxEleApplicationData()
				.setId(getIdMxgraph())
				.setX(x + width - laptopWidth - padding)
				.setY(y + padding)
				.setW(laptopWidth)
				.setH(laptopWidth);
		res.setArchiElem(this);
		return res;
	}

}
