package cl.pricewatcher.model;

import java.util.Date;

public class Product {
	
	int id;
	Store store;
	String name;
	String model;
	Manufacturer manufacturer;
	Product productURL;

	Price bestPrice;
	Price normalPrice;
	Price normalPrice2;
	
	Date lastUpdate;
	
	Specifications specs;
	

}
