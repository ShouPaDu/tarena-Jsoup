package com.spd.myjsoup;

import java.io.IOException;
import java.util.Base64;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * ���������
 * @author shoupadu
 *
 */
public class MyJsoup {
	//����url��username��password
	private static final String URL = "http://code.tarena.com.cn/CGBCode/cgb1904/";
	private static final String USERNAME = "tarenacode";
	private static final String PASSWORD = "code_2017";

	public static void main(String[] args) {

		String encodedAuthString = getEncodedAuthString();
		String rootPath = "E:/";
		getDocumentAndDownload(URL,encodedAuthString,rootPath);

	}

	/**
	 * ��ȡ���ڴ�����������ݲ����е�������
	 * @param url2
	 * @param encodedAuthString
	 * @param rootPath
	 */
	private static void getDocumentAndDownload(String url, String encodedAuthString, String rootPath) {
		//��ȡConnection
		Connection connection = Jsoup.connect(url).header("Authorization", "Basic " + getEncodedAuthString());
		//����Document
		Document document = null;
		try {
			//��ȡdocument
			document = connection.ignoreContentType(true).timeout(10000).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//����У�飬���Ϊ����Ϊ�������ӣ������ļ����ɣ�������е���
		if(document == null) {
			System.out.println("������Դ>>" + url );
		}

	}

	/**
	 * ��ȡBase64���ܺ����֤����
	 * ���ڴ������������HTTP������֤(Basic Authentication)�������û�����������Ҫ�Դ˷�ʽ���ܣ���������ͷ�������󣬿ɰٶȻ�ȡ������Ϣ
	 * @return
	 */
	private static String getEncodedAuthString() {
		String authString = USERNAME + ":" + PASSWORD;
		String encodedString = Base64.getEncoder().encodeToString(authString.getBytes());
		return encodedString;
	}




}
