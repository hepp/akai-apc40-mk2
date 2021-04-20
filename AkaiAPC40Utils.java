import themidibus.MidiBus;

// ///////////////////////////////////////////////////////////////////
// 
// 2021 Hepp Maccoy hepp@audiopixel.com - MIT License
//
// APC40 Mk2 Communications Protocol Version 1.2
//   https://www.akaipro.com/amfile/file/download/file/495/product/15
//
// ///////////////////////////////////////////////////////////////////

public final class AkaiAPC40Utils {

	private AkaiAPC40Utils () { }

	// ///////////////////////////////////////////////////
	// ////////////////    CONSTANTS    //////////////////
	// ///////////////////////////////////////////////////
	
	public static final int MODE_DEFAULT = 0;
	public static final int MODE_ABLETON = 1;
	public static final int MODE_ALTERNATE_ABLETON = 2;

	public static final int RGBTYPE_SOLID = 0;
	public static final int RGBTYPE_ONESHOT_1X24 = 1;
	public static final int RGBTYPE_ONESHOT_1X16 = 2;
	public static final int RGBTYPE_ONESHOT_1X8 = 3;
	public static final int RGBTYPE_ONESHOT_1X4 = 4;
	public static final int RGBTYPE_ONESHOT_1X2 = 5;
	public static final int RGBTYPE_PULSE_1X24 = 6;
	public static final int RGBTYPE_PULSE_1X16 = 7;
	public static final int RGBTYPE_PULSE_1X8 = 8;
	public static final int RGBTYPE_PULSE_1X4 = 9;
	public static final int RGBTYPE_PULSE_1X2 = 10;
	public static final int RGBTYPE_BLINK_1X24 = 11;
	public static final int RGBTYPE_BLINK_1X16 = 12;
	public static final int RGBTYPE_BLINK_1X8 = 13;
	public static final int RGBTYPE_BLINK_1X4 = 14;
	public static final int RGBTYPE_BLINK_1X2 = 15;

	public static final int DUALCOLOR_OFF = 0;
	public static final int DUALCOLOR_YELLOW = 1;
	public static final int DUALCOLOR_ORANGE = 2;

	public static final int KNOBSTYLE_OFF = 0;
	public static final int KNOBSTYLE_DEFAULT = 1;
	public static final int KNOBSTYLE_VOLUME = 2;
	public static final int KNOBSTYLE_PAN = 3;

	public static final int TRACK_COUNT = 8;

	public static final int COLORCODE_COUNT = 127;
	public static final int[][] RGB_COLORS = {{0,0,0},{30,30,30},{127,127,127},{255,255,255},{255,76,76},{255,0,0},{89,0,0},{25,0,0},{255,189,108},{255,84,0},{89,29,0}
		,{39,27,0},{255,255,76},{255,255,0},{89,89,0},{25,25,0},{136,255,76},{84,255,0},{29,89,0},{20,43,0},{76,255,76},{0,255,0},{0,89,0},{0,25,0},{76,255,94},{0,255,25},{0,89,13}
		,{0,25,2},{76,255,136},{0,255,85},{0,89,29},{0,31,18},{76,255,183},{0,255,153},{0,89,53},{0,25,18},{76,195,255},{0,169,255},{0,65,82},{0,16,25},{76,136,255},{0,85,255}
		,{0,29,89},{0,8,25},{76,76,255},{0,0,255},{0,0,89},{0,0,25},{135,76,255},{84,0,255},{25,0,100},{15,0,48},{255,76,255},{255,0,255},{89,0,89},{25,0,25},{255,76,135}
		,{255,0,84},{89,0,29},{34,0,19},{255,21,0},{153,53,0},{121,81,0},{67,100,0},{3,57,0},{0,87,53},{0,84,127},{0,0,255},{0,69,79},{37,0,204},{127,127,127},{32,32,32}
		,{255,0,0},{189,255,45},{175,237,6},{100,255,9},{16,139,0},{0,255,135},{0,169,255},{0,42,255},{63,0,255},{122,0,255},{178,26,125},{64,33,0},{255,74,0},{136,225,6}
		,{114,255,21},{0,255,0},{59,255,38},{89,255,113},{56,255,204},{91,138,255},{49,81,198},{135,127,233},{211,29,255},{255,0,93},{255,127,0},{185,176,0},{144,255,0},{131,93,7}
		,{57,43,0},{20,76,16},{13,80,56},{21,21,42},{22,32,90},{105,60,28},{168,0,10},{222,81,61},{216,106,28},{255,225,38},{158,225,47},{103,181,15},{30,30,48},{220,255,107}
		,{128,255,189},{154,153,255},{142,102,255},{64,64,64},{117,117,117},{224,255,255},{160,0,0},{53,0,0},{26,208,0},{7,66,0},{185,176,0},{63,49,0},{179,95,0}};
	public static final String[] RGB_HEXCOLORS = {"000000","1E1E1E","7F7F7F","FFFFFF","FF4C4C","FF0000","590000","190000","FFBD6C","FF5400","591D00","271B00","FFFF4C"
		,"FFFF00","595900","191900","88FF4C","54FF00","1D5900","142B00","4CFF4C","00FF00","005900","001900","4CFF5E","00FF19","00590D","001902","4CFF88","00FF55","00591D"
		,"001F12","4CFFB7","00FF99","005935","001912","4CC3FF","00A9FF","004152","001019","4C88FF","0055FF","001D59","000819","4C4CFF","0000FF","000059","000019","874CFF"
		,"5400FF","190064","0F0030","FF4CFF","FF00FF","590059","190019","FF4C87","FF0054","59001D","220013","FF1500","993500","795100","436400","033900","005735","00547F"
		,"0000FF","00454F","2500CC","7F7F7F","202020","FF0000","BDFF2D","AFED06","64FF09","108B00","00FF87","00A9FF","002AFF","3F00FF","7A00FF","B21A7D","402100","FF4A00"
		,"88E106","72FF15","00FF00","3BFF26","59FF71","38FFCC","5B8AFF","3151C6","877FE9","D31DFF","FF005D","FF7F00","B9B000","90FF00","835D07","392b00","144C10","0D5038"
		,"15152A","16205A","693C1C","A8000A","DE513D","D86A1C","FFE126","9EE12F","67B50F","1E1E30","DCFF6B","80FFBD","9A99FF","8E66FF","404040","757575","E0FFFF","A00000"
		,"350000","1AD000","074200","B9B000","3F3100","B35F00","4B1502"};
	public static final int[] RGB_HEXCOLOR_INTS = {0x000000,0x1E1E1E,0x7F7F7F,0xFFFFFF,0xFF4C4C,0xFF0000,0x590000,0x190000,0xFFBD6C,0xFF5400,0x591D00,0x271B00
		,0xFFFF4C,0xFFFF00,0x595900,0x191900,0x88FF4C,0x54FF00,0x1D5900,0x142B00,0x4CFF4C,0x00FF00,0x005900,0x001900,0x4CFF5E,0x00FF19,0x00590D,0x001902,0x4CFF88,0x00FF55
		,0x00591D,0x001F12,0x4CFFB7,0x00FF99,0x005935,0x001912,0x4CC3FF,0x00A9FF,0x004152,0x001019,0x4C88FF,0x0055FF,0x001D59,0x000819,0x4C4CFF,0x0000FF,0x000059,0x000019
		,0x874CFF,0x5400FF,0x190064,0x0F0030,0xFF4CFF,0xFF00FF,0x590059,0x190019,0xFF4C87,0xFF0054,0x59001D,0x220013,0xFF1500,0x993500,0x795100,0x436400,0x033900,0x005735
		,0x00547F,0x0000FF,0x00454F,0x2500CC,0x7F7F7F,0x202020,0xFF0000,0xBDFF2D,0xAFED06,0x64FF09,0x108B00,0x00FF87,0x00A9FF,0x002AFF,0x3F00FF,0x7A00FF,0xB21A7D,0x402100
		,0xFF4A00,0x88E106,0x72FF15,0x00FF00,0x3BFF26,0x59FF71,0x38FFCC,0x5B8AFF,0x3151C6,0x877FE9,0xD31DFF,0xFF005D,0xFF7F00,0xB9B000,0x90FF00,0x835D07,0x392b00,0x144C10
		,0x0D5038,0x15152A,0x16205A,0x693C1C,0xA8000A,0xDE513D,0xD86A1C,0xFFE126,0x9EE12F,0x67B50F,0x1E1E30,0xDCFF6B,0x80FFBD,0x9A99FF,0x8E66FF,0x404040,0x757575,0xE0FFFF
		,0xA00000,0x350000,0x1AD000,0x074200,0xB9B000,0x3F3100,0xB35F00,0x4B1502};
	
	
	// ///////////////////////////////////////////////////
	// ///////////   OUTGOING STATE MESSAGES  ////////////
	// ///////////////////////////////////////////////////
	
	public static boolean sendApcButtonSelect(MidiBus midiBus, int trackId, boolean on) {
		if(trackId < 0 || trackId > TRACK_COUNT) { return false; }
		midiSendNoteOn(midiBus, trackId-1, 51, getIntToBool(on)); return true;
	}
	
	public static boolean sendApcButtonRecord(MidiBus midiBus, int trackId, boolean on) {
		if(trackId < 0 || trackId > TRACK_COUNT) { return false; }
		midiSendNoteOn(midiBus, trackId-1, 48, getIntToBool(on)); return true;
	}
	
	public static boolean sendApcButtonSolo(MidiBus midiBus, int trackId, boolean on) {
		if(trackId < 0 || trackId > TRACK_COUNT) { return false; }
		midiSendNoteOn(midiBus, trackId-1, 49, getIntToBool(on)); return true;
	}
	
	public static boolean sendApcButtonActivator(MidiBus midiBus, int trackId, boolean on) {
		if(trackId < 0 || trackId > TRACK_COUNT) { return false; }
		midiSendNoteOn(midiBus, trackId-1, 50, getIntToBool(on)); return true;
	}

	public static boolean sendApcButton8(MidiBus midiBus, int buttonId, boolean on) {
		if(buttonId < 0 || buttonId > 8) { return false; }
		midiSendNoteOn(midiBus, 0, 57 + buttonId, getIntToBool(on)); return true;
	}

	public static boolean sendApcKnob8TopStyle(MidiBus midiBus, int knobId, int style) {
		if(knobId < 0 || knobId > 8) { return false; }
		sendApcKnob(midiBus, 0, 47 + knobId + 8, style); return true;
	}
	
	public static boolean sendApcKnob8Top(MidiBus midiBus, int knobId, int value) {
		if(knobId < 0 || knobId > 8) { return false; }
		sendApcKnob(midiBus, 0, 47 + knobId, value); return true;
	}

	public static boolean sendApcKnob8RightStyle(MidiBus midiBus, int knobId, int style) {
		if(knobId < 0 || knobId > 8) { return false; }
		sendApcKnob(midiBus, 0, 15 + knobId + 8, style); return true;
	}
	
	public static boolean sendApcKnob8Right(MidiBus midiBus, int knobId, int value) {
		if(knobId < 0 || knobId > 8) { return false; }
		sendApcKnob(midiBus, 0, 15 + knobId, value); return true;
	}

	public static void sendApcKnob(MidiBus midiBus, int trackId, int knobId, int value) {
		midiSendControllerChange(midiBus, 0, knobId, value);
	}

	public static boolean sendApcPadRGB(MidiBus midiBus, int blinkModeId, int buttonId, int colorId) {
		if(buttonId < 0 || buttonId > 40) { return false; }
		midiSendNoteOn(midiBus, blinkModeId, buttonId - 1, colorId); return true;
	}

	public static boolean sendApcPadRightRGB(MidiBus midiBus, int blinkModeId, int buttonId, int colorId) {
		if(buttonId < 0 || buttonId > 5) { return false; }
		midiSendNoteOn(midiBus, blinkModeId, buttonId + 81, colorId); return true;
	}

	public static boolean sendApcButtonAB(MidiBus midiBus, int trackId, int dualColorId) {
		if(trackId < 0 || trackId > TRACK_COUNT) { return false; }
		midiSendNoteOn(midiBus, trackId-1, 66, dualColorId); return true;
	}

	public static boolean sendApcButtonStop(MidiBus midiBus, int trackId, boolean on, boolean blinking) {
		if(trackId < 0 || trackId > TRACK_COUNT) { return false; }
		int value = getIntToBool(on);
		if(on && blinking) { value = 2; } 
		midiSendNoteOn(midiBus, trackId-1, 52, value); return true;
	}
	
	public static boolean sendApcButtonAB(MidiBus midiBus, int trackId, boolean on, boolean yellow) {
		if(trackId < 0 || trackId > TRACK_COUNT) { return false; }
		int value = 0;
		if(on) {
			if(yellow) { value = DUALCOLOR_YELLOW; } 
			else { value = DUALCOLOR_ORANGE; }
		} 
		return sendApcButtonAB(midiBus, trackId, value);
	}
	
	public static void sendApcButtonMaster(MidiBus midiBus, boolean on) { midiSendNoteOn(midiBus, 0, 80, getIntToBool(on)); }

	public static void sendApcButtonPan(MidiBus midiBus, boolean on) { midiSendNoteOn(midiBus, 0, 87, getIntToBool(on)); }
	public static void sendApcButtonSends(MidiBus midiBus, boolean on) { midiSendNoteOn(midiBus, 0, 88, getIntToBool(on)); }
	public static void sendApcButtonUser(MidiBus midiBus, boolean on) { midiSendNoteOn(midiBus, 0, 89, getIntToBool(on)); }
	public static void sendApcButtonMetronome(MidiBus midiBus, boolean on) { midiSendNoteOn(midiBus, 0, 90, getIntToBool(on)); }

	public static void sendApcButtonPlay(MidiBus midiBus, boolean on) { midiSendNoteOn(midiBus, 0, 91, getIntToBool(on)); }
	public static void sendApcButtonSession(MidiBus midiBus, boolean on) { midiSendNoteOn(midiBus, 0, 102, getIntToBool(on)); }
	public static void sendApcButtonRecord(MidiBus midiBus, boolean on) { midiSendNoteOn(midiBus, 0, 93, getIntToBool(on)); }
	public static void sendApcButtonBank(MidiBus midiBus, boolean on) { midiSendNoteOn(midiBus, 0, 103, getIntToBool(on)); }

	public static boolean sendApcMode(MidiBus midiBus, int mode) {
		//https://forum.bome.com/t/-apc-40-mkii-change-mode/986
		if(mode < 0 || mode > 2) { return false; }
		byte[] data = new byte[12];
		data[0] = (byte) 0xF0;
		data[1] = (byte) 0x47;
		data[2] = (byte) 0x7F;
		data[3] = (byte) 0x29;
		data[4] = (byte) 0x60;
		data[5] = (byte) 0x00;
		data[6] = (byte) 0x04;
		data[7] = (byte) (unhex(String.valueOf(40 + mode)));
		data[8] = (byte) 0x08;
		data[9] = (byte) 0x02;
		data[10] = (byte) 0x01;
		data[11] = (byte) 0xF7;
		midiSendMessage(midiBus, data);
		return true;
	}
	
	// ///////////////////////////////////////////////////
	// ////////////////   COLOR LOGIC   //////////////////
	// ///////////////////////////////////////////////////
	
	public static int[] getColorCodeRGB(int colorId) {
		int[] results = new int[3];
		if(colorId < 0 || colorId > COLORCODE_COUNT){ return results; }
		results[0] = RGB_COLORS[colorId-1][0];
		results[1] = RGB_COLORS[colorId-1][1];
		results[2] = RGB_COLORS[colorId-1][2];
		return results;
	}

	public static int getColorCodeR(int colorId) {
		if(colorId < 0 || colorId > COLORCODE_COUNT){ return 0; }
		return RGB_COLORS[colorId-1][0];
	}
	
	public static int getColorCodeG(int colorId) {
		if(colorId < 0 || colorId > COLORCODE_COUNT){ return 0; }
		return RGB_COLORS[colorId-1][1];
	}
	
	public static int getColorCodeB(int colorId) {
		if(colorId < 0 || colorId > COLORCODE_COUNT){ return 0; }
		return RGB_COLORS[colorId-1][2];
	}
	
	public static String getColorCodeHex(int colorId) {
		if(colorId < 0 || colorId > COLORCODE_COUNT){ return "000000"; }
		return RGB_HEXCOLORS[colorId-1];
	}
	
	public static int getColorCodeInt(int colorId) {
		if(colorId < 0 || colorId > COLORCODE_COUNT){ return 0; }
		return RGB_HEXCOLOR_INTS[colorId-1];
	}
	
	public static double getColorCodeDistance(int colorId, int r, int g, int b) {
		return colorDist(r, g, b, getColorCodeR(colorId), getColorCodeG(colorId), getColorCodeB(colorId));
	}
	
	public static int[] getNearestColorCodeRGB(int r, int g, int b) {
		int cId = getNearestColorCode(r, g, b);
		
		int[] results = new int[3];
		results[0] = getColorCodeR(cId);
		results[1] = getColorCodeG(cId);
		results[2] = getColorCodeB(cId);
		return results;
	}

	public static int getNearestColorCode(int r, int g, int b) {
		if(r+g+b < 1) { return 0; }
		double cDist = 10000;
		int cId = 0;
		
		for (int i = 0; i < COLORCODE_COUNT; i++) {
			double dist = getColorCodeDistance(i+1, r, g, b);
			if(dist < cDist) {
				cDist = dist;
				cId = i+1;
			}
		}
		return cId;
	}
	
	// ///////////////////////////////////////////////////
	// //////////////   OUTGOING MIDIBUS   ///////////////
	// ///////////////////////////////////////////////////

	private static void midiSendMessage(MidiBus midiBus, byte[] data) {
		midiBus.sendMessage(data);
	}
	
	private static void midiSendNoteOn(MidiBus midiBus, int channel, int pitch, int velocity) {
		midiBus.sendNoteOn(channel, pitch, velocity);
	}
	
	private static void midiSendControllerChange(MidiBus midiBus, int channel, int pitch, int velocity) {
		midiBus.sendControllerChange(channel, pitch, velocity);
	}
	
	// ///////////////////////////////////////////////////
	// ///////////////   INTERNAL UTILS  /////////////////
	// ///////////////////////////////////////////////////
	
	private static int unhex(String value) {
		return (int) (Long.parseLong(value, 16));
	}
	
	private static int getIntToBool(boolean on) {
		if(on){return 127;} return 0;
	}

	private static double colorDist(int r1, int g1, int b1, int r2, int g2, int b2){ 
		//https://stackoverflow.com/a/5069048
		long rmean = ( r1 + r2 ) / 2;
		int r = r1 - r2;
		int g = g1 - g2;
		int b = b1 - b2;
		return Math.sqrt((((512+rmean)*r*r)>>8) + 4*g*g + (((767-rmean)*b*b)>>8));
	}
}
