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
import java.util.Set;

import javax.ws.rs.ApplicationPath;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import com.opensearchserver.utils.rest.RestApplication;
import com.opensearchserver.utils.rest.RestServer;

public class GraphServer extends RestServer {

	public final static int DEFAULT_PORT = 9093;
	public final static String DEFAULT_HOSTNAME = "0.0.0.0";
	public final static String MAIN_JAR = "oss-graph.jar";
	public final static String DEFAULT_DATADIR_NAME = "opensearchserver_graph";

	private GraphServer() {
		super(DEFAULT_HOSTNAME, DEFAULT_PORT, MAIN_JAR, GraphApplication.class,
				DEFAULT_DATADIR_NAME);
	}

	@ApplicationPath("/")
	public static class GraphApplication extends RestApplication {

		@Override
		public Set<Class<?>> getClasses() {
			Set<Class<?>> classes = super.getClasses();
			classes.add(GraphServiceImpl.class);
			return classes;
		}
	}

	@Override
	public void beforeStart(CommandLine cmd, File data_directory)
			throws IOException, ParseException {
		GraphManager.load(data_directory);
	}

	public static void main(String[] args) throws IOException, ParseException {
		new GraphServer().start(args);
	}

}
