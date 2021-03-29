package org.toolup.archi.business.archimate.application;

import org.toolup.archi.business.mxgraph.IMxElemArchi;
import org.toolup.archi.business.mxgraph.MxEleApplicationComponent;

public class ApplicationComponentElement extends AbstractElementApplication{
	public static final String XSITYPE = "archimate:ApplicationComponent";

	public String getXSIType() {
		return XSITYPE;
	}
	@Override
	public IMxElemArchi getMxGraphDecorateElement(int x, int y, int width
			, int padding, String fillColor, String fontColor) {
		int laptopWidth = 18;
		
		
		MxEleApplicationComponent res = new MxEleApplicationComponent()
				.setFillColor(fillColor)
				.setId(getIdMxgraph())
				.setX(x + width - laptopWidth - padding)
				.setY(y + padding)
				.setW(laptopWidth)
				.setH(laptopWidth);
		res.setArchiElem(this);
		return res;
	}
}
