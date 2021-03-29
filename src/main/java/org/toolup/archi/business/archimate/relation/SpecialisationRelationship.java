package org.toolup.archi.business.archimate.relation;

public class SpecialisationRelationship extends AbstractElementRelation {
	
	public static final String XSITYPE = "archimate:SpecialisationRelationship";

	@Override
	public String getXSIType() {
		return XSITYPE;
	}

	@Override
	public String getMxGraphLineStyle() {
		return "html=1;rounded=1;endArrow=block;endSize=8;endFill=0;";
	}

	@Override
	public String toString() {
		return "SpecialisationRelationship [getId()=" + getId() + "]";
	}
	
	
}