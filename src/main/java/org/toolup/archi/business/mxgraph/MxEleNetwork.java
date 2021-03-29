package org.toolup.archi.business.mxgraph;

public class MxEleNetwork extends AbstractMxEle{

	static final int CIRCLES_DIAMETER = 6;
	private int id2;
	private int idC1;
	private int idC2;
	private int idC3;
	private int idC4;
	private int idL1;
	private int idL2;
	private int idL3;
	private int idL4;
	private String style;
	private String content;
	private int xSymbol;
	private int ySymbol;


	@Override
	public MxEleNetwork setId(int id) {
		super.setId(id);
		return this;
	}
	public String getStyle() {
		return style;
	}
	public MxEleNetwork setStyle(String style) {
		this.style = style;
		return this;
	}
	public String getContent() {
		return content;
	}
	public MxEleNetwork setContent(String content) {
		this.content = content;
		return this;
	}
	public int getxSymbol() {
		return xSymbol;
	}
	public MxEleNetwork setxSymbol(int xSymbol) {
		this.xSymbol = xSymbol;
		return this;
	}
	public int getySymbol() {
		return ySymbol;
	}
	public MxEleNetwork setySymbol(int ySymbol) {
		this.ySymbol = ySymbol;
		return this;
	}
	public int getId2() {
		return id2;
	}
	public MxEleNetwork setId2(int id2) {
		this.id2 = id2;
		return this;
	}
	public int getIdC1() {
		return idC1;
	}
	public MxEleNetwork setIdC1(int idC1) {
		this.idC1 = idC1;
		return this;
	}
	public int getIdC2() {
		return idC2;
	}
	public MxEleNetwork setIdC2(int idC2) {
		this.idC2 = idC2;
		return this;
	}
	public int getIdC3() {
		return idC3;
	}
	public MxEleNetwork setIdC3(int idC3) {
		this.idC3 = idC3;
		return this;
	}
	public int getIdC4() {
		return idC4;
	}
	public MxEleNetwork setIdC4(int idC4) {
		this.idC4 = idC4;
		return this;
	}
	public int getIdL1() {
		return idL1;
	}
	public MxEleNetwork setIdL1(int idL1) {
		this.idL1 = idL1;
		return this;
	}
	public int getIdL2() {
		return idL2;
	}
	public MxEleNetwork setIdL2(int idL2) {
		this.idL2 = idL2;
		return this;
	}

	public int getIdL3() {
		return idL3;
	}
	public MxEleNetwork setIdL3(int idL3) {
		this.idL3 = idL3;
		return this;
	}
	public int getIdL4() {
		return idL4;
	}
	public MxEleNetwork setIdL4(int idL4) {
		this.idL4 = idL4;
		return this;
	}
	@Override
	public String toString() {
		return "MxGraphTchnNetwork [id=" + getId() + ", id2=" + id2 + ", style=" + style + ", content=" + content
				+ ", xSymbol=" + xSymbol + ", ySymbol=" + ySymbol + "]";
	}

	@Override
	public String toXmlString() {
		return 
				createCircle(xSymbol, ySymbol, CIRCLES_DIAMETER, idC1) + 
				createCircle(xSymbol + 10, ySymbol, CIRCLES_DIAMETER, idC2) +
				createCircle(xSymbol - 3, ySymbol + 8, CIRCLES_DIAMETER, idC3) +
				createCircle(xSymbol + 7, ySymbol + 8, CIRCLES_DIAMETER, idC4)  +
				createLine(xSymbol + CIRCLES_DIAMETER, ySymbol + CIRCLES_DIAMETER / 2, xSymbol + 10, ySymbol + CIRCLES_DIAMETER / 2, idL1 ) +
				createLine(xSymbol - 3 + CIRCLES_DIAMETER, ySymbol + 8 + CIRCLES_DIAMETER / 2, xSymbol + 7, ySymbol + 8 + CIRCLES_DIAMETER / 2, idL2 ) +
				createLine(xSymbol + CIRCLES_DIAMETER / 2, ySymbol + CIRCLES_DIAMETER , xSymbol - 3 + CIRCLES_DIAMETER / 2, ySymbol + 8, idL3 ) +
				createLine(xSymbol + 10 + CIRCLES_DIAMETER / 2, ySymbol + CIRCLES_DIAMETER , xSymbol + 7 + CIRCLES_DIAMETER / 2, ySymbol + 8, idL4 );
	}

}
