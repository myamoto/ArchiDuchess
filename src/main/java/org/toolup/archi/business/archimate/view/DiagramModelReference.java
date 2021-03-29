package org.toolup.archi.business.archimate.view;


public class DiagramModelReference extends AbstractChildElemView{
	
	public static final String XSITYPE = "archimate:DiagramModelReference";
	
	private String content;
	
	public DiagramModelReference(String content, Integer x, Integer y, Integer width, Integer height) {
		super(x, y, width, height);
		this.content = content;
	}
	
	public String getXSIType() {
		return XSITYPE;
	}

	@Override
	public String getMxGraphElementContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

}
