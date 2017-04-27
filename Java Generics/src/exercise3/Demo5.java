package exercise3;

public class Demo5 {
	
	public void lab5_tests()
	{
		/**
		 * Generic Linked List that can be assigned with a data type
		 */
		LinkedList <Date> date_list = new LinkedList<Date>();
		LinkedList <Point> point_list = new LinkedList<Point>();
		LinkedList <Product> product_list = new LinkedList<Product>();
		LinkedList <Integer> integer_list = new LinkedList<Integer>();
		LinkedList <String> string_list = new LinkedList<String>();
		
		/**
		 * Test Insert functionality with generic list
		 */
		date_list.push_back(2000, new Date(5,9,2015));
		date_list.push_back(2001, new Date(8,20,2013));
		date_list.push_back(2002, new Date(10,23,2019));
		date_list.insert(2222, new Date(28,10,2013));
		date_list.insert(2222, new Date(15,7,2014));
		System.out.println("\nPrinting date_list after its creation ...\n");
		date_list.print();
		
		point_list.push_back(2000, new Point(9.0, 10.1));
		point_list.push_back(2001, new Point(8.7, 9.3));
		point_list.push_back(2002, new Point(7.3, 67.2));
		point_list.insert(2222, new Point(6.3, 7.9));
		point_list.insert(2222, new Point(3.7, 12.4));
		System.out.println("\nPrinting point_list after its creation ...\n");
		point_list.print();
		
		product_list.push_back(2000, new Product("Random1", 27, 7, 2012, 2));
		product_list.push_back(2001, new Product("Random2", 17, 4, 2014, 3));
		product_list.push_back(2002, new Product("Random3", 9, 12, 2011, 4));
		product_list.insert(2222, new Product("Random4", 6, 10, 2015, 4));
		product_list.insert(2222, new Product("Random5", 23, 9, 2010, 6));
		System.out.println("\nPrinting product_list after its creation ...\n");
		product_list.print();
		
		integer_list.push_back(2000,  new Integer(23));
		integer_list.push_back(2001,  new Integer(24));
		integer_list.push_back(2002,  new Integer(266));
		integer_list.push_back(2003,  new Integer(323));
		integer_list.insert(2222,  new Integer(333));
		integer_list.insert(2222,  new Integer(331));
		System.out.println("\nPrinting integer_list after its creation ...\n");
		integer_list.print();
		
		string_list.push_back(2000, new String("Some Name 1"));
		string_list.push_back(2001, new String("Some Name 2"));
		string_list.push_back(2002, new String("Some Name 3"));
		string_list.push_back(2003, new String("Some Name 4"));
		string_list.push_back(2222, new String("Some Name 5"));
		string_list.push_back(2222, new String("Some Name 6"));
		System.out.println("\nPrinting string_list after its creation ...\n");
		string_list.print();
		
		/**
		 * Test searching through the generic lists
		 */
		System.out.println();
		try_to_find(date_list, 2002);
		try_to_find(date_list, 6000);
		
		try_to_find(point_list, 2002);
		try_to_find(point_list, 6000);
		
		try_to_find(product_list, 2002);
		try_to_find(product_list, 6000);
		
		try_to_find(integer_list, 2002);
		try_to_find(integer_list, 6000);
		
		try_to_find(string_list, 2002);
		try_to_find(string_list, 6000);
		
		/**
		 * Test remove functionality of the generic lists
		 */
		date_list.remove(2992);
        date_list.remove(2000);
        date_list.remove(2001);
        System.out.println("\nPrinting date_list after a few removes ...\n");
        date_list.print();
        
		point_list.remove(2992);
        point_list.remove(2000);
        point_list.remove(2001);
        System.out.println("\nPrinting point_list after a few removes ...\n");
        point_list.print();
        
		product_list.remove(2992);
        product_list.remove(2000);
        product_list.remove(2001);
        System.out.println("\nPrinting product_list after a few removes ...\n");
        product_list.print();
        
		integer_list.remove(2992);
        integer_list.remove(2000);
        integer_list.remove(2001);
        System.out.println("\nPrinting integer_list after a few removes ...\n");
        integer_list.print();
        
		string_list.remove(2992);
        string_list.remove(2000);
        string_list.remove(2001);
        System.out.println("\nPrinting string_list after a few removes ...\n");
        string_list.print();
               
		System.out.println ( "\n***----Finished testing----------------***");
	}


	
	void print   (LinkedList<?> lt)
	{
		if (lt.size() == 0)
			System.out.println( "  list is EMPTY.\n");
		for (lt.go_to_first(); lt.cursor_ok(); lt.step_fwd()) {
			System.out.println(lt);
		}
	}

	 <ADa> void try_to_find(LinkedList<ADa>  lt, Integer key )
	{
		lt.find(key);
		if (lt.cursor_ok())

			System.out.println ("Found: " + lt );

		else
			System.out.println("Sorry, couldn't find key: " +  key + " in the table.\n");
	}

		

	public static void main(String [] args)
	{
		Demo5 d = new Demo5();
		d.lab5_tests();

	}
}




