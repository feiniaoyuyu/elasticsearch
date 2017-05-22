package huawei.elasticsearch.tool;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CassSessionFactory 
{
	private static Session session;
	
	private CassSessionFactory(){}
	
	public static synchronized Session getCassSession()
	{
		if(session != null)
		{
			return session;
		}
		Cluster cluster = Cluster.builder().addContactPoint("192.168.249.38").build();;
		session = cluster.connect();
		return session;
	}
}
