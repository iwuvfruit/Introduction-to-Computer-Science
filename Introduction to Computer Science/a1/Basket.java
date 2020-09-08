import java.util.List;

public class Basket {
	private MarketProduct[] basket;
	public Basket() {
		this.basket = new MarketProduct[0];
	}
	public MarketProduct[] getProducts() {
		MarketProduct[] products = new MarketProduct[basket.length];
		for(int i = 0; i < basket.length; i++) {
			products[i] = basket[i];
		}
		return products;
	}
	public void add(MarketProduct product) {
		//add the product at the end of the list of this basket
		//so the basket is array, it's saying create a new basket??
		//terrible code. why cant i use list and add there in the first place
//		List<MarketProduct> basket = new ArrayList<>();
//		basket.add(product); 
		if(this.basket.length == 0) {
			MarketProduct[] basket = new MarketProduct[1];
			basket[0] = product;
		}else {
			//we override basket here
			MarketProduct[] basket = new MarketProduct[this.basket.length+1];
			basket[basket.length-1] = product;
			//not sure if this line is required here 
			this.basket = basket;
		}
	}
	public boolean remove(MarketProduct product) {
		if(product.equals(null)) {
			return true;
		}
		
		for(int i = 0; i < this.basket.length; i++) {
			if(basket[i].equals(product)) {
				MarketProduct[] basket2 = new MarketProduct[this.basket.length-1];
				this.basket = removeArray(basket2, i);
				return true;
			}
		}
		return false;
	}
	public MarketProduct[] removeArray(MarketProduct[] baskett, int index) {
		int count = 0;
		for(int i = 0; i < this.basket.length; i++) {
			if(i != index) {
				baskett[count] = this.basket[i];
				count +=1;
			}
		}
		return baskett;
		
	}
	
	public void clear() {
		this.basket = new MarketProduct[0];
	}
	public int getNumOfProducts() {
		return basket.length;
	}
	public int getSubTotal() {
		int total = 0;
		for(int i = 0; i < this.basket.length; i++) {
			total += this.basket[i].getCost();
		}
		return total;
	}
	public int getTotalTax() {
		int taxTotal = 0;
		for(int i = 0; i < this.basket.length; i++) {
			if(!(this.basket[i] instanceof Egg || this.basket[i] instanceof Fruit)) {
				taxTotal =(int)(this.basket[i].getCost() * 0.15);
				return taxTotal;
			}
		}
		return 0;
	}
	public int getTotalCost() {
		return this.getSubTotal() - this.getTotalTax();
	}
	public String toString() {
		String productLine = "\n";
		for(int i = 0; i < basket.length; i++) {
			String name = basket[i].getName();
			int cost = basket[i].getCost();
			String charge = dollarConversion(cost);
			productLine += (name + "\t" + charge);
		}
		productLine += "\n\n";
		String subTotal = dollarConversion(this.getSubTotal());
		String totalTax = dollarConversion(this.getTotalTax());
		String totalCost = dollarConversion(this.getTotalCost());
		productLine += ("Subtotal" + "\t" +subTotal + "\n" + "Total Tax" + "\t" + totalTax);
		productLine += "\n";
		productLine += ("Total Cost" + "\t" + totalCost);
		
		return productLine;
		
	}
	public String dollarConversion(int cost) {
		String x ="";
		if(cost <= 0) {
			return "-";
		}
		else {
			double dollar = cost / 100.0;
			return x+dollar;
		}
	}
}
