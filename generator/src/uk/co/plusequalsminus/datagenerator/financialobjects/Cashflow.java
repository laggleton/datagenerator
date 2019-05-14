package uk.co.plusequalsminus.datagenerator.financialobjects;

import java.util.Currency;
import java.util.Date;

import uk.co.plusequalsminus.datagenerator.annotations.ForeignKey;

public class Cashflow extends GeneratableObject {
	
	@ForeignKey(type = Trade.class)
	private String tradeId;

	private Date cashflowDate;
	private Currency currency;
	private Double amount;
	
	@ForeignKey(type = CashflowType.class)
	private String cashflowType;

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public Date getCashflowDate() {
		return cashflowDate;
	}

	public void setCashflowDate(Date cashflowDate) {
		this.cashflowDate = cashflowDate;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCashflowType() {
		return cashflowType;
	}

	public void setCashflowType(String cashflowType) {
		this.cashflowType = cashflowType;
	}
		
}
