package huawei.elasticsearch.task;

import huawei.elasticsearch.dao.ICassOperator;
import huawei.elasticsearch.dao.impl.CassOperatorImpl;
import huawei.elasticsearch.model.Machine;

import java.util.List;

public class MainTest {

	public static void main(String[] args) {
		ICassOperator cassOperator = new CassOperatorImpl();
		List<Machine> machines = cassOperator.queryMachine();
	}

}
