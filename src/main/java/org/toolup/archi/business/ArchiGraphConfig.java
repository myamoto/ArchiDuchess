package org.toolup.archi.business;

import org.toolup.devops.ci.git.client.GitRetrieverCfg;

public class ArchiGraphConfig {

	private GitRetrieverCfg gitConf;

	public GitRetrieverCfg getGitConf() {
		return gitConf;
	}

	public ArchiGraphConfig gitConf(GitRetrieverCfg gitConf) {
		this.gitConf = gitConf;
		return this;
	}

	@Override
	public String toString() {
		return "ArchiGraphConfig [gitConf=" + gitConf + "]";
	}

}
