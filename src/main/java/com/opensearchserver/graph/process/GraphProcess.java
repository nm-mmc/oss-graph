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
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeSet;

import javax.ws.rs.core.Response.Status;

import org.apache.http.client.ClientProtocolException;

import com.opensearchserver.graph.model.GraphBase;
import com.opensearchserver.graph.model.GraphNode;
import com.opensearchserver.utils.StringUtils;
import com.opensearchserver.utils.json.JsonApplicationException;
import com.opensearchserver.utils.json.ServerResource;

public class GraphProcess {

	static final String FIELD_NODE_ID = "node_id";
	static final String FIELD_PREFIX_PROPERTY = "prop.";
	static final String FIELD_PREFIX_EDGE = "edge.";

	/**
	 * @param server
	 * @return the right implementation
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private static GraphProcessInterface getImplementation(ServerResource server)
			throws IOException, URISyntaxException {
		if (server == null)
			throw new ClientProtocolException(
					"The server to used has not been configured.");
		switch (server.getVersionOfDefault()) {
		case 1:
			return new GraphProcess1Impl(server);
		case 2:
			return new GraphProcess2Impl(server);
		}
		throw new JsonApplicationException(Status.BAD_REQUEST,
				"Version not supported: " + server.version);
	}

	static String getPropertyField(String name) {
		return StringUtils.fastConcat(GraphProcess.FIELD_PREFIX_PROPERTY, name);
	}

	static String getEdgeField(String name) {
		return StringUtils.fastConcat(GraphProcess.FIELD_PREFIX_EDGE, name);
	}

	public static void createDataIndex(GraphBase base) throws IOException,
			URISyntaxException {
		getImplementation(base.data).createDataIndex(base);
	}

	public static void deleteDataIndex(GraphBase base) throws IOException,
			URISyntaxException {
		getImplementation(base.data).deleteDataIndex(base);
	}

	public static void createUpdateNode(GraphBase base, String node_id,
			GraphNode node) throws IOException, URISyntaxException {
		getImplementation(base.data).createUpdateNode(base, node_id, node);
	}

	public static void createUpdateNodes(GraphBase base,
			LinkedHashMap<String, GraphNode> nodes) throws IOException,
			URISyntaxException {
		if (nodes == null || nodes.isEmpty())
			return;
		getImplementation(base.data).createUpdateNodes(base, nodes);
	}

	public static GraphNode getNode(GraphBase base, String node_id)
			throws IOException, URISyntaxException {
		return getImplementation(base.data).getNode(base, node_id);
	}

	public static void deleteNode(GraphBase base, String node_id)
			throws IOException, URISyntaxException {
		getImplementation(base.data).deleteNode(base, node_id);
	}

	public static GraphNode createEdge(GraphBase base, String node_id,
			String type, String to_node_id) throws IOException,
			URISyntaxException {
		if (base.edge_types == null || base.edge_types.isEmpty())
			throw new JsonApplicationException(Status.BAD_REQUEST,
					"This graph base does not accept edges");
		GraphProcessInterface graphProcess = getImplementation(base.data);

		// Retrieve the node from the index
		GraphNode node = graphProcess.getNode(base, node_id);
		if (node.edges == null)
			node.edges = new LinkedHashMap<String, Set<String>>();

		// Check if the to_node_id is already set
		type = type.intern();
		Set<String> nodeIdSet = node.edges.get(type);
		if (nodeIdSet == null) {
			// We add the first edge
			nodeIdSet = new TreeSet<String>();
			nodeIdSet.add(to_node_id);
			node.edges.put(type, nodeIdSet);
		} else {
			// It is already set, nothing to update
			if (nodeIdSet.contains(to_node_id))
				return node;
			nodeIdSet.add(to_node_id);
		}

		// We do the update
		graphProcess.createUpdateNode(base, node_id, node);
		return node;
	}

	public static GraphNode deleteEdge(GraphBase base, String node_id,
			String type, String to_node_id) throws IOException,
			URISyntaxException {
		GraphProcessInterface graphProcess = getImplementation(base.data);

		// Retrieve the node from the index
		GraphNode node = graphProcess.getNode(base, node_id);
		if (node.edges == null) // There is already no edge
			return node;
		Set<String> nodeIdSet = node.edges.get(type.intern());
		if (nodeIdSet == null) // No edges for this type
			return node;
		if (!nodeIdSet.remove(to_node_id)) // The to_node_id was not here
			return node;

		// We do the update
		graphProcess.createUpdateNode(base, node_id, node);
		return node;
	}

}
