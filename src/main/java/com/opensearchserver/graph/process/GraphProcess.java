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
 */
package com.opensearchserver.graph.process;

import java.io.IOException;

import javax.ws.rs.core.Response.Status;

import org.apache.http.client.ClientProtocolException;

import com.opensearchserver.graph.model.GraphBase;
import com.opensearchserver.graph.model.GraphNode;
import com.opensearchserver.utils.json.JsonApplicationException;
import com.opensearchserver.utils.json.ServerResource;

public class GraphProcess {

	/**
	 * @param server
	 * @return the right implementation
	 * @throws IOException
	 */
	private static GraphProcessInterface getImplementation(ServerResource server)
			throws IOException {
		if (server == null)
			throw new ClientProtocolException(
					"The server to used has not been configured.");
		switch (server.getVersionOfDefault()) {
		case 1:
			return new GraphProcess1Impl();
		case 2:
			return new GraphProcess2Impl();
		}
		throw new JsonApplicationException(Status.BAD_REQUEST,
				"Version not supported: " + server.version);
	}

	public static void createDataIndex(GraphBase base) throws IOException {
		getImplementation(base.data).createDataIndex(base);
	}

	public static void deleteDataIndex(GraphBase base) throws IOException {
		getImplementation(base.data).deleteDataIndex(base);
	}

	public static void createUpdateNode(GraphBase base, String node_id,
			GraphNode node) throws IOException {
		getImplementation(base.data).createUpdateNode(base, node_id, node);
	}

	public static GraphNode getNode(GraphBase base, String node_id)
			throws IOException {
		return getImplementation(base.data).getNode(base, node_id);
	}

	public static GraphNode deleteNode(GraphBase base, String node_id)
			throws IOException {
		return getImplementation(base.data).deleteNode(base, node_id);
	}

	public static GraphNode createEdge(GraphBase base, String node_id,
			String to_node_id) throws IOException {
		GraphProcessInterface graphProcess = getImplementation(base.data);
		GraphNode node_from = new GraphNode();
		GraphNode node_to = new GraphNode();
		graphProcess.loadNodes(base, node_id, node_from, to_node_id, node_to);
		graphProcess.createEdge(base, node_id, node_from, to_node_id, node_to);
		graphProcess.setNodes(base, node_id, node_from, to_node_id, node_to);
		return node_from;
	}

	public static GraphNode deleteEdge(GraphBase base, String node_id,
			String to_node_id) throws IOException {
		GraphProcessInterface graphProcess = getImplementation(base.data);
		GraphNode node_from = new GraphNode();
		GraphNode node_to = new GraphNode();
		graphProcess.loadNodes(base, node_id, node_from, to_node_id, node_to);
		graphProcess.deleteEdge(base, node_id, node_from, to_node_id, node_to);
		graphProcess.setNodes(base, node_id, node_from, to_node_id, node_to);
		return node_from;
	}
}
