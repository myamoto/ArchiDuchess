package org.toolup.archi.io.archi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import org.toolup.archi.business.archimate.AbstractArchimateElement;
import org.toolup.archi.business.archimate.ArchimateModel;
import org.toolup.archi.business.archimate.ArchimateModelException;
import org.toolup.archi.business.archimate.IFolder;
import org.toolup.archi.business.archimate.IRootFolder;
import org.toolup.archi.business.archimate.relation.AbstractElementRelation;
import org.toolup.archi.business.archimate.relation.AccessRelationship;
import org.toolup.archi.business.archimate.view.AbstractChildElemView;
import org.toolup.archi.business.archimate.view.BendPointElementView;
import org.toolup.archi.business.archimate.view.ChildElementView;
import org.toolup.archi.business.archimate.view.DiagramModelView;
import org.toolup.archi.business.archimate.view.GroupElementView;
import org.toolup.archi.business.archimate.view.IViewParent;
import org.toolup.archi.business.archimate.view.NoteElementView;
import org.toolup.archi.business.archimate.view.SourceConnectionElementView;
import org.toolup.archi.io.archi.xmlparser.ModelParser;

public class ArchiIO {

	private ArchiIO() {
		//static class
	}

	public static void write(ArchimateModel archimateModel, String filePath) throws ArchimateIOException{
		try {
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.newDocument();

			Element rootXml = document.createElement("archimate:model");
			document.appendChild(rootXml);

			rootXml.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			rootXml.setAttribute("xmlns:archimate", "http://www.archimatetool.com/archimate");
			rootXml.setAttribute("name", archimateModel.getName());
			rootXml.setAttribute("id", archimateModel.getId());
			rootXml.setAttribute("version", archimateModel.getVersion());

			for (IRootFolder<? extends AbstractArchimateElement> rootFolder : archimateModel.getRootFolderList()) {
				Element rootFolderXml = document.createElement("folder");
				rootFolderXml.setAttribute("name", rootFolder.getName());
				rootFolderXml.setAttribute("id", rootFolder.getId());
				rootFolderXml.setAttribute("type", rootFolder.getType());

				appendChildren(archimateModel, document, rootFolder, rootFolderXml);
				rootXml.appendChild(rootFolderXml);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);

			File outputFile = new File(filePath);
			outputFile.getParentFile().mkdirs();
			outputFile.createNewFile();
			StreamResult result = new StreamResult(outputFile);
			transformer.transform(source, result);
		}catch(IOException | ParserConfigurationException | TransformerException e) {
			throw new ArchimateIOException("failed writing archimate model", e);
		}
	}

	private static void appendChildren(ArchimateModel archimateModel, Document document, IFolder<? extends AbstractArchimateElement> folder, Element folderEle) throws ArchimateIOException {
		for (IFolder<? extends AbstractArchimateElement> subFolder : folder.getFolderList()) {
			Element subFolderXml = document.createElement("folder");
			subFolderXml.setAttribute("name", subFolder.getName());
			subFolderXml.setAttribute("id", subFolder.getId());
			folderEle.appendChild(subFolderXml);
			appendChildren(archimateModel, document, subFolder, subFolderXml);
		}

		for (AbstractArchimateElement element : folder.getElementList()) {
			Element elementXml = document.createElement("element");


			if(element instanceof AbstractElementRelation) {

				elementXml.setAttribute("xsi:type", element.getXSIType());
				elementXml.setAttribute("id", element.getId());
				elementXml.setAttribute("source", ((AbstractElementRelation) element).getSource().getId());
				elementXml.setAttribute("target", ((AbstractElementRelation) element).getTarget().getId());
				if(element instanceof AccessRelationship) {
					elementXml.setAttribute("accessType", ((AccessRelationship) element).getAccessTypeStr());
				}
			} else {
				elementXml.setAttribute("xsi:type", element.getXSIType());
				elementXml.setAttribute("name", element.getName());
				elementXml.setAttribute("id", element.getId());
				if(element.hasDocumentation()) {
					Element documentationXml = document.createElement("documentation");
					documentationXml.setTextContent(element.getDocumentation());
					elementXml.appendChild(documentationXml);
				}
				if(element.hasCustomValues()) {
					for (Entry<String, String> entry : element.getCustomMap().entrySet()) {
						Element propertyXml = document.createElement("property");
						propertyXml.setAttribute("key", entry.getKey());
						propertyXml.setAttribute("value", entry.getValue());
						elementXml.appendChild(propertyXml);
					}
				}

				if(element instanceof DiagramModelView) {
					appendChildrenRecur(archimateModel, document, ((DiagramModelView)element).getChildList(), elementXml);
				}
			}
			folderEle.appendChild(elementXml);
		}
	}

	private static void appendChildrenRecur(ArchimateModel archimateModel, Document document
			, List<AbstractChildElemView> childList, Element parentXml) throws ArchimateIOException {
		for (AbstractChildElemView childElem : childList) {

			Element childXml = document.createElement("child");
			childXml.setAttribute("xsi:type", childElem.getXSIType());
			childXml.setAttribute("id", childElem.getId());
			

			if(childElem.getName() != null) {
				childXml.setAttribute("name", childElem.getName());
			}
				
			if(childElem.hasType()) {
				childXml.setAttribute("type", childElem.getType());
			}
			parentXml.appendChild(childXml);

			Element boundsXml = document.createElement("bounds");
			if(childElem.getxRect() != null)
				boundsXml.setAttribute("x", String.valueOf(childElem.getxRect()));
			if(childElem.getyRect() != null)
				boundsXml.setAttribute("y", String.valueOf(childElem.getyRect()));
			if(childElem.getWidthRect() != null)
				boundsXml.setAttribute("width", String.valueOf(childElem.getWidthRect()));
			if(childElem.getHeightRect() != null)
				boundsXml.setAttribute("height", String.valueOf(childElem.getHeightRect()));

			childXml.appendChild(boundsXml);

			for (SourceConnectionElementView sourceCon : childElem.getSourceConList()) {
				Element sourceConXml = document.createElement("sourceConnection");
				if(sourceCon.getXSIType() != null) sourceConXml.setAttribute("xsi:type", sourceCon.getXSIType());
				if(sourceCon.getArchimateRelationship() != null) sourceConXml.setAttribute("archimateRelationship", sourceCon.getArchimateRelationship().getId());
				sourceConXml.setAttribute("id", sourceCon.getId());
				sourceConXml.setAttribute("source", sourceCon.getSource().getId());
				sourceConXml.setAttribute("target", sourceCon.getTarget().getId());
				childXml.appendChild(sourceConXml);

				for (BendPointElementView bendPoint : sourceCon.getBendPointList()) {
					Element bendPointXml = document.createElement("bendpoint");
					bendPointXml.setAttribute("startX", String.valueOf(bendPoint.getStartX()));
					bendPointXml.setAttribute("startY", String.valueOf(bendPoint.getStartY()));
					bendPointXml.setAttribute("endX", String.valueOf(bendPoint.getEndX()));
					bendPointXml.setAttribute("endY", String.valueOf(bendPoint.getEndY()));

					sourceConXml.appendChild(bendPointXml);
				}
			}
			if(childElem instanceof NoteElementView) {
				String content = ((NoteElementView)childElem).getContent();
				if(content != null && !"".equals(content)) {
					Element contentEle = document.createElement("content");
					contentEle.setTextContent(content);
					childXml.appendChild(contentEle);
				}
			}else if(childElem instanceof ChildElementView) {
				childXml.setAttribute("archimateElement", ((ChildElementView)childElem).getArchimateElement() == null ? null : ((ChildElementView)childElem).getArchimateElement().getId());
			}
			
			if(childElem instanceof IViewParent) {
				String targetConStr = archimateModel.getTargetConnectionsListStr((IViewParent)childElem);
				if(!targetConStr.isEmpty())
					childXml.setAttribute("targetConnections", targetConStr);
				
				appendChildrenRecur(archimateModel, document, ((IViewParent)childElem).getChildList(), childXml);
			}
			
			
		}
	}

	public static ArchimateModel read(String filePath) throws  ArchimateModelException {
		if(filePath == null)
			return null;
		File modelFile = new File(filePath);
		if(!modelFile.exists() || !modelFile.canRead())
			throw new ArchimateModelException(String.format("Archimate File is not readable : %s", filePath));
		try(InputStream is = new FileInputStream(modelFile)){
			return read(is);
		} catch (IOException e) {
			throw new ArchimateModelException(e);
		}
	}

	public static ArchimateModel read(InputStream isArchimateModel) throws ArchimateModelException {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new ArchimateModelException(e);
		}
		Document document;
		try {
			document = builder.parse(isArchimateModel);
		} catch (SAXException | IOException e) {
			throw new ArchimateModelException(e);
		}
		final Element racine = document.getDocumentElement();

		return ModelParser.parse(racine);
	}



}
