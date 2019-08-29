package myJsoup;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Demo1 {

	/**
	 * ���������
	 */
	
	public static void main(String[] args) {
		//����url��username��password
		String url = "http://code.tarena.com.cn/CGBCode/cgb1904/03-web/00_%c8%ed%bc%fe%d7%ca%c1%cf/mariadb-10.3.7-winx64.msi";
		String username = "tarenacode";
		String password = "code_2017";
		//������Ȩ��Ϣ
		String authString = username + ":" + password;
		String encodedString = Base64.getEncoder().encodeToString(authString.getBytes());
		
		//��ȡConnection
		Connection connection = Jsoup.connect(url).header("Authorization", "Basic " + encodedString);
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
			downLoader(connection,url);
		}
		
		
//		downLoad(document);
	}
	/**
	 * Jsoup������Դ
	 * @param connection
	 */
	private static void downLoader(Connection connection,String url) {
		//����response
		Response response = null;
		try {
			response = Jsoup.connect(url).maxBodySize(1024*1024*1024*20).response();
			String contentType = response.contentType();
			System.out.println(contentType);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("����ʱ��ȡresponseʧ�ܣ�");
		}
		BufferedInputStream bodyStream = response.bodyStream();
		//�����ļ���
		String fileName = url.substring(url.indexOf("cgb1904"));
		System.out.println("fileName��" + fileName);
		try {
			FileUtils.copyInputStreamToFile(bodyStream, new File("E:/mariadb-10.3.7-winx64.msi"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		


	}

	
	
	
	
	
	
	
	
	
	
	
//	
//	private static void downLoad(Document document) {
//		if (document == null || document.childNodeSize() == 0) {
//			System.out.println("docΪ�գ�");
//			return;
//		}
//		Elements links = document.getElementsByTag("a");
//		for (Element element : links) {
//			String tempHref = element.attr("href");
//			if(!"../".equals(tempHref)) {
//				System.out.println(tempHref);
//			}
//		}
//		
//	}

	

}
