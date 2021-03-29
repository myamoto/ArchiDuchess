package org.toolup.archi.business.archimate.relation;

public class NoteRelationship extends AbstractElementRelation {

	@Override
	public String getXSIType() {
		return null;
	}
	
	@Override
	public String getMxGraphLineStyle() {
		return "html=1;rounded=1;endArrow=none;";
	}
	
	@Override
	public String getId() {
		return null;
	}


	@Override
	public String toString() {
		return "NoteRelationship [toString()=" + super.toString() + "]";
	}

}

