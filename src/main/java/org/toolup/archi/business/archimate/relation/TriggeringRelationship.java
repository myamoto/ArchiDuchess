package org.toolup.archi.business.archimate.relation;

public class TriggeringRelationship extends AbstractElementRelation {

	public static final String XSITYPE = "archimate:TriggeringRelationship";

	@Override
	public String getXSIType() {
		return XSITYPE;
	}
	
	@Override
	public String getMxGraphLineStyle() {
		return "endArrow=classic;html=1;rounded=1;";
	}

	@Override
	public String toString() {
		return "TriggeringRelationship [getId()=" + getId() + "]";
	}
	
	
}
