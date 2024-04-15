import java.util.List;

/**
 * A very simple tester for Rick's Guitars.
 * 
 * @author OOAD ch 1, with slight modifications by Stina Bridgeman
 */
public class FindGuitarTester {

	private static void initializeInventory ( Inventory inventory ) {
		inventory.addGuitar("11277",3999.95,Builder.COLLINGS,"CJ",Instrument.ACOUSTIC,
		                    Wood.INDIAN_ROSEWOOD,Wood.SITKA,6);
		inventory.addGuitar("V95693",1499.95,Builder.FENDER,"Stratocastor",
		                    Instrument.ELECTRIC,Wood.ALDER,Wood.ALDER,6);
		inventory.addGuitar("V9512",1549.95,Builder.FENDER,"Stratocastor",
		                    Instrument.ELECTRIC,Wood.ALDER,Wood.ALDER,6);
		inventory.addGuitar("122784",5495.95,Builder.MARTIN,"D-18",Instrument.ACOUSTIC,
		                    Wood.MAHOGANY,Wood.ADIRONDACK,6);
		inventory.addGuitar("76531",6295.95,Builder.MARTIN,"OM-28",Instrument.ACOUSTIC,
		                    Wood.BRAZILIAN_ROSEWOOD,Wood.ADIRONDACK,6);
		inventory.addGuitar("70108276",2295.95,Builder.GIBSON,"Les Paul",
		                    Instrument.ELECTRIC,Wood.MAHOGANY,Wood.MAPLE,6);
		inventory.addGuitar("82765501",1890.95,Builder.GIBSON,"SG '61 Reissue",
		                    Instrument.ELECTRIC,Wood.MAHOGANY,Wood.MAHOGANY,6);
		inventory.addGuitar("77023",6275.95,Builder.MARTIN,"D-28",Instrument.ACOUSTIC,
		                    Wood.BRAZILIAN_ROSEWOOD,Wood.ADIRONDACK,6);
		inventory.addGuitar("1092",12995.95,Builder.OLSON,"SJ",Instrument.ACOUSTIC,
		                    Wood.INDIAN_ROSEWOOD,Wood.CEDAR,6);
		inventory.addGuitar("566-62",8999.95,Builder.RYAN,"Cathedral",
		                    Instrument.ACOUSTIC,Wood.COCOBOLO,Wood.CEDAR,6);
		inventory.addGuitar("6 29584",2100.95,Builder.PRS,"Dave Navarro Signature",
		                    Instrument.ELECTRIC,Wood.MAHOGANY,Wood.MAPLE,6);
	}

	public static void main ( String[] args ) {
		Inventory inventory = new Inventory();
		initializeInventory(inventory);

		Instrument2 whatErinLikes =
		    new Guitar("",0,Builder.FENDER,"Stratocastor",Instrument.ELECTRIC,Wood.ALDER,
		               Wood.ALDER,6);
		List<Guitar> matchingGuitars = inventory.search(whatErinLikes);
		if ( !matchingGuitars.isEmpty() ) {
			System.out.println("Erin, you might like these guitars:");
			for ( Guitar guitar : matchingGuitars ) {
				System.out.println("  We have a " + guitar.getBuilder() + " "
				    + guitar.getModel() + " " + guitar.getType() + " guitar:\n  "
				    + guitar.getBackWood() + " back and sides,\n  "
				    + guitar.getTopWood() + " top,\n  " + guitar.getNumStrings()
				    + " strings.\n  You can have it for only $" + guitar.getPrice()
				    + "!\n  ----");
			}
		} else {
			System.out.println("Sorry, Erin, we have nothing for you.");
		}
	}
}
