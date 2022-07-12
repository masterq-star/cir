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

class ECUElm extends CircuitElm implements ClickHandler{
	int opsize, opheight, opwidth, opaddtext;
	static CheckEcu checkecubox;
	Button checkecubutton;
	Label label;
	final int BATT = 8;
	final int IGSW = 10;
	final int CKP =2;
	final int GNDNE =0;
	final int CMP =1;
	final int THW=3;
	final int E2 =5;
	final int VCTPS = 7;
	final int VTA = 9;
	final int GND=11;
	final int BPLUS = 6;
	final int MREL = 4;
	final int IGT3 = 12;
	final int IGT2 = 13;
	final int IGT4 = 14;
	final int IGT1 = 15;
	
	boolean reset;
	final int FLAG_SWAP = 1;
	final int FLAG_SMALL = 2;
	final int FLAG_LOWGAIN = 4;
	final int FLAG_GAIN = 8;
	double teeth_count;
	double cycle;
	public ECUElm(int xx, int yy) {
	    super(xx, yy);
	    noDiagonal = true;
           flags = FLAG_GAIN; // need to do this before setSize()
           setSize(sim.smallGridCheckItem.getState() ? 1 : 2);
           create_button();
	}
	public ECUElm(int xa, int ya, int xb, int yb, int f,
			StringTokenizer st) {
	    super(xa, ya, xb, yb, f);
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
	    } catch (Exception e) {
	    }
	    noDiagonal = true;
	    setSize((f & FLAG_SMALL) != 0 ? 1 : 2);
	}
	String dump() {
	    flags |= FLAG_GAIN;
	    return super.dump() + " " + volts[0] + " " + volts[1] + " "+ volts[2]+ " "+volts[3] + " "+ volts[4]+ " " + volts[5] + " "+ volts[6]+ " "+volts[7] + " "+ volts[8] + " "+ volts[9]+ " " + volts[10] + " "+ volts[11]+ " "+volts[12]+ " "+ volts[13]+ " " + volts[14] + " "+ volts[15]+ " "+volts[16]  ;
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
	    
	    g.setColor(needsHighlight() ? selectColor : lightGrayColor);
	    setPowerColor(g, true);
	    //drawThickLine(g, point1, point2);
	    drawThickPolygon(g, triangle);
	    g.setFont(plusFont);
	    // write lable
	    drawCenteredText(g, "NE-", textp[0].x, textp[0].y, true);
	    drawCenteredText(g, "G", textp[1].x, textp[1].y  , true);
	    drawCenteredText(g, "NE+", textp2[0].x, textp2[0].y, true);
	    drawCenteredText(g, "THW", textp2[1].x, textp2[1].y  , true);
	    drawCenteredText(g, "M-REL", textp3[0].x, textp3[0].y, true);
	    drawCenteredText(g, "E2", textp3[1].x, textp3[1].y  , true);
	    drawCenteredText(g, "B+", textp4[0].x, textp4[0].y, true);
	    drawCenteredText(g, "VC(TPS)", textp4[1].x, textp4[1].y  , true);
	    drawCenteredText(g, "BATT", textp5[0].x, textp5[0].y, true);
	    drawCenteredText(g, "VTA", textp5[1].x, textp5[1].y  , true);
	    drawCenteredText(g, "IGSW", textp6[0].x, textp6[0].y, true);
	    drawCenteredText(g, "GND", textp6[1].x, textp6[1].y  , true);
	    drawCenteredText(g, "IGT 2", textp7[0].x, textp7[0].y, true);
	    drawCenteredText(g, "IGT 4", textp7[1].x, textp7[1].y  , true);
	    drawCenteredText(g, "IGT 1", textp8[0].x, textp8[0].y, true);
	    drawCenteredText(g, "E1", textp8[1].x, textp8[1].y  , true);
	    drawCenteredText(g, "IGT 3", texto3[0].x, texto3[0].y  , true);
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
	out1p[],out2p[],out3p[],out4p[],texto3[];
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
	    
	    
	    textp = newPointArray(2);
	    textp2 = newPointArray(2);
	    textp3 = newPointArray(2);
	    textp4 = newPointArray(2);
	    textp5 = newPointArray(2);
	    textp6 = newPointArray(2);
	    textp7 = newPointArray(2);
	    textp8 = newPointArray(2);
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
	    
	    
	    // draw output end
	    
	    interpPoint2(lead2, point2, out1p[1],  out2p[1], 1, hs*2);
	    interpPoint2(lead2, point2, out3p[1],  out4p[1], 1, hs*4);
	    
	    // draw lable output
	    interpPoint2(lead2, point2, textp7[0],  textp7[1], -0.3, hs*2);
	    interpPoint2(lead2, point2, textp8[0],  textp8[1], -0.3, hs*4);
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
	int getPostCount() { return 17; }
	Point getPost(int n) {
	    return (n == 0) ? in1p[0] : (n == 1) ? in2p[0] : (n == 2) ?
		    in3p[0] :(n == 3) ?in4p[0]:(n==4)?in5p[0]:
		(n==5)?in6p[0]:(n==6)?in7p[0]:(n==7)?
			in8p[0]:(n==8)?in9p[0]:(n==9)?in10p[0]:
			    (n==10)?in11p[0]:(n==11)?in12p[0]:(n==12)?point2:(n==13)?out1p[1]:(n==14)?out2p[1]:(n==15)?out3p[1]:out4p[1];
	    //return (n == 0) ? in1p[0] : (n == 1) ? in2p[0] : (n == 2) ?in3p[0] :(n == 3) ?in4p[0]:(n == 4) ?in5p[0]:point2;
	}
	//int getVoltageSourceCount() { return 1; }
	void getInfo(String arr[]) {
	    arr[0] = "ECU";
	    arr[1] = "V1 = " + getVoltageText(volts[0]);
	    arr[2] = "V2 = " + getVoltageText(volts[1]);
	    arr[3] = "V3 = " + getVoltageText(volts[2]);
	    arr[4] = "V4 = " + getVoltageText(volts[3]);
	    arr[5] = "V5 = " + getVoltageText(volts[4]);
	    arr[6] = "V6 = " + getVoltageText(volts[5]);
	    arr[7] = "V7 = " + getVoltageText(volts[6]);
	    arr[8] = "V8 = " + getVoltageText(volts[7]);
	    arr[9] = "V9 = " + getVoltageText(volts[8]);
	    arr[10] = "V10 = " + getVoltageText(volts[9]);
	    arr[11] = "V11 = " + getVoltageText(volts[10]);
	    arr[12] = "V12 = " + getVoltageText(volts[11]);
	    arr[13] = "V13 = " + getVoltageText(volts[12]);
	    
	}

	double lastvoltcheck;
	double max_amp,min_amp;
	double min_volt =0;
	boolean check1 = false;
	void stamp() {
	    sim.stampResistor(nodes[CKP], nodes[GND],  1000000);
	    sim.stampResistor(nodes[GNDNE], nodes[GND],  0.05);
	    sim.stampResistor(nodes[CMP], nodes[GND],  1000);
	    sim.stampResistor(nodes[THW], nodes[BATT],  10000);
	    sim.stampResistor(nodes[E2], nodes[GND],  1000);
	    sim.stampResistor(nodes[BATT], nodes[VCTPS], 0.005);
	    sim.stampNonLinear(nodes[IGT3]);
	    sim.stampNonLinear(nodes[IGT1]);
	    sim.stampNonLinear(nodes[IGT2]);
	    sim.stampNonLinear(nodes[IGT4]);
	    sim.stampNonLinear(nodes[BATT]);
	    sim.stampNonLinear(nodes[GND]);
	    sim.stampNonLinear(nodes[THW]);
	    sim.stampNonLinear(nodes[VTA]);
	    //sim.stampMatrix(nodes[4], vn, 1);
	}
	void create_button(){
	    sim.addWidgetToVerticalPanel(checkecubutton = new Button("Check ECU"));
	    checkecubutton.addClickHandler(new ClickHandler(){
		public void onClick(ClickEvent event) {
		    
		    checkecubox = new CheckEcu(round(volts[IGSW],2),round(volts[BATT],2),true,false,round(volts[THW],2),round(volts[VTA],2));
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
	    if(volts[BPLUS]>4.5){
		
		if(teeth_count == 8){
			if(volts[CKP]>1 ) 
			    {
			    
			    sim.stampResistor(nodes[BATT], nodes[IGT1], 10);
			    //teeth_count +=0.5;
			    }
			else sim.stampResistor(nodes[GND], nodes[IGT1], 10);
		    }
		if(teeth_count == 9){
			if(volts[CKP]>1 ) 
			    {
			    
			    sim.stampResistor(nodes[BATT], nodes[IGT3], 10);
			    //teeth_count +=0.5;
			    }
			else sim.stampResistor(nodes[GND], nodes[IGT3], 10);
		    }
		if(teeth_count == 10){
			if(volts[CKP]>1 ) 
			    {
			    
			    sim.stampResistor(nodes[BATT], nodes[IGT2], 10);
			    //teeth_count +=0.5;
			    }
			else sim.stampResistor(nodes[GND], nodes[IGT2], 10);
		    }
		if(teeth_count == 11){
			if(volts[CKP]>1 ) 
			    {
			    
			    sim.stampResistor(nodes[BATT], nodes[IGT4], 10);
			    //teeth_count +=0.5;
			    }
			else sim.stampResistor(nodes[GND], nodes[IGT4], 10);
		    }
		    if(volts[CKP] < -2.8) {
			if(check1 == false)
			{
			    check1 = true;
			    teeth_count ++; 
			}
			
			
			//max_amp = sim.t;
		    }
		    else check1 = false;
		    if(volts[CKP]==0) teeth_count = 0;
	    }
	    
	    
	    
	    if(volts[IGSW] >4.5 )sim.stampResistor(nodes[BATT], nodes[MREL], 10);
	    else sim.stampResistor(nodes[IGSW], nodes[GND], 10);
	    
	    
	    
//	    lastvd = vd;
//	    if (sim.converged)
//	      System.out.println((volts[1]-volts[0]) + " " + volts[2] + " " + initvd);
	}
	// there is no current path through the op-amp inputs, but there
	// is an indirect path through the output to ground.
	boolean getConnection(int n1, int n2) { return false; }
	boolean hasGroundConnection() {
	    return true;
	}
	//double getVoltageDiff() { return volts[2] - volts[1]; }
	int getDumpType() { return 'E'; }
	public EditInfo getEditInfo(int n) {
//	    if (n == 0)
//		return new EditInfo("Max Output (V)", maxOut, 1, 20);
//	    if (n == 1)
//		return new EditInfo("Min Output (V)", minOut, -20, 0);
//	    if (n == 2)
//		return new EditInfo("Gain", gain, 10, 1000000);
	    return null;
	}
	public void setEditValue(int n, EditInfo ei) {
//	    if (n == 0)
//		maxOut = ei.value;
//	    if (n == 1)
//		minOut = ei.value;
//	    if (n == 2 && ei.value > 0)
//		gain = ei.value;
	}
	int getShortcut() { return 'E'; }
	
	@Override double getCurrentIntoNode(int n) { 
	    if (n==4)
		return -current;
	   return 0;
	}
	public void onClick(ClickEvent event) {
	    // TODO Auto-generated method stub
	    
	}
	
	void delete() {
		sim.removeWidgetFromVerticalPanel(checkecubutton);
	        super.delete();
	    }
    }
