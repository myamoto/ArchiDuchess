package org.toolup.archi.business.archimate.relation;

public class FlowRelationship extends AbstractElementRelation {

	public static final String XSITYPE = "archimate:FlowRelationship";

	@Override
	public String getXSIType() {
		return XSITYPE;
	}
	@Override
	public String getMxGraphLineStyle() {
		return "endArrow=classic;html=1;rounded=1;dashed=1;dashPattern=5 2;";
	}
	@Override
	public String toString() {
		return "FlowRelationship [getId()=" + getId() + "]";
	}
	
	
}
