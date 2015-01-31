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
import javax.ws.rs.core.UriInfo;

import com.opensearchserver.graph.model.GraphBase;
import com.opensearchserver.graph.model.GraphNode;

@Path("/")
public interface GraphServiceInterface {

	public final static String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";

	@GET
	@Path("/")
	@Produces(APPLICATION_JSON_UTF8)
	public Set<String> list(@Context UriInfo uriInfo);

	@PUT
	@POST
	@Path("/{db_name}")
	@Consumes(APPLICATION_JSON_UTF8)
	@Produces(APPLICATION_JSON_UTF8)
	public GraphBase createUpdateBase(@Context UriInfo uriInfo,
			@PathParam("db_name") String db_name, GraphBase base);

	@GET
	@Path("/{db_name}")
	@Produces(APPLICATION_JSON_UTF8)
	public GraphBase getBase(@Context UriInfo uriInfo,
			@PathParam("db_name") String db_name);

	@DELETE
	@Path("/{db_name}")
	@Produces(APPLICATION_JSON_UTF8)
	public GraphBase deleteBase(@Context UriInfo uriInfo,
			@PathParam("name") String db_name);

	@PUT
	@POST
	@Path("/{db_name}/node/{node_id}")
	@Consumes(APPLICATION_JSON_UTF8)
	@Produces(APPLICATION_JSON_UTF8)
	public GraphNode createUpdateNode(@Context UriInfo uriInfo,
			@PathParam("db_name") String db_name,
			@PathParam("node_id") String node_id, GraphNode node);

	@GET
	@Path("/{db_name}/node/{node_id}")
	@Produces(APPLICATION_JSON_UTF8)
	public GraphNode getNode(@Context UriInfo uriInfo,
			@PathParam("db_name") String db_name,
			@PathParam("node_id") String node_id);

	@DELETE
	@Path("/{db_name}/node/{node_id}")
	@Produces(APPLICATION_JSON_UTF8)
	public GraphNode deleteNode(@Context UriInfo uriInfo,
			@PathParam("db_name") String db_name,
			@PathParam("node_id") String node_id);

	@PUT
	@POST
	@Path("/{db_name}/node/{node_id}/egde/{to_node_id}")
	@Produces(APPLICATION_JSON_UTF8)
	public GraphNode createEdge(@Context UriInfo uriInfo,
			@PathParam("db_name") String db_name,
			@PathParam("node_id") String node_id,
			@PathParam("to_node_id") String to_node_id);

	@DELETE
	@Path("/{db_name}/node/{node_id}/egde/{to_node_id}")
	@Produces(APPLICATION_JSON_UTF8)
	public GraphNode deleteEdge(@Context UriInfo uriInfo,
			@PathParam("db_name") String db_name,
			@PathParam("node_id") String node_id,
			@PathParam("to_node_id") String to_node_id);

}
