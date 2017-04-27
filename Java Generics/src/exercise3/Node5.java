package exercise3;

class Node<ADa>
{
	Integer keyM;
	ADa itemM;
	Node<ADa> nextM;
	
	public Node()
	{
		keyM = null;
		itemM=  null; 
		nextM  = null;
	
	}
	public Node(ADa itemA, Integer keyA, Node<ADa> nextA)
	
	{
		itemM= itemA ;
		keyM = keyA;
		nextM = nextA;
	}

}
