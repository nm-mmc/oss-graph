/**
 * Copyright 2015 OpenSearchServer Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package com.opensearchserver.graph.process;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import com.opensearchserver.client.JsonClient2;
import com.opensearchserver.graph.model.GraphBase;
import com.opensearchserver.graph.model.GraphNode;
import com.opensearchserver.graph.model.GraphNodeResult;
import com.opensearchserver.graph.model.GraphRequest;
import com.opensearchserver.utils.json.JsonApplicationException;
import com.opensearchserver.utils.json.ServerResource;

public class GraphProcess2Impl implements GraphProcessInterface {

	private final JsonClient2 client;

	GraphProcess2Impl(ServerResource server) throws URISyntaxException {
		client = new JsonClient2(server);
	}

	@Override
	public void createDataIndex(GraphBase base) {
		throw new JsonApplicationException(Status.NOT_IMPLEMENTED,
				"Not yet implemented");
	}

	@Override
	public void deleteDataIndex(GraphBase base) {
		throw new JsonApplicationException(Status.NOT_IMPLEMENTED,
				"Not yet implemented");
	}

	@Override
	public void createUpdateNode(GraphBase base, String node_id, GraphNode node) {
		throw new JsonApplicationException(Status.NOT_IMPLEMENTED,
				"Not yet implemented");
	}

	@Override
	public void createUpdateNodes(GraphBase base,
			LinkedHashMap<String, GraphNode> node) throws IOException,
			URISyntaxException {
		// TODO Auto-generated method stub
	}

	@Override
	public GraphNode getNode(GraphBase base, String node_id) {
		throw new JsonApplicationException(Status.NOT_IMPLEMENTED,
				"Not yet implemented");
	}

	@Override
	public void getNodes(GraphBase base,
			Map<String, ? extends GraphNode> nodeMap) throws IOException,
			URISyntaxException {
		throw new JsonApplicationException(Status.NOT_IMPLEMENTED,
				"Not yet implemented");
	}

	@Override
	public void deleteNode(GraphBase base, String node_id) {
		throw new JsonApplicationException(Status.NOT_IMPLEMENTED,
				"Not yet implemented");
	}

	@Override
	public void request(GraphBase base, GraphRequest request,
			List<GraphNodeResult> results) throws IOException,
			URISyntaxException {
		throw new JsonApplicationException(Status.NOT_IMPLEMENTED,
				"Not yet implemented");
	}

}
