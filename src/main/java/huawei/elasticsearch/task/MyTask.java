package huawei.elasticsearch.task;

import huawei.elasticsearch.dao.ICassOperator;
import huawei.elasticsearch.dao.IEsOperator;
import huawei.elasticsearch.dao.impl.CassOperatorImpl;
import huawei.elasticsearch.dao.impl.EsOperatorImpl;
import huawei.elasticsearch.model.Machine;
import huawei.elasticsearch.tool.TimeTool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateResponse;

public class MyTask	extends TimerTask  
{

	private ICassOperator cassOperator = new CassOperatorImpl();
	private IEsOperator	esOperator = new EsOperatorImpl();
	
	@Override
	public void run() 
	{
		List<Machine> machines = cassOperator.queryMachine();
		
		try {
			for (Machine machine : machines) {
				String document = machine.getId()+"_"+machine.getIp();
				GetResponse response = esOperator.searchIndex("mycas", "t_sds_machine", document);
				if(response.isExists())
				{
					if(machine.getName().equals(response.getSource().get("name")))
					{
						// 数据一致则无需任何处理
						continue;
					}
					
					// 不一致则进行修复, 将索引同步与cassandra数据一致
					Map<String,Object> indexMap = new HashMap<String,Object>();
					indexMap.put("name", machine.getName());
					UpdateResponse upResponse = esOperator.updateIndex("mycas", "t_sds_machine", document, indexMap);
					// 记录文件
					noteRepairResult(upResponse,"mycas","t_sds_machine",document);
				}
				
				// 索引不存在,则创建
				Map<String, Object> keyColumns = new HashMap<String,Object>();
				Map<String, Object> indexColumns = new HashMap<String,Object>();
				keyColumns.put("id", machine.getId());
				keyColumns.put("ip", machine.getIp());
				indexColumns.put("name", machine.getName());
				esOperator.createIndex("mycas", "t_sds_machine", document, keyColumns, indexColumns);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void noteRepairResult(UpdateResponse response,String indexName, String type, String document)
	{
		// 写文件:修复结果(成功or失败)、修复时间、修复目标
		String content = TimeTool.getCurrentTime() + " : repair " + 
				indexName + " " + type + " " + document;
		System.out.println(response.isCreated());
		if(!response.isCreated())
		{
			content += " succeed.";
		}
		else
		{
			content += " failed.";
		}
		content += "\r\n";
		File resultFile = new File("D:/repair.txt");
		try {
			if(!resultFile.exists())
			{
				resultFile.createNewFile();
			}
			BufferedWriter out = null; 
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(resultFile, true)));     
			out.write(content);
            out.flush();
            out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		Timer timer = new Timer();
		MyTask task = new MyTask();
		timer.schedule(task, 0, 60000);
	}
}
