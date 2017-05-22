package huawei.elasticsearch.tool;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class EsConnection
{
	private static Client client;
	
	private EsConnection(){}
	
	public static synchronized Client getEsConnection(String ip)
	{
		if(client != null)
		{
			return client;
		}
		
		Settings settings = ImmutableSettings.settingsBuilder()
		        .put("cluster.name", "my-es")
		        .build();
		client = new TransportClient(settings)
        .addTransportAddress(new InetSocketTransportAddress(ip, 9300));
		
		return client;
	}
}
