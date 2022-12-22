package com.douzone.paint.shape;

import com.douzone.paint.i.Drawable;

public abstract class Shape implements Drawable {
	private String lineColor;
	private String fillcolor;
	
	public abstract void draw();
	
	public String getLineColor() {
		return lineColor;
	}
	public void setLineColor(String lineColor) {
		this.lineColor = lineColor;
	}
	public String getFillcolor() {
		return fillcolor;
	}
	public void setFillcolor(String fillcolor) {
		this.fillcolor = fillcolor;
	}
	
	
}
