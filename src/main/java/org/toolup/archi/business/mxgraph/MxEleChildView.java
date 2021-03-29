package org.toolup.archi.business.mxgraph;

import org.toolup.archi.business.archimate.AbstractArchimateObject;

public class MxEleChildView extends AbstractMxEle{

	public static final int WIDTH_DECO = 20;

	private String style;
	private String content;
	private boolean decorated;
	private int x;
	private int y;
	private int w;
	private int h;

	@Override
	public MxEleChildView setId(int id) {
		super.setId(id);
		return this;
	}

	public String getStyle() {
		return style;
	}

	public MxEleChildView setStyle(String style) {
		this.style = style;
		return this;
	}

	public String getContent() {
		return content;
	}

	public MxEleChildView setContent(String content) {
		this.content = content;
		return this;
	}

	public int getX() {
		return x;
	}

	public MxEleChildView setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return y;
	}

	public MxEleChildView setY(int y) {
		this.y = y;
		return this;
	}

	public int getW() {
		return w;
	}

	public MxEleChildView setW(int w) {
		this.w = w;
		return this;
	}

	public int getH() {
		return h;
	}

	public MxEleChildView setH(int h) {
		this.h = h;
		return this;
	}

	public boolean isDecorated() {
		return decorated;
	}

	public MxEleChildView setDecorated(boolean decorated) {
		this.decorated = decorated;
		return this;
	}

	public String toXmlString() {
		if(!decorated)
			return createCell(style, getId(), xmlEscapeText(content), x, y, w, h);

		return createCell(style, getId(), "", x, y, w, h)
				+ createCell("rounded=0;whiteSpace=wrap;html=1;verticalAlign=top;spacingRight=2;strokeColor=none;fillColor=none;", AbstractArchimateObject.nextId(), xmlEscapeText(content), x, y, w - WIDTH_DECO, h);
	}

	public static String createCell(String style, int id, String content, int x, int y, int w, int h) {
		return String.format("<mxCell style=\"%s\" id=\"%d\" value=\"%s\"  parent=\"1\" vertex=\"1\">%n" +
				"			<mxGeometry x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" as=\"geometry\"/>%n" + 
				"		</mxCell>"
				, style
				, id
				, content
				, x
				, y
				, w
				, h);
	}

	static String xmlEscapeText(String t) {
		if(t == null) return null;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < t.length(); i++){
			char c = t.charAt(i);
			
			if(c == '\n' && i > 0 && t.charAt(i - 1) == '\r') sb.append("&#xA;");
			else if(c == ' ' && i > 1 && t.charAt(i - 2) == '\r' && t.charAt(i - 1)== '\n') sb.append('\u00A0');
			else switch(c){
			case '<': sb.append("&lt;"); break;
			case '>': sb.append("&gt;"); break;
			case '\"': sb.append("&quot;"); break;
			case '&': sb.append("&amp;"); break;
			case '\t': sb.append("&#009;");break;
			case ' ': sb.append("&#032;");break;
			case '\'': sb.append("&apos;"); break;
			default:
				if(c>0x7e) {
					sb.append("&#"+((int)c)+";");
				}else
					sb.append(c);
			}
		}
		return sb.toString();
	}

}
