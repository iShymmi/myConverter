import java.awt.Image;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class AnimateLabel implements Runnable {
	private Thread thread;
	private JLabel innerLabel;
	private ImageIcon[][] imagesToAnimate = {
		{
			scaleImage(new ImageIcon("images/surface1.png")),scaleImage(new ImageIcon("images/surface2.png")),scaleImage(new ImageIcon("images/surface3.png")),scaleImage(new ImageIcon("images/surface2.png"))
		},
		{
			scaleImage(new ImageIcon("images/weight.png")),scaleImage(new ImageIcon("images/weight2.png")),scaleImage(new ImageIcon("images/weight3.png")),scaleImage(new ImageIcon("images/weight2.png"))
		},
		{
			scaleImage(new ImageIcon("images/currency1.png")),scaleImage(new ImageIcon("images/currency2.png")),scaleImage(new ImageIcon("images/currency3.png")),scaleImage(new ImageIcon("images/currency4.png"))
		}
	};
	private int converterID;
	private final AtomicBoolean running = new AtomicBoolean(false);
	AnimateLabel(JLabel label, int converterId){
		converterID = converterId;
		innerLabel = label;
	}
	
	public void start() {
		thread = new Thread(this, "Image of " +Integer.toString(converterID));
		thread.start();
		running.set(true);
	}
	
	public void run() {
		while(running.get()) {
			try {
				for (int i = 0; i < imagesToAnimate[converterID].length; i++) {
					innerLabel.setIcon(imagesToAnimate[converterID][i]);
					Thread.sleep(400);	
				}
				}catch(InterruptedException e) {
					Thread.currentThread().interrupt();
				}
		}
	}
	
	public void stop() {
		if(thread != null) {
			running.set(false);
			thread.interrupt();
		}
		
	}
	
	boolean isRunning() {
        return running.get();
    }

	
	ImageIcon scaleImage(ImageIcon imgToScale) {
		Image img = imgToScale.getImage();
		Image imgScale = img.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
		ImageIcon scaledIMG = new ImageIcon(imgScale);
		return scaledIMG;
	}
	
}
