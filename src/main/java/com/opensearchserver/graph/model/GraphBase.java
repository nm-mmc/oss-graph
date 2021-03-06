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
package com.opensearchserver.graph.model;

import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.opensearchserver.utils.json.ServerResource;

@JsonInclude(Include.NON_EMPTY)
public class GraphBase {

	public ServerResource data;

	public Map<String, PropertyTypeEnum> node_properties;

	public Set<String> edge_types;

	public GraphBase() {
		data = null;
		node_properties = null;
		edge_types = null;
	}

	public static enum PropertyTypeEnum {
		indexed, stored;
	}

	@XmlTransient
	@JsonIgnore
	public boolean isEdgeType(String edge_type) {
		return edge_types == null ? false : edge_types.contains(edge_type);
	}

	@XmlTransient
	@JsonIgnore
	public boolean isIndexedProperty(String property) {
		if (node_properties == null)
			return false;
		PropertyTypeEnum type = node_properties.get(property);
		if (type == null)
			return false;
		return type == PropertyTypeEnum.indexed;
	}
}