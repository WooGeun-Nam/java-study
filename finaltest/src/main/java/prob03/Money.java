package prob03;

public class Money {
	private int amount;
	// 해쉬코드 오버라이드
	
	/* 코드 작성 */
	public Money() {
	}
	
	public Money(int amount) {
		this.amount = amount;
	}

	public Money add(Money money) {
		Money m = new Money();
		m.amount = amount+money.amount;
		return m;
	}

	public Money minus(Money money) {
		Money m = new Money();
		m.amount = amount-money.amount;
		return m;
	}

	public Money multiply(Money money) {
		Money m = new Money();
		m.amount = amount*money.amount;
		return m;
	}

	public Money devide(Money money) {
		Money m = new Money();
		m.amount = amount/money.amount;
		return m;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Money) {
			return this.amount==((Money) obj).amount;
		}
		return false;
	}
	
}
