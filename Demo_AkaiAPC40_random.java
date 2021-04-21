package com.haxademic.demo.hardware.midi;
import com.haxademic.core.app.PAppletHax;

import themidibus.MidiBus;
import themidibus.MidiListener;

public class Demo_AkaiAPC40_random
extends PAppletHax implements MidiListener {
	public static void main(String args[]) { arguments = args; PAppletHax.main(Thread.currentThread().getStackTrace()[1].getClassName()); }

	protected MidiBus midiBus;
	
	// Animation properties for quick testing
	private int t50 = 1;
	private int t8 = 1;
	private int t5 = 1;
	private boolean onToggle = false;
	private boolean onToggle1 = false;
	private boolean onToggle2 = false;

	protected void firstFrame() {
		p.frameRate(12);
		
		MidiBus.list();
		midiBus = new MidiBus(this, 0, 3);
		midiBus.addMidiListener(this);
		
		AkaiAPC40Utils.sendApcMode(midiBus, AkaiAPC40Utils.MODE_ABLETON);
		
		AkaiAPC40Utils.sendApcKnob8RightStyle(midiBus, 1, AkaiAPC40Utils.KNOBSTYLE_PAN);
		AkaiAPC40Utils.sendApcKnob8RightStyle(midiBus, 2, AkaiAPC40Utils.KNOBSTYLE_PAN);
		AkaiAPC40Utils.sendApcKnob8RightStyle(midiBus, 3, AkaiAPC40Utils.KNOBSTYLE_VOLUME);
		AkaiAPC40Utils.sendApcKnob8RightStyle(midiBus, 4, AkaiAPC40Utils.KNOBSTYLE_VOLUME);
	}
	
	protected void drawApp() {
		p.background(0);
		
		// Test RBG Pads
		int colorCode = (int) (Math.random()*AkaiAPC40Utils.COLORCODE_COUNT);
		AkaiAPC40Utils.sendApcPadRGB(midiBus, AkaiAPC40Utils.RGBTYPE_ONESHOT_1X4, t50, colorCode);
		AkaiAPC40Utils.sendApcPadRightRGB(midiBus, AkaiAPC40Utils.RGBTYPE_ONESHOT_1X8, t5, colorCode);
		
		// Test Knobs
		AkaiAPC40Utils.sendApcKnob8Right(midiBus, t8, (int) (Math.random()*127));
		AkaiAPC40Utils.sendApcKnob8Top(midiBus, t8, (int) (Math.random()*127));
		
		// Test 1-8 Track Buttons
		AkaiAPC40Utils.sendApcButtonSolo(midiBus, t8, onToggle2);
		AkaiAPC40Utils.sendApcButtonStop(midiBus, t8, !onToggle1, false);
		AkaiAPC40Utils.sendApcButtonSelect(midiBus, t8, onToggle2);
		AkaiAPC40Utils.sendApcButtonActivator(midiBus, t8, !onToggle2);
		AkaiAPC40Utils.sendApcButtonRecord(midiBus, t8, !onToggle1);
		AkaiAPC40Utils.sendApcButtonAB(midiBus, t8, !onToggle1, onToggle2);
		
		// Test 1-8 Device control Buttons
		AkaiAPC40Utils.sendApcButton8(midiBus, t8, onToggle1);
		
		// Test Buttons
		AkaiAPC40Utils.sendApcButtonPan(midiBus, onToggle1);
		AkaiAPC40Utils.sendApcButtonSends(midiBus, onToggle);
		AkaiAPC40Utils.sendApcButtonUser(midiBus, onToggle2);
		
		AkaiAPC40Utils.sendApcButtonPlay(midiBus, !onToggle1);
		AkaiAPC40Utils.sendApcButtonRecord(midiBus, !onToggle2);
		AkaiAPC40Utils.sendApcButtonSession(midiBus, onToggle);
		AkaiAPC40Utils.sendApcButtonMetronome(midiBus, onToggle2);

		AkaiAPC40Utils.sendApcButtonBank(midiBus, onToggle);
		
		
		onToggle = !onToggle;
		t50++; if(t50 > 40) t50 = 0;
		t8++; if(t8 > 8) {t8 = 0; onToggle1 = !onToggle1;}
		t5++; if(t5 > 5) {t5 = 0; onToggle2 = !onToggle2;}
	}
}