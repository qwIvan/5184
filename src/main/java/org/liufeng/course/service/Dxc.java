package org.liufeng.course.service;

import ivan.factory.Factory;
import ivan.vo.VO5184;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class Cu implements Runnable{
	private List<VO5184> list;
	private D d;
	private CountDownLatch latch;
	private String fromUserName;
	Cu(D d,List<VO5184> list,CountDownLatch latch,String fromUserName){
		this.d = d;
		this.list = list;
		this.latch = latch;
		this.fromUserName = fromUserName;
	}
	private void add(VO5184 vo){
		this.d.remove(vo.getZkzh());
		this.list.add(vo);
		this.latch.countDown();
		try {
			Factory.getInstance().addVO(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void run() {
		while(!this.d.isEmpty()){
			String[] str = d.pop();
			VO5184 vo = null;
			try {
				vo = Get.get5184VO(str[0], str[1]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(vo!=null){
				vo.setName(fromUserName);
				add(vo);
			}
		}
	}
}

class D{
	private Queue<String> nydl = new LinkedList<String>();
	private Queue<String> khdl;
	private String zkzh;
	D(Queue<String> khdl){
		this.khdl = khdl;
	}
	public synchronized void clear(){
		this.khdl.clear();
		this.nydl.clear();
	}
	private Queue<String> getNydl(){
		Queue<String> nydl = new LinkedList<String>();
		int i=0;
		while(-5<=i&&i<=5){
			for(int j=1;j<=12;j++){
				String csny;
				if(j<=9){
					csny = 95+i+"0"+j;
				}else{
					csny = 95+i+""+j;
				}
				nydl.add(csny.substring(csny.length()-4));
			}
			i=i>0?-i:-i+1;
		}
		return nydl;
	}
	public synchronized boolean isEmpty(){
		return this.khdl.isEmpty()&&this.nydl.isEmpty();
	}
	public synchronized String[] pop(){
		if(this.nydl.isEmpty()){
			this.nydl = getNydl();
			this.zkzh = this.khdl.poll();
		}
		String csny = this.nydl.poll();
		if(this.zkzh==null||csny==null)return null;
		String[] str = {this.zkzh,csny};
		return str;
	}
	public synchronized void remove(String zkzh){
		if(this.zkzh==zkzh){
			this.nydl.clear();
		}
	}
}

public class Dxc {
	public static List<VO5184> spider(List<String> src,String fromUserName,int thr) {
		Queue<String> que = new LinkedList<String>(src);
		List<VO5184> list = new LinkedList<VO5184>();
		D d = new D(que);
		CountDownLatch latch = new CountDownLatch(thr);
		Cu cu = new Cu(d,list,latch,fromUserName);
		for(int i=0;i<thr*10;i++)
			new Thread(cu).start();
		try {
			latch.await(3, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		d.clear();
		return list;
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("1721505040");
		String csny = new Dxc().spider(list, "", 10).get(0).getCsny();
		System.out.println(csny);
	}
}