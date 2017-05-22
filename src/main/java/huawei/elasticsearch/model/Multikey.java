package huawei.elasticsearch.model;

import com.alibaba.fastjson.JSON;

public class Multikey 
{
	private Integer a;
	private Integer b;
	private Integer c;
	private Integer d;
	private String name;
	
	public Integer getA() {
		return a;
	}
	public void setA(Integer a) {
		this.a = a;
	}
	public Integer getB() {
		return b;
	}
	public void setB(Integer b) {
		this.b = b;
	}
	public Integer getC() {
		return c;
	}
	public void setC(Integer c) {
		this.c = c;
	}
	public Integer getD() {
		return d;
	}
	public void setD(Integer d) {
		this.d = d;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
