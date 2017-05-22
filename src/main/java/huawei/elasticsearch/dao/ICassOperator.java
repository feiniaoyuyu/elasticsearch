package huawei.elasticsearch.dao;

import huawei.elasticsearch.model.Machine;
import huawei.elasticsearch.model.Multikey;

import java.util.List;

public interface ICassOperator 
{
	static final Integer LIMIT_NUM = 2;
	
	/**
	 * 查询全部Machine
	 * @return
	 */
	List<Machine> queryMachine();
	
	List<Multikey> queryMachineForPage(int lastSize,Object partitionKey,Object[] clusterKey);
	
}
