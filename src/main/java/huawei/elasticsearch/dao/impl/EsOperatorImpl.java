package huawei.elasticsearch.dao.impl;

import huawei.elasticsearch.dao.IEsOperator;
import huawei.elasticsearch.tool.EsConnection;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;

import com.alibaba.fastjson.JSON;

public class EsOperatorImpl implements IEsOperator 
{

	private static final String ip = "192.168.249.38";
	
	public IndexResponse createIndex(String indexName, String type, String document,
			Map<String,Object> keyColumns,Map<String,Object> indexColumns) 
	{
		Client client = EsConnection.getEsConnection(ip);
		keyColumns.putAll(indexColumns);
		
		IndexResponse response = client.prepareIndex(indexName, type, document)
				.setRouting("")
		        .setSource(JSON.toJSONString(keyColumns, true))
		        .execute()
		        .actionGet();
		return response;

	}
	
	public GetResponse searchIndex(String indexName, String type, String document)
	{
		Client client = EsConnection.getEsConnection(ip);
		GetResponse response = client.prepareGet(indexName, type, document)
				.setOperationThreaded(false)
		        .execute()
		        .actionGet();
		return response;
	}
	
	public UpdateResponse updateIndex(String indexName, String type, String document, Map<String,Object> indexColumns)
	{
		Client client = EsConnection.getEsConnection(ip);
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index(indexName);
		updateRequest.type(type);
		updateRequest.id(document);
		updateRequest.doc(JSON.toJSONString(indexColumns, true));
		UpdateResponse response = null;
		try {
			response = client.update(updateRequest).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return response;
	}
}
