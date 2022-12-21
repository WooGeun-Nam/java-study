package tv;

public class TV {
	private int channel; // 1~255 로테이션
	private int volume; // 0~100 로테이션
	private boolean power;
	
	private void power(boolean on) {
		this.power = on;
	}
	
	private void channel(int channel) {
		
	}
	
	private void channel(boolean up) {
		
	}
	
	private void volume(int volume) {
		
	}
	private void volume(boolean up) {
		
	}
	
	private void status() {
		System.out.println("TV[power="+(power?"on":"off")+", channel="+channel+", volume="+volume+"]");
	}
}
