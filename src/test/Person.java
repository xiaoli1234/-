package test;

import org.zw.android.framework.db.ColumnString;

public class Person {

	@ColumnString(length=32)
	private String name ;
	
	@ColumnString(length=32)
	private String address ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
