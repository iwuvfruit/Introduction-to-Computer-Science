
public class Egg extends MarketProduct {
	private int nrEggs;
	private int pricePerDozen;
	
	public Egg(String name, int nrRequired, int priceByDozen) {
		super(name);
		this.nrEggs = nrRequired;
		this.pricePerDozen = priceByDozen;
	}

	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		//divide with 12.0
		int pricePerEgg = (int) Math.floor(this.nrEggs * (this.pricePerDozen / 12.0)) ;
		return pricePerEgg;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof Egg) {
			if(((Egg) o).getName() == this.getName() &&
					((Egg) o).getCost() == this.getCost() &&
					((Egg)o).nrEggs == this.nrEggs) {
				return true;
			}
			else {
				return false;
			}
			
		}
		else {
			return false;
		}
	}
	

}
