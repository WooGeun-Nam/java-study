package prob2;

public class SmartPhone extends MusicPhone {
	public void execute (String function) {
		if (function.equals("앱")) {
			playApp();
			return;
		}
		super.execute(function);
	}
	
	private void playApp() {
		System.out.println("앱실행");
	}
}
