package tv;

public class TV {
	private int channel; // 1~255 로테이션
	private int volume; // 0~100 로테이션
	private boolean power;
	
	private int channelCheck(int channel) {
		int result;
		if(channel>255) {
			result = 1;
			return result;
		}
		if(channel<1) {
			result = 255;
			return result;
		}
		return channel;
	}
	
	private int volumeCheck(int volume) {
		int result;
		if(volume>100) {
			result = 0;
			return result;
		}
		if(volume<0) {
			result = 100;
			return result;
		}
		return volume;
	}
	
	public TV(int channel, int volume, boolean power) {
		this.channel = channel;
		this.volume = volume;
		this.power = power;
	}

	public void power(boolean on) {
		this.power = on;
	}
	
	public void channel(int channel) {
		this.channel = channelCheck(channel);
	}
	
	public void channel(boolean up) {
		if(up) {
			this.channel = channelCheck(channel+1);
		} else {
			this.channel = channelCheck(channel-1);
		}
	}
	
	public void volume(int volume) {
		this.volume = volumeCheck(volume);
	}
	public void volume(boolean up) {
		if(up) {
			this.volume = volumeCheck(volume+1);
		} else {
			this.volume = volumeCheck(volume-1);
		}
	}
	
	public void status() {
		System.out.println("TV[power="+(power?"on":"off")+", channel="+channel+", volume="+volume+"]");
	}
}
