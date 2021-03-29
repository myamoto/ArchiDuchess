package org.toolup.archi.business.archimate.technology;

public class TechnologyArtifact extends AbstractElementTechnology{
	public static final String XSITYPE = "archimate:Artifact";

	public TechnologyArtifact() {
		super();
	}

	public String getXSIType() {
		return XSITYPE;
	}

}
