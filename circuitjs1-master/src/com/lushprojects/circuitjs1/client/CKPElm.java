package com.lushprojects.circuitjs1.client;

import com.google.gwt.user.client.ui.Label;

class CKPElm extends VoltageElm{
 
    Scrollbar slider;
	Label label;
	double position=0.5;
    double amplitude =3;
    int numberofcycle =5;
    
    public CKPElm(int xx, int yy) { super(xx, yy, WF_AC); createSlider();}
                public CKPElm(int xa, int ya, int xb, int yb, int f,
            	       StringTokenizer st) {
             super(xa, ya, xb, yb, f, st);
             waveform = WF_AC;
             maxVoltage = amplitude;
             createSlider();
            }
	Class getDumpClass() { return VoltageElm.class; }
	int getDumpType() { return 418; }
	 double getVoltage() {
	     double f = 5000/(slider.getValue());
		    if(sim.t%(numberofcycle*(1/f)) < (1/f)) return 0;
		    double fm = amplitude*Math.sin(2*pi*sim.t*f);
//		    return Math.sin(2*pi*sim.t*3000)*(1.3+Math.sin(2*pi*sim.t*12))*3 +
//		           Math.sin(2*pi*sim.t*2710)*(1.3+Math.sin(2*pi*sim.t*13))*3 +
//			   Math.sin(2*pi*sim.t*2433)*(1.3+Math.sin(2*pi*sim.t*14))*3 + fm;
		    
		    frequency = f;
		    
		    return fm;
	    }
	 void delete() {
		sim.removeWidgetFromVerticalPanel(label);
		sim.removeWidgetFromVerticalPanel(slider);
	        super.delete();
	    }
	 public EditInfo getEditInfo(int n) {
		    if(n==0) return new EditInfo("Number of serrations ()", numberofcycle, 1, 10);
		    return null;
		}
	 public void setEditValue(int n, EditInfo ei) {
		    if (n == 0 && ei.value >= 1) {
			numberofcycle = (int) ei.value;
			setPoints();
		    }
		}
	 void createSlider() {
	    	sim.addWidgetToVerticalPanel(label = new Label("CKP SENSOR"));
	    	label.addStyleName("topSpace");
	    	int value = (int) (position*100);
	    	sim.addWidgetToVerticalPanel(slider = new Scrollbar(Scrollbar.HORIZONTAL, 50, 1, 10, 100));
	   // 	sim.verticalPanel.validate();
	   // 	slider.addAdjustmentListener(this);
	    }
	    void getInfo(String arr[]) {
		arr[0] = "CKP SENSOR";
		arr[1] = "I = " + getCurrentText(getCurrent());
		arr[2] = "Vd" +getVoltageText(getVoltageDiff());
		int i = 3;
		if (waveform != WF_DC && waveform != WF_VAR && waveform != WF_NOISE) {
		    arr[i++] = "f = " + getUnitText(frequency, "Hz");
		    arr[i++] = "Vmax = " + getVoltageText(amplitude);
		    if (waveform == WF_AC && bias == 0)
			arr[i++] = "V(rms) = " + getVoltageText(amplitude/1.41421356);
		    if (bias != 0)
			arr[i++] = "Voff = " + getVoltageText(bias);
		    else if (frequency > 500)
			arr[i++] = "wavelength = " +
			    getUnitText(2.9979e8/frequency, "m");
		}
		if (waveform == WF_DC && current != 0 && sim.showResistanceInVoltageSources)
		    arr[i++] = "(R = " + getUnitText(maxVoltage/current, CirSim.ohmString) + ")";
		arr[i++] = "P = " + getUnitText(getPower(), "W");
	    }
	    void draw(Graphics g) {
		setBbox(x, y, x2, y2);
		
		draw2Leads(g);
		
		setBbox(point1, point2, circleSize);
		interpPoint(lead1, lead2, ps1, .5);
		drawWaveform(g, ps1);
		String inds = "+";
		    
		g.setColor(Color.white);
		g.setFont(unitsFont);
		Point plusPoint = interpPoint(point1, point2, (dn/2+circleSize+4)/dn, 10*dsign );
	        plusPoint.y += 4;
		int w = (int)g.context.measureText(inds).getWidth();;
		g.drawString(inds, plusPoint.x-w/2, plusPoint.y);
		
		updateDotCount();
		if (sim.dragElm != this) {
		    
			drawDots(g, point1, lead1, curcount);
			drawDots(g, point2, lead2, -curcount);
		    
		}
		drawPosts(g);
	    }
		
	    void drawWaveform(Graphics g, Point center) {
		g.setColor(needsHighlight() ? selectColor : Color.gray);
		setPowerColor(g, false);
		int xc = center.x; int yc = center.y;
		drawThickCircle(g, xc, yc, circleSize);
		adjustBbox(xc-circleSize, yc-circleSize,
			   xc+circleSize, yc+circleSize);
		    g.context.beginPath();
		    g.context.setLineWidth(3.0);
                  
		    g.setColor(needsHighlight() ? selectColor : whiteColor);
		    setPowerColor(g, false);
		    drawCenteredText(g, CirSim.LS("CKP"), xc, yc, true);
		    
		    g.context.stroke();
		    g.context.setLineWidth(1.0);
		  
		
		
		
	    }
	 
}
