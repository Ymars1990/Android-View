package com.mars.marsview.utils;

import java.util.Timer;
import java.util.TimerTask;

public abstract class TimeCallFunction {
	private long timeCount;
	private long timeDuration;
	private Timer timer = null;
	private TimerTask task = null;
	private TimetaskFinishCallBack callback;
	public TimeCallFunction(long timeCount, long timeDuration) {
		this.timeCount = timeCount;
		this.timeDuration = timeDuration;
		if(this.timeCount==-1){
			this.timeCount = Long.MAX_VALUE;
		}
	}
	public TimeCallFunction(long timeCount, long timeDuration,TimetaskFinishCallBack callback) {
		this.timeCount = timeCount;
		this.timeDuration = timeDuration;
		this.callback = callback;
		if(this.timeCount==-1){
			this.timeCount = Long.MAX_VALUE;
		}
	}

	public void startTimerTask() {
		timer = new Timer();
		task = new TimerTask() {
			public void run() {
				callTimerTaskFuc();
				timeCount-=timeDuration;
				if(timeCount<0){
					
					Utils.LogShow("��ʱ����", "ʱ���ܳ���");
					timer.cancel();
				}
			}
		};
		timer.schedule(task,0, timeDuration);
	}
	public void stopTimerTask(){
		if(timer!=null){
			timer.cancel();
			Utils.LogShow("��ʱ����", "�����رն�ʱ����");
		}
	}
	public void showTimerTask() {
		timer = new Timer();
		task = new TimerTask() {
			public void run() {
				timeCount-=timeDuration;
				if(timeCount<0){
					callTimerTaskFuc();
					Utils.LogShow("��ʱ����", "ʱ���ܳ���");
					timer.cancel();
				}
			}
		};
		timer.schedule(task,0, timeDuration);
	}
	public abstract void callTimerTaskFuc();
	public interface TimetaskFinishCallBack{
		void timeTaskFinishCallBack();
	}
}
