package spider;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HttpUtil {
	
	public static void main(String[] args){
		
	try{
			
		String[] spider ={"https://www.chsi.com.cn/help/wzdh.jsp"};
		
		for(String contr :spider){
			
			Document document = Jsoup.connect(contr).userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)").get();
			System.out.println(contr);
			String pathname=contr;
			pathname= pathname.replace("https://", "");
			pathname = pathname.replace('/', ' ');             
			pathname = pathname.replace('\\', ' ');              
			pathname = pathname.replace(':', ' ');              
			pathname = pathname.replace('<', ' ');              
			pathname = pathname.replace('>', ' ');              
			pathname = pathname.replace('|', ' ');
			pathname = pathname.replace("","");
			pathname=pathname+".txt";
	
			FileOutputStream os = new FileOutputStream(pathname,true);
			os.write(contr.getBytes("utf-8"));
			os.write("\r\n".getBytes());
			os.write(document.html().getBytes("utf-8"));
			
		}
		}catch (Exception e) {
			try {
				FileOutputStream file  = new FileOutputStream("F:/java.txt",true);
				file.write(e.toString().getBytes());
				file.write("\r\n".getBytes());
				System.out.println(e);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		

	}

}
