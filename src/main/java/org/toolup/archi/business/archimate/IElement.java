package org.toolup.archi.business.archimate;

import org.toolup.archi.business.mxgraph.IMxElemArchi;

public interface IElement extends IArchimateObject {
	String getXSIType();
	
	IMxElemArchi getMxGraphDecorateElement(int x, int y, int width, int padding, String fillColor, String fontColor);
	
	boolean isOverrideMxDraw();
	
	String getDefaultFontColor();
	
	String getDefaultFillColor();

	String getDecoratedStyle(String result);
}
