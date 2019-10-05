package spider;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

public class Crawler {

    private ArrayList<ArrayList<String>> urlWaiting1 = new ArrayList<ArrayList<String>>();      //等待被处理的url，声明二维的原因是想第一维储存搜索到了树的第几层，第二维存储了这一层所有的链接
    private ArrayList<String> urlWaiting2=new ArrayList<String>();
    private ArrayList<String> urlProcessed = new ArrayList<String>();   //处理过的url
    private ArrayList<String> urlError = new ArrayList<String>();       //错误的url
    static int deep=2;                                                  //设置深度为2
    private int numFindUrl = 0;     //find the number of url
    static int height=deep-1;       //这是为了后面的循环方便
    public Crawler() {}


    /**
     * start crawling
     */
    public void begin() {            //开始抓取


        for(int i=0;i<=height;i++)       //一层一层的处理
        {

            ArrayList<String> urlWaiting_temp=new ArrayList<String>();
            urlWaiting_temp=urlWaiting1.get(i);
            while(!urlWaiting_temp.isEmpty()) {   //如果这一层非空就处理
                processURL(urlWaiting_temp.remove(0), i); //处理url函数，后面有定义
            }
        }
        //抓取结束后的一些日志，后面有log函数的定义
        log("finish crawling");
        log("the number of urls that were found:" + numFindUrl);
        log("the number of urls that were processed:" + urlProcessed.size());
        log("the number of urls that resulted in an error:" + urlError.size());
    }

    /**
     * Called internally to process a URL
     *
     * @param strUrl
     *            The URL to be processed.
     */
    public void processURL(String strUrl,int height) {  //处理url，传height的参的目的是记录当前处理的url是第几层的
        URL url = null;
        try {
            url = new URL(strUrl);
            log("Processing: " + url);
            // get the URL's contents
            URLConnection connection = url.openConnection();
            if ((connection.getContentType() != null)
                    && !connection.getContentType().toLowerCase()
                    .startsWith("text/")) {
                log("Not processing because content type is: "
                        + connection.getContentType());
                return;
            }

            // read the URL
            InputStream is = connection.getInputStream();
            Reader r = new InputStreamReader(is);
            // parse the URL
            HTMLEditorKit.Parser parse = new HTMLParse().getParser();
            parse.parse(r, new Parser(url,height), true); //解析url


        } catch (IOException e) {
            urlError.add(url.toString());
            log("Error: " + url);
            return;
        }
        // mark URL as complete
        urlProcessed.add(url.toString());  //添加url为已经处理
        log("Complete: " + url);
    }

    /**
     * Add a URL to waiting list.
     *
     * @param url
     */
    public void addURL(String url,int height) { //添加url时考虑到树的深度和urlWaiting1是二维的特性，代码可能写的有一些冗余了

        for(int i=0;i<urlWaiting1.size();i++)
        {
            if(urlWaiting1.get(i).contains(url))
            {

                return;
            }
        }
        if (urlError.contains(url)) {

            return;
        }
        if (urlProcessed.contains(url)) {

            return;
        }
        log("Adding to workload: " + url);
        if(height==0) {
            urlWaiting2.add(url);
            urlWaiting1.add(height, urlWaiting2);
            numFindUrl++;
        }
        else
        {

            if(urlWaiting1.size()<=height)   //判断如果是新的一层的url（也就是height增加了，就为urlWaiting1的索引加1。
            {
                ArrayList<String> urlTemp=new ArrayList<String>();
                urlTemp.add(url);
                urlWaiting1.add(height, urlTemp);
                numFindUrl++;
            }
            else {                       //如果这个url到来之前这一层已经有url了，那就先把那一层所有的url拿出来，把它放进去，再全放回urlWaiting1里。
                urlWaiting2 = urlWaiting1.remove(height);
                urlWaiting2.add(url);
                urlWaiting1.add(height, urlWaiting2);
                numFindUrl++;
            }
        }

    }

    /**
     * Called internally to log information This basic method just writes the
     * log out to the stdout.
     *
     * @param entry
     *            The information to be written to the log.
     */
    public void log(String entry) {
        System.out.println((new Date()) + ":" + entry);
    }

   //重设getParser为public
    protected class HTMLParse extends HTMLEditorKit {
        public HTMLEditorKit.Parser getParser() {
            return super.getParser();
        }
    }

    /**
     * A HTML parser callback used by this class to detect links
     *
     */
    protected class Parser extends HTMLEditorKit.ParserCallback {
        protected URL base;
        protected int height;
        public Parser(URL base,int height) {
            this.base = base;
            this.height=height;

        }
  //一个主要的回调函数，这里值关心了href标签
        public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {


            String href = (String) a.getAttribute(HTML.Attribute.HREF);

            if ((href == null) && (t == HTML.Tag.FRAME))
                href = (String) a.getAttribute(HTML.Attribute.SRC);

            if (href == null) {

                return;

            }
            int i = href.indexOf('#');
            if (i != -1)
                href = href.substring(0, i);

            if (href.toLowerCase().startsWith("mailto:"))
                return;

            handleLink(base, href,height+1);
            //log(base+href);
        }

        public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
            handleSimpleTag(t, a, pos); // handle the same way

        }


    }
    public void handleLink(URL base, String str,int height) {
        try {
            URL url = new URL(base, str); //base为当前路径，str为相对路径，这样加一起组成绝对路径。
            addURL(url.toString(),height);

        } catch (MalformedURLException e) {
            log("Found malformed URL: " + str);
        }
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.addURL("https://weibo.com/",1);
        crawler.begin();
    }
}

//以上就是所有，这个代码用java直接可以跑可以看到结果，希望对你有所帮助~

