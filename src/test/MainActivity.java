package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.android.framework.R;
import org.zw.android.framework.IAccessDatabase;
import org.zw.android.framework.IBitmapDownloader;
import org.zw.android.framework.cache.RecyclingImageView;

import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends BaseActivity {
	
	static String url = "http://img10.3lian.com/d0214/file/2011/11/25/89a92fa59782aa6eb6bf106912a29fab.jpg" ;
	static String url2 = "http://i8.hexunimg.cn/2011-12-06/136028951.jpg" ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		IAccessDatabase db = mFramework.getAccessDatabase() ;
		
		
		RecyclingImageView view = (RecyclingImageView) findViewById(R.id.bitmap_view);
		RecyclingImageView view2 = (RecyclingImageView) findViewById(R.id.bitmap_view_2);
//		
		IBitmapDownloader downloader = mFramework.getBitmapDownloader() ;
//		
		downloader.downloadBitmap("mnt/sdcard/journey_time.png", view) ;
		downloader.downloadBitmap(url2, view2, R.drawable.ic_launcher) ;
		
//		mFramework.getAsyncExecutor().executeTask(new IAsyncTask() {
//			
//			@Override
//			public Object onProcessing() {
//				System.out.println("-----------------");
//				
//				return null;
//			}
//		}) ;
		
//		db.deleteTable(Student.class);
//		db.execute("select * from student", null);
//		db.updateTable(Student.class);
//		
//		
		List<Student> list = db.queryObjects("select * from Student", Student.class);
		
		List<Student> l = new ArrayList<Student>();
		byte[] icon = new byte[]{0,1,2,3,4};
//		
		Student stu = new Student() ;
		stu.setName("周伟");
		stu.setSex((short)2);
		stu.setBirtherday(new Date());
		stu.setIcon(icon) ;
		stu.setM((byte)128);
		stu.setAddress("成都市高新区新乐北巷2号");
		l.add(stu);
		
		stu = new Student() ;
		stu.setName("周伟2");
		stu.setSex((short)2);
		stu.setBirtherday(new Date());
		stu.setIcon(icon) ;
		stu.setM((byte)128);
		stu.setAddress("成都市高新区新乐北巷2号");
		l.add(stu);
		
		stu = new Student() ;
		stu.setName("周伟3");
		stu.setSex((short)2);
		stu.setBirtherday(new Date());
		stu.setIcon(icon) ;
		stu.setM((byte)128);
		stu.setAddress("成都市高新区新乐北巷2号");
		l.add(stu);
		
		db.saveObjectList(l);
////		
		list = db.queryObjects("select * from Student", Student.class);
//		
		System.out.println("--------" + list);
//		
//		list = db.queryObjects("select * from Student where sex = ?", new String[]{String.valueOf(2)}, Student.class);
		
//		List<Student> list = new ArrayList<Student>();
//		
//		for(int i = 0 ; i < 1000 ; i++){
//			Student stu = new Student();
//			stu.setName("zhouwei id = " + i);
//			list.add(stu);
//		}
//		
//		long enter = System.currentTimeMillis() ;
//		db.saveObjectList(list);
//		System.out.println(" 消耗时间 >> " + (System.currentTimeMillis() - enter));
//		
//		int index = 0 ;
////		
//		for(Student su : list){
//			
////			db.deleteObject(su);
//			
////			su.setName("insert " + su.getName() + " id = " + index);
////			
////			db.saveObject(su);
////			
////			index++ ;
//		}
//		
//		list = db.queryObjects("select * from Student", Student.class);
//		
//		for(Student su : list){
//			
//			if(su == null || su.getName() == null){
//				System.out.println("----------");
//			} else {
//				System.out.println(su.getName());
//			}
//		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
