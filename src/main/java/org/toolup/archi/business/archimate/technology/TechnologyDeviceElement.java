package org.toolup.archi.business.archimate.technology;

import org.toolup.archi.business.mxgraph.IMxElemArchi;
import org.toolup.archi.business.mxgraph.MxEleDevice;

public class TechnologyDeviceElement extends AbstractElementTechnology{
	public static final String XSITYPE = "archimate:Device";

	public TechnologyDeviceElement() {
		super();
	}

	public String getXSIType() {
		return XSITYPE;
	}

	@Override
	public IMxElemArchi getMxGraphDecorateElement(int x, int y, int width, int padding, String fillColor, String fontColor) {
		int laptopWidth = 18;
		MxEleDevice res = new MxEleDevice()
				.setId(getIdMxgraph())
				.setX(x + width - laptopWidth - padding)
				.setY(y + padding)
				.setW(laptopWidth)
				.setH(laptopWidth);
		res.setArchiElem(this);
		return res;
	}
}
