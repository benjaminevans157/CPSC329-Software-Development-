package lab2pack;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author OOAD ch 2
 */
public class Remote {

  private DogDoor door_;

  public Remote ( DogDoor door ) {
    door_ = door;
  }

  public void pressButton () {
    System.out.println("Pressing the remote control button...");
    if ( door_.isOpen() ) {
      door_.close();
    } else {
      door_.open();
      final Timer timer = new Timer();
      timer.schedule(new TimerTask() {
        public void run() {
          door_.close();
          timer.cancel();
        }
      }, 5000);
    }
  }

}
