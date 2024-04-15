
/**
 * @author be8584
 */
public class Mandolin extends Instrument2 {
	String serialNumber_;

	/**
	 * @param serialNumber
	 * @param price
	 * @param model
	 * @param type
	 * @param backWood
	 * @param topWood
	 * @param style
	 */
	public Mandolin ( String serialNumber, double price, String model, Instrument type,
	                  Wood backWood, Wood topWood, String style ) {
			}

	/**
	 * @param serialNumber
	 * @param price
	 * @param model
	 * @param type
	 * @param backWood
	 * @param topWood
	 * @param style
	 */
	public Mandolin ( String serialNumber, double price, String model,
	                  Instrument type, Wood backWood, Wood topWood,
	                  String style ) {
		super();
		serialNumber_ = serialNumber;
		price_ = price;
		model_ = model;
		type_ = type;
		backWood_ = backWood;
		topWood_ = topWood;
		style_ = style;
	}

	double price_;
	String model_;
	Instrument type_;
	Wood backWood_;
	Wood topWood_;
	String style_;

	/**
	 * @return the style
	 */
	public String getStyle () {
		return style_;
	}

}
