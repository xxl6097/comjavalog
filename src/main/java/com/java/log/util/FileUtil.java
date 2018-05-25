package com.java.log.util;

import java.io.*;

public class FileUtil {

	public static byte[] toByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int n = 0;
		while ((n = in.read(buffer)) != -1) {
			out.write(buffer, 0, n);
		}
		return out.toByteArray();
	}


	public static String readFile(File file){
	    String str="";
	    FileInputStream in = null;
	    try {
	        in = new FileInputStream(file);
	        int size = in.available();
	        byte[] buffer = new byte[size];
	        in.read(buffer);
	        in.close();
	        str = new String(buffer,"utf-8");
	    } catch (IOException e) {
	    	e.printStackTrace();
	        return null;
	    }finally {
	    	if(in != null){
	    		try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				};
	    	}
		}
	    return str;
	}
	
	public static String readFile(InputStream in){
	    String str="";
	    try {
	        int size=in.available();
	        byte[] buffer=new byte[size];
	        in.read(buffer);
	        in.close();
	        str=new String(buffer,"utf-8");
	    } catch (IOException e) {
	    	e.printStackTrace();
	        return null;
	    }finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    return str;
	}


	public static byte[] loadContent(String fileName) throws IOException {
		InputStream input = null;
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			File file = new File(fileName);
			input = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int size;
			while (-1 != (size = input.read(buffer))) {
				output.write(buffer, 0, size);
			}
			output.flush();
			return output.toByteArray();
		} catch (FileNotFoundException e) {
			return null;
		} finally {
			try {
				if (null != input) {
					input.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


}


