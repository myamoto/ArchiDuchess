package org.toolup.archi.business.mxgraph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class MxEleSourceCon extends AbstractMxEle{
		
		private String style;
		private int idSrcPt;
		private int idTgtPt;
		private int xSrc;
		private int ySrc;
		private int xTgt;
		private int yTgt;
		private final List<Point> pointList;
		
		public MxEleSourceCon() {
			pointList = new ArrayList<>();
		}
		
		@Override
		public MxEleSourceCon setId(int id) {
			super.setId(id);
			return this;
		}
		
		
		public String getStyle() {
			return style;
		}
		public MxEleSourceCon setStyle(String style) {
			this.style = style;
			return this;
		}
		public int getIdSrcPt() {
			return idSrcPt;
		}
		public MxEleSourceCon setIdSrcPt(int idSrcPt) {
			this.idSrcPt = idSrcPt;
			return this;
		}
		public int getIdTgtPt() {
			return idTgtPt;
		}
		public MxEleSourceCon setIdTgtPt(int idTgtPt) {
			this.idTgtPt = idTgtPt;
			return this;
		}
		public int getxSrc() {
			return xSrc;
		}
		public MxEleSourceCon setxSrc(int xSrc) {
			this.xSrc = xSrc;
			return this;
		}
		public int getySrc() {
			return ySrc;
		}
		public MxEleSourceCon setySrc(int ySrc) {
			this.ySrc = ySrc;
			return this;
		}
		public int getxTgt() {
			return xTgt;
		}
		public MxEleSourceCon setxTgt(int xTgt) {
			this.xTgt = xTgt;
			return this;
		}
		public int getyTgt() {
			return yTgt;
		}
		public MxEleSourceCon setyTgt(int yTgt) {
			this.yTgt = yTgt;
			return this;
		}
		public List<Point> getPointList() {
			return pointList;
		}
		public MxEleSourceCon setPointList(List<Point> pointList) {
			this.pointList.clear();
			if(pointList != null) this.pointList.addAll(pointList);
			return this;
		}
	
		public String toXmlString() {
			StringBuilder pointsXmlElemSb = new StringBuilder();
			if(!getPointList().isEmpty()) {
				pointsXmlElemSb.append("<Array as=\"points\">\n");
				for (Point p : getPointList()) {
					pointsXmlElemSb.append(String.format("<mxPoint x=\"%d\" y=\"%d\"/>%n", p.x, p.y));
				}
				pointsXmlElemSb.append("</Array>");
			}
			return String.format(
					"		<mxCell id=\"%d\" value=\"\" style=\"%s\" edge=\"1\" parent=\"1\" source=\"%d\" target=\"%d\">%n" + 
							"			<mxGeometry width=\"50\" height=\"50\" relative=\"1\" as=\"geometry\">%n" + 
							"				<mxPoint x=\"%d\" y=\"%d\" as=\"sourcePoint\"/>%n" + 
							"				<mxPoint x=\"%d\" y=\"%d\" as=\"targetPoint\"/>%n" + 
							"				%s%n" +
							"			</mxGeometry>%n" + 
							"		</mxCell>%n"
							, getId()
							, getStyle()
							, getIdSrcPt()
							, getIdTgtPt()
							, getxSrc()
							, getySrc()
							, getxTgt()
							, getyTgt()
							, pointsXmlElemSb.toString());
		}
	}