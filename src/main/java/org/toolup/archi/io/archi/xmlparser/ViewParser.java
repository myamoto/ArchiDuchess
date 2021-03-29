package org.toolup.archi.io.archi.xmlparser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.toolup.archi.business.archimate.ArchimateModel;
import org.toolup.archi.business.archimate.ArchimateModelException;
import org.toolup.archi.business.archimate.IElement;
import org.toolup.archi.business.archimate.IFolder;
import org.toolup.archi.business.archimate.relation.AbstractElementRelation;
import org.toolup.archi.business.archimate.relation.NoteRelationship;
import org.toolup.archi.business.archimate.view.AbstractChildElemView;
import org.toolup.archi.business.archimate.view.AbstractElementView;
import org.toolup.archi.business.archimate.view.ChildElementView;
import org.toolup.archi.business.archimate.view.DiagramModelReference;
import org.toolup.archi.business.archimate.view.DiagramModelView;
import org.toolup.archi.business.archimate.view.FolderView;
import org.toolup.archi.business.archimate.view.GroupElementView;
import org.toolup.archi.business.archimate.view.IViewParent;
import org.toolup.archi.business.archimate.view.NoteElementView;
import org.toolup.archi.business.archimate.view.RootFolderView;
import org.toolup.archi.business.archimate.view.SourceConnectionElementView;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ViewParser implements IArchiRootFolderParser<RootFolderView>{

	private static final String CHILD_XSI = "child";

	private static final  String SOURCE_CON_ELEM = "sourceConnection";

	public RootFolderView parse(Element folderViewRootElement, ArchimateModel model) throws ArchimateModelException {
		if(folderViewRootElement == null) 
			return null;

		RootFolderView result = new RootFolderView();
		ElementParser.setValues(result, folderViewRootElement);

		Map<String, String> viewNames = new HashMap<>();
		parseViewNamesRecur(folderViewRootElement, viewNames);

		for(Node child = folderViewRootElement.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("element".equals(((Element)child).getNodeName())){
				try {
					result.addElement(parseElementView((Element)child, model, viewNames));
				}catch(ArchimateModelException e) {
					throw new ArchimateModelException(String.format("view %s", ((Element)child).getAttribute("name")), e);
				}
			}else if("folder".equals(((Element)child).getNodeName())) {
				result.addFolder(parseFolderView((Element)child, model, viewNames));
			}else{
				throw new ArchimateModelException(String.format("Unsupported view element : <%s", ((Element)child).getNodeName()));
			}
		}

		updateTargetRecur(result);
		return result;
	}

	private static void updateTargetRecur(IFolder<AbstractElementView> viewFolder) {
		for (IFolder<AbstractElementView> subFolder : viewFolder.getFolderList()) {
			updateTargetRecur(subFolder);
		}
		for (AbstractElementView view : viewFolder.getElementList()) {
			updateTargetRecur(((DiagramModelView)view).getChildList(), (DiagramModelView)view);
		}
	}

	private static void updateTargetRecur(List<AbstractChildElemView> childList, DiagramModelView view) {
		for (AbstractChildElemView child : childList) {
			for (SourceConnectionElementView sourceCon : child.getSourceConList()) {
				sourceCon.setTarget(view.findChildRecur(sourceCon.getTargetId()));
			}
		}
		for (AbstractChildElemView child : childList) {
			if(child instanceof IViewParent) {
				updateTargetRecur(((IViewParent)child).getChildList(), view);
			}
		}
	}

	private static FolderView parseFolderView(Element folderViewElement, ArchimateModel model, Map<String, String> viewNames) throws ArchimateModelException {

		if(folderViewElement == null) 
			return null;

		FolderView result = new FolderView();
		ElementParser.setValues(result, folderViewElement);



		for(Node child = folderViewElement.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("element".equals(((Element)child).getNodeName())){
				result.addElement(parseElementView((Element)child, model, viewNames));
			}else if("folder".equals(((Element)child).getNodeName())) {
				result.addFolder(parseFolderView((Element)child, model, viewNames));
			}else{
				throw new ArchimateModelException(String.format("Unsupported view element : <%s", ((Element)child).getNodeName()));
			}
		}
		return result;
	}

	private static void parseViewNamesRecur(Element folder, Map<String, String> viewNames) throws ArchimateModelException {
		for(Node child = folder.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;


			if("element".equals(((Element)child).getNodeName())){
				String childXsiType = ((Element)child).getAttribute("xsi:type");
				if(DiagramModelView.XSITYPE.equals(childXsiType)) {
					viewNames.put(((Element)child).getAttribute("id"), ((Element)child).getAttribute("name"));
				}
			}else if("folder".equals(((Element)child).getNodeName())) {
				parseViewNamesRecur((Element)child, viewNames);
			}else{
				throw new ArchimateModelException(String.format("Unsupported view element : <%s", ((Element)child).getNodeName()));
			}
		}
	}

	private static AbstractElementView parseElementView(Element viewElem, ArchimateModel model, Map<String, String> viewNames) throws ArchimateModelException {
		if(viewElem == null)
			return null;
		String childXsiType = viewElem.getAttribute("xsi:type");

		AbstractElementView elemView;
		if(DiagramModelView.XSITYPE.equals(childXsiType)) {
			try {
				elemView = parseDiagramModel(viewElem, model, viewNames);
			}catch(ArchimateModelException e) {
				throw new ArchimateModelException(String.format("view < %s >", viewElem.getAttribute("name")), e);
			}

		}else {
			throw new ArchimateModelException(String.format("Unsupported view xsi:type : %s", childXsiType));
		}

		ElementParser.setValues(elemView, viewElem);

		return elemView;
	}

	private static AbstractElementView parseDiagramModel(Element dmXmlElem, ArchimateModel model, Map<String, String> viewNames) throws ArchimateModelException {
		if(dmXmlElem == null)
			return null;
		DiagramModelView result = new DiagramModelView();
		ElementParser.setValues(result, dmXmlElem);
		for(Node child = dmXmlElem.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if(CHILD_XSI.equals(((Element)child).getNodeName())){
				result.addElement(parseDiagramModelElem(result.getName(), (Element)child, model, 0, 0, viewNames));
			}else if(!ElementParser.isPossibleNodeName(((Element)child).getNodeName())){
				throw new ArchimateModelException(String.format("Unsupported %s element : <%s", DiagramModelView.XSITYPE, ((Element)child).getNodeName()));
			}
		}
		return result;
	}

	private static AbstractChildElemView parseDiagramModelElem(String viewName, Element xmlEle, ArchimateModel model, int xRelativeTo, int yRelativeTo
			, Map<String, String> viewNames) throws ArchimateModelException {
		if(xmlEle == null)
			return null;
		String dmChildXsiType = xmlEle.getAttribute("xsi:type");
		AbstractChildElemView result = null;
		if(NoteElementView.XSITYPE.equals(dmChildXsiType)) {
			result = parseNoteElementView(xmlEle, xRelativeTo, yRelativeTo, dmChildXsiType);
		}else if(GroupElementView.XSITYPE.equals(dmChildXsiType)) {
			//			result = parseChildElementView(viewName, xmlEle, model, xRelativeTo, yRelativeTo, viewNames);
			result = parseGroupElementView(viewName, xmlEle, model, xRelativeTo, yRelativeTo, viewNames);
		}else if(ChildElementView.XSITYPE.equals(dmChildXsiType)) {
			result = parseChildElementView(viewName, xmlEle, model, xRelativeTo, yRelativeTo, viewNames);
		}else if(DiagramModelReference.XSITYPE.equals(dmChildXsiType)) {
			Rectangle bounds = getBounds(xmlEle);
			if(bounds == null) throw new ArchimateModelException(String.format("no bounds element found for view child with id %s", xmlEle.getAttribute("id")));
			String vName = viewNames.get(xmlEle.getAttribute("model"));
			result = new DiagramModelReference(vName != null ? vName : ""
				, bounds.getX()
				, bounds.getY()
				, bounds.getWidth()
				, bounds.getHeight());

		}else {
			throw new ArchimateModelException(String.format("Unsupported archimate:ArchimateDiagramModel child xsi:type : %s", dmChildXsiType));
		}
		result.setFillColor(xmlEle.getAttribute("fillColor"));
		result.setFontColor(xmlEle.getAttribute("fontColor"));
		ElementParser.setValues(result, xmlEle);

		for(Node child = xmlEle.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if(SOURCE_CON_ELEM.equals(((Element)child).getNodeName())) {
				SourceConnectionElementView sourceCon = parseSourceCon(((Element)child), model);

				sourceCon.setSource(result);
				result.addSourceCon(sourceCon);
			}
		}

		return result;
	}

	private static NoteElementView parseNoteElementView(Element xmlEle, int xRelativeTo, int yRelativeTo, String xsiType) throws ArchimateModelException {
		Rectangle bounds = getBounds(xmlEle);
		if(bounds == null) throw new ArchimateModelException(String.format("no bounds element found for view child with id %s", xmlEle.getAttribute("id")));

		String content = null;
		for(Node child = xmlEle.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("content".equals(((Element)child).getNodeName())) {
				content = ((Element)child).getTextContent();
			}
		}
		return new NoteElementView(content
				, bounds.getX()
				, bounds.getY()
				, bounds.getWidth()
				, bounds.getHeight());
	}

	private static GroupElementView parseGroupElementView(String viewName, Element xmlEle, ArchimateModel model, int xRelativeTo, int yRelativeTo, Map<String, String> viewNames) throws ArchimateModelException {
		for(Node child = xmlEle.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if(!"feature".equals(((Element)child).getNodeName())
					&& !"bounds".equals(((Element)child).getNodeName()) &&
					!CHILD_XSI.equals(((Element)child).getNodeName()) &&
					!SOURCE_CON_ELEM.equals(((Element)child).getNodeName())){
				throw new ArchimateModelException(String.format("Unsupported view child element : %s", ((Element)child).getNodeName()));
			}
		}

		Rectangle bounds = getBounds(xmlEle);
		if(bounds == null)
			throw new ArchimateModelException(String.format("no bounds element found for view child with id %s", xmlEle.getAttribute("id")));

		GroupElementView result = new GroupElementView(xmlEle.getAttribute("name")
				, bounds.getX()
				, bounds.getY()
				, bounds.getWidth()
				, bounds.getHeight());
		result.setType(xmlEle.getAttribute("type"));
		result.setAbsoluteX(xRelativeTo + (result.getxRect() != null ? result.getxRect() : 0));
		result.setAbsoluteY(yRelativeTo + (result.getyRect() != null ? result.getyRect() : 0));
		for(Node child = xmlEle.getFirstChild(); child != null; child = child.getNextSibling()) {

			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("feature".equals(((Element)child).getNodeName())){
				//TODO
			}else if(CHILD_XSI.equals(((Element)child).getNodeName())){
				result.addElement(parseDiagramModelElem(viewName, (Element)child, model, result.getAbsoluteX(), result.getAbsoluteY(), viewNames));
			}
		}
		return result;
	}

	private static ChildElementView parseChildElementView(String viewName, Element dmChildXmlElem, ArchimateModel model, int xRelativeTo, int yRelativeTo, Map<String, String> viewNames) throws ArchimateModelException {
		for(Node child = dmChildXmlElem.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if(!"feature".equals(((Element)child).getNodeName())
					&& !"bounds".equals(((Element)child).getNodeName()) &&
					!CHILD_XSI.equals(((Element)child).getNodeName()) &&
					!SOURCE_CON_ELEM.equals(((Element)child).getNodeName())){
				throw new ArchimateModelException(String.format("Unsupported view child element : %s", ((Element)child).getNodeName()));
			}
		}

		Rectangle bounds = getBounds(dmChildXmlElem);
		if(bounds == null)
			throw new ArchimateModelException(String.format("no bounds element found for view child with id %s", dmChildXmlElem.getAttribute("id")));

		IElement elem;
		String ref = dmChildXmlElem.getAttribute("archimateElement");
		if(ref != null && !ref.isEmpty()) {
			elem = model.findArchimateElement(ref);
			if(elem == null )
				throw new ArchimateModelException(viewName + " - missing object for " + dmChildXmlElem.getAttribute("id") + " archimateElement : "+ ref);
		} else {
			elem = null;
		}
		ChildElementView result = new ChildElementView(elem
				, bounds.getX()
				, bounds.getY()
				, bounds.getWidth()
				, bounds.getHeight());
		result.setType(dmChildXmlElem.getAttribute("type"));
		result.setAbsoluteX(xRelativeTo + (result.getxRect() != null ? result.getxRect() : 0));
		result.setAbsoluteY(yRelativeTo + (result.getyRect() != null ? result.getyRect() : 0));
		for(Node child = dmChildXmlElem.getFirstChild(); child != null; child = child.getNextSibling()) {

			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("feature".equals(((Element)child).getNodeName())){
				//TODO
			}else if(CHILD_XSI.equals(((Element)child).getNodeName())){
				((ChildElementView)result).addElement(parseDiagramModelElem(viewName, (Element)child, model, result.getAbsoluteX(), result.getAbsoluteY(), viewNames));
			}
		}
		return result;
	}

	private static Rectangle getBounds(Element dmChildXmlElem) {
		Element boundsElement = null;
		for(Node child = dmChildXmlElem.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("bounds".equals(((Element)child).getNodeName())){
				boundsElement = (Element)child;
			}
		}
		if(boundsElement == null)
			return null;
		return new Rectangle(getIntValue(boundsElement.getAttribute("x"))
				, getIntValue(boundsElement.getAttribute("y"))
				, getIntValue(boundsElement.getAttribute("width"))
				, getIntValue(boundsElement.getAttribute("height")));
	}

	private static SourceConnectionElementView parseSourceCon(Element sourceConXmlEle, ArchimateModel model) throws ArchimateModelException {
		if(sourceConXmlEle == null)
			return null;

		if(sourceConXmlEle.hasAttribute("xsi:type") && !SourceConnectionElementView.XSITYPE_CON.equals(sourceConXmlEle.getAttribute("xsi:type"))) {
			throw new ArchimateModelException(String.format("Unsupported sourceConnection for %s xsi:type : %s"
					, sourceConXmlEle.getAttribute("id")
					, sourceConXmlEle.getAttribute("xsi:type")));
		}

		SourceConnectionElementView result = new SourceConnectionElementView();
		if(sourceConXmlEle.hasAttribute("xsi:type"))
			result.setXSITYPE(sourceConXmlEle.getAttribute("xsi:type"));
		ElementParser.setValues(result, sourceConXmlEle);

		result.setColor(sourceConXmlEle.getAttribute("lineColor"));

		result.setArchimateRelationship((AbstractElementRelation)model.findArchimateRelationship(sourceConXmlEle.getAttribute("archimateRelationship")));
		if(result.getArchimateRelationship() == null) {
			result.setArchimateRelationship(new NoteRelationship());
		}
		result.setTargetId(sourceConXmlEle.getAttribute("target"));

		for(Node child = sourceConXmlEle.getFirstChild(); child != null; child = child.getNextSibling()) {
			if(child.getNodeType() != Node.ELEMENT_NODE) 
				continue;
			if("bendpoint".equals(((Element)child).getNodeName())){
				result.addBendPoint(
						getIntValue(((Element)child).getAttribute("startX"))
						, getIntValue(((Element)child).getAttribute("startY"))
						, getIntValue(((Element)child).getAttribute("endX"))
						, getIntValue(((Element)child).getAttribute("endY")));
			}else if("documentation".equals(((Element)child).getNodeName())) {
				result.setDocumentation(((Element)child).getNodeValue());
			}else if(SOURCE_CON_ELEM.equals(((Element)child).getNodeName())) {
				SourceConnectionElementView sourceCon = parseSourceCon(((Element)child), model);

				sourceCon.setSource(result);
				result.addSourceCon(sourceCon);
			}else {
				throw new ArchimateModelException(String.format("Unsupported %s element : <%s", sourceConXmlEle.getAttribute("xsi:type"), ((Element)child).getNodeName()));
			}
		}
		return result;
	}

	private static Integer getIntValue(String intString) {
		return intString.isEmpty() ? null : Integer.parseInt(intString);
	}

	private static class Rectangle{
		private Integer x;
		private Integer y;
		private Integer width;
		private Integer height;

		public Rectangle(Integer x, Integer y, Integer width, Integer height) {
			super();
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		public Integer getX() {
			return x;
		}

		public Integer getY() {
			return y;
		}

		public Integer getWidth() {
			return width;
		}

		public Integer getHeight() {
			return height;
		}
	}

	@Override
	public String getType() {
		return RootFolderView.TYPE;
	}

}
