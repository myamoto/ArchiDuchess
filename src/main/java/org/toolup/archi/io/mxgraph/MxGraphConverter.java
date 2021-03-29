package org.toolup.archi.io.mxgraph;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toolup.archi.business.archimate.ArchimateModel;
import org.toolup.archi.business.archimate.ArchimateModelException;
import org.toolup.archi.business.archimate.view.AbstractChildElemView;
import org.toolup.archi.business.archimate.view.DiagramModelView;
import org.toolup.archi.business.archimate.view.IViewParent;
import org.toolup.archi.business.archimate.view.SourceConnectionElementView;
import org.toolup.archi.business.mxgraph.IMxElemArchi;
import org.toolup.archi.business.mxgraph.MxEleSourceCon;
import org.toolup.archi.io.archi.ArchiIO;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.mxgraph.io.mxCodec;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxGraphHandler;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.util.png.mxPngEncodeParam;
import com.mxgraph.util.png.mxPngImageEncoder;
import com.mxgraph.view.mxGraph;

public class MxGraphConverter {

	private static Logger logger = LoggerFactory.getLogger(MxGraphConverter.class);

	private MxGraphConverter() {}

	private static ArchimateModel readModel(InputStream archiIs) throws MxGraphConverterException {
		try {
			return ArchiIO.read(archiIs);
		} catch (ArchimateModelException e) {
			throw new MxGraphConverterException(e);
		}
	}

	private static ArchimateModel readModel(String filePath) throws MxGraphConverterException {
		try {
			return ArchiIO.read(filePath);
		} catch (ArchimateModelException e) {
			throw new MxGraphConverterException(e);
		}
	}

	public static byte[] toMxGraph(String archiFilePath, String viewName) throws MxGraphConverterException {
		return readMxGraph(readModel(archiFilePath), viewName);
	}

	public static byte[] toMxGraph(InputStream archiIs, String viewName) throws MxGraphConverterException {
		return readMxGraph(readModel(archiIs), viewName);
	}

	public static byte[] toMxGraphImg(String archiFilePath, String viewName) throws MxGraphConverterException {
		String erXmlString = new String(toMxGraph(archiFilePath, viewName), Charset.forName("utf-8"));

		Document doc = mxXmlUtils.parseXml(erXmlString);
		mxGraph graph = new mxGraph();
		mxCodec codec = new mxCodec(doc);
		codec.decode(doc.getDocumentElement(), graph.getModel());

		mxGraphComponent graphComponent = new mxGraphComponent(graph) {
			private static final long serialVersionUID = 2516639423458462304L;

			protected mxGraphHandler createGraphHandler() {
				return new mxGraphHandler(this) {
					protected void installDragGestureHandler() {
						//Avoid headless exception. Thanks to : https://lkumarjain.blogspot.com/2013/05/using-jgraphx-in-headless-environment.html
					}
				};
			}
		};
		BufferedImage image = mxCellRenderer.createBufferedImage(graphComponent.getGraph(), null, 1, Color.WHITE, graphComponent.isAntiAlias(), null, graphComponent.getCanvas());
		mxPngEncodeParam param = mxPngEncodeParam.getDefaultEncodeParam(image);
		param.setCompressedText(new String[] { "mxGraphModel", erXmlString });

		try(ByteArrayOutputStream bos = new ByteArrayOutputStream()){
			mxPngImageEncoder encoder = new mxPngImageEncoder(bos, param);

			if (image == null) return new byte[0];

			encoder.encode(image);
			return bos.toByteArray();
		} catch (IOException e) {
			throw new MxGraphConverterException(e);
		}
	}

	private static byte[] readMxGraph(ArchimateModel model, String viewName) throws MxGraphConverterException {
		DiagramModelView view = model.findViewName(viewName);
		if(view == null) throw new MxGraphConverterException(String.format("no view named %s in model with ID %s", viewName, model.getId()));
		try(ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			writeMxGraph(view, bos);
			return bos.toByteArray();
		} catch (IOException e) {
			throw new MxGraphConverterException(e);
		}
	}

	public static void toMxGraphFiles(String archimateFilePath, String outputDirPath, boolean overwriteFile) throws IOException, SAXException, ArchimateModelException, MxGraphConverterException {
		try(InputStream archiIS = new FileInputStream(archimateFilePath)){
			toMxGraphFiles(archiIS, outputDirPath, overwriteFile);
		}
	}

	public static void toMxGraphFiles(InputStream archimateIs, String outputDirPath, boolean overwriteFile) throws MxGraphConverterException {
		ArchimateModel model;
		try {
			model = ArchiIO.read(archimateIs);
		} catch (ArchimateModelException e) {
			throw new MxGraphConverterException(e);
		}
		for (DiagramModelView view 	: model.getDiagramModelViewList()) {
			File outputFile = new File(outputDirPath, String.format("%s_%s.xml", cleanFileName(model.getName()), cleanFileName(view.getName())));
			try {
				if(!overwriteFile && !outputFile.createNewFile())
					throw new MxGraphConverterException(String.format("Could not create file %s", outputFile.getAbsolutePath()));
			} catch (IOException e) {
				throw new MxGraphConverterException(e);
			}
			try(FileOutputStream fos = new FileOutputStream(outputFile)) {
				writeMxGraph(view, fos);
			} catch (IOException e) {
				throw new MxGraphConverterException(e);
			}
		}
	}

	private static String cleanFileName(String name) {
		if(name == null ) return null;
		return name.replaceAll("[^éàèa-zA-Z0-9\\.\\-]", "_");
	}

	private static int getMinXRecur(List<AbstractChildElemView> elems) throws MxGraphConverterException {
		int result = Integer.MAX_VALUE;
		for(AbstractChildElemView child : elems) {
			if(child.getxRect() != null && child.getxRect() - ipad < result)
				result = child.getxRect() - ipad;
			for (SourceConnectionElementView sourceCon: child.getSourceConList()) {
				for(Point p : sourceCon.getMxGraphElementPoints()) {
					if( p.x - ipad < result) result = p.x - ipad;
				}
			}
			if(child instanceof IViewParent ) {
				int subMin = getMinXRecur(((IViewParent)child).getChildList());
				if(subMin - ipad < result ) result = subMin - ipad;
			}
		}
		return result;
	}
	private static int getMaxXRecur(List<AbstractChildElemView> elems) throws MxGraphConverterException {
		int result = 0;
		for(AbstractChildElemView child : elems) {
			if(child.getxRect() != null && child.getxRect() + child.getWidthRect() + ipad > result)
				result = child.getxRect() + child.getWidthRect() + ipad;
			for (SourceConnectionElementView sourceCon: child.getSourceConList()) {
				for(Point p : sourceCon.getMxGraphElementPoints()) {
					if(p.x + ipad >  result) result = p.x + ipad;
				}
			}
			if(child instanceof IViewParent ) {
				int subMax = getMaxXRecur(((IViewParent)child).getChildList());
				if(subMax + ipad > result ) result = subMax + ipad;
			}
		}
		return result;
	}

	private static int getMinYRecur(List<AbstractChildElemView> elems) throws MxGraphConverterException {
		int result = Integer.MAX_VALUE;
		for(AbstractChildElemView child : elems) {
			if(child.getyRect() != null && child.getyRect() - ipad < result)
				result = child.getyRect() - ipad;
			for (SourceConnectionElementView sourceCon: child.getSourceConList()) {
				for(Point p : sourceCon.getMxGraphElementPoints()) {
					if( p.y - ipad < result) result = p.y - ipad;
				}
			}
			if(child instanceof IViewParent ) {
				int subMin = getMinYRecur(((IViewParent)child).getChildList());
				if(subMin - ipad < result ) result = subMin - ipad;
			}
		}
		return result;
	}

	private static int getMaxYRecur(List<AbstractChildElemView> elems) throws MxGraphConverterException {
		int result = 0;
		for(AbstractChildElemView child : elems) {
			if(child.getyRect() != null && child.getyRect() + child.getHeightRect() + ipad > result)
				result = child.getyRect() + child.getHeightRect() + ipad;
			for (SourceConnectionElementView sourceCon: child.getSourceConList()) {
				for(Point p : sourceCon.getMxGraphElementPoints()) {
					if(p.y + ipad >  result) result = p.y + ipad;
				}
			}
			if(child instanceof IViewParent ) {
				int subMax = getMaxYRecur(((IViewParent)child).getChildList());
				if(subMax + ipad > result ) result = subMax + ipad;
			}
		}
		return result;
	}

	private static final  int ipad = 0;
	private static void writeMxGraph(DiagramModelView view, OutputStream os) throws IOException, MxGraphConverterException {
		BufferedWriter wr = null;
		try {
			setMXGraphCoordinnatesRecur(view.getChildList(), 0, 0);

			int maxX = getMaxXRecur(view.getChildList());
			int maxY = getMaxYRecur(view.getChildList());
			int minY = getMinYRecur(view.getChildList());
			int minX = getMinXRecur(view.getChildList());

			wr = new BufferedWriter(new OutputStreamWriter(os, Charset.forName("utf-8")));

			String xmlElem = String.format("<mxGraphModel dx=\"0\" dy=\"0\" grid=\"1\" "
					+ "gridSize=\"10\" guides=\"1\" tooltips=\"1\" "
					+ "connect=\"1\" arrows=\"1\" fold=\"1\" page=\"1\" "
					+ "pageScale=\"1\" "
					+ "pageWidth=\"%d\" "
					+ "pageHeight=\"%d\" background=\"#ffffff\">%n",
					maxX - minX, 
					maxY - minY);
			wr.write(xmlElem
					+ "<root>\n"
					+ "		<mxCell id=\"0\"/>\r\n" + 
					"		<mxCell id=\"1\" parent=\"0\"/>");

			setMXGraphCoordinnatesRecur(view.getChildList(), - minX + ipad, - minY + ipad);

			List<IMxElemArchi> cells = writeChildrenRecur(view.getChildList(), wr);

			List<Integer> ids = cells.stream()
					.map(c -> {
						List<Integer> res = new ArrayList<>();
						res.add(c.getId());
						//						if(c instanceof MxEleSourceCon)
						//							res.addAll(Arrays.asList(((MxEleSourceCon)c).getIdSrcPt(), ((MxEleSourceCon)c).getIdTgtPt()));
						return res;
					})
					.flatMap(List::stream)
					.collect(Collectors.toList());

			Set<Integer> duplicates = findDuplicateBySetAdd(ids);
			if(!duplicates.isEmpty()) {

				for (Integer id : duplicates) {
					logger.debug(" - duplicate " + id);
					cells.stream()
					.filter(c -> c.getId() == id || (c instanceof MxEleSourceCon && (((MxEleSourceCon)c).getIdSrcPt() == id || ((MxEleSourceCon)c).getIdTgtPt() == id)))
					.collect(Collectors.toList())
					.forEach(c -> {
						logger.debug("    " + c.getClass() + "-"+c.hashCode()+" -> " + c.getId());
						logger.debug("    " + c.getArchiElem());
						if(c instanceof MxEleSourceCon) {
							logger.debug("      src " + ((MxEleSourceCon)c).getIdSrcPt());
							logger.debug("      tgt " + ((MxEleSourceCon)c).getIdTgtPt());
						}
					});
				}
				//						throw new ArchimateConverterException("duplicate ids " + duplicates);
			}

			wr.write("</root>\r\n</mxGraphModel>");
		}finally {
			if(wr != null) wr.close();
		}
	}

	private static void setMXGraphCoordinnatesRecur(List<AbstractChildElemView> childList, int xRelativeTo, int yRelativeTo) {
		for(AbstractChildElemView child : childList) {
			child.setxMGraph(child.getxRect() != null ? child.getxRect() + xRelativeTo : xRelativeTo);
			child.setyMGraph(child.getyRect() != null ? child.getyRect() + yRelativeTo : yRelativeTo);

			if(child instanceof IViewParent) 
				setMXGraphCoordinnatesRecur(((IViewParent)child).getChildList()
						, child.getxMGraph()
						, child.getyMGraph());
		}		
	}

	private static List<IMxElemArchi> writeChildrenRecur(List<AbstractChildElemView> childList, BufferedWriter wr) throws IOException, MxGraphConverterException {
		List<IMxElemArchi> result = new ArrayList<>();
		for(AbstractChildElemView child : childList) {
			IMxElemArchi mx = child.createCell();
			if(mx  == null) continue;
			result.add(mx);
			wr.write(mx.toXmlString());
		}
		for(AbstractChildElemView child : childList) {
			if(child instanceof IViewParent) 
				result.addAll(writeChildrenRecur(((IViewParent)child).getChildList(), wr));
		}

		for (AbstractChildElemView child : childList) {
			for (SourceConnectionElementView sourceCon: child.getSourceConList()) {
				IMxElemArchi mx = sourceCon.createMxGraphCell();
				if(mx == null) continue;
				result.add(mx);
				wr.write(mx.toXmlString());
			}
		}
		return result;
	}

	private static <T> Set<T> findDuplicateBySetAdd(List<T> list) {
		Set<T> items = new HashSet<>();
		return list.stream()
				.filter(n -> !items.add(n)) // Set.add() returns false if the element was already in the set.
				.collect(Collectors.toSet());

	}


	public static void main(String[] args) throws IOException, SAXException, ArchimateModelException, MxGraphConverterException, URISyntaxException {
		MxGraphConverter.toMxGraphFiles(new File(MxGraphConverter.class.getClassLoader().getResource("DIAMS.archimate").toURI()).getAbsolutePath(), "./target/", true);
	}

}
