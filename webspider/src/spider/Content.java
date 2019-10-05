package spider;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Content {
	
	public static void main(String[] args) {
		
		URL url = null;
		
		URLConnection urlcon=null;
		
		BufferedReader read=null;
		
		PrintWriter write=null;
		
		String regex = "http://[\\w+\\.?/?]+\\.[A-Za-z]+";

		Pattern p = Pattern.compile(regex);

		
		try {
			url = new URL("http://www.hnist.cn/");
			
			urlcon=url.openConnection();
			
			write = new PrintWriter(new FileWriter("G:/java.txt"),true);
			
			read = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
			
			String buff =null;
			
			while((buff=read.readLine())!=null){
				
				Matcher buf_m = p.matcher(buff);
				
				while(buf_m.find()){
					
					write.println(buf_m.group());
				}	
			}
			System.out.println("获取数据成功=====");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			read.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		write.close();
		
	}

}
