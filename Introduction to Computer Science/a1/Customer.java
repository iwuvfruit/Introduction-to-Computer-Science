
public class Customer {
	private String name;
	private int balance;
	private Basket basket;
	
	public Customer(String name, int blanace) {
		this.name = name;
		this.balance = balance;
		this.basket = null;
	}
	public String getName() {
		return this.name;
	}
	public Basket getBasket() {
		return this.basket();
	}
	public int getBalance() {
		return this.balance;
	}
	public Basket basket() {
		return this.basket;
	}
	public int addFunds(int centsToBeAdded) {
		if(centsToBeAdded < 0) {
			throw new IllegalArgumentException("can't add negative cents");
		}
		else {
			this.balance += centsToBeAdded;
			return balance;
		}
	}
	public void addToBasket(MarketProduct product) {
		this.basket.add(product);
	}
	public boolean removeFromBasket(MarketProduct product) {
		return this.basket.remove(product);
	}
	public String checkOut() {
		String recipt = "";
		if(this.balance < this.basket.getTotalCost()) {
			throw new IllegalArgumentException("not enough money");
		}
		else {
			this.balance -= this.basket.getTotalCost();
			recipt = this.basket.toString();
			this.basket.clear();
		}
		return recipt;
	}
}
