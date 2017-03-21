package com.mars.marsview.entity;

public class TimeLine {

	private String timelineStep;
	private String timelineContent;
	private int flag;
	/**
	 * @return the timelineStep
	 */
	public String getTimelineStep() {
		return timelineStep;
	}
	/**
	 * @param timelineStep the timelineStep to set
	 */
	public void setTimelineStep(String timelineStep) {
		this.timelineStep = timelineStep;
	}
	/**
	 * @return the timelineContent
	 */
	public String getTimelineContent() {
		return timelineContent;
	}
	/**
	 * @param timelineContent the timelineContent to set
	 */
	public void setTimelineContent(String timelineContent) {
		this.timelineContent = timelineContent;
	}
	/**
	 * @return the flag
	 */
	public int getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public TimeLine(String timelineStep, String timelineContent, int flag) {
		super();
		this.timelineStep = timelineStep;
		this.timelineContent = timelineContent;
		this.flag = flag;
	}
	
}
