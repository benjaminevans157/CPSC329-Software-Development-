
/**
 * @author be8584
 *
 */
public class Instrument2 {

	protected String serialNumber_;

	/**
	 * @return the price
	 */
	public double getPrice () {
		return price_;
	}

	/**
	 * @param price
	 *          the price to set
	 */
	public void setPrice ( double price ) {
		price_ = price;
	}

	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber () {
		return serialNumber_;
	}

	/**
	 * @return the builder
	 */
	public Builder getBuilder () {
		return builder_;
	}

	/**
	 * @return the model
	 */
	public String getModel () {
		return model_;
	}

	/**
	 * @return the type
	 */
	public Instrument getType () {
		return type_;
	}

	/**
	 * @return the backWood
	 */
	public Wood getBackWood () {
		return backWood_;
	}

	/**
	 * @return the topWood
	 */
	public Wood getTopWood () {
		return topWood_;
	}
/*
	protected double price_;
	protected Builder builder_;
	protected String model_;
	protected Instrument type_;
	protected Wood backWood_;
	protected Wood topWood_;
*/
	/**
	 * 
	 */
	public Instrument2 () {
		super();
	}

}