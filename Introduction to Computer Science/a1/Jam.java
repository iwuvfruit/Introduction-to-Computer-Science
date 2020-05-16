
public class Jam extends MarketProduct {
	private int nrJars;
	private int pricePerJar;
	
	public Jam(String name, int nrJars, int pricePerJar) {
		super(name);
		// TODO Auto-generated constructor stub
		this.nrJars = nrJars;
		this.pricePerJar = pricePerJar;
	}

	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		int cost = nrJars * pricePerJar;
		return cost;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof Jam) {
			if( ((Jam) o).getName() == this.getName() && 
					((Jam)o).nrJars == this.nrJars &&
					((Jam)o).getCost() == this.getCost()) {
				return true;
			}
		}
		return false;
	}
	
	
	
}
