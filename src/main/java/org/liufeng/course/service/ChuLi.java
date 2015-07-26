package org.liufeng.course.service;

import ivan.factory.Factory;
import ivan.vo.VO5184;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChuLi {
	private String content;
	private String fromUserName;
	private VO5184 vo;
	private List<VO5184> near;
	public List<VO5184> mine = new LinkedList<VO5184>();
	public List<VO5184> other = new LinkedList<VO5184>();
	public List<VO5184> get;
	ChuLi(String content,String fromUserName){
		this.content = content;
		this.fromUserName = fromUserName;
		this.near = db();
	}
	private List<VO5184> db(){
		List<VO5184> list = null;
		Set<VO5184> set = null;
		try {
			set = Factory.getInstance().getFullNear(content);
			list = new LinkedList<VO5184>(set);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return list;
	}










	public VO5184 getvo(){
		if(vo!=null)return vo;
		for(VO5184 i:near){
			if(i.getZkzh().equals(content)){
				vo = i;
				break;
			}
		}
		get = Dxc.spider(need(),fromUserName,5-near.size()>=3?5-near.size():2);
		if(vo==null){
			for(VO5184 i:get){
				if(i.getZkzh().equals(content)){
					System.out.println(near.remove(i)+"\tChuli.java第78行near.remove(i)");//测试此句有没有用!!!!!!!!!!!!!!!!!!!
					vo = i;
					break;
				}
			}
		}
		return vo;
	}
	public List<String> need(){//返回待抓取的考号列表
		fen();
		List<String> khlist = needName(content);
		for(VO5184 i:near){
			khlist.remove(i.getZkzh());
		}
		Collections.shuffle(khlist);
		if(khlist.remove(content))khlist.add(0, content);
		return khlist;
	}
	private void fen(){
		Collections.shuffle(near);
		for(VO5184 i:near){
			if(i.getName().equals(fromUserName)){
				mine.add(i);
			}else{
				other.add(i);
			}
		}
	}
	private List<String> needName(String zkzh){
		List<String> list = new LinkedList<String>();
		int i=0;
		while(-25<=i&&i<=25){
			list.add(new DecimalFormat("0000000000").format(Long.valueOf(zkzh)+i));
			i=i>0?-i:-i+1;
		}
		return list;
	}










	public Map<String,String> toMap(){
		Map<String,String> map = new LinkedHashMap<String, String>();
		List<VO5184> all = new LinkedList<VO5184>();
		all.addAll(get);
		all.addAll(other);
		all.addAll(mine);
		if(all.isEmpty())return map;
		all.remove(vo);
		for(VO5184 i:all){
			if(map.get(i.getZymc())==null){
				map.put(i.getZymc(), i.getXm());
			}else{
				String oldStr = map.get(i.getZymc());
				map.put(i.getZymc(), oldStr + "、" + i.getXm());
			}
		}
		return map;
	}









}