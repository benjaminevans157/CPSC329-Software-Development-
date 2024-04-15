
/**
 * @author be8584
 */
public class Guitar extends Instrument2 {

	/**
	 * @param serialNumber
	 * @param price
	 * @param builder
	 * @param model
	 * @param type
	 * @param backWood
	 * @param topWood
	 * @param numStrings
	 * @param price_ 
	 * @param builder_ 
	 * @param model_ 
	 * @param type_ 
	 * @param backWood_ 
	 * @param topWood_ 
	 */
	public Guitar ( String serialNumber, double price, Builder builder,
	                String model, Instrument type, Wood backWood, Wood topWood,
	                int numStrings, double price_, Builder builder_, String model_, Instrument type_, Wood backWood_, Wood topWood_ ) {
		super();
		
		numStrings_ = numStrings;
	}

	/**
	 * @return the numStrings
	 */
	public int getNumStrings () {
		return numStrings_;
	}

	private int numStrings_;
}
