package org.toolup.archi.business.archimate.relation;

public class AssignmentRelationship extends AbstractElementRelation {
	
	public static final String XSITYPE = "archimate:AssignmentRelationship";

	@Override
	public String getXSIType() {
		return XSITYPE;
	}
	
	@Override
	public String getMxGraphLineStyle() {
		return "endArrow=classic;html=1;rounded=1;verticalAlign=bottom;startArrow=circle;startFill=1;startSize=3;endSize=8;";
	}

	@Override
	public String toString() {
		return "AssignmentRelationship [getId()=" + getId() + "]";
	}

	
}