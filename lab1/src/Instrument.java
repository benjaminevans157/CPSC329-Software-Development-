

/**
 * Guitar types.
 * 
 * @author Stina Bridgeman, based on OOAD ch 1
 */
public enum Instrument {

	// legal values
	ACOUSTIC("acoustic"), ELECTRIC("electric");

	/**
	 * The string name associated with the guitar type.
	 */
	private String name_;

	/**
	 * Create a new Instrument with the specified string name.
	 * 
	 * @param name
	 */
	private Instrument ( String name ) {
		name_ = name;
	}

	/**
	 * @return the string name associated with the guitar type
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
	 * Retrieve the Instrument associated with a particular string name.
	 * 
	 * @param name
	 * @return the associated Instrument
	 * @exception IllegalArgumentException
	 *              if there is no Instrument for the specified name
	 */
	public static Instrument fromString ( String name ) {
		if ( name == null ) {
			throw new IllegalArgumentException("name cannot be null");
		}

		for ( Instrument instrument : Instrument.values() ) {
			if ( instrument.getName().toLowerCase().equals(name.toLowerCase()) ) {
				return instrument;
			}
		}

		throw new IllegalArgumentException("'" + name
		    + "' is not a legal value for " + Instrument.class.getName());
	}
}
