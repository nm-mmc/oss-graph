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

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.opensearchserver.graph.model.GraphBase;
import com.opensearchserver.utils.json.DirectoryJsonManager;

public class GraphManager extends DirectoryJsonManager<GraphBase> {

	public static volatile GraphManager INSTANCE = null;

	public static void load(File directory) throws IOException {
		if (INSTANCE != null)
			throw new IOException("Already loaded");
		INSTANCE = new GraphManager(directory);
	}

	private GraphManager(File directory) throws JsonGenerationException,
			JsonMappingException, JsonParseException, IOException {
		super(directory, GraphBase.class);
	}
}
