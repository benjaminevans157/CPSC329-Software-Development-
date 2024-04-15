package lab2pack;
/**
 * @author OOAD ch 2
 */
public class DogDoor {

  private boolean open_;

  public DogDoor () {
    open_ = false;
  }

  public void open () {
    System.out.println("The dog door opens.");
    open_ = true;
  }

  public void close () {
    System.out.println("The dog door closes.");
    open_ = false;
  }

  public boolean isOpen () {
    return open_;
  }

}
