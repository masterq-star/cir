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

import javax.swing.text.html.CSS;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Button;

public class CheckEcu extends PopupPanel {
	
    VerticalPanel vp;
	Button okButton;
	
	CheckEcu(Double vIGSW , Double vBATT 
		, Boolean NE, Boolean G ,Boolean vss,
		Boolean knk,
		Double vTHW , Double vVTA) {
		super();
		vp = new VerticalPanel();
		setWidget(vp);
		String check_igsw = vIGSW >4.5 ? "OK":"ERROR",
			check_batt = vBATT >4.5 ? "OK":"ERROR",
			checkckp = NE ? "OK":"ERROR",
			checkcmp= G ? "OK":"ERROR",
			checkthw = vTHW <6&& vTHW>0.1 ? "OK":"ERROR",
			checkvta= vVTA >0 ? "OK":"ERROR",
			checkvss = vss?"OK":"ERROR",
				checkknk = knk ? "OK":"ERROR";
		
//Mod.Begin
		vp.setWidth("500px");
//		vp.add(new HTML("<img src='http://hbhanoi.com/wp-content/uploads/2021/03/z2404963907257_a2d939259bd5af891b2748b7c340f039.jpg' width='179' height='135' border='0' align='right' alt='Electrodynamic Immortality Defines Free Energy' />" +
////Mod.End
//		"<p>Circuit Simulator Bảng version "+version+".</p>"));
//Mod.End
		String code_html = 
			"<head><style> #tabletest {font-family: arial, sans-serif;border-collapse: collapse;width: 100%;}"
			+ ".td, .th {border: 1px solid #dddddd;text-align: left;padding: 8px;}"
			+ "</style></head>"
			 + "<body>"
			+ "<h2> Bảng Kiểm tra Kết quả Đấu nối</h2>"
			+ "<table id='tabletest' ><tr><th class='th'>Chân đo</th><th class='th'>kết quả đo</th><th class='th'>kết quả kiểm tra</th></tr>"
			+ "<tr><td class='td'>IGSW</td><td  class='td'>"+ String.valueOf(vIGSW)+ "V"+"</td><td  class='td'>"+check_igsw+"</td></tr>"
			+ "<tr><td class='td'>BATT</td><td class='td'>"+String.valueOf(vBATT)+ "V"+"</td><td class='td'>"+check_batt+"</td></tr>"
			+ "<tr><td class='td'>CẢM BIẾN CKP</td><td class='td'>"+ String.valueOf(NE)+"</td><td class='td'>"+checkckp+"</td></tr>"
			+ "<tr><td class='td'>CẢM BIẾN CMP</td><td class='td'>"+String.valueOf(G)+"</td><td class='td'>"+checkcmp+"</td></tr>"
			+ "<tr><td class='td'>CẢM BIẾN THW</td><td class='td'>"+String.valueOf(vTHW)+"</td><td class='td'>"+checkthw+"</td></tr>"
			+ "<tr><td class='td'>CẢM BIẾN TPS</td><td class='td'>"+String.valueOf(vVTA)+"</td><td class='td'>"+checkvta+"</td></tr>"
			+ "<tr><td class='td'>CẢM BIẾN VSS</td><td class='td'>"+String.valueOf(vss)+"</td><td class='td'>"+checkvss+"</td></tr>"
			+ "<tr><td class='td'>CẢM BIẾN KNK</td><td class='td'>"+String.valueOf(knk)+"</td><td class='td'>"+checkknk+"</td></tr>"
			+ "</table>"
			+ "</body>";
		
                //vp.add(new HTML(code_css));
		vp.add(new HTML(code_html));
		
		
		
		
		vp.add(okButton = new Button("CLOSE&nbsp;THIS&nbsp;WINDOW"));
		okButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				close();
			}
		});
		center();
		show();
	}

	public void close() {
		hide();
	}
}
