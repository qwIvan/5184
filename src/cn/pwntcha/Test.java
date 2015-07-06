package cn.pwntcha;


public class Test {
	public static void main(String[] args) {
		String content = "1401911047";
		StringBuffer sb = new StringBuffer();
		new Thread(new ImagePreProcess(content, sb)).run();
		System.out.println(sb.toString());
	}
	
}
