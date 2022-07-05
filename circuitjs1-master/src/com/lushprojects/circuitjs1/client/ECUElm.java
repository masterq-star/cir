package com.lushprojects.circuitjs1.client;

class ECUElm extends CircuitElm {
    int opsize, opheight, opwidth, opaddtext;
    public ECUElm(int xx, int yy) {
	    super(xx, yy);
	    noDiagonal = true;
	}
    public ECUElm(int xa, int ya, int xb, int yb, int f,StringTokenizer st) {
	super(xa, ya, xb, yb, f);
	
    }
    boolean nonLinear() { return true; }
    void draw(Graphics g) {
	    setBbox(point1, point2, 12);
	    setVoltageColor(g, volts[0]);
	    drawThickLine(g, in1p[0], in1p[1]);
	    setVoltageColor(g, volts[1]);
	    drawThickLine(g, in2p[0], in2p[1]);
	    setVoltageColor(g, volts[2]);
	    drawThickLine(g, lead2, point2);
	    g.setColor(needsHighlight() ? selectColor : lightGrayColor);
	    setPowerColor(g, true);
	    drawThickPolygon(g, triangle);
	    g.setFont(plusFont);
	    drawCenteredText(g, "-", textp[0].x, textp[0].y-2, true);
	    drawCenteredText(g, "+", textp[1].x, textp[1].y  , true);
	    curcount = updateDotCount(current, curcount);
	    drawDots(g, point2, lead2, curcount);
	    drawPosts(g);
	}
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
	    textp = newPointArray(2);
	    interpPoint2(point1, point2, in1p[0],  in2p[0], 0, hs);
	    interpPoint2(lead1 , lead2,  in1p[1],  in2p[1], 0, hs);
	    interpPoint2(lead1 , lead2,  textp[0], textp[1], .2, hs);
	    Point tris[] = newPointArray(2);
	    interpPoint2(lead1,  lead2,  tris[0], tris[1],  0, hs*2);
	    triangle = createPolygon(tris[0], tris[1], lead2);
	    plusFont = new Font("SansSerif", 0, opsize == 2 ? 14 : 10);
	}
   

}
