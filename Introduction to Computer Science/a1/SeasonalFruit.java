
public class SeasonalFruit extends Fruit {

	public SeasonalFruit(String name, double weight, int price) {
		super(name, weight, price);
		// TODO Auto-generated constructor stub
	}
	

	public int getCost() {
		int discount = (int) (super.getCost() * 0.15);
		int cost = (int)(super.getCost() * 0.85);
		return cost;
		
	}
	
}
