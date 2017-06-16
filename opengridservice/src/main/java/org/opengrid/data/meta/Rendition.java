package org.opengrid.data.meta;

public class Rendition {
	private String icon;
	private String color;
	private String fillColor;
	private int opacity;
	private int size;
	private int borderWidth=3;
	
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getFillColor() {
		return fillColor;
	}
	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}
	public int getOpacity() {
		return opacity;
	}
	public void setOpacity(int opacity) {
		this.opacity = opacity;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getBorderWidth() {
		return borderWidth;
	}
	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}
	
}
