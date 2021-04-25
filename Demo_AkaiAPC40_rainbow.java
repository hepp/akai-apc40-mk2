import themidibus.MidiBus;
import themidibus.MidiListener;

public class Demo_AkaiAPC40_rainbow {
	protected MidiBus midiBus;

	protected void firstFrame() {
		
		MidiBus.list();
		midiBus = new MidiBus(this, 0, 3);
		midiBus.addMidiListener(this);
		
		AkaiAPC40Utils.sendApcMode(midiBus, AkaiAPC40Utils.MODE_ABLETON);
	}
	
	protected void drawApp() {
		for (int i = 0; i < APC40Utils.NUM_RGB_PADS + APC40Utils.NUM_RGB_PADS_RIGHT; i++) {
			
			float hue = (i*.0004f) + (frameCount*.001f);
			float brightness = (float) (((i%3)+.7f) * .5f);
			int colorCode = getColorCode(hue, 1, brightness);
			
			int buttonId = i+1;
			if(i >= APC40Utils.NUM_RGB_PADS) {
				buttonId -= APC40Utils.NUM_RGB_PADS;
				APC40Utils.sendPadRightRGB(midiBus, APC40Utils.RGBTYPE_SOLID, buttonId, colorCode);
			}else {
				APC40Utils.sendPadRGB(midiBus, APC40Utils.RGBTYPE_SOLID, buttonId, colorCode);
			}
		}
	}
	
	protected int getColorCode(float hue, float saturation, float brightness) {
		int color = Color.HSBtoRGB(hue, saturation, brightness);
		return APC40Utils.getNearestColorCode(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF);
	}
}