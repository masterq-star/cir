package com.lushprojects.circuitjs1.client;

import com.google.gwt.user.client.ui.Label;

class CKPSenSor extends RailElm  {
        Scrollbar slider;
	Label label;
	double position=0.5;
        double amplitude =3;
        int numberofcycle =5;
    public CKPSenSor(int xx, int yy) {
	super(xx, yy, WF_AC); 
	maxVoltage = amplitude;
	createSlider();
    }
	public CKPSenSor(int xa, int ya, int xb, int yb, int f,
		       StringTokenizer st) {
	    super(xa, ya, xb, yb, f, st);
	    waveform = WF_AC;
	    maxVoltage = amplitude;
	    createSlider();
	}
	void drawRail(Graphics g) {
	    drawRailText(g, "CKP");
	}
	double countcycle = 0;
	double getVoltage() {
	    double f = 5000/(slider.getValue());
	    if(sim.t%(numberofcycle*(1/f)) < (1/f)) return 0;
	    double fm = amplitude*Math.sin(2*pi*sim.t*f);
//	    return Math.sin(2*pi*sim.t*3000)*(1.3+Math.sin(2*pi*sim.t*12))*3 +
//	           Math.sin(2*pi*sim.t*2710)*(1.3+Math.sin(2*pi*sim.t*13))*3 +
//		   Math.sin(2*pi*sim.t*2433)*(1.3+Math.sin(2*pi*sim.t*14))*3 + fm;
	    
	    frequency = f;
	    
	    return fm;
	}
	
	void stepFinished() {
	    countcycle++;
	    if(countcycle > 56 ) countcycle = 0;
	}

	int getDumpType() { return 417; }
	//int getShortcut() { return 0; }
	
	public EditInfo getEditInfo(int n) {
	    if(n==0) return new EditInfo("Number of serrations ()", numberofcycle, 1, 10);
	    return null;
	}
	void createSlider() {
	    	sim.addWidgetToVerticalPanel(label = new Label("CKP SENSOR"));
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
	    void getInfo(String arr[]) {
		arr[0] = "CKP SENSOR";
		arr[1] = "I = " + getCurrentText(getCurrent());
		arr[2] = ((this instanceof RailElm) ? "V = " : "Vd = ") +
		    getVoltageText(getVoltageDiff());
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
		    arr[i++] = "(R = " + getUnitText(maxVoltage/current, sim.ohmString) + ")";
		arr[i++] = "P = " + getUnitText(getPower(), "W");
	    }
	   
	    public void setEditValue(int n, EditInfo ei) {
		    if (n == 0 && ei.value >= 1) {
			numberofcycle = (int) ei.value;
			setPoints();
		    }
		}
}
