package huawei.elasticsearch.tool;

public class ElasticRepair implements Runnable
{
	private boolean start = true;

	public void setStart(boolean status)
	{
		this.start = status;
	}
	
	public void run() {
		while(true)
		{
			if(!start)
			{
				break;
			}
			System.out.println(Thread.currentThread().getName() + ": repair");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
