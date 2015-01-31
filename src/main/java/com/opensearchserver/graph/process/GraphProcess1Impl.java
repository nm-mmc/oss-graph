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

import javax.ws.rs.core.Response.Status;

import com.opensearchserver.graph.model.GraphBase;
import com.opensearchserver.graph.model.GraphNode;
import com.opensearchserver.utils.json.JsonApplicationException;

public class GraphProcess1Impl implements GraphProcessInterface {

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
	public GraphNode getNode(GraphBase base, String node_id) {
		throw new JsonApplicationException(Status.NOT_IMPLEMENTED,
				"Not yet implemented");
	}

	@Override
	public void loadNodes(GraphBase base, String node_id1, GraphNode node1,
			String node_id2n, GraphNode node2) {
		throw new JsonApplicationException(Status.NOT_IMPLEMENTED,
				"Not yet implemented");
	}

	@Override
	public void setNodes(GraphBase base, String node_id1, GraphNode node1,
			String node_id2, GraphNode node2) {
		// TODO Auto-generated method stub

	}

	@Override
	public GraphNode deleteNode(GraphBase base, String node_id) {
		throw new JsonApplicationException(Status.NOT_IMPLEMENTED,
				"Not yet implemented");
	}

	@Override
	public GraphNode createEdge(GraphBase base, String from_id,
			GraphNode from_node, String to_id, GraphNode to_node) {
		throw new JsonApplicationException(Status.NOT_IMPLEMENTED,
				"Not yet implemented");
	}

	@Override
	public GraphNode deleteEdge(GraphBase base, String from_id,
			GraphNode from_node, String to_id, GraphNode to_node) {
		throw new JsonApplicationException(Status.NOT_IMPLEMENTED,
				"Not yet implemented");
	}

}
