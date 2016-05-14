package com.madx.ste.example;

import java.math.BigDecimal;

public class FirstExample {
	private BigDecimal x;
	private int y;
	private SecondExample z;
	private String m;
	public FirstExample(BigDecimal x, int y, SecondExample z, String m) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.m = m;
	}
	public BigDecimal getX() {
		return x;
	}
	public void setX(BigDecimal x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public SecondExample getZ() {
		return z;
	}
	public void setZ(SecondExample z) {
		this.z = z;
	}
	public String getM() {
		return m;
	}
	public void setM(String m) {
		this.m = m;
	}
	
	
}
