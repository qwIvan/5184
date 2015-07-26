package org.liufeng.course.service;

import ivan.vo.VO5184;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class Get2{
	public static VO5184 get(String zkzh,String fromUserName){
		CountDownLatch latch = new CountDownLatch(7);
		Cus thr = new Cus(zkzh,latch,fromUserName);
		for(int i=0;i<15;i++)
			new Thread(thr).start();
		try {
			latch.await(4, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		thr.dl.clear();
		if(thr.vo==null){
			return null;//这里需要改
		}else{
			return thr.vo;
		}
	}
}

class Cus implements Runnable{
	private String zkzh;
	private CountDownLatch latch;
	private String fromUserName;
	Queue<String> dl = getDl();
	VO5184 vo;
	Cus(String zkzh, CountDownLatch latch,String fromUserName){
		this.zkzh = zkzh;
		this.latch = latch;
		this.fromUserName = fromUserName;
	}
	
	public void run(){
		String csny;
		while(this.vo==null&&(csny = pop())!=null){
			VO5184 tmp = null;
			try {
				tmp = Get.get5184VO(zkzh,csny );
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(tmp!=null){
				tmp.setName(fromUserName);
				this.vo = tmp;
				dl.clear();
				for(int i=0;i<7;i++)
					latch.countDown();
			}
		}
		latch.countDown();
	}
	
	private synchronized String pop(){
		return dl.poll();
	}

	public Queue<String> getDl(){
		Queue<String> dl = new LinkedList<String>();
		int i = 0;
		while(-4<=i&&i<=4){
			for(int j=1;j<=12;j++){
				String csny;
				if(j<=9){
					csny = 95+i+"0"+j;
				}else{
					csny = 95+i+""+j;
				}
				dl.add(csny);
			}
			i=i>0?-i:-i+1;
		}
		return dl;
	}
}