package prob03;

public class Money {
	private int amount;
	// 해쉬코드 오버라이드
	
	/* 코드 작성 */
	public Money(int amount) {
		this.amount = amount;
	}

	public int add(Money money) {
		return amount+money.amount;
	}

	public int minus(Money money) {
		return amount-money.amount;
	}

	public int multiply(Money money) {
		return amount*money.amount;
	}

	public int devide(Money money) {
		return amount/money.amount;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.amount==(int)obj;
	}
	
}
