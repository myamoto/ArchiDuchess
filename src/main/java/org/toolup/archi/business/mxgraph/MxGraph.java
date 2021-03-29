package org.toolup.archi.business.mxgraph;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;

import com.mxgraph.io.mxCodec;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;

public class MxGraph {

	private String url;
	private String name;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public mxGraph retrieveMxGraph(CloseableHttpClient httpclient) throws IOException {
		try(CloseableHttpResponse httpResp = httpclient.execute(new HttpGet(url))){
			String responseContent = EntityUtils.toString(httpResp.getEntity());
			Document document = mxXmlUtils.parseXml(responseContent);
			mxCodec codec = new mxCodec(document);
			mxGraph graph = new mxGraph();
			codec.decode(document.getDocumentElement(), graph.getModel());
			return graph;
		}
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
}
