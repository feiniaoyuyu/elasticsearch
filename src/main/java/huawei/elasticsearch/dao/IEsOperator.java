package huawei.elasticsearch.dao;

import java.util.Map;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;

public interface IEsOperator 
{
	/**
	 * 创建
	 * @param indexName
	 * @param type
	 * @param keyColumns
	 * @param indexColumns
	 * @return
	 */
	IndexResponse createIndex(String indexName, String type, String document,
			Map<String,Object> keyColumns,Map<String,Object> indexColumns);

	/**
	 * 查询
	 * @param indexName
	 * @param type
	 * @param document
	 * @return
	 */
	GetResponse searchIndex(String indexName, String type, String document);
	
	/**
	 * 更新
	 * @param indexName
	 * @param type
	 * @param document
	 * @param indexColumns
	 * @return
	 */
	public UpdateResponse updateIndex(String indexName, String type, String document,
			Map<String,Object> indexColumns);
}
