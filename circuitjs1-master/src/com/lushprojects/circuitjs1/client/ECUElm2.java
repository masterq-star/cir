/*    
    Copyright (C) Paul Falstad and Iain Sharp
    
    This file is part of CircuitJS1.

    CircuitJS1 is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 2 of the License, or
    (at your option) any later version.

    CircuitJS1 is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with CircuitJS1.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.lushprojects.circuitjs1.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;

class ECUElm2 extends CircuitElm implements ClickHandler{
	int opsize, opheight, opwidth, opaddtext;
	static CheckEcu checkecubox;
	boolean outputvalve;
	Button checkecubutton;
	Label label;
	final int BATT = 8;
	final int IGSW = 10;
	final int OUTMAP =0;
	final int GNDMAP =1;
	final int BMAP =2;
	final int HT=3;
	final int BOXY =5;
	final int OX = 7;
	final int GNDOXY = 9;
	final int GND=11;
	final int BPLUS = 6;
	final int MREL = 4;
	final int INJ3 = 20;
	final int INJ2 = 18;
	final int INJ4 = 22;
	final int INJ1 = 16;
	final int GNDVSS = 14;
	final int OUTVSS = 12;
	final int VCCVSS = 13;
	final int EKNK=15;
	final int KNK1=17;
	final int GND2=19;
	final int OCVSUB=21;
	final int OCVPLUS=23;
	final int IGF =24;
	
	
	boolean reset;
	double duty_cycle_vvti;
	final int FLAG_SWAP = 1;
	final int FLAG_SMALL = 2;
	final int FLAG_LOWGAIN = 4;
	final int FLAG_GAIN = 8;
	double teeth_count;
	double cycle_vvti = 0.025;
	public ECUElm2(int xx, int yy) {
	    super(xx, yy);
	    noDiagonal = true;
	    duty_cycle_vvti=1;
           flags = FLAG_GAIN; // need to do this before setSize()
           setSize(sim.smallGridCheckItem.getState() ? 1 : 2);
           create_button();
	}
	public ECUElm2(int xa, int ya, int xb, int yb, int f,
			StringTokenizer st) {
	    super(xa, ya, xb, yb, f);
	    duty_cycle_vvti=1;
	    create_button();
	    // GBW has no effect in this version of the simulator, but we
	    // retain it to keep the file format the same
	    try {
		volts[0] = new Double(st.nextToken()).doubleValue();
		volts[1] = new Double(st.nextToken()).doubleValue();
		volts[2] = new Double(st.nextToken()).doubleValue();
		volts[3] = new Double(st.nextToken()).doubleValue();
		volts[4] = new Double(st.nextToken()).doubleValue();
		volts[5] = new Double(st.nextToken()).doubleValue();
		volts[6] = new Double(st.nextToken()).doubleValue();
		volts[7] = new Double(st.nextToken()).doubleValue();
		volts[8] = new Double(st.nextToken()).doubleValue();
		volts[9] = new Double(st.nextToken()).doubleValue();
		volts[10] = new Double(st.nextToken()).doubleValue();
		volts[11] = new Double(st.nextToken()).doubleValue();
		volts[12] = new Double(st.nextToken()).doubleValue();
		volts[13] = new Double(st.nextToken()).doubleValue();
		volts[14] = new Double(st.nextToken()).doubleValue();
		volts[15] = new Double(st.nextToken()).doubleValue();
		volts[16] = new Double(st.nextToken()).doubleValue();
		volts[17] = new Double(st.nextToken()).doubleValue();
		volts[18] = new Double(st.nextToken()).doubleValue();
		volts[19] = new Double(st.nextToken()).doubleValue();
		volts[20] = new Double(st.nextToken()).doubleValue();
		volts[21] = new Double(st.nextToken()).doubleValue();
		volts[22] = new Double(st.nextToken()).doubleValue();
		volts[23] = new Double(st.nextToken()).doubleValue();
		volts[24] = new Double(st.nextToken()).doubleValue();
	    } catch (Exception e) {
	    }
	    noDiagonal = true;
	    setSize((f & FLAG_SMALL) != 0 ? 1 : 2);
	}
	String dump() {
	    flags |= FLAG_GAIN;
	    return super.dump() + " " + volts[0] + " " + volts[1] + " "+ volts[2]+ " "+volts[3] + " "+ volts[4]+
		    " " + volts[5] + " "+ volts[6]+ " "+
	    volts[7] + " "+ volts[8] + " "+ volts[9]+ " " + volts[10] +
	    " "+ volts[11]+ " "+volts[12]+ " "+ volts[13]+ " " + volts[14] + " "+ volts[15]+ " "+volts[16] 
		    + " "+volts[17]+ " "+ volts[18]+ " " + volts[19] + " "+ volts[20]+ " "+volts[21]
			    + " "+ volts[22]+ " " + volts[23] + " "+ volts[24];
	}
	boolean nonLinear() { return true; }
	void draw(Graphics g) {
	    setBbox(point1, point2, opheight*2);
	    setVoltageColor(g, volts[0]);
	    drawThickLine(g, in1p[0], in1p[1]);
	    setVoltageColor(g, volts[1]);
	    drawThickLine(g, in2p[0], in2p[1]);
	    setVoltageColor(g, volts[2]);
	    drawThickLine(g, in3p[0], in3p[1]);
	    setVoltageColor(g, volts[3]);
	    drawThickLine(g, in4p[0], in4p[1]);
	    setVoltageColor(g, volts[4]);
	    drawThickLine(g, in5p[0], in5p[1]);
	    setVoltageColor(g, volts[5]);
	    drawThickLine(g, in6p[0], in6p[1]);
	    setVoltageColor(g, volts[6]);
	    drawThickLine(g, in7p[0], in7p[1]);
	    setVoltageColor(g, volts[7]);
	    drawThickLine(g, in8p[0], in8p[1]);
	    setVoltageColor(g, volts[8]);
	    drawThickLine(g, in9p[0], in9p[1]);
	    setVoltageColor(g, volts[9]);
	    drawThickLine(g, in10p[0], in10p[1]);
	    setVoltageColor(g, volts[10]);
	    drawThickLine(g, in11p[0], in11p[1]);
	    setVoltageColor(g, volts[11]);
	    drawThickLine(g, in12p[0], in12p[1]);
	    
	    // out put
	    setVoltageColor(g, volts[12]);
	    drawThickLine(g, lead2, point2);
	    setVoltageColor(g, volts[13]);
	    drawThickLine(g, out1p[0], out1p[1]);
	    setVoltageColor(g, volts[14]);
	    drawThickLine(g, out2p[0], out2p[1]);
	    setVoltageColor(g, volts[15]);
	    drawThickLine(g, out3p[0], out3p[1]);
	    setVoltageColor(g, volts[16]);
	    drawThickLine(g, out4p[0], out4p[1]);
	    setVoltageColor(g, volts[17]);
	    drawThickLine(g, out5p[0], out5p[1]);
	    setVoltageColor(g, volts[18]);
	    drawThickLine(g, out6p[0], out6p[1]);
	    setVoltageColor(g, volts[19]);
	    drawThickLine(g, out7p[0], out7p[1]);
	    setVoltageColor(g, volts[20]);
	    drawThickLine(g, out8p[0], out8p[1]);
	    setVoltageColor(g, volts[21]);
	    drawThickLine(g, out9p[0], out9p[1]);
	    setVoltageColor(g, volts[22]);
	    drawThickLine(g, out10p[0], out10p[1]);
	    setVoltageColor(g, volts[23]);
	    drawThickLine(g, out11p[0], out11p[1]);
	    setVoltageColor(g, volts[24]);
	    drawThickLine(g, out12p[0], out12p[1]);
	    
	    g.setColor(needsHighlight() ? selectColor : lightGrayColor);
	    setPowerColor(g, true);
	    //drawThickLine(g, point1, point2);
	    drawThickPolygon(g, triangle);
	    g.setFont(plusFont);
	    // write lable
	    drawCenteredText(g, "VG", textp[0].x, textp[0].y, true);
	    drawCenteredText(g, "EVG", textp[1].x, textp[1].y  , true);
	    drawCenteredText(g, "B+", textp2[0].x, textp2[0].y, true);
	    drawCenteredText(g, "HT", textp2[1].x, textp2[1].y  , true);
	    drawCenteredText(g, "M-REL", textp3[0].x, textp3[0].y, true);
	    drawCenteredText(g, "B+", textp3[1].x, textp3[1].y  , true);
	    drawCenteredText(g, "B+", textp4[0].x, textp4[0].y, true);
	    drawCenteredText(g, "OX", textp4[1].x, textp4[1].y  , true);
	    drawCenteredText(g, "BATT", textp5[0].x, textp5[0].y, true);
	    drawCenteredText(g, "E1", textp5[1].x, textp5[1].y  , true);
	    drawCenteredText(g, "IGSW", textp6[0].x, textp6[0].y, true);
	    drawCenteredText(g, "GND", textp6[1].x, textp6[1].y  , true);
	    drawCenteredText(g, "NC", textp7[0].x, textp7[0].y, true);
	    drawCenteredText(g, "GND", textp7[1].x, textp7[1].y  , true);
	    drawCenteredText(g, "NC", textp8[0].x, textp8[0].y, true);
	    drawCenteredText(g, "#10", textp8[1].x, textp8[1].y  , true);
	    
	    
	    drawCenteredText(g, "NC", textp9[0].x, textp9[0].y, true);
	    drawCenteredText(g, "#20", textp9[1].x, textp9[1].y  , true);
	    drawCenteredText(g, "GND", textp10[0].x, textp10[0].y, true);
	    drawCenteredText(g, "#30", textp10[1].x, textp10[1].y  , true);
	    drawCenteredText(g, "OCV-", textp11[0].x, textp11[0].y, true);
	    drawCenteredText(g, "#40", textp11[1].x, textp11[1].y  , true);
	    drawCenteredText(g, "OCV+", textp12[0].x, textp12[0].y, true);
	    drawCenteredText(g, "NC", textp12[1].x, textp12[1].y  , true);
	    drawCenteredText(g, "NC", texto3[0].x, texto3[0].y  , true);
	    g.setFont(lablefont);
	    drawCenteredText(g, "ECU", lable[0].x,lable[0].y  , true);
	    curcount = updateDotCount(current, curcount);
	    drawDots(g, point2, lead2, curcount);
	    drawPosts(g);
	}
	double getPower() { return volts[4]*current; }
	Point in1p[], in2p[],in3p[], in4p[],in5p[], in6p[] ,
	in7p[], in8p[],in9p[], in10p[],in11p[], in12p[] ,
	textp[],lable[],textp2[],textp3[],textp4[],textp5[],textp6[],textp7[],textp8[],
	textp9[],textp10[],textp11[],textp12[],textp13[],textp14[],textp15[],
	out1p[],out2p[],out3p[],out4p[],out5p[],out6p[],out7p[],out8p[],
	out9p[],out10p[],out11p[],out12p[],
	texto3[];
	Polygon triangle;
	Font plusFont;
	Font lablefont;
	void setSize(int s) {
	    opsize = s;
	    opheight = 8*s;
	    opwidth = 13*s;
	    flags = (flags & ~FLAG_SMALL) | ((s == 1) ? FLAG_SMALL : 0);
	}
	void setPoints() {
	    super.setPoints();
	    if (dn > 150 && this == sim.dragElm)
		setSize(2);
	    int ww = opwidth;
	    if (ww > dn/2)
		ww = (int) (dn/2);
	    calcLeads(ww*9);
	    int hs = opheight*dsign;
	    if ((flags & FLAG_SWAP) != 0)
		hs = -hs;
	    in1p = newPointArray(2);
	    in2p = newPointArray(2);
	    in3p = newPointArray(2);
	    in4p = newPointArray(2);
	    in5p = newPointArray(2);
	    in6p = newPointArray(2);
	    in7p = newPointArray(2);
	    in8p = newPointArray(2);
	    in9p = newPointArray(2);
	    in10p = newPointArray(2);
	    in11p = newPointArray(2);
	    in12p = newPointArray(2);
	    
	    out1p = newPointArray(2);
	    out2p = newPointArray(2);
	    out3p = newPointArray(2);
	    out4p = newPointArray(2);
	    out5p = newPointArray(2);
	    out6p = newPointArray(2);
	    out7p = newPointArray(2);
	    out8p = newPointArray(2);
	    out9p = newPointArray(2);
	    out10p = newPointArray(2);
	    out11p = newPointArray(2);
	    out12p = newPointArray(2);
	    
	    
	    textp = newPointArray(2);
	    textp2 = newPointArray(2);
	    textp3 = newPointArray(2);
	    textp4 = newPointArray(2);
	    textp5 = newPointArray(2);
	    textp6 = newPointArray(2);
	    textp7 = newPointArray(2);
	    textp8 = newPointArray(2);
	    textp9 = newPointArray(2);
	    textp10 = newPointArray(2);
	    textp11 = newPointArray(2);
	    textp12 = newPointArray(2);
	    textp13 = newPointArray(2);
	    textp14 = newPointArray(2);
	    textp15 = newPointArray(2);
	    lable = newPointArray(1);
	    texto3 = newPointArray(1);
	  //draw point start
	    interpPoint2(point1, point2, in1p[0],  in2p[0], 0, hs);
	    interpPoint2(point1, point2, in3p[0],  in4p[0], 0, hs*3);
	    interpPoint2(point1, point2, in5p[0],  in6p[0], 0, hs*5);
	    interpPoint2(point1, point2, in7p[0],  in8p[0], 0, hs*7);
	    interpPoint2(point1, point2, in9p[0],  in10p[0], 0, hs*9);
	    interpPoint2(point1, point2, in11p[0],  in12p[0], 0, hs*11);
	    // draw end point
	    interpPoint2(lead1 , lead2,  in1p[1],  in2p[1], 0, hs);
	    interpPoint2(lead1 , lead2,  in3p[1],  in4p[1], 0, hs*3);
	    interpPoint2(lead1 , lead2,  in5p[1],  in6p[1], 0, hs*5);
	    interpPoint2(lead1 , lead2,  in7p[1],  in8p[1], 0, hs*7);
	    interpPoint2(lead1 , lead2,  in9p[1],  in10p[1], 0, hs*9);
	    interpPoint2(lead1 , lead2,  in11p[1],  in12p[1], 0, hs*11);
	    // draw position text
	    interpPoint2(lead1 , lead2,  textp[0], textp[1], 0.15, hs);
	    interpPoint2(lead1 , lead2,  textp2[0], textp2[1], 0.15, hs*3);
	    interpPoint2(lead1 , lead2,  textp3[0], textp3[1], 0.15, hs*5);
	    interpPoint2(lead1 , lead2,  textp4[0], textp4[1], 0.15, hs*7);
	    interpPoint2(lead1 , lead2,  textp5[0], textp5[1], 0.15, hs*9);
	    interpPoint2(lead1 , lead2,  textp6[0], textp6[1], 0.15, hs*11);
	    
	    // draw output begin
	    interpPoint2(lead2, point2, out1p[0],  out2p[0], 0, hs*2);
	    interpPoint2(lead2, point2, out3p[0],  out4p[0], 0, hs*4);
	    interpPoint2(lead2, point2, out5p[0],  out6p[0], 0, hs*6);
	    interpPoint2(lead2, point2, out7p[0],  out8p[0], 0, hs*8);
	    interpPoint2(lead2, point2, out9p[0],  out10p[0], 0, hs*10);
	    interpPoint2(lead2, point2, out11p[0],  out12p[0], 0, hs*12);
	    // draw output end
	    
	    interpPoint2(lead2, point2, out1p[1],  out2p[1], 1, hs*2);
	    interpPoint2(lead2, point2, out3p[1],  out4p[1], 1, hs*4);
	    interpPoint2(lead2, point2, out5p[1],  out6p[1], 1, hs*6);
	    interpPoint2(lead2, point2, out7p[1],  out8p[1], 1, hs*8);
	    interpPoint2(lead2, point2, out9p[1],  out10p[1], 1, hs*10);
	    interpPoint2(lead2, point2, out11p[1],  out12p[1], 1, hs*12);
	    
	    // draw lable output
	    interpPoint2(lead2, point2, textp7[0],  textp7[1], -0.3, hs*2);
	    interpPoint2(lead2, point2, textp8[0],  textp8[1], -0.3, hs*4);
	    interpPoint2(lead2, point2, textp9[0],  textp9[1], -0.3, hs*6);
	    interpPoint2(lead2, point2, textp10[0],  textp10[1], -0.3, hs*8);
	    interpPoint2(lead2, point2, textp11[0],  textp11[1], -0.3, hs*10);
	    interpPoint2(lead2, point2, textp12[0],  textp12[1], -0.3, hs*12);
	    interpPoint(lead2, point2, texto3[0], -0.3);
	    //
	    Point rect[] = newPointArray(4);
	    interpPoint2(lead1,  lead2,  rect[0], rect[1],  0, hs*13);
	    interpPoint2(lead1,  lead2,  rect[2], rect[3],  1, hs*13);
	    
	    // point lable 
	    
	    interpPoint(lead1, lead2, lable[0], 0.5);
	    triangle = createPolygon(rect[0], rect[1],  rect[3], rect[2]);
	    plusFont = new Font("SansSerif", 0, opsize == 2 ? 14 : 10);
	    lablefont = new Font("SansSerif", 1, 20);
	}
	int getPostCount() { return 25; }
	Point getPost(int n) {
	    return (n == 0) ? in1p[0] : (n == 1) ? in2p[0] : (n == 2) ?
		    in3p[0] :(n == 3) ?in4p[0]:(n==4)?in5p[0]:
		(n==5)?in6p[0]:(n==6)?in7p[0]:(n==7)?
			in8p[0]:(n==8)?in9p[0]:(n==9)?in10p[0]:
			    (n==10)?in11p[0]:(n==11)?in12p[0]:(n==12)?point2:(n==13)?out1p[1]:(n==14)?out2p[1]:(n==15)?out3p[1]:
				(n==16)?out4p[1]:(n==17)?out5p[1]:(n==18)?out6p[1]:(n==19)?out7p[1]:(n==20)?out8p[1]:(n==21)?out9p[1]:(n==22)?out10p[1]:(n==23)?out11p[1]:out12p[1];
	    //return (n == 0) ? in1p[0] : (n == 1) ? in2p[0] : (n == 2) ?in3p[0] :(n == 3) ?in4p[0]:(n == 4) ?in5p[0]:point2;
	}
	int getVoltageSourceCount() { return 1; }
	void getInfo(String arr[]) {
	    arr[0] = "ECU";
	    arr[1] = "count" + teeth_count; 
	    arr[2] = "volt" + voltSource;
//	    arr[1] = "V1 = " + getVoltageText(volts[0]);
//	    arr[2] = "V2 = " + getVoltageText(volts[1]);
//	    arr[3] = "V3 = " + getVoltageText(volts[2]);
//	    arr[4] = "V4 = " + getVoltageText(volts[3]);
//	    arr[5] = "V5 = " + getVoltageText(volts[4]);
//	    arr[6] = "V6 = " + getVoltageText(volts[5]);
//	    arr[7] = "V7 = " + getVoltageText(volts[6]);
//	    arr[8] = "V8 = " + getVoltageText(volts[7]);
//	    arr[9] = "V9 = " + getVoltageText(volts[8]);
//	    arr[10] = "V10 = " + getVoltageText(volts[9]);
//	    arr[11] = "V11 = " + getVoltageText(volts[10]);
//	    arr[12] = "V12 = " + getVoltageText(volts[11]);
//	    arr[13] = "V13 = " + getVoltageText(volts[12]);
	    
	}

	double lastvoltcheck;
	double max_amp,min_amp;
	double min_volt =0;
	boolean check1 = false;
	void stamp() {
//	    
//	    sim.stampResistor(nodes[BATT], nodes[VCCVSS], 175000);
//	    sim.stampResistor(nodes[VCCVSS], nodes[GND], 125000);
//	    sim.stampResistor(nodes[GNDVSS],nodes[GND], 0.05);
//	    sim.stampResistor(nodes[EKNK], nodes[GND], 0.005);
//	    sim.stampResistor(nodes[BPLUSIDL], nodes[BATT], 0.005);
	    sim.stampVoltageSource(nodes[GND], nodes[INJ1], voltSource);
	    sim.stampVoltageSource(nodes[GND], nodes[INJ2], voltSource);
	    sim.stampVoltageSource(nodes[GND], nodes[INJ3], voltSource);
	    sim.stampVoltageSource(nodes[GND], nodes[INJ4], voltSource);
	    sim.stampResistor(nodes[HT], nodes[GND], 0.000005);
	    sim.stampResistor(nodes[BOXY], nodes[BATT], 0.005);
	    sim.stampResistor(nodes[GNDOXY], nodes[GND], 0.005);
	    sim.stampResistor(nodes[BATT], nodes[BMAP], 0.005);
	    sim.stampResistor(nodes[GNDMAP], nodes[GND], 0.005);
	    sim.stampResistor(nodes[GND], nodes[GND2], 0.05);
	    sim.stampResistor(nodes[OCVSUB], nodes[GND], 0.05);
	    sim.stampNonLinear(nodes[OCVPLUS]);
	    sim.stampNonLinear(nodes[BATT]);
	    sim.stampNonLinear(nodes[GND]);
	    sim.stampNonLinear(nodes[VCCVSS]);
	    //sim.stampMatrix(nodes[4], vn, 1);
	}
	void create_button(){
	    sim.addWidgetToVerticalPanel(checkecubutton = new Button("Kiểm Tra Ecu"));
	    checkecubutton.addClickHandler(new ClickHandler(){
		public void onClick(ClickEvent event) {
		    
		    //checkecubox = new CheckEcu(round(volts[IGSW],2),round(volts[BATT],2),check_ckp,check_cmp,check_vss,check_knk,round(volts[THW],2),round(volts[VTA],2));
		    }
	    });
	}
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	boolean first_ckp  = false, first_cmp = false,first_vss = false,check_ckp = false,check_cmp = false,check_vss = false, check_knk=false;
	double first_time,first_time_cmp,first_time_vss;
	void doStep() {
//	    double vd = volts[1] - volts[0];
//	    if (Math.abs(lastvd-vd) > .1)
//		sim.converged = false;
//	    else if (volts[2] > maxOut+.1 || volts[2] < minOut-.1)
//		sim.converged = false;
//	    double x = 0;
	//    int vn = sim.nodeList.size()+voltSource;
//	    double dx = 0;
//	    if (vd >= maxOut/gain && (lastvd >= 0 || sim.getrand(4) == 1)) {
//		dx = 1e-4;
//		x = maxOut - dx*maxOut/gain;
//	    } else if (vd <= minOut/gain && (lastvd <= 0 || sim.getrand(4) == 1)) {
//		dx = 1e-4;
//		x = minOut - dx*minOut/gain;
//	    } else
//		dx = gain;
//	    GWT.log("opamp " + vd + " " + volts[2] + " " + dx + " "  + x + " " + lastvd + " " + sim.converged +"  "+ voltSource+"   "+sim.nodeList.size());
	   // GWT.log("test node " + vn);
	    // newton-raphson
//	    sim.stampMatrix(vn, nodes[0],1/5);
//	    sim.stampMatrix(vn, nodes[1], 1/2);
//	    sim.stampMatrix(vn, nodes[2], 1/3);
//	    sim.stampMatrix(vn, nodes[3],1/4);
//	    sim.stampMatrix(vn, nodes[4], 1);
//	    sim.stampRightSide(vn, 10);
//	  volts[6]=6;
	    if(volts[BPLUS]>11){
		 sim.updateVoltageSource(nodes[GND], nodes[INJ1], voltSource,getvolt_inj());
		 sim.updateVoltageSource(nodes[GND], nodes[INJ2], voltSource,getvolt_inj());
		 sim.updateVoltageSource(nodes[GND], nodes[INJ3], voltSource,getvolt_inj());
		 sim.updateVoltageSource(nodes[GND], nodes[INJ4], voltSource,getvolt_inj());
		 
		 
		 if(sim.t%cycle_vvti > 0 && sim.t%cycle_vvti < (cycle_vvti*duty_cycle_vvti)/100){
		     sim.stampResistor(nodes[BATT], nodes[OCVPLUS],1000);
		 }
		 else sim.stampResistor(nodes[GND], nodes[OCVPLUS], 1000);
	    }
	    
	   // volts[BMAP]=5;
	    
	    if(volts[IGSW] >11.5 )sim.stampResistor(nodes[BATT], nodes[MREL], 10);
	    else sim.stampResistor(nodes[IGSW], nodes[GND], 10);
	    
	    
	    
//	    lastvd = vd;
//	    if (sim.converged)
//	      System.out.println((volts[1]-volts[0]) + " " + volts[2] + " " + initvd);
	}
	double getvolt_inj(){
	    double datareturn=3;
	    double time =sim.t%0.025;
	    if(sim.t%0.05 > 0 && sim.t%0.05< 0.02) datareturn= 3;
	    else if(sim.t%0.05 >= 0.02 && sim.t%0.05< 0.025) datareturn = 0;
	    else if(sim.t%0.05 >= 0.025 && sim.t%0.05< 0.05) datareturn = 3 + 1/(time *1000);

            if(datareturn > 12) datareturn = 12;
	    return datareturn;
	    
	}
	double getVoltageDiff() { return volts[BATT] - volts[GND]; }
	// there is no current path through the op-amp inputs, but there
	// is an indirect path through the output to ground.
	boolean getConnection(int n1, int n2) { return false; }
	boolean hasGroundConnection() {
	    return true;
	}
	//double getVoltageDiff() { return volts[2] - volts[1]; }
	int getDumpType() { return 426; }
	public EditInfo getEditInfo(int n) {
            if(n==0){
        	EditInfo ei = new EditInfo("", 0, -1, -1);
    	    ei.checkbox = new Checkbox("mở van / đóng van", outputvalve);
    	    return ei;
            }
            if(n==1){
        	return new EditInfo("Duty cycle VVTI", duty_cycle_vvti, 0, 0);
            }
	    return null;
	}
	public void setEditValue(int n, EditInfo ei) {
	    if(n == 0) outputvalve = ei.checkbox.getState();
	    if(n==1) duty_cycle_vvti = ei.value;
		
//	    if (n == 0)
//		maxOut = ei.value;
//	    if (n == 1)
//		minOut = ei.value;
//	    if (n == 2 && ei.value > 0)
//		gain = ei.value;
	}
	
	@Override double getCurrentIntoNode(int n) { 
	    if (n==4)
		return -current;
	   return 0;
	}
	public void onClick(ClickEvent event) {
	    // TODO Auto-generated method stub
	    
	}
	void reset() {
		int i;
		for (i = 0; i != getPostCount()+getInternalNodeCount(); i++)
		    volts[i] = 0;
		curcount = 0;
		teeth_count =0;
	    }
	
	void delete() {
		sim.removeWidgetFromVerticalPanel(checkecubutton);
	        super.delete();
	    }
	 public Boolean checkInfor(){return true;}
	    String codeHtmlString = "<div style='overflow-y: scroll; height:400px;'>"
	    	+ "<img src='https://i0.wp.com/gomechanic.in/blog/wp-content/uploads/2020/10/ecu_steuergeraet_epd_thumbnail.jpg?resize=696%2C392&ssl=1' alt='ECU' height='300'>"
	    	+ "<h3>ECU LÀ GÌ?</h3>"
	    	+ "<p>ECU là một hệ thống điều khiển điện tử của xe ôtô và là tên viết tắt của từ Electronic Control Unit. Để dễ hiểu và dễ hình dung hơn bạn có thể hiểu ECU chính là phần hộp đen, được ví như một “bộ não” có thể tiếp nhận thông tin, chi phối và ghi lại các hoạt động của xe ôtô. Nếu bạn là tín đồ của các bộ phim hình sự, thì sẽ không thể quên được các chi tiết phá án của cảnh sát, cần sử dụng đến hộp đen ôtô để làm bằng chứng, đó chính là ECU.</p>"
	    	+ "<h3>Nguyên lý hoạt động của ECU</h3>"
	    	+ "<p>ECU hoạt động được một phần là do cảm biến tốc độ của động cơ và các vị trí piston. Sự phụ thuộc lẫn nhau này sẽ hỗ trợ và giúp ECU có thể xác định được chính xác mọi thời điểm phun xăng, đánh lửa để tạo ra khả năng tiết kiệm nhiên liệu và hiệu suất xe được tốt nhất.</p>"
	    	+ "<p>ECU còn có quá trình tính toán để đưa ra các góc đánh lửa theo các chế độ hoạt động khác nhau của động cơ phụ thuộc vào các loại cảm biến hóa. Ngoài ra, ECU còn có vai trò làm giảm khí thải ra môi trường nhờ các hoạt động của cảm biến Oxy xác định phần hòa khí tức thời. </p>"
	    	+ "<img src='https://tinbanxe.vn/uploads/news/2020_04/nguyen-ly-hoat-dong-cua-ecu.jpg' alt='ECU2' height='300'>"
	    	+ "</div>";
	    public String CodeHtml(){
		return codeHtmlString;
	    }
    }
