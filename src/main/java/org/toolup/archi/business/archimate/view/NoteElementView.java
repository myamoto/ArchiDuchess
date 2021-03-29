package org.toolup.archi.business.archimate.view;

public class NoteElementView extends AbstractChildElemView {

	public static final String XSITYPE = "archimate:Note";

	private String content;

	public NoteElementView(String content, Integer x, Integer y, Integer width, Integer height) {
		super(x, y, width, height);
		this.content = content;
	}

	@Override
	public String getXSIType() {
		return XSITYPE;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public String getMxGraphElementContent() {
		return content != null ? content : "";
	}


	@Override
	protected String getStyle() {
		String fillColor = getFillColor();
		String fontColor = getFontColor();
		if(fillColor == null || fillColor.isEmpty()) fillColor = "white";
		if(fontColor == null || fontColor.isEmpty()) fontColor = "black";
		return String.format("fillColor=%s;fontColor=%s;align=left;word-wrap=break-word;wordWrap=break-word;rounded=0;whiteSpace=wrap;html=1;verticalAlign=top;spacingRight=2;",
				fillColor, fontColor
				);
	}
}
