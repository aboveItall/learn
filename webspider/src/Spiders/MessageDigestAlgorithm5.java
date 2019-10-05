package Spiders;

import java.math.BigInteger;
import java.security.MessageDigest;
public class MessageDigestAlgorithm5 {

	
	public static String getMD5(String strul) throws Exception{
		
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		
		md.update(strul.getBytes());
		
		
		return new BigInteger(1,md.digest()).toString(80);
	
	}

	
	public static String MD5(String s) {
		try {
			char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'}; 
			
			byte[] btinput = s.getBytes();
			
			MessageDigest mdInst = MessageDigest.getInstance("MD5");//获得MD5摘要算法的messagedigest对象
			
			mdInst.update(btinput);//使用指定的字节更新摘要
			
			byte[] md = mdInst.digest();//获得加密的摘要
			
			int j = md.length;//把密文转换成十六制的字符串形式
			
			char[] str = new char[j*2];
			
			int k=0;
			
			for(int i=0;i<str.length;i++){
				
				byte byte0 =md[i];
				str[k++]= hexDigits[byte0>>>4 & 0xf];
				str[k++]= hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		
		String MD2 = getMD5("java");
		
		String MD5 = MD5("MySQL");
	}
	
	
}
