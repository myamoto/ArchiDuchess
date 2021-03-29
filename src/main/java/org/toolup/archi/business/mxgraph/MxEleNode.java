package org.toolup.archi.business.mxgraph;

import org.toolup.archi.business.archimate.AbstractArchimateObject;

public class MxEleNode extends AbstractMxEle{

	private static final int CUBE_TOTALWIDTH = 12;
	
	private int x;
	private int y;
	private int w;
	private int h;
	@Override
	public MxEleNode setId(int id) {
		super.setId(id);
		return this;
	}
	
	public int getX() {
		return x;
	}
	public MxEleNode setX(int x) {
		this.x = x;
		return this;
	}
	public int getY() {
		return y;
	}
	public MxEleNode setY(int y) {
		this.y = y;
		return this;
	}
	public int getW() {
		return w;
	}
	public MxEleNode setW(int w) {
		this.w = w;
		return this;
	}
	public int getH() {
		return h;
	}
	public MxEleNode setH(int h) {
		this.h = h;
		return this;
	}
	
	
	@Override
	public String toXmlString() {
		int padding = 0;
		int xCube =  x + w - CUBE_TOTALWIDTH - padding;
		int yCube =  y + padding;
		return  createIsoCube(xCube, yCube, CUBE_TOTALWIDTH, AbstractArchimateObject.nextId());
	}

}