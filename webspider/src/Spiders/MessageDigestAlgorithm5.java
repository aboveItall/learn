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
			
			MessageDigest mdInst = MessageDigest.getInstance("MD5");//���MD5ժҪ�㷨��messagedigest����
			
			mdInst.update(btinput);//ʹ��ָ�����ֽڸ���ժҪ
			
			byte[] md = mdInst.digest();//��ü��ܵ�ժҪ
			
			int j = md.length;//������ת����ʮ���Ƶ��ַ�����ʽ
			
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
