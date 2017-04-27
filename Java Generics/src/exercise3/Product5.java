package exercise3;

class Product {

	private String name;
	private Date shelving;
	private int shelf;

	public Product(String n, int day, int month, int year, int sh){
		name = n;
		shelving = new Date(day, month, year);
		shelf = sh;
	}

	public Date get_date() {
		return shelving;
	}

	public void set_date(Date newDate) {
		shelving = newDate;
	}

	public String get_name() {
		return name;
	}

	public void setname(String newName) {
		name = newName;
	}

	public int get_shelf() {
		return shelf;
	}

	public void set_shelf(int sh) {
		shelf = sh;
	}

	public String toString(){
		String s;
		s = "Product Name: " + name + "||" + "Selving Date: " + shelving + "||" + "Shelf: " + shelf;
		return s;
	}
}
