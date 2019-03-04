package test;

import java.util.Date;

import org.zw.android.framework.db.ColumnBinary;
import org.zw.android.framework.db.ColumnByte;
import org.zw.android.framework.db.ColumnDate;
import org.zw.android.framework.db.ColumnInt;
import org.zw.android.framework.db.ColumnShort;
import org.zw.android.framework.db.ColumnString;
import org.zw.android.framework.db.Table;
import org.zw.android.framework.db.core.ColumnPrimaryKey;
import org.zw.android.framework.db.core.ColumnPrimaryKey.PrimaryKeyType;

@Table(TableName = "Student")
public class Student extends Person {

	@ColumnPrimaryKey(Type = PrimaryKeyType.AUTO)
	@ColumnInt
	private int id ;
	
	@ColumnString(length=32)
	private short sex ;
	
	@ColumnDate
	private Date birtherday ;
	
	@ColumnBinary
	private byte[] icon ;
	
	@ColumnByte
	private byte m ;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public short getSex() {
		return sex;
	}

	public void setSex(short sex) {
		this.sex = sex;
	}

	public Date getBirtherday() {
		return birtherday;
	}

	public void setBirtherday(Date birtherday) {
		this.birtherday = birtherday;
	}

	public byte[] getIcon() {
		return icon;
	}

	public byte getM() {
		return m;
	}

	public void setM(byte m) {
		this.m = m;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}
}
