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
		article.setTitle(vo.getXm() + "ͬѧ,���Ѿ����㶫�Ƽ�ѧԺ¼ȡ��");
		article.setDescription("��ϲ��λ�ܽ��뵽�㶫�Ƽ�ѧԺ������ͥ���������Ϣ���鿴���������ѧ����!");
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
		article1.setTitle(vo.getXm() + "ͬѧ,���Ѿ����㶫�Ṥְҵ����ѧԺ¼ȡ��");
		article1.setDescription("��ϲ��λ�ܽ��뵽�㶫�Ṥְҵ����ѧԺ������ͥ���������Ϣ���鿴����������ѧ����!");
		article1.setPicUrl("http://mmbiz.qpic.cn/mmbiz/h5OHXiaOOBnplxvU6ib1icZNUqMuunUnRAXO4D1I7ffibibgaE22EXQFjfYTLKDYLk4MnjafXiaSH4bVlupEJD4SOmvw/0");
		article1.setUrl("http://mp.weixin.qq.com/s?__biz=MzA3MDg5NDcyOA==&mid=200688939&idx=1&sn=bc5a9dd40947962ecace50dbbf05e3fa");
		list.add(article1);
		Article article2 = new Article();
		article2.setTitle("�������");
		article2.setDescription("����˴��ӹ�������QQȺ");
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
			// Ĭ�Ϸ��ص��ı���Ϣ����
			String respContent = "΢�ž������ˣ������·��ͣ�";

			// xml�������
			Map<String, String> requestMap = null;
			try {
				requestMap = MessageUtil.parseXml(request);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// ���ͷ��ʺţ�open_id��
			String fromUserName = requestMap.get("FromUserName");
			// �����ʺ�
			String toUserName = requestMap.get("ToUserName");
			// ��Ϣ����
			String msgType = requestMap.get("MsgType");

			// �ظ��ı���Ϣ
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			

			// �ı���Ϣ
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				String content = requestMap.get("Content").trim();
				if(content.matches("\\d{10}")){//����׼��֤��






//					StringBuffer sb = new StringBuffer();
//					new Thread(new ImagePreProcess(content, sb)).start();
					ChuLi cl = new ChuLi(content,fromUserName);
					VO5184 vo = cl.getvo();
					if(vo==null){
						respContent = "��ʱδ�����¼ȡ����������㻹δ��¼ȡ������׼��֤���Ƿ���ȷ�����Ժ�����[˥]\nÿ��18:30����¼ȡ����";
					}else{
						/*if ((vo.getZymc().equals("�㶫����ѧԺ(ԭ�Ϻ�������Ϣ����ְҵѧԺ)"))) {
			            	respContent = String.valueOf(Character.toChars(127881)) + "������ף" + vo.getXm() + "ͬѧ�����Ҵ���ļ�ͥ�����������ʦ��[����]\n�˽����¼ȡ��Ϣ��<a href=\"http://shang.qq.com/wpa/qunwpa?idkey=930952754c5d2a805f940654670f6ea44635173a90fa5690ed435a48ae1764d0\">�������</a>\n��¼ȡרҵ��Ӷ���΢�źţ�neusoftbigtree";
			            }else */if (vo.getZymc().equals("�㶫�Ṥְҵ����ѧԺ")) {
							return MessageUtil.newsMessageToXml(GuangQing(vo,fromUserName,toUserName));
						}else if (vo.getZymc().equals("�㶫�Ƽ�ѧԺ")) {
							return MessageUtil.newsMessageToXml(KeJi(vo,fromUserName,toUserName));
						}
						respContent = "ף��"+vo.getXm()+"ͬѧ��"+vo.getZymc()+"¼ȡΪ2014����������";//+sb.toString();
					}



					Map<String,String> map = cl.toMap();
					if(!map.isEmpty()){
						if(vo!=null){
							respContent += "\n\n�������ǻ��鵽���С��飺";
							String schoolmate = map.get(vo.getZymc());
							if(schoolmate!=null){
								map.remove(vo.getZymc());
								respContent += "\n"+schoolmate+"Ҳ��"+vo.getZymc()+"¼ȡ";
							}
						}else{
							respContent += "\n\n�������ǲ鵽���С��飺";
						}
						int count = 0;
						for(Entry<String, String> i:map.entrySet()){
							respContent += "\n"+i.getValue()+"��"+i.getKey()+"¼ȡ";
							if(++count>=5)break;
						}
					}



					if(cl.get!=null&cl.get.size()>0){
						respContent += "\n�������ͼ�������С���[����]";
					}else{
						respContent += "\n\nС��ʾ:��������׼��֤�ſ��Բ�ѯͬѧ¼ȡ���";
					}






				}else if(content.indexOf("רҵ")!=-1){
					respContent = "��ʱδ�鵽���¼ȡרҵ";
				}else{//��ʽ����ȷ
					respContent = "��ʽ����ȷ������������10λ׼��֤�ţ�лл[˥]";
				}
			}
			// ͼƬ��Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "�뷢��׼��֤�ţ�лл[˥]";
			}
			// ����λ����Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "�뷢��׼��֤�ţ�лл[˥]";
			}
			// ������Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "�뷢��׼��֤�ţ�лл[˥]";
			}
			// ��Ƶ��Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "�뷢��׼��֤�ţ�лл[˥]";
			}
			// �¼�����
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// �¼�����
				String eventType = requestMap.get("Event");
				// ����
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					if(toUserName.equals("gh_75f35b597cf1")){
						respContent = "����΢��gdgxkx\n����10λ׼��֤�ſ��Բ�ѯ¼ȡ��Ϣ[����]\n��������׼��֤�ſ��Բ�ѯͬѧ¼ȡ���";
					}
				}
				// ȡ������
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO ȡ�����ĺ��û����ղ������ںŷ��͵���Ϣ����˲���Ҫ�ظ���Ϣ
				}
				// �Զ���˵�����¼�
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// TODO �Զ���˵�Ȩû�п��ţ��ݲ����������Ϣ
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
//					map.put(i.getZymc(),oldStr+"��"+i.getXm());
//				}
//			}
//		}
//		return map;
//	}
}
