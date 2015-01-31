Usage
=====

A description of the Web services provided by OpenSearchServer Graph.

List of Graph database 
----------------------

    curl -XGET http://localhost:9093
    
```json
[
	"base1",
	"base2",
	"base3"
]
```
    
Create a Graph database
-----------------------

	curl -XPOST -H "Content-Type:application/json" --data-binary @file.json http://localhost:9093/{db_name}

To create an item, just POST a JSON structure describing the database.

Replace {db_name} by the name of your database.


Here is the structure of a database:

```json
{
    "data": {
        "url": "http://localhost:8080",
        "name": "oss_graph",
        "time_out": 30000
    }, 
    "node_properties": {
        "type": "indexed",
        "date": "indexed",
        "name": "stored",
        "user": "stored"
    },
    "edge_types": [ "see", "buy"]
}
```

- "data": specifies the OpenSearchServer backend instance and the index which contains the data.
- "node_properties": the possible properties for a node. A property can be indexed or stored.
- "edge_types" : The possible type of edges. 


Get a database item
---------------------

    curl -XGET http://localhost:9093/{db_name}

Delete a database item
-----------------------

    curl -XDELETE http://localhost:9093/{db_name}
    
Insert or update a node
-----------------------

	curl -XPOST -H "Content-Type:application/json" --data-binary @file.json http://localhost:9093/{db_name}/node/{node_id}
	
The structure of a node:

- "properties": Set the properties attached to this node.
- "edges": Set the connections with the other nodes.

An example:

```json
{
    "properties": {
        "type": "visit",
        "user": "john",
         "date": "20150115"
    },
    "edges": {
       "see": ["p1", "p2"],
       "buy": ["p1"]
    }
}
```

On this example, we store a node which represent the visit of "john" on our e-commerce web site.
John watched two products (p1 and p2) and he finally buy the product p1.

Get a node
----------

	curl -XDELETE http://localhost:9093/{db_name}/node/{node_id}
	
```json
{
     "properties": {
        "type": "product",
        "name": "product1"
    }
}
```
	
Delete a node
-------------

	curl -XDELETE http://localhost:9093/{db_name}/node/{node_id}