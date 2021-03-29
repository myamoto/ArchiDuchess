package org.toolup.archi.business.mxgraph;

import org.toolup.archi.business.archimate.AbstractArchimateObject;
import org.toolup.archi.business.archimate.application.AbstractElementApplication;

public class MxEleApplicationComponent extends AbstractMxEle{

	private static final int DEVICE_TOTALWIDTH = 12;

	private int x;
	private int y;
	private int w;
	private int h;
	
	private String fillColor;

	@Override
	public MxEleApplicationComponent setId(int id) {
		super.setId(id);
		return this;
	}
	
	public MxEleApplicationComponent setFillColor(String fillColor) {
		this.fillColor = fillColor;
		return this;
	}
	
	public int getX() {
		return x;
	}
	public MxEleApplicationComponent setX(int x) {
		this.x = x;
		return this;
	}
	public int getY() {
		return y;
	}
	public MxEleApplicationComponent setY(int y) {
		this.y = y;
		return this;
	}
	public int getW() {
		return w;
	}
	public MxEleApplicationComponent setW(int w) {
		this.w = w;
		return this;
	}
	public int getH() {
		return h;
	}
	public MxEleApplicationComponent setH(int h) {
		this.h = h;
		return this;
	}

	@Override
	public String toXmlString() {
		int padding = 0;
		int xDevice =  x + w - DEVICE_TOTALWIDTH - padding;
		int yDevice =  y + padding;
		return  createApplicationComponent(xDevice, yDevice, DEVICE_TOTALWIDTH, fillColor != null ? fillColor : AbstractElementApplication.FILL_COLOR, AbstractArchimateObject.nextId());
	}

	protected String createApplicationComponent(int x, int y, int w, String fillColor, int id) {

		int wBig = 2 * w / 3;
		int hSmall = wBig / 3;
		return 
				createRectangle(x + (w - wBig), y, wBig, w, fillColor, AbstractArchimateObject.nextId())
				+ createRectangle(x, y + hSmall, wBig, hSmall, fillColor, AbstractArchimateObject.nextId())
				+ createRectangle(x, y + hSmall * 3, wBig, hSmall, fillColor, AbstractArchimateObject.nextId());
	}
	
}
