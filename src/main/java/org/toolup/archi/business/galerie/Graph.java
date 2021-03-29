package org.toolup.archi.business.galerie;

import java.util.ArrayList;
import java.util.List;

public class Graph {

	private String fileName;
	private String graphName;
	private final List<String> views;
	private final StringBuilder error;
	

	public Graph() {
		error = new StringBuilder();
		views = new ArrayList<>();
	}
	
	public String getError() {
		return error.toString();
	}

	public Graph error(String error) {
		this.error.setLength(0);
		appendError(error);
		return this;
	}
	
	public Graph appendError(String error) {
		if(error != null) this.error.append(error);
		return this;
	}

	public String getFileName() {
		return fileName;
	}

	public Graph fileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	public String getGraphName() {
		return graphName;
	}

	public Graph graphName(String graphName) {
		this.graphName = graphName;
		return this;
	}

	public List<String> getViews() {
		return views;
	}
	
	public Graph addView(String view) {
		this.views.clear();
		if(view != null) this.views.add(view);
		return this;
	}
	
	public Graph views(List<String> views) {
		this.views.clear();
		if(views != null) this.views.addAll(views);
		return this;
	}

	@Override
	public String toString() {
		return "Graph [fileName=" + fileName + ", graphName=" + graphName + ", views=" + views + ", error=" + error
				+ "]";
	}
	
}
