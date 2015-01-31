OpenSearchServer Graph
======================

An open source graph engine for OpenSearchServer.  

Requirement
-----------

- Java Runtime 7
- OpenSearchServer v1.5.10 or newer
- Maven 3.0 or newer (for building only)

How to build
------------

The compilation and packaging requires [Maven 3.0 or newer](http://maven.apache.org/)

Clone the source code:

```shell
git clone https://github.com/opensearchserver/oss-graph.git
```

Compile and package (the binary will located in the target directory):

```shell
mvn clean package
```

Run the server
--------------

OpenSearchServer Graph is a deamon program. It will listen by default to the port 9093.

Create a directory where the daemon can store its configuration files.

```shell
mkdir ~/oss_graph
```

Run OpenSearchServer Graph. Here are the parameters:

- 'd': The path of the data directory (~/opensearchserver_graph by default).
- 'p': The TCP port used by the daemon (9093 by default).
- 'h': The hostname (0.0.0.0 by default).

```shell
java -jar oss-graph-0.1-exec.jar -d ~/oss_graph
```

You can now use the REST service:

```
curl -XGET "http://localhost:9093"
```

Usage
-----

The provided [REST/Json Web Service is described here](src/doc/usage.md).

Issues and change Log
---------------------

Issues and milestones are tracked on GitHub:

- [Open issues](https://github.com/opensearchserver/oss-graph/issues?q=is%3Aopen+is%3Aissue)
- [Closed issues](https://github.com/opensearchserver/oss-graph/issues?q=is%3Aissue+is%3Aclosed)

License Apache 2
----------------

Copyright 2015 [OpenSearchServer Inc.](http://www.opensearchserver.com)


Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.