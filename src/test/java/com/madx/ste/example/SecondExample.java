package com.madx.ste.example;

import java.util.Arrays;
import java.util.List;

public class SecondExample {
	private int q;
	private List<Integer> w;
	private Integer[] e;
	public SecondExample(int q, List<Integer> w, Integer[] e) {
		this.q = q;
		this.w = w;
		this.e = e;
	}
	public int getQ() {
		return q;
	}
	public void setQ(int q) {
		this.q = q;
	}
	public List<Integer> getW() {
		return w;
	}
	public void setW(List<Integer> w) {
		this.w = w;
	}
	public Integer[] getE() {
		return e;
	}
	public void setE(Integer[] e) {
		this.e = e;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SecondExample other = (SecondExample) obj;
		if (!Arrays.equals(e, other.e))
			return false;
		if (q != other.q)
			return false;
		if (w == null) {
			if (other.w != null)
				return false;
		} else if (!w.equals(other.w))
			return false;
		return true;
	}
	
}
