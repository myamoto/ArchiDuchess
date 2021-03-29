package org.toolup.archi.business.archimate.technology;

import org.toolup.archi.business.archimate.AbstractArchimateObject;
import org.toolup.archi.business.mxgraph.IMxElemArchi;
import org.toolup.archi.business.mxgraph.MxEleNetwork;

public class TechnologyCommunicationNetworkElement extends AbstractElementTechnology{

	public static final String XSITYPE = "archimate:CommunicationNetwork";

	public TechnologyCommunicationNetworkElement() {
		super();
	}

	@Override
	public IMxElemArchi getMxGraphDecorateElement(int x, int y, int width, int padding, String fillColor, String fontColor) {
		MxEleNetwork res =  new MxEleNetwork()
				.setId(getIdMxgraph())
				.setId2(AbstractArchimateObject.nextId())
				.setIdC1(AbstractArchimateObject.nextId())
				.setIdC2(AbstractArchimateObject.nextId())
				.setIdC3(AbstractArchimateObject.nextId())
				.setIdC4(AbstractArchimateObject.nextId())
				.setIdL1(AbstractArchimateObject.nextId())
				.setIdL2(AbstractArchimateObject.nextId())
				.setIdL3(AbstractArchimateObject.nextId())
				.setIdL4(AbstractArchimateObject.nextId())
				.setxSymbol(x + width - 15 - padding)
				.setySymbol(y + padding);
		

		res.setArchiElem(this);
		return res;
	}
	public String getXSIType() {
		return XSITYPE;
	}


}
