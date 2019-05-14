package uk.co.plusequalsminus.datagenerator.financialobjects;

import java.util.Currency;

import uk.co.plusequalsminus.datagenerator.annotations.ForeignKey;
import uk.co.plusequalsminus.datagenerator.annotations.Ignorable;

/**
 * 
 * A GeneratableObject for Trades
 * @author Lawrence Aggleton
 * 
 */

public class Trade extends GeneratableObject {
	
	private Integer identity;
	
	@ForeignKey(type = Book.class)
	private String book;
	
	private String trader;
	private Integer size;
	private Currency currency;
	private Double price;
	
	@ForeignKey(type = Institution.class)
	private String counterparty;
	
	@Ignorable
	protected static final Integer MAX_SIZE_VALUE = new Integer("1000000000");
	@Ignorable
	protected static final Integer MIN_SIZE_VALUE = new Integer("0");
	
	public Trade(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	public Integer getIdentity() {
		return identity;
	}
	public void setIdentity(Integer identity) {
		this.identity = identity;
	}
	
	public String getBook() {
		return book;
	}
	public void setBook(String book) {
		this.book = book;
	}
	public String getTrader() {
		return trader;
	}
	public void setTrader(String trader) {
		this.trader = trader;
	}
	public Integer getSize() {
		return size;
	}
	
	public void setSize(Integer size) {
		if (checkWithinAllowableRange("size", size)) {
			this.size = size;
		}
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		if (checkWithinAllowableRange("price", price)) {
			this.price = price;
		}
	}
	
	
}
