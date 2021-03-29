package org.toolup.archi.business.archimate.view;

import java.util.ArrayList;
import java.util.List;

import org.toolup.archi.business.mxgraph.IMxElemArchi;
import org.toolup.archi.business.mxgraph.MxEleChildView;

public abstract class AbstractChildElemView extends AbstractElementView {
	
	private int absoluteX;
	private int absoluteY;

	private Integer xRect;
	private Integer yRect;
	private Integer widthRect;
	private Integer heightRect;
	
	private int defaultWidth = 120;
	private int defaultHeight = 55;
	
	private String fontColor;
	private String fillColor;
	private String strokeColor;
	
	private String type;
	
	private Integer xMGraph;
	private Integer yMGraph;

	private List<SourceConnectionElementView> sourceConList;
	
	public AbstractChildElemView(Integer x, Integer y, Integer width, Integer height) {
		sourceConList = new ArrayList<>();
		this.xRect = x;
		this.yRect = y;
		this.widthRect = width;
		this.heightRect = height;
	}
	
	public Integer getxRect() {
		return xRect;
	}

	public Integer getyRect() {
		return yRect;
	}

	public Integer getWidthRect() {
		return widthRect != null ? widthRect : defaultWidth;
	}

	public Integer getHeightRect() {
		return heightRect != null ? heightRect : defaultHeight;
	}
	
	public int getAbsoluteX() {
		return absoluteX;
	}

	public void setAbsoluteX(int absoluteX) {
		this.absoluteX = absoluteX;
	}

	public int getAbsoluteY() {
		return absoluteY;
	}

	public void setAbsoluteY(int absoluteY) {
		this.absoluteY = absoluteY;
	}
	
	public List<SourceConnectionElementView> getSourceConList() {
		return sourceConList;
	}

	public boolean addSourceCon(SourceConnectionElementView sourceCon) {
		return sourceConList.add(sourceCon);
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public boolean hasType() {
		return type != null && !type.trim().isEmpty();
	}
	
	public abstract String getMxGraphElementContent();

	@Override
	public String getDefaultFontColor() {
		return null;
	}
	
	@Override
	public String getDefaultFillColor() {
		return null;
	}
	
	public IMxElemArchi createCell() {
		MxEleChildView res =  new MxEleChildView()
				.setId(getIdMxgraph())
				.setStyle(getStyle())
				.setX(getxMGraph())
				.setY(getyMGraph())
				.setW(getWidthRect())
				.setH(getHeightRect())
				.setContent(getMxGraphElementContent());		
		res.setArchiElem(this);
		return res;
	}
	
	protected String getStyle() {
		String fillClr = getFillColor();
		String fontClr = getFontColor();
		if(fontClr == null || fontClr.isEmpty()) fontClr = getDefaultFontColor();
		String result = "rounded=0;whiteSpace=wrap;html=1;verticalAlign=top;spacingRight=2;";
		if(fillClr != null && !fillClr.isEmpty()) result += "fillColor=" + fillClr + ";";
		if(fontClr != null && !fontClr.isEmpty()) result += "fontColor=" + fontClr + ";";
		if(strokeColor != null && !strokeColor.isEmpty()) result += "strokeColor=" + strokeColor + ";";
		return result;
	}
	
	@Override
	public String toString() {
		return "AbstractChildElemView [xRect=" + xRect
				+ ", yRect=" + yRect + ", widthRect=" + widthRect + ", heightRect=" + heightRect + ", type=" + type +"]";
	}

	public Integer getxMGraph() {
		return xMGraph;
	}

	public void setxMGraph(Integer xMgraph) {
		this.xMGraph = xMgraph;
	}

	public Integer getyMGraph() {
		return yMGraph;
	}

	public void setyMGraph(Integer yMGraph) {
		this.yMGraph = yMGraph;
	}
	
	public String getStrokeColor() {
		return strokeColor == null || strokeColor.isEmpty() ? getDefaultStrokeColor() : strokeColor;
	}
	

	public String getFontColor() {
		return fontColor == null || fontColor.isEmpty() ? getDefaultFontColor() : fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public String getFillColor() {
		return fillColor == null || fillColor.isEmpty() ? getDefaultFillColor() : fillColor;
	}

	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}

}
