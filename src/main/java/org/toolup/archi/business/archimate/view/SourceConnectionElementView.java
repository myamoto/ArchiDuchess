package org.toolup.archi.business.archimate.view;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.toolup.archi.business.archimate.relation.AbstractElementRelation;
import org.toolup.archi.business.mxgraph.MxEleSourceCon;
import org.toolup.archi.io.mxgraph.MxGraphConverterException;

public class SourceConnectionElementView extends AbstractElementView {

	public static final String XSITYPE_CON = "archimate:Connection";
	private String xSITYPE;
	
	private List<BendPointElementView> bendPointList;

	private AbstractElementRelation archimateRelationship;
	private AbstractElementView source;
	private AbstractElementView target;

	private String targetId;

	private List<SourceConnectionElementView> sourceConList;
	private String color;
	
	public SourceConnectionElementView() {
		sourceConList = new ArrayList<>();
		bendPointList = new ArrayList<>();
	}
	
	public String getColor() {
		return color;
	}
	public SourceConnectionElementView setColor(String color) {
		this.color = color;
		return this;
	}

	public void setSource(AbstractElementView source) {
		this.source = source;
	}

	public void setTarget(AbstractElementView target) {
		this.target = target;
	}

	@Override
	public String getXSIType() {
		return xSITYPE;
	}
	
	public SourceConnectionElementView setXSITYPE(String xSITYPE) {
		this.xSITYPE = xSITYPE;
		return this;
	}

	public void setArchimateRelationship(AbstractElementRelation archimateRelationship) {
		this.archimateRelationship = archimateRelationship;
	}

	public AbstractElementRelation getArchimateRelationship() {
		return archimateRelationship;
	}

	public AbstractElementView getSource() {
		return source;
	}

	public AbstractElementView getTarget() {
		return target;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getTargetId() {
		return targetId;
	}

	public List<BendPointElementView> getBendPointList() {
		return bendPointList;
	}

	public boolean addBendPoint(Integer startX, Integer startY, Integer endX, Integer endY) {
		BendPointElementView bendPoint = new BendPointElementView();
		bendPoint.setStartX(startX);
		bendPoint.setStartY(startY);
		bendPoint.setEndX(endX);
		bendPoint.setEndY(endY);

		return bendPointList.add(bendPoint);
	}

	@Override
	public String toString() {
		return "SourceConnectionElementView [bendPointList=" + bendPointList + ", archimateRelationship="
				+ archimateRelationship + ", source=" + source + ", target=" + target + ", targetId=" + targetId + "]";
	}

	public MxEleSourceCon createMxGraphCell() throws MxGraphConverterException {
		return getArchimateRelationship().createMxCell(getSource(), getTarget(), getBendPointList(), color);
	}
	
	public List<Point> getMxGraphElementPoints() throws MxGraphConverterException {
		return getArchimateRelationship().getMxGraphElementPoints(getSource(), getTarget(), getBendPointList());
	}

	public List<SourceConnectionElementView> getSourceConList() {
		return sourceConList;
	}

	public boolean addSourceCon(SourceConnectionElementView sourceCon) {
		return sourceConList.add(sourceCon);
	}

}
