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
package com.opensearchserver.graph;

import java.io.IOException;
import java.util.Set;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.opensearchserver.graph.model.GraphBase;
import com.opensearchserver.graph.model.GraphNode;
import com.opensearchserver.graph.process.GraphProcess;
import com.opensearchserver.utils.json.JsonApplicationException;

@Path("/")
public class GraphServiceImpl implements GraphServiceInterface {

	@Override
	public Set<String> list(UriInfo uriInfo) {
		return GraphManager.INSTANCE.nameSet();
	}

	@Override
	public GraphBase createUpdateBase(UriInfo uriInfo,
			@PathParam("db_name") String db_name, GraphBase base) {
		try {
			GraphManager.INSTANCE.set(db_name, base);
			if (base.data != null)
				GraphProcess.createDataIndex(base);
			return base;
		} catch (Exception e) {
			throw new JsonApplicationException(e);
		}
	}

	@Override
	public GraphBase getBase(UriInfo uriInfo,
			@PathParam("db_name") String db_name) {
		GraphBase base = GraphManager.INSTANCE.get(db_name);
		if (base == null)
			throw new JsonApplicationException(Status.NOT_FOUND,
					"Graph base not found: " + db_name);
		return base;
	}

	@Override
	public GraphBase deleteBase(UriInfo uriInfo, String db_name) {
		GraphBase base = GraphManager.INSTANCE.delete(db_name);
		if (base == null)
			throw new JsonApplicationException(Status.NOT_FOUND,
					"Graph base not found: " + db_name);
		try {
			GraphProcess.deleteDataIndex(base);
		} catch (IOException e) {
			throw new JsonApplicationException(e);
		}
		return base;
	}

	@Override
	public GraphNode createUpdateNode(UriInfo uriInfo, String db_name,
			String node_id, GraphNode node) {
		try {
			GraphProcess.createUpdateNode(getBase(uriInfo, db_name), node_id,
					node);
			return node;
		} catch (IOException e) {
			throw new JsonApplicationException(e);
		}
	}

	@Override
	public GraphNode getNode(UriInfo uriInfo, String db_name, String node_id) {
		try {
			GraphNode node = GraphProcess.getNode(getBase(uriInfo, db_name),
					node_id);
			if (node == null)
				throw new JsonApplicationException(Status.NOT_FOUND,
						"Graph node not found: " + node_id);
			return node;
		} catch (IOException e) {
			throw new JsonApplicationException(e);
		}
	}

	@Override
	public GraphNode deleteNode(UriInfo uriInfo, String db_name, String node_id) {
		try {
			GraphNode node = getNode(uriInfo, db_name, node_id);
			GraphProcess.deleteNode(getBase(uriInfo, db_name), node_id);
			return node;
		} catch (IOException e) {
			throw new JsonApplicationException(e);
		}
	}

	@Override
	public GraphNode createEdge(UriInfo uriInfo, String db_name,
			String node_id, String to_node_id) {
		try {
			return GraphProcess.createEdge(getBase(uriInfo, db_name), node_id,
					to_node_id);
		} catch (IOException e) {
			throw new JsonApplicationException(e);
		}
	}

	@Override
	public GraphNode deleteEdge(UriInfo uriInfo, String db_name,
			String node_id, String to_node_id) {
		try {
			return GraphProcess.deleteEdge(getBase(uriInfo, db_name), node_id,
					to_node_id);
		} catch (IOException e) {
			throw new JsonApplicationException(e);
		}
	}

}
