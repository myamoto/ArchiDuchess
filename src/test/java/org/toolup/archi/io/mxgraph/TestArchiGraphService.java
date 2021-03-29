package org.toolup.archi.io.mxgraph;

import org.toolup.archi.business.galerie.Gallery;
import org.toolup.archi.business.galerie.Graph;
import org.toolup.archi.business.galerie.Group;
import org.toolup.archi.service.ArchiGraphException;
import org.toolup.archi.service.ArchiGraphService;

public class TestArchiGraphService {

	//	@Test
	public void test() {

		try {
			/*
			 * configure gitlab.
			 * note : not tested against github, but could easyly be adapted.
			 */
			ArchiGraphService s = new ArchiGraphService();
			s.config("https://my-gitlab-repo.org"
					, "{{my_git_personnal_token}}"
					, "https://my-gitlab-repo.org/path/to/MyArchiProject"
					, "/archiduchess/working/directory");
			
			/* pull project and points to master's last commit.
			 * parse directory directory structure and deduce "groups".
			 * one group = one directory with one or more ".archimate" file.
			 */
			Gallery galry = s.readGalery(); 
			
			for (String grpPath : galry.getGroups()) { //iterate over groups
				Group grp = s.readGroup(grpPath);	//parse group graphs. one "group" correspond to a directory of "Archi" files.
				for(Graph graph : grp.getGraphs()) {//each graph correspond to one "Archi" file.
					for(String view : graph.getViews()) { //One "Archi" file can contain several schemas.
						s.readMxGraph(grp.getPath(), graph.getFileName(), view);//convert to mxGraph
					}
				}
			}
		}catch (ArchiGraphException ex) {
			ex.printStackTrace();
		}
	}
	
}
