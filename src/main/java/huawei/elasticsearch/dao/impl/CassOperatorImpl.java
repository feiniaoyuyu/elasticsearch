package huawei.elasticsearch.dao.impl;

import huawei.elasticsearch.dao.ICassOperator;
import huawei.elasticsearch.model.Machine;
import huawei.elasticsearch.model.Multikey;
import huawei.elasticsearch.tool.CassSessionFactory;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.common.lang3.StringUtils;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;

public class CassOperatorImpl implements ICassOperator
{

	private static final String QUERY_ALL_MECHINE = "select * from mycas.t_sds_machine;";
	private static final int FETCH_SIZE = 20;
	private static final String QUERY_FIRST = "select * from mycas.t_sds_multikey limit ?;";
	private static final String QUERY_SAME_PARTITION_KEY = "select * from mycas.t_sds_multikey where token(partitionKey)=token(?) and clusterKeys limit ? ALLOW FILTERING;";
	private static final String QUERY_DIFFER_PARTITION_KEY = "select * from mycas.t_sds_multikey where token(partitionKey)>token(?) limit ?;";
	private static final String[] columns = new String[]{"b","c","d"};
	private static final String partitionKeyName = "a";
	
	public List<Machine> queryMachine() 
	{	
		List<Machine> machines = new ArrayList<Machine>();
		Session session = CassSessionFactory.getCassSession();
		Statement statement = new SimpleStatement(QUERY_ALL_MECHINE);
		statement.setFetchSize(FETCH_SIZE);
		ResultSet rows = session.execute(statement);
		for (Row row : rows) 
		{
			Machine machine = new Machine();
			machine.setId(row.getInt("id"));
			machine.setIp(row.getString("ip"));
			machine.setName(row.getString("name"));
			machine.setType(row.getInt("type"));
			
			System.out.println(row.getObject("id"));
			System.out.println(row.getObject("ip"));
			System.out.println(row.getObject("name"));
			System.out.println(row.getObject("type"));
			
			machines.add(machine);
		}
		return machines;
	}

	public List<Multikey> queryMachineForPage(int lastSize,Object partitionKey,Object[] clusterKeys) {
		Session session = CassSessionFactory.getCassSession();
		BoundStatement bindStatement = null;
		if(null == partitionKey)
		{
			bindStatement = new BoundStatement(session.prepare(QUERY_FIRST)).bind(LIMIT_NUM);
		}
		else if(LIMIT_NUM == lastSize)
		{
			//clusterKeys
			String clusterKeyCondition = "("+StringUtils.join(columns,",")+")>("+StringUtils.join(clusterKeys, ",") +")";
			bindStatement = new BoundStatement(session.prepare(QUERY_SAME_PARTITION_KEY.replace("clusterKeys", clusterKeyCondition).replace("partitionKey", partitionKeyName))).bind(partitionKey,LIMIT_NUM);
		}
		else
		{
			bindStatement = new BoundStatement(session.prepare(QUERY_DIFFER_PARTITION_KEY.replace("partitionKey", partitionKeyName))).bind(partitionKey,LIMIT_NUM);;
		}
		
		ResultSet rs = session.execute(bindStatement);
		List<Multikey> multikeys = new ArrayList<Multikey>();
		for(Row row : rs)
		{
			Multikey m = new Multikey();
			m.setA(row.getInt("a"));
			m.setB(row.getInt("b"));
			m.setC(row.getInt("c"));
			m.setD(row.getInt("d"));
			m.setName(row.getString("name"));
			
			multikeys.add(m);
		}
		return multikeys;
	}
	
	
}
