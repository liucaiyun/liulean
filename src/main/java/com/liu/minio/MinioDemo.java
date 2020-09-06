package com.liu.minio;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * minioDemo
 */
public class MinioDemo {
	 
	/**
	 * 文档上传
	 * @param args
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws XmlPullParserException
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeyException, XmlPullParserException {
		    try {
		      // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
		      MinioClient minioClient = new MinioClient("http://10.10.58.204:9000", "AKIAIOSFODNN7EXAMPLE", "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY");

		      // 检查存储桶是否已经存在
		      boolean isExist = minioClient.bucketExists("asiatrip");
		      if(isExist) {
		        System.out.println("Bucket already exists.");
		      } else {
		        // 创建一个名为asiatrip的存储桶，用于存储照片的zip文件。
		        minioClient.makeBucket("asiatrip");
		      }

		      // 使用putObject上传一个文件到存储桶中。
		      minioClient.putObject("asiatrip","fad.png", "E:/fad.png");
		      //minioClient.getObject(bucketName, objectName, offset)
		      System.out.println("/home/user/Photos/asiaphotos.zip is successfully uploaded as asiaphotos.zip to `asiatrip` bucket.");
		    } catch(MinioException e) {
		      System.out.println("Error occurred: " + e);
		    }
		  }
	 /**
	  * 下载图片
	  */
	 @Test
	 public void down(){
		 InputStream stream=null;
		 FileOutputStream fileO=null;
		 try {
			  // 调用statObject()来判断对象是否存在。
			  // 如果不存在, statObject()抛出异常,
			  // 否则则代表对象存在。
		      MinioClient minioClient = new MinioClient("http://10.10.58.204:9000", "AKIAIOSFODNN7EXAMPLE", "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY");

			  minioClient.statObject("asiatrip", "cc.jpg");

			  // 获取"myobject"的输入流。
			   stream = minioClient.getObject("asiatrip", "cc.jpg");
              File file=new File("E:/123.jpg");
              fileO=new FileOutputStream(file);
			  // 读取输入流直到EOF并打印到控制台。
			  byte[] buf = new byte[16384];
			  int bytesRead;
			  while ((bytesRead = stream.read(buf, 0, buf.length)) >= 0) {
				  fileO.write(buf);
				 
			  }
			  fileO.flush();
			} catch (Exception e) {
			  System.out.println("Error occurred: " + e);
			}finally{
				 try {
					stream.close();
					fileO.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
	 }
}
