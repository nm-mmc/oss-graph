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
package com.opensearchserver.graph.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.opensearchserver.graph.GraphServer;
import com.opensearchserver.graph.model.GraphBase;
import com.opensearchserver.graph.model.GraphBase.PropertyTypeEnum;
import com.opensearchserver.graph.model.GraphNode;
import com.opensearchserver.utils.json.JsonMapper;
import com.opensearchserver.utils.json.ServerResource;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FullTest {

	private static volatile boolean started;

	public static final String BASE_OSS = "http://localhost:8080";
	public static final String BASE_URL = "http://localhost:9093";
	public static final String TEST_BASE = "oss-graph-test";
	public static final int PRODUCT_NUMBER = 200;
	public static final int VISIT_NUMBER = 10000;

	public static final ContentType APPLICATION_JSON_UTF8 = ContentType.create(
			"application/json", Consts.UTF_8);

	@Before
	public void create() throws IOException, ParseException {
		if (started)
			return;
		// start the server
		GraphServer.main(null);
		started = true;
	}

	@Test
	public void test000CreateDatabase() throws IOException {

		GraphBase base = new GraphBase();
		base.data = new ServerResource();
		base.data.url = BASE_OSS;
		base.data.version = 1;
		base.data.name = TEST_BASE;
		base.node_properties = new HashMap<String, PropertyTypeEnum>();
		base.node_properties.put("type", PropertyTypeEnum.indexed);
		base.node_properties.put("date", PropertyTypeEnum.indexed);
		base.node_properties.put("name", PropertyTypeEnum.stored);
		base.node_properties.put("user", PropertyTypeEnum.stored);
		base.edge_types = new HashSet<String>();
		base.edge_types.add("see");
		base.edge_types.add("buy");

		HttpResponse response = Request
				.Put(BASE_URL + '/' + TEST_BASE)
				.bodyString(JsonMapper.MAPPER.writeValueAsString(base),
						APPLICATION_JSON_UTF8).execute().returnResponse();
		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
	}

	@Test
	public void test100PutProductNodes() throws IOException {
		for (int i = 0; i < PRODUCT_NUMBER; i++) {
			GraphNode node = new GraphNode();
			node.properties = new HashMap<String, String>();
			node.properties.put("type", "product");
			node.properties.put("name", "product" + i);
			HttpResponse response = Request
					.Put(BASE_URL + '/' + TEST_BASE + "/node/p" + i)
					.bodyString(JsonMapper.MAPPER.writeValueAsString(node),
							APPLICATION_JSON_UTF8).execute().returnResponse();
			Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		}
	}

	@Test
	public void test110PutVisitNodes() throws IOException {
		for (int i = 0; i < VISIT_NUMBER; i += 100) {
			Map<String, GraphNode> nodeMap = new LinkedHashMap<String, GraphNode>();
			for (int k = 0; k < 100; k++) {
				GraphNode node = new GraphNode();
				node.properties = new HashMap<String, String>();
				node.properties.put("type", "visit");
				node.properties.put("user",
						"user" + RandomUtils.nextInt(0, 100));
				node.properties.put("date",
						"201501" + RandomUtils.nextInt(10, 31));
				node.edges = new HashMap<String, Set<String>>();
				int seePages = RandomUtils.nextInt(3, 12);
				Set<String> set = new TreeSet<String>();
				for (int j = 0; j < seePages; j++)
					set.add("p" + RandomUtils.nextInt(0, PRODUCT_NUMBER / 2));
				node.edges.put("see", set);
				if (RandomUtils.nextInt(0, 10) == 0) {
					int buyItems = RandomUtils.nextInt(1, 5);
					set = new TreeSet<String>();
					for (int j = 0; j < buyItems; j++)
						set.add("p"
								+ RandomUtils.nextInt(0, PRODUCT_NUMBER / 2));
					node.edges.put("buy", set);
				}
				nodeMap.put("v" + (i + k), node);
			}
			HttpResponse response = Request
					.Put(BASE_URL + '/' + TEST_BASE + "/node")
					.bodyString(JsonMapper.MAPPER.writeValueAsString(nodeMap),
							APPLICATION_JSON_UTF8).execute().returnResponse();
			Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		}
	}

	private boolean nodeExists(int visiteNodeId) throws IOException {
		HttpResponse response = Request
				.Get(BASE_URL + '/' + TEST_BASE + "/node/v" + visiteNodeId)
				.execute().returnResponse();
		Assert.assertThat(response.getStatusLine().getStatusCode(),
				AnyOf.anyOf(Is.is(200), Is.is(404)));
		Assert.assertThat(response.getEntity().getContentType().getValue(),
				Is.is(APPLICATION_JSON_UTF8.toString()));
		return response.getStatusLine().getStatusCode() == 200;
	}

	@Test
	public void test200PutRandomEdges() throws IOException {
		for (int i = 0; i < VISIT_NUMBER / 100; i++) {
			int visitNodeId = RandomUtils.nextInt(VISIT_NUMBER / 2,
					VISIT_NUMBER);
			if (!nodeExists(visitNodeId))
				continue;
			int productNodeId = RandomUtils.nextInt(PRODUCT_NUMBER / 2,
					PRODUCT_NUMBER);
			HttpResponse response = Request
					.Put(BASE_URL + '/' + TEST_BASE + "/node/v" + visitNodeId
							+ "/edge/see/p" + productNodeId).execute()
					.returnResponse();
			Assert.assertThat(response.getStatusLine().getStatusCode(),
					AnyOf.anyOf(Is.is(200), Is.is(404)));
			Assert.assertThat(response.getEntity().getContentType().getValue(),
					Is.is(APPLICATION_JSON_UTF8.toString()));
		}
	}

	@Test
	public void test210DeleteRandomEdges() throws IOException {
		for (int i = 0; i < VISIT_NUMBER / 100; i++) {
			int visiteNodeId = RandomUtils.nextInt(0, VISIT_NUMBER / 2);
			if (!nodeExists(visiteNodeId))
				continue;
			int productNodeId = RandomUtils.nextInt(0, PRODUCT_NUMBER / 2);
			HttpResponse response = Request
					.Delete(BASE_URL + '/' + TEST_BASE + "/node/v"
							+ visiteNodeId + "/edge/see/p" + productNodeId)
					.execute().returnResponse();
			Assert.assertThat(response.getStatusLine().getStatusCode(),
					AnyOf.anyOf(Is.is(200), Is.is(404)));
		}
	}

	@Test
	public void test999DeleteDatabase() throws IOException {
		HttpResponse response = Request.Delete(BASE_URL + '/' + TEST_BASE)
				.execute().returnResponse();
		Assert.assertEquals(200, response.getStatusLine().getStatusCode());

	}
}
