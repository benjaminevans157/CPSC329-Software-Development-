/**
 * 
 */


/**
 * Guitar builders.
 * 
 * @author Stina Bridgeman, based on OOAD ch 1
 */
public enum Builder {

	// legal values
	FENDER("Fender"), MARTIN("Martin"), GIBSON("Gibson"), COLLINGS("Collings"),
	OLSON("Olson"), RYAN("Ryan"), PRS("PRS"), ANY("any");
	
	/**
	 * The string name associated with the builder.
	 */
	private String name_;

	/**
	 * Create a new Builder with the specified string name.
	 * 
	 * @param name
	 */
	private Builder ( String name ) {
		name_ = name;
	}

	/**
	 * @return the string name associated with the builder
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
	 * Retrieve the Builder associated with a particular string name.
	 * 
	 * @param name
	 * @return the associated Builder
	 * @exception IllegalArgumentException
	 *              if there is no Builder for the specified name
	 */
	public static Builder fromString ( String name ) {
		if ( name == null ) {
			throw new IllegalArgumentException("name cannot be null");
		}

		for ( Builder builder : Builder.values() ) {
			if ( builder.getName().toLowerCase().equals(name.toLowerCase()) ) {
				return builder;
			}
		}

		throw new IllegalArgumentException("'" + name
		    + "' is not a legal value for " + Builder.class.getName());
	}
}
