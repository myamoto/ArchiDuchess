package org.toolup.archi.service;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.toolup.archi.business.ArchiGraphConfig;
import org.toolup.archi.business.archimate.ArchimateModel;
import org.toolup.archi.business.archimate.ArchimateModelException;
import org.toolup.archi.business.archimate.view.DiagramModelView;
import org.toolup.archi.business.galerie.Gallery;
import org.toolup.archi.business.galerie.Graph;
import org.toolup.archi.business.galerie.Group;
import org.toolup.archi.io.archi.ArchiIO;
import org.toolup.archi.io.mxgraph.MxGraphConverterException;
import org.toolup.devops.ci.git.client.GITSCMException;
import org.toolup.devops.ci.git.client.GitRetriever;
import org.toolup.devops.ci.git.client.GitRetrieverCfg;
import org.toolup.archi.io.mxgraph.MxGraphConverter;

/**
 * Thread-safe GITOPS compliant Archi galery service.
 *
 */
public class ArchiGraphService {

	private Gallery currentGallery;
	
	private static final String ARCHI_EXT = ".archimate";
	
	private ArchiGraphConfig conf;

	private boolean running;
	
	private synchronized void setFinished() {
		this.running = false;
	}

	private synchronized void setStarted() throws ArchiGraphException {
		if(this.running) throw new ArchiGraphException("already running. dont try to break my monothreaded singleton please.", 503);
		this.running = true;
	}

	public synchronized boolean isRunning() {
		return running;
	}
	
	public void config(String gitBaseUrl, String gitPersonalToken, String projectUrl, String workingDir) {
		conf = new ArchiGraphConfig().gitConf(new GitRetrieverCfg()
				.gitlabBaseURL(gitBaseUrl)
				.gitPersonalToken(gitPersonalToken)
				.gitProjectURL(projectUrl)
				.workingDirectoryPath(workingDir));
	}

	private void refreshLocalDatas() throws ArchiGraphException {
		if(conf == null || conf.getGitConf() == null)
			throw new ArchiGraphException("conf. must be set.", 500);
		
		try{
			setStarted();
			GitRetriever gitRetriever = new GitRetriever()
					.progressMonitor(null)
					.defaultMasterBranch("master");
			gitRetriever.config(conf.getGitConf());
			gitRetriever.fetch();
			gitRetriever.checkout();
			if(!gitRetriever.getBehindCommits().isEmpty()) {
				gitRetriever.pull();
			}
		}catch(GITSCMException e) {
			throw new ArchiGraphException(e, 500);
		}finally {
			setFinished();
		}
	}

	public String retrieveArchiGraphFile(String path, String graphName) throws ArchiGraphException{
		if(conf == null || conf.getGitConf() == null)
			throw new ArchiGraphException("conf. must be set.", 500);
		
		final String finalName = graphName.endsWith(ARCHI_EXT) ? graphName : graphName + ARCHI_EXT;
		File r = new File(getDirPath(path), finalName);
		if(!r.exists() || !r.isFile() || !r.canRead()) throw new ArchiGraphException(String.format("invalid model : '%s' not readable.", graphName), 400);
		return r.getAbsolutePath();
	}
	
	public Gallery readGalery() throws ArchiGraphException {
		if(isRunning())
			return currentGallery;
		
		refreshLocalDatas();
		File galerieDir = new File(getGaleryPath());
		Collection<File> dirList = FileUtils.listFilesAndDirs(galerieDir, FalseFileFilter.FALSE, new IOFileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory() 
						&& StringUtils.isAlphanumeric(file.getName()); 
			}

			@Override
			public boolean accept(File dir, String name) {
				return true;
			}
		});
		
		dirList = dirList
				.stream()
				.filter(d -> d.listFiles(f -> f.getName().endsWith(ARCHI_EXT)).length > 0)
				.collect(Collectors.toList());
		currentGallery = new Gallery().groups(
				dirList
					.stream()
					.map(f -> {
						String r = f.getAbsolutePath().substring(galerieDir.getAbsolutePath().length());
						if(r.startsWith(File.separator))
							r = r.substring(1);
						return r;
					})
					.collect(Collectors.toList()));
		return currentGallery;
	}
	
	public Group readGroup(String group) throws ArchiGraphException {
		File pathDir = new File(getDirPath(group));
		File[] graphFiles = pathDir.listFiles(f -> f.getName().endsWith(ARCHI_EXT));
		
		Group result = new Group().path(group);
		if(graphFiles == null || graphFiles.length == 0) return result;
		
		for (File graphFile : graphFiles) {
			String modelName = graphFile.getName().substring(0, graphFile.getName().length() - ARCHI_EXT.length());
			
			Graph graph = new Graph()
					.graphName(modelName)
					.fileName(graphFile.getName());
			try {
				graph.views(listViewsNames(ArchiIO.read(retrieveArchiGraphFile(group, modelName))));
			} catch (ArchimateModelException e) {
				throw new ArchiGraphException(String.format("%s on graph [ %s ]", e.getMessage(), graphFile.getAbsolutePath()), e, 500);
			}
			Collections.sort(graph.getViews());
			result.addGraph(graph);
		}

		Collections.sort(result.getGraphs(), new Comparator<Graph>() {
			public int compare(Graph o1, Graph o2) {
				return o1 == null || o2 == null ? 0 : o1.getGraphName().compareTo(o2.getGraphName());
			}
		});
		return result;
	}

	public List<String> listViewsNames(ArchimateModel mdl) {
		return mdl.getDiagramModelViewList()
				.stream()
				.filter(v -> {
					return v.getDocumentation() == null || !v.getDocumentation().contains("<disabled>");
				})
				.map(DiagramModelView::getName)
				.collect(Collectors.toList());
	}
	
	public byte[] readMxGraphImg(String path, String graphName, String viewName) throws ArchiGraphException {
		try {
			return MxGraphConverter.toMxGraphImg(retrieveArchiGraphFile(path, graphName), viewName);
		} catch (MxGraphConverterException e) {
			throw new ArchiGraphException(String.format("error loading %s / %s / %s", path, graphName, viewName ), e, 500);
		}
	}

	
	public byte[] readMxGraph(String path, String graphName, String viewName) throws ArchiGraphException {
		try {
			return MxGraphConverter.toMxGraph(retrieveArchiGraphFile(path, graphName), viewName);
		} catch (MxGraphConverterException e) {
			throw new ArchiGraphException(String.format("error loading %s / %s / %s", path, graphName, viewName ), e, 500);
		}
	}

	private String getDirPath(String path) throws ArchiGraphException {
		if(path == null || path.isEmpty()) throw new ArchiGraphException("path must be specified.", 400);
		if(path.contains(".")) throw new ArchiGraphException("invalid path : can't contain '.'", 400);
		File r = new File(getGaleryPath(), path);
		if(!r.exists() || !r.isDirectory() || !r.canRead()) throw new ArchiGraphException(
				String.format("invalid path : %s is not a readable directory.", path), 400);
		return r.getAbsolutePath();
	}
	
	private String getGaleryPath() throws ArchiGraphException {
		if(conf == null || conf.getGitConf() == null) throw new ArchiGraphException("conf. must be set.", 500);
		File r = new File(conf.getGitConf().getProjectDirPath());
		if(!r.exists() || !r.isDirectory() || !r.canRead()) throw new ArchiGraphException(
				"invalid workspace : not a readable directory.", 400);
		return r.getAbsolutePath();
	}
	
	
}
