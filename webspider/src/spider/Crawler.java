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

    private ArrayList<ArrayList<String>> urlWaiting1 = new ArrayList<ArrayList<String>>();      //�ȴ��������url��������ά��ԭ�������һά���������������ĵڼ��㣬�ڶ�ά�洢����һ�����е�����
    private ArrayList<String> urlWaiting2=new ArrayList<String>();
    private ArrayList<String> urlProcessed = new ArrayList<String>();   //�������url
    private ArrayList<String> urlError = new ArrayList<String>();       //�����url
    static int deep=2;                                                  //�������Ϊ2
    private int numFindUrl = 0;     //find the number of url
    static int height=deep-1;       //����Ϊ�˺����ѭ������
    public Crawler() {}


    /**
     * start crawling
     */
    public void begin() {            //��ʼץȡ


        for(int i=0;i<=height;i++)       //һ��һ��Ĵ���
        {

            ArrayList<String> urlWaiting_temp=new ArrayList<String>();
            urlWaiting_temp=urlWaiting1.get(i);
            while(!urlWaiting_temp.isEmpty()) {   //�����һ��ǿվʹ���
                processURL(urlWaiting_temp.remove(0), i); //����url�����������ж���
            }
        }
        //ץȡ�������һЩ��־��������log�����Ķ���
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
    public void processURL(String strUrl,int height) {  //����url����height�Ĳε�Ŀ���Ǽ�¼��ǰ�����url�ǵڼ����
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
            parse.parse(r, new Parser(url,height), true); //����url


        } catch (IOException e) {
            urlError.add(url.toString());
            log("Error: " + url);
            return;
        }
        // mark URL as complete
        urlProcessed.add(url.toString());  //���urlΪ�Ѿ�����
        log("Complete: " + url);
    }

    /**
     * Add a URL to waiting list.
     *
     * @param url
     */
    public void addURL(String url,int height) { //���urlʱ���ǵ�������Ⱥ�urlWaiting1�Ƕ�ά�����ԣ��������д����һЩ������

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

            if(urlWaiting1.size()<=height)   //�ж�������µ�һ���url��Ҳ����height�����ˣ���ΪurlWaiting1��������1��
            {
                ArrayList<String> urlTemp=new ArrayList<String>();
                urlTemp.add(url);
                urlWaiting1.add(height, urlTemp);
                numFindUrl++;
            }
            else {                       //������url����֮ǰ��һ���Ѿ���url�ˣ��Ǿ��Ȱ���һ�����е�url�ó����������Ž�ȥ����ȫ�Ż�urlWaiting1�
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

   //����getParserΪpublic
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
  //һ����Ҫ�Ļص�����������ֵ������href��ǩ
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
            URL url = new URL(base, str); //baseΪ��ǰ·����strΪ���·����������һ����ɾ���·����
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

//���Ͼ������У����������javaֱ�ӿ����ܿ��Կ��������ϣ��������������~

