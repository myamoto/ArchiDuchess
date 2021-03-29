package org.toolup.archi.business.archimate.relation;

public class InfluenceRelationship extends AbstractElementRelation {
	
	public static final String XSITYPE = "archimate:InfluenceRelationship";

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
		return "InfluenceRelationship [getId()=" + getId() + "]";
	}
	
	
}