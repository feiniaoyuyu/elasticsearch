package huawei.elasticsearch.tool;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class EsConnection
{
	private static transient Client client;
	
	private EsConnection(){}
	
	@SuppressWarnings("resource")
	public static Client getEsConnection(String ip)
	{
		if(client == null)
		{
			synchronized (EsConnection.class)
			{
				if (client == null)
				{
					Settings settings = ImmutableSettings.settingsBuilder()
					        .put("cluster.name", "my-es")
					        .build();
					client = new TransportClient(settings)
			        .addTransportAddress(new InetSocketTransportAddress(ip, 9300));
				}
			}
		}
		
		return client;
	}
}
