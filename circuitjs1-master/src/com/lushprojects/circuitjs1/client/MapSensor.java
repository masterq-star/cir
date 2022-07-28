package com.lushprojects.circuitjs1.client;

import com.google.gwt.user.client.ui.Label;

class MapSensor extends CircuitElm {

    int opsize, opheight, opwidth, opaddtext;
    final int VCC = 0;
    final int GND = 2;
    final int OUT = 1;
    Scrollbar slider;
	Label label;
    
	
	boolean reset;
	
	public MapSensor(int xx, int yy) {
	    super(xx, yy);
	    setSize(sim.smallGridCheckItem.getState() ? 1 : 2);
	    createSlider();
	   
	}
	public MapSensor(int xa, int ya, int xb, int yb, int f,
			StringTokenizer st) {
	    super(xa, ya, xb, yb, f);
	    createSlider();
	    try {
		volts[0] = new Double(st.nextToken()).doubleValue();
		volts[1] = new Double(st.nextToken()).doubleValue();
		volts[2] = new Double(st.nextToken()).doubleValue();
	    } catch (Exception e) {
	    }
	    setSize(2);
	}
	String dump() {
	    return super.dump()  + " "  + volts[0] + " " + volts[1]+ " " + volts[2] ;
	}
	void draw(Graphics g) {
	    setBbox(point1, point2, opheight*2);
	    setVoltageColor(g, volts[0]);
	    drawThickLine(g, in1p[0], in1p[1]);
	    setVoltageColor(g, volts[1]);
	    drawThickLine(g, in2p[0], in2p[1]);
	    setVoltageColor(g, volts[2]);
	    drawThickLine(g, point1, lead1);
	    g.setColor(needsHighlight() ? selectColor : lightGrayColor);
	    setPowerColor(g, true);
	    //drawThickLine(g, point1, point2);
	    drawThickPolygon(g, triangle);
	    g.setFont(plusFont);
	    drawCenteredText(g, "VCC", textp[0].x, textp[0].y-2, true);
	    drawCenteredText(g, "GND", textp[1].x, textp[1].y  , true);
	    drawCenteredText(g, "OUT", textp[2].x, textp[2].y  , true);
	    curcount = updateDotCount(current, curcount);
	    drawDots(g, point2, lead2, curcount);
	    drawPosts(g);
	}
	double getPower() { return volts[2]*current; }
	Point in1p[], in2p[], textp[];
	Polygon triangle;
	Font plusFont;
	void setSize(int s) {
	    opsize = s;
	    opheight = 8*s;
	    opwidth = 13*s;
	}
	void setPoints() {
	    super.setPoints();
	    if (dn > 150 && this == sim.dragElm)
		setSize(2);
	    int ww = opwidth;
	    if (ww > dn/2)
		ww = (int) (dn/2);
	    calcLeads(ww*2);
	    int hs = opheight*dsign;
	    in1p = newPointArray(2);
	    in2p = newPointArray(2);
	    textp = newPointArray(3);
	    interpPoint2(point1, point2, in1p[0],  in2p[0], 0, hs*2);
	    interpPoint2(lead1 , lead2,  in1p[1],  in2p[1], 0, hs*2);
	    interpPoint2(lead1 , lead2,  textp[0], textp[1], 0.3, hs*2);
	    interpPoint(lead1,lead2,textp[2],0.3);
	    Point tris[] = newPointArray(4);
	    interpPoint2(lead1,  lead2,  tris[0], tris[1],  0, hs*3);
	    interpPoint2(lead1,  lead2,  tris[2], tris[3],  1.7, hs*3);
	    triangle = createPolygon(tris[0], tris[1],  tris[3], tris[2]);
	    plusFont = new Font("SansSerif", 0, 8);
	}
	int getPostCount() { return 3; }
	Point getPost(int n) {
	    return (n == 0) ? in1p[0] : (n == 1) ? in2p[0] : point1;
	}
	//int getVoltageSourceCount() { return 1; }
	void getInfo(String arr[]) {
	    arr[0] = "Map Sensor";
	    arr[1] = "V+ = " + getVoltageText(volts[1]);
	    arr[2] = "V- = " + getVoltageText(volts[0]);
	    // sometimes the voltage goes slightly outside range, to make
	    // convergence easier.  so we hide that here.
	    arr[3] = "Iout = " + getCurrentText(-current);
	}

	double lastvd;
	boolean nonLinear() { return true; }
	void stamp() {
	    //sim.stampResistor(nodes[0], nodes[1], 100);
	    sim.stampNonLinear(nodes[OUT]);
	    sim.stampNonLinear(nodes[VCC]);
	    sim.stampNonLinear(nodes[GND]);
	}
	void doStep() {
//	    if(sim.t % 0.01 > 0 && sim.t % 0.01 < 0.005)
//		       sim.stampResistor(nodes[2], nodes[0], 0.05);
//		   else sim.stampResistor(nodes[2], nodes[1], 0.05);
	    sim.stampResistor(nodes[2], nodes[0], 1000);
	    sim.stampResistor(nodes[2], nodes[1], slider.getValue() * 10);
//	    if (sim.converged)
//	      System.out.println((volts[1]-volts[0]) + " " + volts[2] + " " + initvd);
	}
	// there is no current path through the op-amp inputs, but there
	// is an indirect path through the output to ground.
	boolean getConnection(int n1, int n2) { return false; }
	boolean hasGroundConnection() {
	    return true;
	}
	void createSlider() {
	    	sim.addWidgetToVerticalPanel(label = new Label("Giá trị đo PSI"));
	    	label.addStyleName("topSpace");
	    	sim.addWidgetToVerticalPanel(slider = new Scrollbar(Scrollbar.HORIZONTAL, 50, 1, 10, 100));
	   // 	sim.verticalPanel.validate();
	   // 	slider.addAdjustmentListener(this);
	    }
	void delete() {
		sim.removeWidgetFromVerticalPanel(label);
	        sim.removeWidgetFromVerticalPanel(slider);
	        super.delete();
	    }
	int getDumpType() { return 427; }
	public EditInfo getEditInfo(int n) {
	    return null;
	}
	public void setEditValue(int n, EditInfo ei) {
	}
	
	
}