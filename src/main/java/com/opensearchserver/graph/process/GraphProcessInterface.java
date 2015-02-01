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
import java.util.List;

import com.opensearchserver.graph.model.GraphBase;
import com.opensearchserver.graph.model.GraphNode;
import com.opensearchserver.graph.model.GraphNodeResult;
import com.opensearchserver.graph.model.GraphRequest;

public interface GraphProcessInterface {

	void createDataIndex(GraphBase base) throws URISyntaxException, IOException;

	void deleteDataIndex(GraphBase base) throws URISyntaxException, IOException;

	void createUpdateNode(GraphBase base, String node_id, GraphNode node)
			throws IOException, URISyntaxException;

	void createUpdateNodes(GraphBase base,
			LinkedHashMap<String, GraphNode> nodes) throws IOException,
			URISyntaxException;

	GraphNode getNode(GraphBase base, String node_id) throws IOException,
			URISyntaxException;

	void deleteNode(GraphBase base, String node_id) throws IOException,
			URISyntaxException;

	void request(GraphBase base, GraphRequest request,
			List<GraphNodeResult> results) throws IOException,
			URISyntaxException;

	void loadNodes(List<GraphNodeResult> results) throws IOException,
			URISyntaxException;

}
