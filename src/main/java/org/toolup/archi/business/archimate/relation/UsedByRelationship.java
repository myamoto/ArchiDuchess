package org.toolup.archi.business.archimate.relation;


public class UsedByRelationship extends AbstractElementRelation {

	public static final String XSITYPE = "archimate:UsedByRelationship";

	@Override
	public String getXSIType() {
		return XSITYPE;
	}
	@Override
	public String getMxGraphLineStyle() {
		return "html=1;rounded=1;endArrow=none;startArrow=classic;";
	}
	@Override
	public String toString() {
		return "UsedByRelationship [getId()=" + getId() + "]";
	}
	
}
