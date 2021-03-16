package project2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class executionClass {

	static int[] checKArray;
	static int[][] edgeArray;
	static int[] distArray;
	static int size;
	static String source;
	static int vertChse;
	static List<String> textLines ;
	static int minimum ;
	
	static HashMap<String,Integer> vertMap = new HashMap<String,Integer>();
	static HashMap<Integer,Integer> parentTrack = new HashMap<Integer,Integer>();
	static HashMap<Integer,String> vertTrack = new HashMap<Integer,String>();
	
	public static void main(String[] args) throws IOException 
	{
		constructGraph();
	}
	// Constructs graph
	public static void constructGraph() throws IOException
	{
		String FilePath = "G:\\USA\\UNCC\\Sem-1\\Algorithms and Data Structure\\Project-2\\texting_3.txt";
		
		textLines = Files.readAllLines(Paths.get(FilePath), java.nio.charset.StandardCharsets.UTF_8);
		
		int read=0;
		int edges;
		String type;
		
		readVertics();
		
		String lineString = textLines.get(read);
		String gap = "[ ]+";
		String[] values = lineString.split(gap);
		
		size = Integer.parseInt(values[0]);
		edges = Integer.parseInt(values[1]);
		type = values[2];
		
		checKArray=new int[size];
		edgeArray=new int[size][size];
		distArray=new int[size];
		
		read++;
		
		while(read<=edges)
		{
			lineString = textLines.get(read);
			values = lineString.split(gap);
			edgeArray[vertMap.get(values[0])][vertMap.get(values[1])] = Integer.parseInt(values[2]);
			
			if(type.equals("U") || type.equals("u"))
			{
				edgeArray[vertMap.get(values[1])][vertMap.get(values[0])] = Integer.parseInt(values[2]);
			}
			
			read++;
		}
		if((read+1) == textLines.size())
		{
			lineString = textLines.get(read);
			values = lineString.split(gap);
			source = values[0];
			
			initializeVerticesDist();
			printLowPathCost();
		}
		else
		{
		    Set vertSet = vertMap.entrySet();
		      
		    Iterator ite = vertSet.iterator();
		      
		     while(ite.hasNext()) 
		     {
		         Map.Entry elem = (Map.Entry)ite.next();
		         source = (String) elem.getKey();
				 initilizeCheckArray();
				 initializeVerticesDist();
				 printLowPathCost();
				 parentTrack.clear();
		     }
		}  
	}
	// Vertices HashMap is created for further usage
	static void readVertics()
	{
		int vertNum=1;
		String lnStrng;
		String gap = "[ ]+";
		String[] values;
		int pos=0;
		
		while(vertNum<textLines.size())
		{
			lnStrng = textLines.get(vertNum);
			values = lnStrng.split(gap);
			
			for (int i = 0; i < (values.length-1); i++)
			{
			    if(!vertMap.containsKey(values[i]))
			    {
			    	vertMap.put(values[i],pos);
			    	vertTrack.put(pos,values[i]);
			    	pos++;
			    }
			}
			vertNum++;
		}
	}
	// Initialize the distances between the vertices 
	static void initializeVerticesDist()
	{
		for(int count=0;count<size;count++)
		{
			distArray[count] = Integer.MAX_VALUE;
		}
		distArray[vertMap.get(source)] = 0;
		parentTrack.put(vertMap.get(source),-1);
		
		startDiJkstraAlgo();
	}
	//Dijkstra Algorithm
	static void startDiJkstraAlgo()
	{
		int Parent;
		for (int count = 0; count < size; count++) 
		{  
			vertChse = chseMinVert(); 
			
			if(minimum == Integer.MAX_VALUE)
			{
				break;
			}
			
			Parent = vertChse;
			
			for (int count1 = 0; count1 < size; count1++) 
			{
				if(checKArray[count1] == 0 && edgeArray[vertChse][count1]!=0 && distArray[vertChse]+edgeArray[vertChse][count1] < distArray[count1])
				{
					distArray[count1] = distArray[vertChse] + edgeArray[vertChse][count1];
					parentTrack.put(count1,Parent);
				}
			}
		}
	}
	// Chooses the minimum vertex
	static int chseMinVert()
	{
		minimum = Integer.MAX_VALUE;
		int minIndex=-1; 

		for (int count= 0; count < size; count++) 
		{
			if (checKArray[count] == 0 && distArray[count] <= minimum) 
			{ 
				minimum = distArray[count]; 
				minIndex = count; 
			} 
		}
		checKArray[minIndex] = 1;
		return minIndex;
	}
	//Initialize the Array
	static void initilizeCheckArray()
	{
		for(int count=0;count<size;count++)
		{
			checKArray[count] = 0;
		}
	}
	//Print the final path
	static void printLowPathCost() 
	{ 
      Set parentSet = parentTrack.entrySet();
      
      Iterator ite = parentSet.iterator();
      
      int parentValue=0;
      int keyValue=0;
      
      System.out.println("Path for Lowest Cost from Source "+vertTrack.get(vertMap.get(source)) + " to ");
      while(ite.hasNext()) 
      {
    	 Map.Entry parentMap_1 = (Map.Entry)ite.next();
         
         parentValue= (int)parentMap_1.getValue();
         keyValue = (int)parentMap_1.getKey();
         
         System.out.print("Vertex "+vertTrack.get(keyValue)+" ---- "+vertTrack.get(keyValue));
         while( parentValue>=0)
         {
        	 System.out.print("<-"+vertTrack.get(parentValue));
        	 parentValue = (int)parentTrack.get(parentValue);
         }
         System.out.println(" \t Path Cost(lowest) - "+distArray[keyValue]);
      }
	} 
}
