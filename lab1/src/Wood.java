

/**
 * Kinds of wood.
 * 
 * @author Stina Bridgeman, based on OOAD ch 1
 */
public enum Wood {

	// legal values
	INDIAN_ROSEWOOD("Indian Rosewood"), BRAZILIAN_ROSEWOOD("Brazilian Rosewood"),
	MAHOGANY("Mahogany"), MAPLE("Maple"), COCOBOLO("Cocobolo"), CEDAR("Cedar"),
	ADIRONDACK("Adirondack"), ALDER("Alder"), SITKA("Sitka");

	/**
	 * The string name associated with the wood type.
	 */
	private String name_;

	/**
	 * Create a new Wood with the specified string name.
	 * 
	 * @param name
	 */
	private Wood ( String name ) {
		name_ = name;
	}

	/**
	 * @return the string name associated with the wood type
	 */
	public String getName () {
		return name_;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString () {
		return name_;
	}

	/**
	 * Retrieve the Wood associated with a particular string name.
	 * 
	 * @param name
	 * @return the associated Wood
	 * @exception IllegalArgumentException
	 *              if there is no Wood for the specified name
	 */
	public static Wood fromString ( String name ) {
		if ( name == null ) {
			throw new IllegalArgumentException("name cannot be null");
		}

		for ( Wood wood : Wood.values() ) {
			if ( wood.getName().toLowerCase().equals(name.toLowerCase()) ) {
				return wood;
			}
		}

		throw new IllegalArgumentException("'" + name
		    + "' is not a legal value for " + Wood.class.getName());
	}
}
