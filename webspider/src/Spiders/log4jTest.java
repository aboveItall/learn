package Spiders;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class log4jTest {
	
	private static Logger logger = LogManager.getLogger(log4jTest.class);
			
	public static void main(String[] args) throws InterruptedException {
		
		//PropertyConfigurator.configure("log4j.properties");//���������ļ�

		logger.error("Did it again!");
		logger.info("����info��Ϣ"); 
        logger.debug("����debug��Ϣ");
        logger.warn("����warn��Ϣ");
        logger.fatal("����fatal��Ϣ");
        logger.log(Level.DEBUG, "����debug��Ϣ");
        logger.trace("�Ѿ�����");
	}

}
   