package org.toolup.archi.business.mxgraph;

import org.toolup.archi.business.archimate.other.JunctionElement;
import org.toolup.archi.business.archimate.other.JunctionElement.JunctionType;

public class MxEleJunction extends AbstractMxEle{
	private int x;
	private int y;
	private int diameter;

	@Override
	public MxEleJunction setId(int id) {
		super.setId(id);
		return this;
	}
	public int getX() {
		return x;
	}
	public MxEleJunction setX(int x) {
		this.x = x;
		return this;
	}
	public int getY() {
		return y;
	}
	public MxEleJunction setY(int y) {
		this.y = y;
		return this;
	}
	public int getDiameter() {
		return diameter;
	}
	public MxEleJunction setDiameter(int diameter) {
		this.diameter = diameter;
		return this;
	}

	@Override
	public String toXmlString() {
		return  createCircle(x, y, diameter, ((JunctionElement)getArchiElem()).getType() == JunctionType.or ? "none" : "black", getId());
	}
}