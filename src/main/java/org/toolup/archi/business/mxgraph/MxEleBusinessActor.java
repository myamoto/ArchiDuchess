package org.toolup.archi.business.mxgraph;

import org.toolup.archi.business.archimate.AbstractArchimateObject;

public class MxEleBusinessActor extends AbstractMxEle{
	private static final int DEVICE_TOTALWIDTH = 16;
	
	private int x;
	private int y;
	private int w;
	private int h;

	@Override
	public MxEleBusinessActor setId(int id) {
		super.setId(id);
		return this;
	}
	public int getX() {
		return x;
	}
	public MxEleBusinessActor setX(int x) {
		this.x = x;
		return this;
	}
	public int getY() {
		return y;
	}
	public MxEleBusinessActor setY(int y) {
		this.y = y;
		return this;
	}
	public int getW() {
		return w;
	}
	public MxEleBusinessActor setW(int w) {
		this.w = w;
		return this;
	}
	public int getH() {
		return h;
	}
	public MxEleBusinessActor setH(int h) {
		this.h = h;
		return this;
	}

	
	@Override
	public String toXmlString() {
		int padding = 0;
		int xDevice =  x + w - DEVICE_TOTALWIDTH - padding;
		int yDevice =  y + padding;
		return  createActor(xDevice, yDevice, DEVICE_TOTALWIDTH, AbstractArchimateObject.nextId());
	}
}