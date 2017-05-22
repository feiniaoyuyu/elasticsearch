package huawei.elasticsearch;

import huawei.elasticsearch.dao.ICassOperator;
import huawei.elasticsearch.dao.impl.CassOperatorImpl;
import huawei.elasticsearch.model.Multikey;

import java.util.List;

public class CassMain {

	public static void main(String[] args) {
		ICassOperator cass = new CassOperatorImpl();
		Object partitionKey = null;
		Object[] clusterKey = new Object[3];
		Multikey latestMultikey = null;
		int lastSize = 0;
		while(true)
		{
			List<Multikey> result = cass.queryMachineForPage(lastSize, partitionKey, clusterKey);
			if(!result.isEmpty())
			{
				latestMultikey = result.get(result.size() - 1);
			}
			
			if(lastSize == 0 && result.isEmpty())
			{
				break;
			}
			System.out.println(result);
			System.out.println("================================");
			partitionKey = latestMultikey.getA();
			lastSize = result.size();
			clusterKey[0] = latestMultikey.getB();
			clusterKey[1] = latestMultikey.getC();
			clusterKey[2] = latestMultikey.getD();
		}
	}

}
