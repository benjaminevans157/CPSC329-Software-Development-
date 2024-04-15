import java.util.LinkedList;
import java.util.List;

/**
 * Rick's inventory - a collection of guitars, with search features.
 * 
 * @author OOAD ch 1, modified to use generics, for-each loops, and additional
 *         error-checking by Stina Bridgeman
 */
public class Inventory {

	/**
	 * The collection of guitars.
	 */
	private List<Guitar> guitars_;

	/**
	 * Create a new Inventory, initially empty.
	 */
	public Inventory () {
		guitars_ = new LinkedList<Guitar>();
	}

	/**
	 * Add a new guitar with the specified properties to the inventory.
	 * 
	 * @param serialNumber
	 * @param price
	 * @param builder
	 * @param model
	 * @param type
	 * @param backWood
	 * @param topWood
	 * @param numStrings
	 */
	public void addGuitar ( String serialNumber, double price, Builder builder,
	                        String model, Instrument type, Wood backWood, Wood topWood,
	                        int numStrings ) {
		Guitar guitar =
		    new Guitar(serialNumber,price,builder,model,type,backWood,topWood,
		               numStrings);
		guitars_.add(guitar);
	}

	/**
	 * Retrieve the guitar with the specified serial number.
	 * 
	 * @param serialNumber
	 * @return the guitar with the specified serial number, or null if there is no
	 *         such guitar
	 */
	public Instrument2 getGuitar ( String serialNumber ) {
		for ( Instrument2 guitar : guitars_ ) {
			if ( guitar.getSerialNumber().equals(serialNumber) ) {
				return guitar;
			}
		}
		return null;
	}

	/**
	 * Retrieve the guitar matching the specifications provided. Use null or ""
	 * (empty string) for properties where any match is acceptable.
	 * 
	 * @param searchGuitar
	 *          guitar specifications (searchGuitar != null)
	 * @return the matching guitar, or null if there is no such guitar
	 */
	public List<Guitar> search ( Instrument2 searchGuitar ) {
		List<Guitar> matchingGuitars = new LinkedList<Guitar>();

		if ( searchGuitar == null ) {
			throw new IllegalArgumentException("search guitar cannot be null");
		}

		for ( Guitar guitar : guitars_ ) {
			if ( searchGuitar.getBuilder() != guitar.getBuilder() ) {
				continue;
			}

			String model = searchGuitar.getModel();
			if ( model != null && !model.equals("")
			    && !model.equals(guitar.getModel()) ) {
				continue;
			}

			if ( searchGuitar.getType() != guitar.getType() ) {
				continue;
			}

			if ( searchGuitar.getBackWood() != guitar.getBackWood() ) {
				continue;
			}

			if ( searchGuitar.getTopWood() != guitar.getTopWood() ) {
				continue;
			}

			matchingGuitars.add(guitar);
		}

		return matchingGuitars;
	}
}
