package org.toolup.archi.business.archimate.relation;

public class CompositeRelationship extends AbstractElementRelation {

	public static final String XSITYPE = "archimate:CompositionRelationship";

	@Override
	public String getXSIType() {
		return XSITYPE;
	}

	@Override
	public String getMxGraphLineStyle() {
		return "html=1;rounded=1;startArrow=diamond;startFill=1;endArrow=none;startSize=12;";
	}

	@Override
	public String toString() {
		return "CompositeRelationship [getId()=" + getId() + "]";
	}


}
