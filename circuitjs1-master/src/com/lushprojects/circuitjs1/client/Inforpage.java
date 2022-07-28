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

public class Inforpage extends PopupPanel {
	
    VerticalPanel vp;
	Button okButton;
	
	Inforpage(String Html) {
		super();
		vp = new VerticalPanel();
		setWidget(vp);
		
		
//Mod.Begin
		vp.setWidth("500px");


		vp.add(new HTML(Html));
		
		
		
		
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