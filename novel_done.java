package exam;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class novel {
	public static void main(String args[]) throws IOException{
		int page=0;
		String o_url,c_url,i_page; // ��url, �@�����ܪ�url
		String txt="";
		BufferedReader buf = new BufferedReader(
                new InputStreamReader(System.in)); 
		System.out.print("�п�J���} : ");
		String url = buf.readLine();
		o_url = url.replaceAll("-1-", "page");
		page = checkpage(url);
		//catch_txt(url);
		for(int i = 1; i<=page;i++)
		{
			i_page ="-" + Integer.toString(i) + "-";
			c_url=o_url.replaceAll("page", i_page);
			//System.out.println("o_url : " + o_url + ";; c_url : " + c_url);
			txt = catch_txt(c_url);
		}
	}
	public static String catch_txt(String url){
		String result_txt="",nowtxt;
		try
		{
			Document doc = Jsoup.connect(url).get();  
			Elements container = doc.select(".postmessage");
			Iterator itr = container.iterator();
			while(itr.hasNext()) {
		         Object element = itr.next();
		         result_txt+=element.toString();	
		         
					///////����X��r�������ҥh����
					result_txt= result_txt.replaceAll("<br />","");
					result_txt = result_txt.replaceAll("</div>","");
					result_txt = result_txt.replaceAll("<div id=\"postmessage_\\d*\" class=\"postmessage\">", "");
					result_txt = result_txt.replaceAll("&nbsp;", "");
					result_txt = result_txt.replaceAll("<a.*a>", "");
					result_txt = result_txt.replaceAll("<strong.*strong>", "");
					result_txt = result_txt.replaceAll("<i.*i>", "");
					result_txt = result_txt.replaceAll("&\\.;", "");
					//////////////////////////////////////
					writetxt(result_txt, url);
		      }	
			System.out.print(result_txt);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("IOException happened !");
		}
		
		return result_txt;	
	}
	
	public static int checkpage(String url)
	{
		int page=0; String txt;
		try
		{
			Document doc = Jsoup.connect(url).get();  
			Element container = doc.select("a.last").first();
			txt = container.text();
			txt = txt.replaceAll("\\.", "");
			txt = txt.replaceAll(" ", "");
			page = Integer.parseInt(txt);
		}
		catch(Exception e)
		{
			//�w��BUG�A���Ƥ֩�Q�|�X�{nullPointer
			e.printStackTrace();
			System.out.println("checkpage�����ƿ��~�A�нT�{���}�C");
		}
		return page;
	}
	
	@SuppressWarnings("null")
	public static void writetxt(String txt,String url)throws IOException
	{
		String title = get_title(url);
		FileWriter fw = new FileWriter("D:\\"+title+".txt",true);
		txt = txt.replaceAll("\n", "\r\n");
		fw.write(txt);
		fw.flush();
		fw.close();
	}
	public static String get_title(String url)throws IOException
	{
		String title;
		Document doc = Jsoup.connect(url).get();  
		Element container = doc.select("h1").first();
		title = container.text();
		title = title.replaceAll("\\[.*\\]", "");
		title = title.replaceAll("�@��.*", "");	
		return title;
		
	}
	
}
