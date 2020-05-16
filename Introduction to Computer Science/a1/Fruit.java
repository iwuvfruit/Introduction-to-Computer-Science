
public class Fruit extends MarketProduct {
	private double weight;
	private int pricePerKg;
	
	
	public Fruit(String name, double weight, int price) {
		super(name);
		// TODO Auto-generated constructor stub
		this.weight = weight;
		this.pricePerKg = price; 
		
	}
	

	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		int cost = (int) (this.pricePerKg * weight);
		return cost;

	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof Fruit) {
			if( ((Fruit) o).getName() == this.getName() && 
					((Fruit)o).weight == this.weight &&
					((Fruit)o).getCost() == this.getCost()) {
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
