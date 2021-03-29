package org.toolup.archi.business.galerie;

import java.util.ArrayList;
import java.util.List;

public class Group {

	private String path;
	private final List<Graph> graphs;
	
	public Group() {
		graphs = new ArrayList<>();
	}

	public String getPath() {
		return path;
	}

	public Group path(String path) {
		this.path = path;
		return this;
	}

	public List<Graph> getGraphs() {
		return graphs;
	}
	
	public Group addGraph(Graph graph) {
		if(graph != null) this.graphs.add(graph);
		return this;
	}
	
	public Group graphs(List<Graph> graphs) {
		this.graphs.clear();
		if(graphs != null) this.graphs.addAll(graphs);
		return this;
	}

	@Override
	public String toString() {
		return "Group [path=" + path + ", graphs=" + graphs + "]";
	}

	
	
	
}
