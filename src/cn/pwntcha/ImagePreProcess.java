package cn.pwntcha;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;




public class ImagePreProcess implements Runnable{

	public static int isBlack(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() <= 400) {
			return 1;
		}
		return 0;
	}
	
	public static int isWhite(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() > 400) {
			return 1;
		}
		return 0;
	}

	public static BufferedImage removeBackgroud(URLConnection conn)
			throws Exception {
		InputStream input = conn.getInputStream();
		BufferedImage img = ImageIO.read(input);
		input.close();
		int width = img.getWidth();
		int height = img.getHeight();
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				if (isWhite(img.getRGB(x, y)) == 1) {
					img.setRGB(x, y, Color.WHITE.getRGB());
				} else {
					img.setRGB(x, y, Color.BLACK.getRGB());
				}
			}
		}
		return img;
	}

	public static List<BufferedImage> splitImage(BufferedImage img)
			throws Exception {
		List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
		subImgs.add(img.getSubimage(7, 3, 9, 13));
		subImgs.add(img.getSubimage(20, 3, 9, 13));
		subImgs.add(img.getSubimage(33, 3, 9, 13));
		subImgs.add(img.getSubimage(46, 3, 9, 13));
		subImgs.add(img.getSubimage(59, 3, 9, 13));
		subImgs.add(img.getSubimage(72, 3, 9, 13));
		return subImgs;
	}

	public static Map<BufferedImage, String> loadTrainData() throws Exception {
		Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();
		File dir = new File("C:/5184/train");
		File[] files = dir.listFiles();
		for (File file : files) {
			map.put(ImageIO.read(file), file.getName().charAt(0) + "");
		}
		return map;
	}

	public static String getSingleCharOcr(BufferedImage img,
			Map<BufferedImage, String> map) {
		String result = "";
		int width = img.getWidth();
		int height = img.getHeight();
		int min = width * height;
		for (BufferedImage bi : map.keySet()) {
			int count = 0;
			Label1: for (int x = 0; x < width; ++x) {
				for (int y = 0; y < height; ++y) {
					if (isWhite(img.getRGB(x, y)) != isWhite(bi.getRGB(x, y))) {
						count++;
						if (count >= min)
							break Label1;
					}
				}
			}
			if (count < min) {
				min = count;
				result = map.get(bi);
			}
		}
		return result;
	}

	public static String getAllOcr(URLConnection conn) throws Exception {
		BufferedImage img = removeBackgroud(conn);
		List<BufferedImage> listImg = splitImage(img);
		Map<BufferedImage, String> map = loadTrainData();
		String result = "";
		for (BufferedImage bi : listImg) {
			result += getSingleCharOcr(bi, map);
		}
		return result;
	}


	/**
	 * @param args
	 * @throws Exception
	 */
	
	public static String getCookie(URLConnection conn){
		Map<String, List<String>> headers = conn.getHeaderFields();
		String cookie = "";
		List<String> setcookie = headers.get("Set-Cookie");
		for(String s:setcookie){
			cookie += s.split(";")[0]+";";
		}
		return cookie;
	}
	
	public static String post(String mailNum,String checkCode,String cookie) throws IOException, ParserException {
		URL url = new URL("http://www.ems.com.cn/ems/order/noticeQuery");
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("Cookie", cookie);
		conn.setDoOutput(true);
		OutputStream output = conn.getOutputStream();
		byte[] b = ("mailNum="+mailNum+"&checkCode="+checkCode).getBytes();
		output.write(b);
		output.close();
		
		Parser par = new Parser(conn);
		par.setEncoding("UTF-8");
		NodeFilter filter = new HasAttributeFilter("class","mailnum_result_box");
		NodeList nodes = par.extractAllNodesThatMatch(filter);
		if(nodes.size()==0)return null;
		String html = nodes.elementAt(0).toHtml();
//		FileOutputStream out = new FileOutputStream("C:/5184/test/"+mailNum+".html");
		if(html!=null){
//			out.write(("<link rel=\"stylesheet\" href=\"ems.css\" type=\"text/css\" media=\"all\">"+html).getBytes());
			return "\n你的录取通知书于"+nodes.elementAt(0).getFirstChild().getNextSibling().toHtml().replaceAll("<br />", "，").replaceAll("<[^>]+>", "").replaceAll("\\s", "").replaceFirst("邮件号码：\\d*您的邮件于", "").replaceAll("&nbsp;", " ");
		}
		return null;
	}
	
	private String s;
	private StringBuffer sb;
	public ImagePreProcess(String s,StringBuffer sb){
		this.s = s;
		this.sb = sb;
	}
	public void run() {
		URLConnection conn = null;
		try {
			conn = new URL("http://www.ems.com.cn/ems/rand").openConnection();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String text = null;
		try {
			text = getAllOcr(conn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String cookie = getCookie(conn);
		String info = null;
		try {
			info = post(s,text,cookie);
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(info!=null){
			this.sb.append(info);
		}
	}
}
