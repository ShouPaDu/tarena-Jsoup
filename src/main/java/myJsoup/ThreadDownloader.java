package myJsoup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
 
/**
 * ���߳�����
 * 
 * @author jam
 *
 */
public class ThreadDownloader {
 
	public static void main(String[] args) throws IOException, InterruptedException {
		// ��¼��ʼ���ص�ʱ��
		long begin_time = new Date().getTime();
 
		// ����һ��URL����
		// ��hao123��վ����һ�����뷨�����������ص�ַ
		URL url = new URL("http://code.tarena.com.cn/CGBCode/cgb1904/05-jt/code/day01/Day01.docx");
 
		
		// ��ȡ����
		URLConnection conn = url.openConnection();
		
		// ��ȡ�ļ�ȫ·��
		String fileName = url.getFile();
 
		// ��ȡ�ļ���
		fileName = fileName.substring(fileName.lastIndexOf("/"));
 
		System.out.println("��ʼ����>>>");
 
		// ��ȡ�ļ���С
		int fileSize = conn.getContentLength();
 
		System.out.println("�ļ��ܹ���С��" + fileSize + "�ֽ�");
 
		// ���÷ֿ��С
		int blockSize = 1024 * 1024;
		// �ļ��ֿ������
		int blockNum = fileSize / blockSize;
 
		if ((fileSize % blockSize) != 0) {
			blockNum += 1;
		}
 
		System.out.println("�ֿ���->�߳�����" + blockNum);
 
		Thread[] threads = new Thread[blockNum];
		for (int i = 0; i < blockNum; i++) {
 
			// ��������������Ҫ�õ��ı���
			final int index = i;
			final int finalBlockNum = blockNum;
			final String finalFileName = fileName;
 
			// ����һ���߳�
			threads[i] = new Thread() {
				public void run() {
					try {
 
						// ���»�ȡ����
						URLConnection conn = url.openConnection();
						// ���»�ȡ��
						InputStream in = conn.getInputStream();
						// ������ʼ�ͽ�����
						int beginPoint = 0, endPoint = 0;
 
						System.out.print("��" + (index + 1) + "���ļ���");
						beginPoint = index * blockSize;
 
						// �жϽ�����
						if (index < finalBlockNum - 1) {
							endPoint = beginPoint + blockSize;
						} else {
							endPoint = fileSize;
						}
 
						System.out.println("��ʼ�ֽ�����" + beginPoint + ",�����ֽ�����" + endPoint);
 
						// �����ص��ļ��洢��һ���ļ�����
						//�����ļ��в�����ʱ�����½�
						File filePath = new File("E:/temp_file/");
						if (!filePath.exists()) {
							filePath.mkdirs();
						}
						
						FileOutputStream fos = new FileOutputStream(new File("E:/temp_file/", finalFileName + "_" + (index + 1)));
 
						// ���� beginPoint���ֽڽ��ж�ȡ
						in.skip(beginPoint);
						byte[] buffer = new byte[1024];
						int count;
						// ���嵱ǰ���ؽ���
						int process = beginPoint;
						// ��ǰ���ȱ���С�ڽ����ֽ���
						while (process < endPoint) {
 
							count = in.read(buffer);
							// �ж��Ƿ�������һ��
							if (process + count >= endPoint) {
								count = endPoint - process;
								process = endPoint;
							} else {
								// ���㵱ǰ����
								process += count;
							}
							// �����ļ���
							fos.write(buffer, 0, count);
 
						}
						fos.close();
						in.close();
 
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
 
			};
			threads[i].start();
 
		}
 
		// �������̶߳�����ʱ�ſ�ʼ�ļ��ĺϲ�
		for (Thread t : threads) {
			t.join();
		}
 
		// �����ļ��в����ڣ��򴴽�һ���ļ���
		File filePath = new File("E:/download/");
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		// �����ļ������
		FileOutputStream fos = new FileOutputStream("E:/download/" + fileName);
		for (int i = 0; i < blockNum; i++) {
			FileInputStream fis = new FileInputStream("E:/temp_file/" + fileName + "_" + (i + 1));
			byte[] buffer = new byte[1024];
			int count;
			while ((count = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, count);
			}
			fis.close();
		}
		fos.close();
 
		long end_time = new Date().getTime();
		long seconds = (end_time - begin_time) / 1000;
		long minutes = seconds / 60;
		long second = seconds % 60;
 
		System.out.println("�������,��ʱ��" + minutes + "��" + second + "��");
 
	}
 
}
