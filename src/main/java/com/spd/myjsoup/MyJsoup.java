package com.spd.myjsoup;

import java.io.IOException;
import java.util.Base64;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 主程序入口
 * @author shoupadu
 *
 */
public class MyJsoup {
	//定义url、username、password
	private static final String URL = "http://code.tarena.com.cn/CGBCode/cgb1904/";
	private static final String USERNAME = "tarenacode";
	private static final String PASSWORD = "code_2017";

	public static void main(String[] args) {

		String encodedAuthString = getEncodedAuthString();
		String rootPath = "E:/";
		getDocumentAndDownload(URL,encodedAuthString,rootPath);

	}

	/**
	 * 获取达内代码服务器内容并进行迭代下载
	 * @param url2
	 * @param encodedAuthString
	 * @param rootPath
	 */
	private static void getDocumentAndDownload(String url, String encodedAuthString, String rootPath) {
		//获取Connection
		Connection connection = Jsoup.connect(url).header("Authorization", "Basic " + getEncodedAuthString());
		//定义Document
		Document document = null;
		try {
			//获取document
			document = connection.ignoreContentType(true).timeout(10000).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//进行校验，如果为空则为下载链接，下载文件即可，否则进行迭代
		if(document == null) {
			System.out.println("下载资源>>" + url );
		}

	}

	/**
	 * 获取Base64加密后的认证密码
	 * 达内代码服务器采用HTTP基本认证(Basic Authentication)，所以用户名和密码需要以此方式加密，放在请求头进行请求，可百度获取更多信息
	 * @return
	 */
	private static String getEncodedAuthString() {
		String authString = USERNAME + ":" + PASSWORD;
		String encodedString = Base64.getEncoder().encodeToString(authString.getBytes());
		return encodedString;
	}




}
