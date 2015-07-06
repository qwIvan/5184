package org.liufeng.course.service;

import ivan.vo.NeusoftVO;
import ivan.vo.VO5184;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Get {
	private static JSONObject getJsonString(String zkzh,String csny) {
		try{
			URL url = new URL("http://www.5184.com/gk/common/get_lq.php");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("X-Requested-With","XMLHttpRequest");
			connection.setDoOutput(true);
			OutputStream out = connection.getOutputStream();
			String xh = "zkzh="+zkzh+"&csny="+csny;
			byte[] data = xh.getBytes();
			out.write(data);
			out.close();
			InputStream inputStream = connection.getInputStream();  
			Reader reader = new InputStreamReader(inputStream, "UTF-8");  
			BufferedReader bufferedReader = new BufferedReader(reader);  
			String str = null;  
			StringBuffer sb = new StringBuffer();  
			while ((str = bufferedReader.readLine()) != null) {  
				sb.append(str);  
			}  
			reader.close();
			return new JSONObject(new JSONTokener(sb.toString()));  
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public static VO5184 get5184VO(String zkzh,String csny) throws Exception{
		VO5184 vo = null;
		JSONObject obj = getJsonString(zkzh, csny);
		if(obj!=null&&obj.getString("flag").equals("1")){
			JSONObject result = obj.getJSONObject("result");
			vo = new VO5184();
			vo.setZkzh(zkzh);
			vo.setCsny(csny);
			vo.setXm(result.getString("xm"));
			vo.setLbm(result.getString("lbm"));
			vo.setPcm(result.getString("pcm"));
			vo.setZymc(result.getString("zymc"));
			vo.setYxdm(result.getString("yxdm"));
		}
		return vo;
	}
	public static NeusoftVO getNeusoft(String param) throws IOException, ParserException{
		URL url = new URL("http://www.neusoft.gd.cn:8080/xwzt/08luqu/fresh.asp");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStream out = conn.getOutputStream();
		String xh = "xh="+param;
		byte[] data = xh.getBytes();
		out.write(data);
		out.close();
		Parser parser = new Parser(conn);
		parser.setEncoding("UTF-8");
		NodeFilter filter = new TagNameFilter("li");
		NodeList nodes = parser.extractAllNodesThatMatch(filter);
		if(nodes.size()==0)return null;
		else{
			String name = nodes.elementAt(0).toPlainTextString().replaceFirst("姓名: ", "");
			String idnum = nodes.elementAt(1).toPlainTextString().replaceFirst("身份证号: ", "");
			String sex = nodes.elementAt(2).toPlainTextString().replaceFirst("性别: ", "");
			String cnum = nodes.elementAt(3).toPlainTextString().replaceFirst("考试号: ", "");
			String major = nodes.elementAt(4).toPlainTextString().replaceFirst("录取专业: ", "");
			NeusoftVO result = new NeusoftVO();
			result.setName(name);
			result.setIdnum(idnum);
			result.setSex(sex);
			result.setCnum(cnum);
			result.setMajor(major);
			return result;
		}
	}
	public static String haiyang(String zkzh,String sfzh) throws IOException, ParserException{
		URL url = new URL("http://www.gdcjxy.com/Admissions/lqcx/index.asp");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStream out = conn.getOutputStream();
		String xh = "zkzh="+zkzh+"&sfzh="+sfzh+"";
		byte[] data = xh.getBytes();
		out.write(data);
		out.close();
		Parser parser = new Parser(conn);
		parser.setEncoding("GBK");
		NodeFilter filter1 = new TagNameFilter("table");
		NodeFilter filter2 = new HasAttributeFilter( "bgcolor", "#E1E1E1" );
		AndFilter filter = new AndFilter(filter1,filter2);
		NodeList nodes = parser.extractAllNodesThatMatch(filter); 
		if(nodes.size()==0)return null;
		else{
			TableTag node = (TableTag)nodes.elementAt(0);
			return node.getRow(3).toPlainTextString();
		}
	}
}
