package org.zw.android.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.http.protocol.HTTP;

public class InputStreamUtils {
	 

    final static int BUFFER_SIZE = 4096;  

      

    /** 

     * 将InputStream转换成String 

     * @param in InputStream 

     * @return String 

     * @throws Exception 

     *  

     */  

    public static String InputStreamTOString(InputStream in) throws Exception{  

          

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  

        byte[] data = new byte[BUFFER_SIZE];  

        int count = -1;  

        while((count = in.read(data,0,BUFFER_SIZE)) != -1)  

            outStream.write(data, 0, count);  

          

        data = null;  

        return new String(outStream.toByteArray(),HTTP.UTF_8);  

    }  

      

    /** 

     * 将InputStream转换成某种字符编码的String 

     * @param in 

     * @param encoding 

     * @return 

     * @throws Exception 

     */  

         public static String InputStreamTOString(InputStream in,String encoding) throws Exception{  

          

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  

        byte[] data = new byte[BUFFER_SIZE];  

        int count = -1;  

        while((count = in.read(data,0,BUFFER_SIZE)) != -1)  

            outStream.write(data, 0, count);  

          

        data = null;  

        return new String(outStream.toByteArray(),HTTP.UTF_8);  

    }  

      

    /** 

     * 将String转换成InputStream 

     * @param in 

     * @return 

     * @throws Exception 

     */  

    public static InputStream StringTOInputStream(String in) throws Exception{  

          

        ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes(HTTP.UTF_8));  

        return is;  

    }  

      

    /** 

     * 将InputStream转换成byte数组 

     * @param in InputStream 

     * @return byte[] 

     * @throws IOException 

     */  

    public static byte[] InputStreamTOByte(InputStream in) throws IOException{  

          

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  

        byte[] data = new byte[BUFFER_SIZE];  

        int count = -1;  

        while((count = in.read(data,0,BUFFER_SIZE)) != -1)  

            outStream.write(data, 0, count);  

          

        data = null;  

        return outStream.toByteArray();  

    }  

      

    /** 

     * 将byte数组转换成InputStream 

     * @param in 

     * @return 

     * @throws Exception 

     */  

    public static InputStream byteTOInputStream(byte[] in) throws Exception{  

          

        ByteArrayInputStream is = new ByteArrayInputStream(in);  

        return is;  

    }  

      

    /** 

     * 将byte数组转换成String 

     * @param in 

     * @return 

     * @throws Exception 

     */  

    public static String byteTOString(byte[] in) throws Exception{  

          

        InputStream is = byteTOInputStream(in);  

        return InputStreamTOString(is);  

    }
    
    public static  Object toObject (byte[] bytes) {      
        Object obj = null;      
        try {        
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);        
            ObjectInputStream ois = new ObjectInputStream (bis);        
            obj = ois.readObject();      
            ois.close();   
            bis.close();   
        } catch (IOException ex) {        
            ex.printStackTrace();   
        } catch (ClassNotFoundException ex) {        
            ex.printStackTrace();   
        }      
        return obj;    
    }  
    
    public static  byte[] toByteArray (Object obj) {      
        byte[] bytes = null;      
        ByteArrayOutputStream bos = new ByteArrayOutputStream();      
        try {        
            ObjectOutputStream oos = new ObjectOutputStream(bos);         
            oos.writeObject(obj);        
            oos.flush();         
            bytes = bos.toByteArray ();      
            oos.close();         
            bos.close();        
        } catch (IOException ex) {        
            ex.printStackTrace();   
        }      
        return bytes;    
    }  
}
