package huawei.elasticsearch.tool;

public class ElasticRepairMain {

	public static void main(String[] args)throws Exception {
		ElasticRepair er = new ElasticRepair();
		new Thread(er).start();
		Thread.sleep(100000);
		er.setStart(false);
	}

}
