package org.toolup.archi.business.archimate.relation;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import org.toolup.archi.business.archimate.AbstractArchimateElement;
import org.toolup.archi.business.archimate.IElement;
import org.toolup.archi.business.archimate.view.AbstractChildElemView;
import org.toolup.archi.business.archimate.view.AbstractElementView;
import org.toolup.archi.business.archimate.view.BendPointElementView;
import org.toolup.archi.business.mxgraph.MxEleSourceCon;
import org.toolup.archi.io.mxgraph.MxGraphConverterException;

public abstract class AbstractElementRelation extends AbstractArchimateElement{

	private IElement source;
	private IElement target;

	public IElement getSource() {
		return source;
	}
	public void setSource(IElement source) {
		this.source = source;
	}
	public IElement getTarget() {
		return target;
	}
	public void setTarget(IElement target) {
		this.target = target;
	}


	abstract String getMxGraphLineStyle();
	//	public String getMxGraphLineStyle() {
	//		return "endArrow=classic;html=1;rounded=1;";
	//	}


	public List<Point> getMxGraphElementPoints(AbstractElementView src, AbstractElementView tgt, List<BendPointElementView> bendPs)throws MxGraphConverterException {
		Rectangle boundsSrc = getBounds(src);
		Rectangle boundstgt = getBounds(tgt);
		if(boundsSrc == null || boundstgt == null) return new ArrayList<>();
		Point srcIntersecPoint = getIntersecPoint(boundsSrc, boundstgt);
		Point targetIntersecPoint = getIntersecPoint(boundstgt, boundsSrc);
		if(srcIntersecPoint == null || targetIntersecPoint == null) return new ArrayList<>();

		Point srcCenter = new Point(boundsSrc.x + boundsSrc.width / 2, boundsSrc.y + boundsSrc.height / 2);

		if(bendPs.isEmpty()) return new ArrayList<>();
		List<Point> pointList = new ArrayList<>();
		for (BendPointElementView bendP : bendPs) {
			int x = srcCenter.x + bendP.getStartX();
			int y = srcCenter.y + bendP.getStartY();
			pointList.add(new Point(x, y));
		}
		return pointList;
	}


	public MxEleSourceCon createMxCell(AbstractElementView src, AbstractElementView tgt, List<BendPointElementView> bendPs, String color) throws MxGraphConverterException {
		Rectangle boundsSrc = getBounds(src);
		Rectangle boundstgt = getBounds(tgt);
		if(boundsSrc == null || boundstgt == null) return null;
		Point srcIntersecPoint = getIntersecPoint(boundsSrc, boundstgt);
		Point targetIntersecPoint = getIntersecPoint(boundstgt, boundsSrc);
		if(srcIntersecPoint == null || targetIntersecPoint == null)
			return null;
		List<Point> pointList = getMxGraphElementPoints(src, tgt, bendPs);

		MxEleSourceCon res = new MxEleSourceCon()
				.setId(getIdMxgraph())
				.setIdSrcPt(src.getIdMxgraph())
				.setIdTgtPt(tgt.getIdMxgraph())
				.setStyle(getStyle(pointList, boundsSrc, boundstgt, color))
				.setxSrc(srcIntersecPoint.x)
				.setySrc(srcIntersecPoint.y)
				.setxTgt(targetIntersecPoint.x)
				.setyTgt(targetIntersecPoint.y)
				.setPointList(pointList);
		res.setArchiElem(this);
		return res;
	}

	private String getStyle(List<Point> pointList, Rectangle boundsSrc, Rectangle boundstgt, String color) {
		String result = getMxGraphLineStyle();

		if(color == null || color.isEmpty()) color = "#000000"; 
		if(color != null)result += String.format("strokeColor=%s;", color);
		if(pointList.isEmpty()) return result;
		Point startP = pointList.get(0);
		Point endP = pointList.get(pointList.size()-1);
		float exitX;
		if(startP.x > boundsSrc.x + boundsSrc.width) {
			exitX = 1;
		}else if(startP.x < boundsSrc.x) {
			exitX = 0;
		}else {
			exitX = ((float)startP.x - boundsSrc.x)/ (float)boundsSrc.width;
		}
		float exitY;
		if(startP.y > boundsSrc.y + boundsSrc.height) {
			exitY = 1;
		}else if(startP.y < boundsSrc.y) {
			exitY = 0;
		}else {
			exitY = ((float)startP.y - boundsSrc.y)/ (float)boundsSrc.height;
		}
		float entryX;
		//endP : [x=585,y=420]
		//[x=852,y=492,width=85,height=25]
		if(endP.x > boundstgt.x + boundstgt.width) {
			entryX = 1;
		}else if(endP.x < boundstgt.x) {
			entryX = 0;
		}else {
			entryX = ((float)endP.x - boundstgt.x)/ (float)boundstgt.width;
		}
		float entryY;
		//y = 399
		//boundstgt.y = 677
		//boundstgt.height =
		if(endP.y > boundstgt.y + boundstgt.height) {
			entryY = 1;
		}else if(endP.y < boundstgt.y) {
			entryY = 0;
		}else {
			entryY = ((float)endP.y- boundstgt.y)/ (float)boundstgt.height;
		}
		return result + String.format(java.util.Locale.US, "exitX=%.2f;exitY=%.2f;entryX=%.2f;entryY=%.2f;", exitX, exitY, entryX, entryY);
	}



	private Rectangle getBounds(AbstractElementView item) {
		if(!(item instanceof AbstractChildElemView))return null;
		AbstractChildElemView elem = (AbstractChildElemView)item;
		return new Rectangle(elem.getxMGraph(), elem.getyMGraph(), elem.getWidthRect(), elem.getHeightRect());
	}

	protected Point getIntersecPoint(Rectangle r, Point p) {
		if(p.x >= r.x && p.x <= r.x + r.width) {
			return getIntersecPoint(new Rectangle(p.x, r.y, 0, r.height), new Rectangle(p.x, p.y, 0, 0));
		}else if(p.y >= r.y && p.y <= r.y + r.height) {
			return getIntersecPoint(new Rectangle(r.x, p.y, r.width, 0), new Rectangle(p.x, p.y, 0, 0));
		}else {
			return getIntersecPoint(new Rectangle(r.x, r.y, r.width, r.height), new Rectangle(p.x, p.y, 0, 0));
		}
	}

	protected Point getIntersecPoint(Rectangle target, Rectangle o2) {
		Line2D lineBetween = new Line2D.Float(
				target.x + target.width / 2
				, target.y + target.height / 2
				, o2.x + o2.width / 2
				, o2.y + o2.height / 2);

		//on teste si le segment entre les deux rectangles coupe une des bordures...
		Line2D lineIntersect = new Line2D.Float(
				target.x
				, target.y
				, target.x + target.width
				, target.y);
		if(!linesIntersect(lineIntersect, lineBetween))
			lineIntersect = new Line2D.Float(
					target.x + target.width
					, target.y
					, target.x + target.width
					, target.y + target.height);
		if(!linesIntersect(lineIntersect, lineBetween))
			lineIntersect = new Line2D.Float(
					target.x + target.width
					, target.y + target.height
					, target.x
					, target.y + target.height);
		if(!linesIntersect(lineIntersect, lineBetween))
			lineIntersect = new Line2D.Float(
					target.x
					, target.y + target.height
					, target.x
					, target.y);

		//si le segment entre les deux rectangles coupe une des bordures...
		if(linesIntersect(lineIntersect, lineBetween))
			//renvoyer l'intersection des deux segments
			return new Point((int)Math.round((lineIntersect.getX1() + lineIntersect.getX2())/2)
					, (int)Math.round((lineIntersect.getY1() + lineIntersect.getY2())/2));
		return null;
	}


	private boolean linesIntersect(Line2D line1, Line2D line2) {
		return line1.intersectsLine(line2);
	}

	public String getDefaultFontColor() {
		return null;
	}

	public String getDefaultFillColor() {
		return null;
	}


	@Override
	public String toString() {
		return "AbstractElementRelation [source=" + source + ", target=" + target + "] extends " + super.toString();
	}


}

