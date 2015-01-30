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

import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.opensearchserver.graph.model.Base;
import com.opensearchserver.graph.model.Node;
import com.opensearchserver.utils.json.JsonApplicationException;

@Path("/")
public class GraphService {

	public final static String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";

	@GET
	@Path("/")
	@Produces(APPLICATION_JSON_UTF8)
	public Set<String> list(@Context UriInfo uriInfo) {
		return GraphBaseManager.INSTANCE.getNameSet();
	}

	@PUT
	@Path("/{db_name}")
	@Produces(APPLICATION_JSON_UTF8)
	public void createBase(@Context UriInfo uriInfo,
			@PathParam("db_name") String db_name) {
		GraphBaseManager.INSTANCE.createBase(db_name);
	}

	@GET
	@Path("/{db_name}")
	@Produces(APPLICATION_JSON_UTF8)
	public void getBase(@Context UriInfo uriInfo,
			@PathParam("db_name") String db_name) {
		if (!GraphBaseManager.INSTANCE.baseExists(db_name))
			throw new JsonApplicationException(Status.NOT_FOUND, "Unknown: "
					+ db_name);
	}

	@DELETE
	@Path("/{db_name}")
	@Produces(APPLICATION_JSON_UTF8)
	public void deleteBase(@Context UriInfo uriInfo,
			@PathParam("name") String db_name) {
		if (!GraphBaseManager.INSTANCE.baseExists(db_name))
			throw new JsonApplicationException(Status.NOT_FOUND, "Unknown: "
					+ db_name);
	}

	private Base getBase(String db_name) {
		Base base = GraphBaseManager.INSTANCE.getBase(db_name);
		if (base == null)
			throw new JsonApplicationException(Status.NOT_FOUND, "Unknown: "
					+ db_name);
		return base;
	}

	@POST
	@Path("/{db_name}/node")
	@Consumes(APPLICATION_JSON_UTF8)
	@Produces(APPLICATION_JSON_UTF8)
	public Node createNode(@Context UriInfo uriInfo,
			@PathParam("db_name") String db_name, Map<String, Object> properties) {
		return getBase(db_name).createNode(properties);
	}

	@PUT
	@Path("/{db_name}/node/{node_id}")
	@Consumes(APPLICATION_JSON_UTF8)
	@Produces(APPLICATION_JSON_UTF8)
	public Node updateNode(@Context UriInfo uriInfo,
			@PathParam("db_name") String db_name,
			@PathParam("node_id") Long node_id, Map<String, Object> properties) {
		return getBase(db_name).updateNode(node_id, properties);
	}

	@DELETE
	@Path("/{db_name}/node/{node_id}")
	@Produces(APPLICATION_JSON_UTF8)
	public Node deleteNode(@Context UriInfo uriInfo,
			@PathParam("db_name") String db_name,
			@PathParam("node_id") Long node_id) {
		Node node = getBase(db_name).deleteNode(node_id);
		if (node == null)
			throw new JsonApplicationException(Status.NOT_FOUND, "Unknown: "
					+ node_id);
		return node;
	}

}
