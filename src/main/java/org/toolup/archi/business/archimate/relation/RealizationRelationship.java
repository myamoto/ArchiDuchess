package org.toolup.archi.business.archimate.relation;

public class RealizationRelationship extends AbstractElementRelation {

	public static final String XSITYPE = "archimate:RealizationRelationship";

	@Override
	public String getXSIType() {
		return XSITYPE;
	}
	
	@Override
	public String getMxGraphLineStyle() {
		return "html=1;rounded=1;endArrow=block;endSize=11;endFill=0;dashed=1;dashPattern=2 2;";
	}

	@Override
	public String toString() {
		return "RealizationRelationship [getId()=" + getId() + "]";
	}
	
	

}
