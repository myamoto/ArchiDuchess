package org.toolup.archi.business.mxgraph;

import java.awt.Point;

import org.toolup.archi.business.archimate.AbstractArchimateObject;
import org.toolup.archi.business.archimate.IElement;

public abstract class AbstractMxEle implements IMxElemArchi{
	private int id;
	private IElement archiElem;
	
	public int getId() {
		return id;
	}
	public AbstractMxEle setId(int id) {
		this.id = id;
		return this;
	}
	public IElement getArchiElem() {
		return archiElem;
	}
	public void setArchiElem(IElement archiElem) {
		this.archiElem = archiElem;
	}
	
	protected String createCircle(int x, int y, int d, String fillColor, int id) {
		return createEllipse(x, y, d, d, fillColor, id);
	}
	
	protected String createCircle(int x, int y, int d, int id) {
		return createCircle(x, y, d, "none", id);
	}
	
	protected String createEllipse(int x, int y, int w, int h, String fillColor, int id) {
		return String.format(
				 "<mxCell vertex=\"1\" id=\"%d\" value=\"\" style=\"shape=ellipse;fillColor=%s;strokeColor=black;\"  parent=\"1\">"
				+ "<mxGeometry x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" as=\"geometry\"/>"
				+ "</mxCell>"
				, id, fillColor, x, y, w, h);
	}
	protected String createRectangle(int x, int y, int w, int h, String fillColor, String strokeColor,  int id) {
		return createRectangle(x, y, w, h, fillColor, strokeColor, false, id);
	}
	protected String createRectangle(int x, int y, int w, int h, String fillColor, String strokeColor, boolean rounded, int id) {
		String style = String.format("shape=rectangle;fillColor=%s;strokeColor=%s;", fillColor, strokeColor);
		if(rounded) style += "rounded=1;";
		return String.format(
				 "<mxCell vertex=\"1\" id=\"%d\" value=\"\" style=\"%s\"  parent=\"1\">"
				+ "<mxGeometry x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" as=\"geometry\"/>"
				+ "</mxCell>"
				, id,  style,x, y, w, h);
	}
	protected String createRectangle(int x, int y, int w, int h, String fillColor, int id) {
		return createRectangle(x, y, w, h, fillColor, "black", false, id);
	}
	
	protected String createRectangleRounded(int x, int y, int w, int h, String fillColor, int id) {
		return createRectangle(x, y, w, h, fillColor, "black", true, id);
	}
	

	
	protected String createLine(int xSrc, int ySrc, int xTgt, int yTgt, int id) {
		return String.format(
				"<mxCell id='%d' value='' style='endArrow=none;html=1;entryX=0;entryY=0.5;entryDx=0;entryDy=0;strokeColor=black;' edge='1' parent='1' source='3' target='2'>" +
				 "<mxGeometry width='50' height='50' relative='1' as='geometry'>" +
					"<mxPoint x='%d' y='%d' as='sourcePoint'/>" +
					"<mxPoint x='%d' y='%d' as='targetPoint'/>" +
				"</mxGeometry>" +
			"</mxCell>",
			id, xSrc, ySrc, xTgt, yTgt);
	}
	
	protected String createActor(int x, int y, int w, int id) {
		int headDiameter = w / 4;
		int bodyLength = 2 * w / 5;
		Point p1 = new Point(x + w / 2, y + headDiameter);
		Point p2 = new Point(p1.x, p1.y + bodyLength);
		Point p3 = new Point(x, p1.y);
		Point p4 = new Point(x + w, p3.y);
		Point p5 = new Point(x + headDiameter, y + w);
		Point p6 = new Point(x + w - headDiameter, y + w);
		return createCircle(x + (w - headDiameter) / 2, y, headDiameter, id)
				+ createLine(p1.x, p1.y, p2.x, p2.y, AbstractArchimateObject.nextId())
				+ createLine(p3.x, p3.y, p1.x, p1.y + headDiameter, AbstractArchimateObject.nextId())
				+ createLine(p4.x, p4.y, p1.x, p1.y + headDiameter, AbstractArchimateObject.nextId())
				+ createLine(p2.x, p2.y, p5.x, p5.y, AbstractArchimateObject.nextId())
				+ createLine(p2.x, p2.y, p6.x, p6.y, AbstractArchimateObject.nextId());
		
	}
	
	
	protected String createIsoCylender(int x, int y, int w, String fillColor, int id) {
		
		int hElps = w / 3;
		return  createEllipse(x, y + 2 * hElps, w, hElps, fillColor, AbstractArchimateObject.nextId())
				+ createRectangle(x + 1, y + hElps / 2, w - 2, 2 * hElps, fillColor, fillColor, AbstractArchimateObject.nextId())
				+ createEllipse(x, y, w, hElps, fillColor, AbstractArchimateObject.nextId())
				
				+ createLine(x, y + hElps / 2, x, y + 5 * hElps /2, AbstractArchimateObject.nextId())
				+ createLine(x + w, y + hElps / 2, x + w, y + 5 * hElps /2, AbstractArchimateObject.nextId()) ;
	}
	
	protected String createIsoCube(int x, int y, int w, int id) {
		
		int wCube = 3 * w / 4;
		int d = wCube / 3;
		Point p1 = new Point(x, y + d);
		Point p2 = new Point(p1.x, p1.y + wCube);
		Point p3 = new Point(p2.x + 3 * wCube / 4 , p2.y + d);
		Point p4 = new Point(p3.x , p3.y - wCube );
		
		Point p5 = new Point(p4.x + 3 * wCube / 4 , p4.y - d);
		Point p6 = new Point(p5.x , p5.y + wCube );

		Point p7 = new Point(p4.x , y);
		
		return createLine(p1.x, p1.y, p2.x, p2.y, AbstractArchimateObject.nextId()) 
				+ createLine(p2.x, p2.y, p3.x, p3.y, AbstractArchimateObject.nextId())
				+ createLine(p3.x, p3.y, p4.x, p4.y, AbstractArchimateObject.nextId())
				+ createLine(p4.x, p4.y, p1.x, p1.y, AbstractArchimateObject.nextId())
				+ createLine(p4.x, p4.y, p5.x, p5.y, AbstractArchimateObject.nextId())
				+ createLine(p5.x, p5.y, p6.x, p6.y, AbstractArchimateObject.nextId())
				+ createLine(p6.x, p6.y, p3.x, p3.y, AbstractArchimateObject.nextId())
				+ createLine(p5.x, p5.y, p7.x, p7.y, AbstractArchimateObject.nextId())
				+ createLine(p1.x, p1.y, p7.x, p7.y, AbstractArchimateObject.nextId());
	}
	
	protected String createDevice(int x, int y, int w, int id) {

		int wScreen = 2 * w / 3;
		Point p1 = new Point(x + (w - wScreen) / 2, y);
		Point p2 = new Point(p1.x + wScreen, p1.y);
		Point p3 = new Point(p2.x, p2.y + wScreen);
		Point p4 = new Point(p1.x , p2.y + wScreen );
		
		Point p5 = new Point(x + w, y + w);
		Point p6 = new Point(x, y + w);
		
		return createLine(p1.x, p1.y, p2.x, p2.y, AbstractArchimateObject.nextId()) 
				+ createLine(p2.x, p2.y, p3.x, p3.y, AbstractArchimateObject.nextId())
				+ createLine(p3.x, p3.y, p4.x, p4.y, AbstractArchimateObject.nextId())
				+ createLine(p4.x, p4.y, p1.x, p1.y, AbstractArchimateObject.nextId())
				
				+ createLine(p3.x, p3.y, p5.x, p5.y, AbstractArchimateObject.nextId()) 
				+ createLine(p5.x, p5.y, p6.x, p6.y, AbstractArchimateObject.nextId())
				+ createLine(p6.x, p6.y, p4.x, p4.y, AbstractArchimateObject.nextId());
		
		
	}
}
