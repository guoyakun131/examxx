package com.extr.file.util;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class PSOTest1 extends Frame implements Runnable {
	
	/**
	 * 粒子个数
	 */
	private static int particleNumber;
	/**
	 * 迭代次数
	 */
	private static int iterations;
	private static int k = 1;
	final private static float C1 = 2;
	final private static float C2 = 2;
	final private static float WMIN = 0;
	final private static float WMAX = 4;
	final private static float VMAX = 0;
	private static float r1;
	private static float r2;
	/**
	 * 粒子位置
	 */
	private static float x[][];
	/**
	 * 粒子速度
	 */
	private static float v[][];
	/**
	 * 粒子最优位置
	 */
	private static float xpbest[][];
	/**
	 * 粒子最优值
	 */
	private static float pbest[];
	/**
	 * 全局最好值
	 */
	private static float gbest = 10;
	/**
	 * 全局最好位置
	 */
	private static float xgbest[];
	private static float w;
	private static float s;
	private static float h;
	/**
	 * 适应度
	 */
	private static float fit[];

	public void lzqjs() {

		w = (float) (0.9 - k * (0.9 - 0.4) / iterations);
		for (int i = 0; i < particleNumber; i++) {
			fit[i] =(float) ( -1*Math.pow(x[i][0], 2) - x[i][0]);
			if (fit[i] > pbest[i]) {
				
				pbest[i] = fit[i];
				xpbest[i][0] = x[i][0];
				if(x[i][0] > 4) x[i][0] = 4;
				if(x[i][0] < 0) x[i][0] = 0;
			}
			if (pbest[i] > gbest) {
				gbest = pbest[i];
				xgbest[0] = xpbest[i][0];
			}
			/*System.out.println("pbest[i]=" + pbest[i]);
			System.out.println("gbest=" + gbest);*/
		}
		for (int i = 0; i < particleNumber; i++) {
			for (int j = 0; j < 1; j++) {
				v[i][j] = (float) (w * v[i][j] + C1 * Math.random()
						* (xpbest[i][j] - x[i][j]) + C2 * Math.random()
						* (xgbest[j] - x[i][j]));
				x[i][j] = (float) (x[i][j] + v[i][j]);
			}
		}
	}

	public static void main(String[] args) {
		particleNumber = Integer.parseInt(JOptionPane
				.showInputDialog("请输入粒子个数1-300）"));
		iterations = Integer.parseInt(JOptionPane
				.showInputDialog("请输入迭代次数10-100）"));
		x = new float[particleNumber][1];
		v = new float[particleNumber][1];
		fit = new float[particleNumber];
		pbest = new float[particleNumber];
		xpbest = new float[particleNumber][1];
		xgbest = new float[2];
		for (int i = 0; i < particleNumber; i++) {
			pbest[i] = 0;
			xpbest[i][0] = 0;
		}
		xgbest[0] = 0;
		xgbest[1] = 0;
		for (int i = 0; i < particleNumber; i++) {
			for (int j = 0; j < 1; j++) {
				x[i][j] = (float) (Math.random());
				v[i][j] = (float) (Math.random());
			}
		}
		PSOTest1 threada = new PSOTest1();
		/*threada.setTitle("基于粒子群的粒子位置动态显示");
		threada.setSize(800, 800);
		threada.addWindowListener(new gbck());
		threada.setVisible(true);*/
		Thread threadc = new Thread(threada);
		threadc.start();
		
	}

	/*static class gbck extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	}*/

	public void run() {
		lzqjs();
		System.out.println(gbest + "------" + xpbest[0][0]);
		/*repaint();*/
	}

	/*public void paint(Graphics g) {
		g.setColor(new Color(0, 0, 0));
		for (int i = 0; i < particleNumber; i++) {
			g.drawString("*", (int) (gbest + 200), (int) (x[i][0] + 200));
			System.out.println("x[i][0]=" + x[i][0] + "  " + "x[i][1]=" + x[i][1]);
		}
		g.setColor(new Color(255, 0, 0));
		g.drawString("适应度函数：" + gbest + "      参数1：" + xgbest[0] + "     参数2："
				+ xgbest[1], 50, 725);

		try {
			lzqjs();
			k = k + 1;
			if (k >= iterations) {
				System.exit(0);
			}
			Thread.sleep((int) (1000));
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
		repaint();
		
	}*/
}