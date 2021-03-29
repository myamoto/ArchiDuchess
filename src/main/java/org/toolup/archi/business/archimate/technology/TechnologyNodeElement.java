package org.toolup.archi.business.archimate.technology;

import org.toolup.archi.business.mxgraph.MxEleNode;

public class TechnologyNodeElement extends AbstractElementTechnology{

	public static final String XSITYPE = "archimate:Node";

	public TechnologyNodeElement() {
		super();
	}

	public String getXSIType() {
		return XSITYPE;
	}
	
	@Override
	public MxEleNode getMxGraphDecorateElement(int x, int y, int width, int padding, String fillColor, String fontColor) {
		int cubeWidth = 12;
		MxEleNode res = new MxEleNode()
				.setId(getIdMxgraph())
				.setX(x + width - cubeWidth - padding)
				.setY(y + padding)
				.setW(cubeWidth)
				.setH(cubeWidth);
		
		res.setArchiElem(this);
		return res;
	}
	
}

