/*******************************************************************************
 * This implementation is modified from：
 * 	 	http://grepcode.com/file/repo1.maven.org/maven2/com.googlecode.jasima/jasima-main/1.3.0/jasima/core/statistics/QuantileEstimator.java 
 * 
 * This file is part of jasima, v1.3, the Java simulator for manufacturing and 
 * logistics.
 *  
 * Copyright (c) 2015 		jasima solutions UG
 * Copyright (c) 2010-2015 Torsten Hildebrandt and jasima contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.test.quantile;

import java.util.Arrays;

/**
 * 
 * 分位值运算基于以下论文：
 * <ul>
 * <li>1. <a href=
 * "http://pierrechainais.ec-lille.fr/Centrale/Option_DAD/IMPACT_files/Dynamic%20quantiles%20calcultation%20-%20P2%20Algorythm.pdf">
 * Raj Jain, Imrich Chlamtac: The P2 Algorithm for Dynamic Calculation of
 * Quantiles and Histograms Without Storing Observations, ACM 28, 10 (1985) </a>
 * </li>
 * <li>2. <a href=
 * "http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.50.6060&rep=rep1&type=pdf">
 * Kimmo Raatikainen: Simultaneous estimation of several percentiles,
 * Simulations Councils (1987) </a></li>
 * </ul>
 * 
 * @author BurningIce
 *
 */
public class QuantileP2 {
	private double[] quartileList;
	private double[] markers_x = null;// 每个marker所代表的分位置横坐标
	private double[] markers_y = null;// 每个marker所代表的分位置纵坐标
	private int count;
	private int[] p2_n;

	public QuantileP2(double[] quartileList) {
		this.quartileList = quartileList;
		Arrays.sort(this.quartileList);
		markers_y = new double[this.quartileList.length * 2 + 3];
		initMarkers();
	}

	public double[] getMarkers_Y(){
		return markers_y;
	}
	public double[] getMarkers_X(){
		return markers_x;
	}
	public QuantileP2(double[] quartileList, double[] markers_y, int count) {
		assert quartileList != null && quartileList.length > 0;
		assert markers_y != null && markers_y.length > 0;
		assert markers_y.length == 2 * quartileList.length + 3;

		this.markers_y = markers_y;
		this.quartileList = quartileList;
		this.count = count;
		Arrays.sort(this.quartileList);
		initMarkers();
	}

	private void initMarkers() {
		int quartile_count = this.quartileList.length;
		int marker_count = quartile_count * 2 + 3;
		this.markers_x = new double[marker_count];
		this.markers_x[0] = .0;
		this.p2_n = new int[markers_y.length];
		for (int i = 0; i < quartile_count; i++) {
			double marker = this.quartileList[i];
			markers_x[i * 2 + 1] = (marker + markers_x[i * 2]) / 2;
			markers_x[i * 2 + 2] = marker;
		}
		markers_x[marker_count - 2] = (1 + this.quartileList[quartile_count - 1]) / 2;
		markers_x[marker_count - 1] = 1.0;
		for (int i = 0; i < marker_count; ++i) {
			// should this look at the desired marker pos?
			p2_n[i] = i;
		}
	}

	public void add(double v) {
		if (Double.isNaN(v)) {
			return;
		}

		int obsIdx = this.count;
		++this.count;

		if (obsIdx < markers_y.length) {
			// initialization
			markers_y[obsIdx] = v;

			if (obsIdx == markers_y.length - 1) {
				// finish initialization
				Arrays.sort(markers_y);
			}
		} else {
			// usual case
			int k = Arrays.binarySearch(markers_y, v);
			if (k < 0) {
				k = -(k + 1);
			}

			if (k == 0) {
				markers_y[0] = v;
				k = 1;
			} else if (k == markers_y.length) {
				k = markers_y.length - 1;
				markers_y[k] = v;
			}

			for (int i = k; i < p2_n.length; ++i) {
				++p2_n[i];
			}

			for (int i = 1; i < markers_y.length - 1; ++i) {
				double n_ = markers_x[i] * obsIdx;
				double di = n_ - p2_n[i];
				if ((di-1.0 >=0.000001  && p2_n[i + 1] - p2_n[i] > 1) || ((di+1.0 <=0.000001  && p2_n[i - 1] - p2_n[i] < -1))) {
					int d = di < 0 ? -1 : 1;

					double qi_ = quadPred(d, i);
					if (qi_ < markers_y[i - 1] || qi_ > markers_y[i + 1]) {
						qi_ = linPred(d, i);
					}
					markers_y[i] = qi_;
					p2_n[i] += d;
				}
			}
		}
	}

	public double[] merge(QuantileP2 qm) {
		if (markers_y == null || this.count == 0) {
			markers_y = qm.markers();
			this.p2_n = new int[markers_y.length];
			this.count = qm.getCount();
		} else {
			int i = 0;
			for (double q : markers_x) {
				markers_y[i] = mergeByQuantile(qm, q);
				i++;
			}
			
			this.count += qm.getCount();
		}
		
		return markers_y;
	}

	public double[] merge(double[] quantiles, double[] p2_q, int count) {
		QuantileP2 q = new QuantileP2(quantiles, p2_q, count);
		return merge(q);
	}

	private double mergeByQuantile(QuantileP2 qm, double quartile) {
		double y_max = Double.MIN_VALUE, y_min = Double.MAX_VALUE;
		double totla_count = count + qm.getCount();
		double temp = quantile(quartile);
		if (y_max < temp)
			y_max = temp;
		if (y_min > temp)
			y_min = temp;
		temp = qm.quantile(quartile);
		if (y_max < temp)
			y_max = temp;
		if (y_min > temp)
			y_min = temp;

		while (y_max - y_min > 0.01) {
			double y_mid = (y_max + y_min) / 2;
			double x_sum = count * antiFunction(markers_x, markers_y, y_mid)
					+ qm.getCount() * antiFunction(qm.getQuantileList(), qm.markers(), y_mid);
			if (x_sum > totla_count * quartile)
				y_max = y_mid;
			else
				y_min = y_mid;
		}
		return (int) y_max;
	}

	/**
	 * Estimates a quantile. If there is no marker for the quantile p, linear
	 * interpolation between the two closest markers is performed. If p is NaN,
	 * NaN will be returned. If there haven't been enough observations or the
	 * markers are not initialized, NaN is returned. If <code>p &lt;= 0.0</code>
	 * or <code>p &gt;= 1.0</code>, the minimum or maximum will be returned.
	 * 
	 * @param p
	 *            any number
	 * @return a number that is estimated to be bigger than 100p percent of all
	 *         numbers or Double.NaN, if no data is available
	 */
	public double quantile(double p) {
		if (Double.isNaN(p) || p2_n == null)
			return Double.NaN;
		if (this.count < p2_n.length)
			return markers_y[(int) (p * (count - 1))];
		if (p <= 0.0)
			return markers_y[0];
		if (p >= 1.0)
			return markers_y[markers_y.length - 1];
		int idx = Arrays.binarySearch(markers_x, p);
		if (idx < 0) {
			int left = -idx - 2;
			int right = -idx - 1;
			double pl = markers_x[left];
			double pr = markers_x[right];
			return (markers_y[left] * (pr - p) + markers_y[right] * (p - pl)) / (pr - pl);
		}
		return markers_y[idx];
	}

	public double[] markers() {
		if (this.count < markers_y.length) {
			double[] result = new double[count];
			double[] markers = new double[markers_y.length];
			double[] pw_q_copy = markers_y.clone();
			Arrays.sort(pw_q_copy);
			for (int i = pw_q_copy.length - count, j = 0; i < pw_q_copy.length; i++, j++) {
				result[j] = pw_q_copy[i];
			}
			for (int i = 0; i < pw_q_copy.length; i++) {
				markers[i] = result[(int) Math.round((count - 1) * i * 1.0 / (pw_q_copy.length - 1))];
			}
			return markers;
		}
		return this.markers_y;
	}

	private double quadPred(int d, int i) {
		double qi = markers_y[i];
		double qip1 = markers_y[i + 1];
		double qim1 = markers_y[i - 1];
		int ni = p2_n[i];
		int nip1 = p2_n[i + 1];
		int nim1 = p2_n[i - 1];

		double a = (ni - nim1 + d) * (qip1 - qi) / (nip1 - ni);
		double b = (nip1 - ni - d) * (qi - qim1) / (ni - nim1);
		return qi + (d * (a + b)) / (nip1 - nim1);
	}

	private double linPred(int d, int i) {
		double qi = markers_y[i];
		double qipd = markers_y[i + d];
		int ni = p2_n[i];
		int nipd = p2_n[i + d];

		return qi + d * (qipd - qi) / (nipd - ni);
	}

	public int getCount() {
		return count;
	}

	public double[] getQuantileList() {
		return markers_x;
	}

	private double[] lagrange(double[] pointx, double[] pointy) {
		double x1 = pointx[0];
		double x2 = pointx[1];
		double x3 = pointx[2];
		double y1 = pointy[0];
		double y2 = pointy[1];
		double y3 = pointy[2];
		double p = y1 / ((x1 - x2) * (x1 - x3));
		double q = y2 / ((x2 - x1) * (x2 - x3));
		double r = y3 / ((x3 - x1) * (x3 - x2));
		double a = p + q + r;
		double b = -p * (x2 + x3) - q * (x1 + x3) - r * (x1 + x2);
		double c = p * x2 * x3 + q * x1 * x3 + r * x1 * x2;
		double[] result = { a, b, c };
		return result;
	}

	private double antiFunction(double[] markers_x, double[] markers_y, double y) {
		int right = markers_x.length - 1, left = 0;
		if (y >= markers_y[right])
			return 1;
		if (y <= markers_y[left])
			return 0;
		while (right - left > 1) {
			int mid = (int) ((right + left) / 2);
			if (markers_y[mid] <= y)
				left = mid;
			else if (markers_y[mid] > y)
				right = mid;
		}
		int[] idx_ = new int[3];
		if (left == 0)
			idx_[0] = 0;
		else
			idx_[0] = left - 1;
		for (int j = 1; j < 3; j++) {
			idx_[j] = j + idx_[0];
		}
		double[] x_ = new double[3];
		double[] y_ = new double[3];
		for (int i = 0; i < 3; i++) {
			x_[i] = markers_x[idx_[i]];
			y_[i] = markers_y[idx_[i]];
		}
		double[] params = lagrange(x_, y_);
		double ans = (-params[1] + Math.sqrt(Math.pow(params[1], 2) - 4 * params[0] * (params[2] - y)))
				/ (2 * params[0]);
		try {
			assert ans < 1 && ans > 0;
		} catch (AssertionError e) {
			int mmm = 1;
		}
		return ans;
	}
}
