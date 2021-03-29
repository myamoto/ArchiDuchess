package org.toolup.archi.business.archimate.view;

public class BendPointElementView extends AbstractElementView {

	private Integer startX;
	private Integer startY;
	private Integer endX;
	private Integer endY;

	private Integer startXMGraph;
	private Integer startYMGraph;
	private Integer endXMGraph;
	private Integer endYMGraph;
	
	public String getXSIType() {
		return null;
	}

	public Integer getStartX() {
		return startX != null ? startX : 0;
	}

	public void setStartX(Integer startX) {
		this.startX = startX;
	}

	public Integer getStartY() {
		return startY != null ? startY : 0;
	}

	public void setStartY(Integer startY) {
		this.startY = startY;
	}

	public Integer getEndX() {
		return endX != null ? endX : 0;
	}

	public void setEndX(Integer endX) {
		this.endX = endX;
	}

	public Integer getEndY() {
		return  endY != null ? endY : 0;
	}

	public void setEndY(Integer endY) {
		this.endY = endY;
	}
	

	public Integer getStartXMGraph() {
		return startXMGraph;
	}

	public BendPointElementView setStartXMGraph(Integer startXMGraph) {
		this.startXMGraph = startXMGraph;
		return this;
	}

	public Integer getStartYMGraph() {
		return startYMGraph;
	}

	public BendPointElementView setStartYMGraph(Integer startYMGraph) {
		this.startYMGraph = startYMGraph;
		return this;
	}

	public Integer getEndXMGraph() {
		return endXMGraph;
	}

	public BendPointElementView setEndXMGraph(Integer endXMGraph) {
		this.endXMGraph = endXMGraph;
		return this;
	}

	public Integer getEndYMGraph() {
		return endYMGraph;
	}

	public BendPointElementView setEndYMGraph(Integer endYMGraph) {
		this.endYMGraph = endYMGraph;
		return this;
	}

	public boolean isConsistent() {
		return startX != null && startY != null && endX != null && endY != null;
	}
	
}
