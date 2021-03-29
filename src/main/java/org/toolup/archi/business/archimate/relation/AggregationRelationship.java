package org.toolup.archi.business.archimate.relation;

public class AggregationRelationship extends AbstractElementRelation {

	public static final String XSITYPE = "archimate:AggregationRelationship";

	@Override
	public String getXSIType() {
		return XSITYPE;
	}

	@Override
	public String getMxGraphLineStyle() {
		return "html=1;rounded=1;endArrow=none;endSize=12;startArrow=diamond;startSize=12;startFill=0;align=left;verticalAlign=bottom;";
	}

	@Override
	public String toString() {
		return "AggregationRelationship [getId()=" + getId() + "]";
	}
	
	
}

