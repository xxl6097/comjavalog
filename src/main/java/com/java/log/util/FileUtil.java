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


	/**
	 * 删除文件，可以是文件或文件夹
	 *
	 * @param fileName
	 *            要删除的文件名
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			System.out.println("删除文件失败:" + fileName + "不存在！");
			return false;
		} else {
			if (file.isFile())
				return deleteFile(fileName);
			else
				return deleteDirectory(fileName);
		}
	}

	/**
	 * 删除单个文件
	 *
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}

	/**
	 * 删除目录及目录下的文件
	 *
	 * @param dir
	 *            要删除的目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			System.out.println("删除目录失败：" + dir + "不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = deleteDirectory(files[i]
						.getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			System.out.println("删除目录失败！");
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			System.out.println("删除目录" + dir + "成功！");
			return true;
		} else {
			return false;
		}
	}

}


