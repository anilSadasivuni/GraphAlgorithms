import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Prims {
	static int[] checKArray;
	static int[][] edgeArray;
	static int[] distArray;
	static int size;
	static String source;
	static int vertChse;
	static List<String> textLines;
	static int minimum;
	
	static HashMap<String,Integer> vertMap = new HashMap<String,Integer>();
	static HashMap<Integer,Integer> parentTrack = new HashMap<Integer,Integer>();
	static HashMap<Integer,String> vertTrack = new HashMap<Integer,String>();
	
	public static void main(String[] args) throws IOException 
	{
		constructGraph();
		primMST(parentTrack,size,edgeArray);	
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
		if((read+1) != textLines.size())
		{
			read--;
		}
		lineString = textLines.get(read);
		values = lineString.split(gap);
		source = values[0];
		
		initializeVerticesDist();
		
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
			
	}
	// Prims Algorithm
	static void primMST(HashMap parents,int n,int edges[][]) {
		for(int i=0;i<n;i++) {
			int min=chseMinVert();
			for(int j=0;j<n;j++) {
				if(edgeArray[min][j]!=0 && checKArray[j]==0 && 
						edgeArray[min][j] < distArray[j]) {
						parentTrack.put(j, min);
						distArray[j]=edgeArray[min][j];
				}
			}
			
		}
		printMST(parentTrack, n, distArray);
	}
	// Prints all the edges and their cost.
	static void printMST(HashMap parents, int n, int distArray[]) {
		int totalCost=0;
		System.out.println("Edge \t Weight");
		for(int i=0;i<n;i++) {
			if(null!=parents.get(i) && null!=vertTrack.get(parents.get(i))) {
				totalCost=totalCost+distArray[i];
				System.out.println(vertTrack.get(i)+" ----- "+vertTrack.get(parents.get(i))+" Distance is "+distArray[i]);			
			}
		}
		System.out.println("Total Cost of the MST is "+totalCost);
	}
	// Initialize the array to check whether node is visited or not.
	static void initilizeCheckArray()
	{
		for(int count=0;count<size;count++)
		{
			checKArray[count] = 0;
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
}
