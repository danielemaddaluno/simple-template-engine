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
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FirstExample other = (FirstExample) obj;
		if (m == null) {
			if (other.m != null)
				return false;
		} else if (!m.equals(other.m))
			return false;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y != other.y)
			return false;
		if (z == null) {
			if (other.z != null)
				return false;
		} else if (!z.equals(other.z))
			return false;
		return true;
	}
}
