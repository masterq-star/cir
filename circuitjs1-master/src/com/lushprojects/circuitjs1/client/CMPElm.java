package com.lushprojects.circuitjs1.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;

class CMPElm extends VoltageElm{
 
    Scrollbar slider;
	Label label;
	double position=0.5;
    double amplitude =3;
    int numberofcycle =34;
    double NMUL,NMISS;
    double gearRatio = 0;
    int MISSINGTEETH = 2;
    
    
    public CMPElm(int xx, int yy) { super(xx, yy, WF_AC);
   createSlider();
    }
                public CMPElm(int xa, int ya, int xb, int yb, int f,
            	       StringTokenizer st) {
             super(xa, ya, xb, yb, f, st);
             waveform = WF_AC;
             maxVoltage = amplitude;
             createSlider();
            }
	Class getDumpClass() { return VoltageElm.class; }
	int getDumpType() { return 419; }
	 double getVoltage() {
	     int cycle_silder = 100 - slider.getValue();
	     double datareturn ;
	     double acycle;
	     gearRatio = slider.getValue()/5;
	     double f = 5000/(cycle_silder);
	     double cycle = 1/f;
	     double dutycycle = 1/(2*f); 
	     NMUL = 1/f * numberofcycle;
	     NMISS = MISSINGTEETH * 1/f;
	     acycle = (NMUL + NMISS)/2;
	     double current_time_in_cycle = sim.t%acycle;
	     if(current_time_in_cycle >0 && current_time_in_cycle< (0+ dutycycle) ) datareturn = amplitude;
	     else if(current_time_in_cycle >3*cycle && current_time_in_cycle< (3*cycle+ dutycycle) ) datareturn = amplitude;
	     else if(current_time_in_cycle >5*cycle && current_time_in_cycle< (5*cycle+ dutycycle) ) datareturn = amplitude;
	     else if(current_time_in_cycle >6*cycle && current_time_in_cycle< (6*cycle+ dutycycle) ) datareturn = amplitude;
	     else if(current_time_in_cycle >9*cycle && current_time_in_cycle< (9*cycle+ dutycycle) ) datareturn = amplitude;
	     else if(current_time_in_cycle >12*cycle && current_time_in_cycle< (12*cycle+ dutycycle) ) datareturn = amplitude;
	     else if(current_time_in_cycle >15*cycle && current_time_in_cycle< (15*cycle+ dutycycle) ) datareturn = amplitude;
	     else datareturn =0;
	     
	     return datareturn;
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
	    	sim.addWidgetToVerticalPanel(label = new Label("CMP SENSOR"));
	    	label.addStyleName("topSpace");
	    	sim.addWidgetToVerticalPanel(slider = new Scrollbar(Scrollbar.HORIZONTAL, 50, 1, 10, 100));
	   // 	sim.verticalPanel.validate();
	   // 	slider.addAdjustmentListener(this);
	    }
	    void getInfo(String arr[]) {
		arr[0] = "CMP SENSOR";
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
	    double angle;
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
//		drawPosts(g);
		
		int cr = 18;
		setBbox(point1, point2, cr);
		//draw2Leads(g);
		//getCurrent();
		doDots(g);
		setPowerColor(g, true);
		Color cc = new Color((int) (165), (int) (165), (int) (165));
		g.setColor(cc);
		g.fillOval(motorCenter.x-(cr+40), motorCenter.y-(cr), (cr)*2, (cr)*2);
		cc = new Color((int) (10), (int) (10), (int) (10));

		g.setColor(cc);
		double angleAux = Math.round(angle*300.0)/300.0;
		g.fillOval(motorCenter.x-(int)(cr/2.2) - 40, motorCenter.y-(int)(cr/2.2), (int)(2*cr/2.2), (int)(2*cr/2.2));

		g.setColor(cc);
		//draw 3 black line in motor
		interpPointFix(lead1, lead2, ps1, 0.5 + .28*Math.cos(angleAux*gearRatio) ,.28*Math.sin(angleAux*gearRatio));
		interpPointFix(lead1, lead2, ps2, 0.5 - .28*Math.cos(angleAux*gearRatio) , -.28*Math.sin(angleAux*gearRatio));
		fixpoint(ps1,ps2);
		drawThickerLine(g, ps1, ps2);
		interpPointFix(lead1, lead2, ps1, 0.5 + .28*Math.cos(angleAux*gearRatio+pi/3),+ .28*Math.sin(angleAux*gearRatio+pi/3));
		interpPointFix(lead1, lead2, ps2, 0.5 - .28*Math.cos(angleAux*gearRatio+pi/3),+ -.28*Math.sin(angleAux*gearRatio+pi/3));
		fixpoint(ps1,ps2);
		drawThickerLine(g, ps1, ps2);
		
		interpPointFix(lead1, lead2, ps1, 0.5 + .28*Math.cos(angleAux*gearRatio+2*pi/3),+ .28*Math.sin(angleAux*gearRatio+2*pi/3));
		interpPointFix(lead1, lead2, ps2, 0.5 - .28*Math.cos(angleAux*gearRatio+2*pi/3),+ -.28*Math.sin(angleAux*gearRatio+2*pi/3));
		fixpoint(ps1,ps2);
		drawThickerLine(g, ps1, ps2);

		drawPosts(g);
	    }
	    void fixpoint(Point a, Point b){
		a.x -= 40;
		b.x -= 40;
	    }
	    void interpPointFix(Point a, Point b, Point c, double f, double g) {
		int gx = b.y-a.y;
		int gy = a.x-b.x;
		c.x = (int) Math.round(a.x*(1-f)+b.x*f+g*gx);
		c.y = (int) Math.round(a.y*(1-f)+b.y*f+g*gy);
	    }
		
	    void startIteration() {
		// update angle:
		angle= angle + slider.getValue()*sim.timeStep;
	    }
	    static void drawThickerLine(Graphics g, Point pa, Point pb) {
		g.setLineWidth(6.0);
		g.drawLine(pa.x, pa.y, pb.x, pb.y);
		g.setLineWidth(1.0);
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
		    drawCenteredText(g, CirSim.LS("CMP"), xc, yc, true);
		    
		    g.context.stroke();
		    g.context.setLineWidth(1.0);
		  
		
		
		
	    }
	    Point motorCenter;

	    void setPoints() {
		super.setPoints();
		calcLeads(36);
		motorCenter = interpPoint(point1, point2, .5);
		allocNodes();
	    }
	 
}
