package org.toolup.archi.business.mxgraph;

import org.toolup.archi.business.archimate.AbstractArchimateObject;
import org.toolup.archi.business.archimate.application.AbstractElementApplication;

public class MxEleApplicationData extends AbstractMxEle{

private static final int DEVICE_TOTALWIDTH = 12;
	
	private int x;
	private int y;
	private int w;
	private int h;

	@Override
	public MxEleApplicationData setId(int id) {
		super.setId(id);
		return this;
	}
	public int getX() {
		return x;
	}
	public MxEleApplicationData setX(int x) {
		this.x = x;
		return this;
	}
	public int getY() {
		return y;
	}
	public MxEleApplicationData setY(int y) {
		this.y = y;
		return this;
	}
	public int getW() {
		return w;
	}
	public MxEleApplicationData setW(int w) {
		this.w = w;
		return this;
	}
	public int getH() {
		return h;
	}
	public MxEleApplicationData setH(int h) {
		this.h = h;
		return this;
	}
	
	@Override
	public String toXmlString() {
		int padding = 0;
		int xDevice =  x + w - DEVICE_TOTALWIDTH - padding;
		int yDevice =  y + padding;
		return  createIsoCylender(xDevice, yDevice, DEVICE_TOTALWIDTH, AbstractElementApplication.FILL_COLOR, AbstractArchimateObject.nextId());
	}

}
