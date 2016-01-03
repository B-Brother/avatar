package com.alibaba.avatar.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import com.alibaba.avatar.client.entity.ListData;
import com.alibaba.avatar.client.entity.MetaData;

public class AvatarDataTransfer {

	// 配置中心域名
	private static String AVATAR_HOST = "http://www.avatar-config.com:8099";
	
	public static String sendRegister(MetaData meta, ListData data){
		StringBuffer response = new StringBuffer();
		
		StringBuffer urlBuffer = new StringBuffer(AVATAR_HOST);
		urlBuffer.append("?");
		urlBuffer.append("group=");
		urlBuffer.append(meta.getGroup());
		urlBuffer.append("&dataId=");
		urlBuffer.append(meta.getDataId());
		urlBuffer.append("&subDataId=");
		urlBuffer.append(data.getSubDataId());
		urlBuffer.append("&content=");
		urlBuffer.append(data.getContent());
		
		try {
			URL url = new URL(urlBuffer.toString()); 
			  
			URLConnection connection = url.openConnection();
			
			InputStream is = connection.getInputStream();  
			
            Scanner sc = new Scanner(is);  
            
            while(sc.hasNextLine()){  
                response.append(sc.nextLine());
                response.append("\r\n");
            }  
			  
            sc.close();
            is.close(); 
		} catch (MalformedURLException e) { 
			e.printStackTrace();
		} catch (IOException e) {  
			e.printStackTrace();
		}
	
		return response.toString();
	} 
	
}
