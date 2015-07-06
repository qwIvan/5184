package org.liufeng.course.service;

import ivan.vo.VO5184;
import org.liufeng.course.message.resp.Article;
import org.liufeng.course.message.resp.NewsMessage;
import org.liufeng.course.message.resp.TextMessage;
import org.liufeng.course.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
public class CoreService {
	private static NewsMessage KeJi(VO5184 vo,String fromUserName,String toUserName){
		Article article = new Article();
		article.setTitle(vo.getXm() + "同学,你已经被广东科技学院录取！");
		article.setDescription("恭喜各位能进入到广东科技学院这个大家庭。点击本消息，查看广科新生入学攻略!");
		article.setPicUrl("http://mmbiz.qpic.cn/mmbiz/h5OHXiaOOBnqqd2HTmMjOKhMQGM3jeZPmx6XW6Q5pzmGVPasgHEKGOb57CicouJWicO6z0zFJdfrdmibAYj8jib4VHA/0");
		article.setUrl("http://mp.weixin.qq.com/s?__biz=MzA3MDg5NDcyOA==&mid=200663597&idx=1&sn=328f119391f5c5d46104d9713346af9e");
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType("news");
		newsMessage.setFuncFlag(0);
		newsMessage.setArticleCount(1);
		List<Article> list = new ArrayList<Article>();
		list.add(article);
		newsMessage.setArticles(list);
		return newsMessage;
	}

	private static NewsMessage GuangQing(VO5184 vo,String fromUserName,String toUserName){
		List<Article> list = new ArrayList<Article>();
		Article article1 = new Article();
		article1.setTitle(vo.getXm() + "同学,你已经被广东轻工职业技术学院录取！");
		article1.setDescription("恭喜各位能进入到广东轻工职业技术学院这个大家庭。点击本消息，查看广轻新生入学攻略!");
		article1.setPicUrl("http://mmbiz.qpic.cn/mmbiz/h5OHXiaOOBnplxvU6ib1icZNUqMuunUnRAXO4D1I7ffibibgaE22EXQFjfYTLKDYLk4MnjafXiaSH4bVlupEJD4SOmvw/0");
		article1.setUrl("http://mp.weixin.qq.com/s?__biz=MzA3MDg5NDcyOA==&mid=200688939&idx=1&sn=bc5a9dd40947962ecace50dbbf05e3fa");
		list.add(article1);
		Article article2 = new Article();
		article2.setTitle("加入广轻");
		article2.setDescription("点击此处加广轻新生QQ群");
		article2.setPicUrl("http://mmbiz.qpic.cn/mmbiz/h5OHXiaOOBnplxvU6ib1icZNUqMuunUnRAXO4D1I7ffibibgaE22EXQFjfYTLKDYLk4MnjafXiaSH4bVlupEJD4SOmvw/0");
		article2.setUrl("http://shang.qq.com/wpa/qunwpa?idkey=bc4cc306a0984aaef08d5a85eb918bfe96263c14ee63fc8ca72b0e389edc1b36");
		list.add(article2);
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType("news");
		newsMessage.setFuncFlag(0);
		newsMessage.setArticleCount(2);
		newsMessage.setArticles(list);
		return newsMessage;
	}

	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
			// 默认返回的文本消息内容
			String respContent = "微信君出错了，请重新发送！";

			// xml请求解析
			Map<String, String> requestMap = null;
			try {
				requestMap = MessageUtil.parseXml(request);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				String content = requestMap.get("Content").trim();
				if(content.matches("\\d{10}")){//输入准考证号






//					StringBuffer sb = new StringBuffer();
//					new Thread(new ImagePreProcess(content, sb)).start();
					ChuLi cl = new ChuLi(content,fromUserName);
					VO5184 vo = cl.getvo();
					if(vo==null){
						respContent = "暂时未有你的录取结果，可能你还未被录取，请检查准考证号是否正确，或稍后再试[衰]\n每天18:30更新录取数据";
					}else{
						/*if ((vo.getZymc().equals("广东东软学院(原南海东软信息技术职业学院)"))) {
			            	respContent = String.valueOf(Character.toChars(127881)) + "热烈庆祝" + vo.getXm() + "同学加入我大东软的家庭！！我是你的师兄[呲牙]\n了解更多录取信息请<a href=\"http://shang.qq.com/wpa/qunwpa?idkey=930952754c5d2a805f940654670f6ea44635173a90fa5690ed435a48ae1764d0\">点击这里</a>\n查录取专业请加东软微信号：neusoftbigtree";
			            }else */if (vo.getZymc().equals("广东轻工职业技术学院")) {
							return MessageUtil.newsMessageToXml(GuangQing(vo,fromUserName,toUserName));
						}else if (vo.getZymc().equals("广东科技学院")) {
							return MessageUtil.newsMessageToXml(KeJi(vo,fromUserName,toUserName));
						}
						respContent = "祝贺"+vo.getXm()+"同学被"+vo.getZymc()+"录取为2014级新生！！";//+sb.toString();
					}



					Map<String,String> map = cl.toMap();
					if(!map.isEmpty()){
						if(vo!=null){
							respContent += "\n\n另外我们还查到你的小伙伴：";
							String schoolmate = map.get(vo.getZymc());
							if(schoolmate!=null){
								map.remove(vo.getZymc());
								respContent += "\n"+schoolmate+"也被"+vo.getZymc()+"录取";
							}
						}else{
							respContent += "\n\n但是我们查到你的小伙伴：";
						}
						int count = 0;
						for(Entry<String, String> i:map.entrySet()){
							respContent += "\n"+i.getValue()+"被"+i.getKey()+"录取";
							if(++count>=5)break;
						}
					}



					if(cl.get!=null&cl.get.size()>0){
						respContent += "\n还不快截图发给你的小伙伴[呲牙]";
					}else{
						respContent += "\n\n小提示:输入相邻准考证号可以查询同学录取结果";
					}






				}else if(content.indexOf("专业")!=-1){
					respContent = "暂时未查到你的录取专业";
				}else{//格式不正确
					respContent = "格式不正确，请重新输入10位准考证号，谢谢[衰]";
				}
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "请发送准考证号，谢谢[衰]";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "请发送准考证号，谢谢[衰]";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "请发送准考证号，谢谢[衰]";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "请发送准考证号，谢谢[衰]";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					if(toUserName.equals("gh_75f35b597cf1")){
						respContent = "加我微信gdgxkx\n发送10位准考证号可以查询录取信息[呲牙]\n输入相邻准考证号可以查询同学录取结果";
					}
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// TODO 自定义菜单权没有开放，暂不处理该类消息
				}
			}
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);

		return respMessage;
	}

//	public static Map<String,String> changeVO(String zkzh,VO5184 vo) throws Exception{
//		List<VO5184> near = null;
//		near = Factory.getInstance().getFullNear(zkzh);
//		return getMap(zkzh, near, vo);
//	}
//	
//	public static Map<String,String> getMap(String zkzh,List<VO5184> near,VO5184 vo){
//		Map<String,String> map = new LinkedHashMap<String,String>();
//		for(VO5184 i:near){
//			if(i.getZkzh().equals(zkzh)){
//				vo = i;
//			}else{
//				if(map.get(i.getZymc())==null){
//					map.put(i.getZymc(), i.getXm());
//				}else{
//					String oldStr = map.get(i.getZymc());
//					map.put(i.getZymc(),oldStr+"、"+i.getXm());
//				}
//			}
//		}
//		return map;
//	}
}
