package myJsoup;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import sun.net.www.http.HttpClient;

public class TestDemo {

	@Test
	public void test01() throws IOException {
		//定义url、username、password
		String url = "http://code.tarena.com.cn/CGBCode/cgb1904/03-web/00_%c8%ed%bc%fe%d7%ca%c1%cf/mariadb-10.3.7-winx64.msi";
		String username = "tarenacode";
		String password = "code_2017";
		//加密授权信息
		String authString = username + ":" + password;
		String encodedString = Base64.getEncoder().encodeToString(authString.getBytes());
		//创建HttpClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://code.tarena.com.cn/CGBCode/cgb1904/03-web/00_%c8%ed%bc%fe%d7%ca%c1%cf/mariadb-10.3.7-winx64.msi");
		//添加请求头信息
		httpGet.addHeader("Authorization", "Basic " + encodedString);
		//发送请求，获取响应
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpEntity entity = response.getEntity();
		//获取文件长度
		long contentLength = entity.getContentLength();
		System.out.println(contentLength);
		//获取下载文件流
		InputStream content = null;
		try {
			content = entity.getContent();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("获取下载文件流失败");
		}
		File file = new File("d:/aa.msi");
		FileOutputStream fileout = new FileOutputStream(file);
		 /**
         * 根据实际运行效果 设置缓冲区大小
         */
        byte[] buffer = new byte[2048];
        int ch = 0;
        while ((ch = content.read(buffer)) != -1) {
            fileout.write(buffer, 0, ch);
        }
        content.close();
        fileout.flush();
        fileout.close();
		
	}
}
