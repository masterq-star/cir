package com.lushprojects.circuitjs1.client;

class KnkElm extends VoltageElm{
    double maxVoltage =2.5;
    double nonknk = 0.5;
    static AboutBox aboutBox;
    double nomalknk = 1.5;
    public  KnkElm(int xx, int yy) {
	super(xx, yy, WF_NOISE);
	
    }
    public KnkElm(int xa, int ya, int xb, int yb, int f,
 	       StringTokenizer st) {
                  super(xa, ya, xb, yb, f, st);
                  waveform = WF_NOISE;
                 }
    int getDumpType() { return 421; }
    public boolean getInformationhtml() { return true; }
    void stepFinished() {
	if(sim.t % 0.002 < 0.001) noiseValue = (sim.random.nextDouble()*2-1) * nonknk;
	else if (sim.t%0.002 < 0.0011)  noiseValue = (sim.random.nextDouble()*2-1) * maxVoltage;
	else if(sim.t%0.002 < 0.0012) noiseValue = (sim.random.nextDouble()*2 -1) * (maxVoltage-0.4);
	else if(sim.t%0.002 < 0.0013) noiseValue = (sim.random.nextDouble()*2 -1) * (maxVoltage-0.8);
	else if(sim.t%0.002 < 0.0016)noiseValue = (sim.random.nextDouble()*2 -1) * nonknk;
	else if(sim.t%0.002 < 0.0017)noiseValue = (sim.random.nextDouble()*2 -1) * nomalknk;
	else if(sim.t%0.002 < 0.0018)noiseValue = (sim.random.nextDouble()*2 -1) * (nomalknk-0.4);
	else if(sim.t%0.002 < 0.0019)noiseValue = (sim.random.nextDouble()*2 -1) * (nomalknk -0.8);
	else noiseValue = (sim.random.nextDouble()*2-1) * nonknk;
    }
    double getVoltage() {
	return noiseValue;  
	    }
    public EditInfo getEditInfo(int n) {
	return null;
    }
    void draw(Graphics g){
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
	    drawCenteredText(g, CirSim.LS("kNK"), xc, yc, true);
	    
	    g.context.stroke();
	    g.context.setLineWidth(1.0);
	  
	
	
	
    }
    public Boolean checkInfor(){return true;}
    String codeHtmlString = "<div style='overflow-y: scroll; height:400px;'>"
    	+ "<img src='https://oto.edu.vn/finalweb/wp-content/uploads/2018/08/cam-bien-kich-no-2.jpg' alt='Knk Sensor' height='300'>"
    	+ "<h3>1. Chức năng và nhiệm vụ của cảm biến kích nổ</h3>"
    	+ "<p>Nhiệm vụ của cảm biến kích nổ Knock Sensor là để đo tiếng gõ trong động cơ và phát ra tín hiệu điện áp gửi về ECU, từ đó ECU sẽ nhận và phân tích tín hiệu đó để điều chỉnh góc đánh lửa sớm làm giảm tiếng gõ (Thông thường tiếng gõ sinh ra là do va đập các chi tiết cơ khí trong động cơ bởi hiện tượng kích nổ).</p>"
    	+ "<h3>2. Cấu tạo của cảm biến kích nổ</h3>"
    	+ "<p>Cảm biến kích nổ có cấu tạo bởi 1 vật liệu áp điện, tinh thể thạch anh. Khi có tiếng gõ, cảm biến với tinh thể thạch anh sẽ tự phát ra điện áp và gửi về ECU.</p>"
    	+ "<h3>Nguyên lí hoạt động của cảm biến kích nổ</h3>"
    	+ "<p>Khi động cơ hoạt động, vì lý do nào đó dẫn tới có tiếng gõ (tự kích nổ, động cơ nóng quá, va đập cơ khí….) cảm biến sẽ tạo ra 1 tín hiệu điện áp gửi về ECU và ECU sẽ điều chỉnh trễ góc đánh lửa lại để giảm tiếng gõ.</p>"
    	+ "<p>Cụ thể: Các phần tử áp điện của cảm biến kích nổ được thiết kế có kích thước với tần số riêng trùng với tần số rung của động cơ khi có hiện tượng kích nổ để xảy ra hiệu ứng cộng hưởng (f = 6KHz – 13KHz).</p>"
    	+ "<p>Như vậy, khi động cơ có xảy ra hiện tượng kích nổ, tinh thể thạch anh sẽ chịu áp lực lớn nhất và sinh ra một điện áp. Tín hiệu điện áp này có giá trị nhỏ hơn 2,5V. Nhờ tín hiệu này, ECU động cơ nhận biết hiện tượng kích nổ và điều chỉnh giảm góc đánh lửa cho đến khi không còn kích nổ. ECU động cơ có thể điều chỉnh thời điểm đánh lửa sớm trở lại.</p>"
    	+ "</div>";
    public String CodeHtml(){
	return codeHtmlString;
    }
}
