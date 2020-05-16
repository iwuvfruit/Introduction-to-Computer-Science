
public class Basket {
	private MarketProduct[] basket;
	public Basket () {
		this.basket = null;
	}
	public MarketProduct[] getProducts() {
		
//		MarketProduct[] newArray = this.basket.clone();
		//we want (Not a copy of the references so shouldn't use clone())
		MarketProduct[] newBasket = new MarketProduct[this.basket.length];
		for(int i = 0; i < this.basket.length; i++) {
			newBasket[i] = this.basket[i];
		}
		return newBasket;
		}
	public void add(MarketProduct product) {
		if(this.basket == null) {
			MarketProduct[] basket = new MarketProduct[1];
			basket[0] = product;
		}
		else {
			MarketProduct[] basket = new MarketProduct[this.basket.length + 1];
			for(int i = 0; i <this.basket.length; i++) {
				basket[i] = this.basket[i];
			}
			basket[this.basket.length] = product;
		}
		
	}
	public boolean remove(MarketProduct target) {
		int index = 0;
		MarketProduct[] basket =new MarketProduct[this.basket.length-1];
		for(int i = 0; i <= this.basket.length; i++) {
			if((this.basket[i]).equals(target)) {
				index = i;
			}
			break;
		}
		if(index == 0) {
			return false;
		}
		for(int i = 0; i < index; i++) {
			basket[i] = this.basket[i];
		}
		for(int i = index+1; i<this.basket.length; i++) {
			basket[i] = this.basket[i+1];
		}
		return true;
	}
	public void clear() {
		this.basket = null;
	}
	public int getNumOfProducts() {
		int count = 0;
		for(int i = 0; i <this.basket.length; i++) {
			if(this.basket[i] != null) {
				count ++;
			}
		}
		return count;
	}
	public int getSubTotal() {
		int cost = 0;
		for(int i = 0; i <this.basket.length; i++) {
			cost += this.basket[i].getCost();
		}
		return cost;
	}
	public int getTotalTax() {
		int taxCost = 0;
		for(int i = 0; i <this.basket.length; i++) {
			if(this.basket[i] instanceof Jam) {
				taxCost = (int) (this.basket[i].getCost() * 0.15);
			}
		}
		return taxCost;
	}
	
	public int getTotalCost() {
		int totalCost = this.getSubTotal() - this.getTotalCost();
		return totalCost;
	}
	
	public String toString() {
		String productLine = " ";
		for(int i = 0; i <this.basket.length; i++) {
			String productName = this.basket[i].getName();
			int price = this.basket[i].getCost();
			String dollar = dollorConverter(price);
			productLine = productName +"\t" + dollar + "\n";
		}
		int subTotalInCents = this.getSubTotal();
		String subTotal = dollorConverter(subTotalInCents);
		int taxTotalInCents = this.getTotalTax();
		String totalTax = dollorConverter(taxTotalInCents);
		int totalCostInCents = this.getTotalCost();
		String totalCost = dollorConverter(totalCostInCents);
		
		productLine += "Subtotal" + "\t" + subTotal + "\n" + "Total Tax" + "\t" + totalTax + "\n\n" + 
		"Total Cost" + "\t" + totalCost;
		return productLine;
		
		
	}
	public String dollorConverter(int price) {
//		String strDouble = String.format("%.2f", 1.23456);
		if(price < 0) {
			return "-";
		}
		double x = price / 100;
		String dollor = String.format("%.2f", x);
		return dollor;

	}
	
	

}
