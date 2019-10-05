package Spiders;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class log4jTest {
	
	private static Logger logger = LogManager.getLogger(log4jTest.class);
			
	public static void main(String[] args) throws InterruptedException {
		
		//PropertyConfigurator.configure("log4j.properties");//加载配置文件

		logger.error("Did it again!");
		logger.info("我是info信息"); 
        logger.debug("我是debug信息");
        logger.warn("我是warn信息");
        logger.fatal("我是fatal信息");
        logger.log(Level.DEBUG, "我是debug信息");
        logger.trace("已经结束");
	}

}
   