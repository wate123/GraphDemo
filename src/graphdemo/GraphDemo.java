package graphdemo;

/**
 * @file GraphTester.java
 * @author Duncan, Jun Lin
 * @date 99-99-9999
 * Description: Sample text-based menu-driven program to test
 * the weighted diagraph implementation.
 */

import java.io.*;
import java.util.*;
import java.util.function.Function;

public class GraphDemo
{
   public static final Double INFINITY = Double.MAX_VALUE;

   public static void main(String []args) throws GraphException
   {
      if (args.length != 1)
      {
         System.out.println("Usage: GraphDemo <filename>");
         System.exit(1);
      }
      City c1, c2;
      Scanner console;
      int menuReturnValue, i,j;
      Function<City,PrintStream> f = aCity -> System.out.printf("%-2d  %-30s%n",aCity.getKey(),aCity.getLabel().trim());      
      Graph<City> g = readGraph(args[0]);
      long s = g.size();
      menuReturnValue = -1;
      while (menuReturnValue != 0)
      {
         menuReturnValue = menu();
         switch(menuReturnValue)
         {
            case 1: //Adjacency Matrix
               System.out.println();
               System.out.println("Adjacency Matrix For The Graph In "+args[0]);
               System.out.println("=========================================================================================");
               
			   //write code to generate the adjacency matrix      
               
			   System.out.println("=========================================================================================");
               System.out.println();
               System.out.println();
               break;
            case 2: //Transitive Closure Matrix
               System.out.println("Transitive Closure Matrix For The Graph In "+args[0]);
               System.out.println("=========================================================================================");
			   
               //write code to generate the transitive closure matrix       
			   
               System.out.println("=========================================================================================");
               System.out.println();
               System.out.println();
               break;
            case 3://Shortest-path algorithm
               console = new Scanner(System.in);
               System.out.printf("Enter the source vertex: ");      
               int src = console.nextInt();
			   City srcCity = g.retrieveVertex(new City(source));
               System.out.printf("Enter the destination vertex: ");      
               int dest = console.nextInt();
			   City destCity = g.retrieveVertex(new City(dest));			   
               if (g.isReachable(srcCity,destCity))
               {
                  double totalDistance = 0;
				  
                  //Declare additional variables and arrays as necessary
                  
				  System.out.printf("Shortest route from %s to %s:%n",srcCity.getLabel(),destCity.getLabel());				   
                  System.out.println("=========================================================================================");
                  //Write code to call either dijkstra or floyd method and then     
				  //Give all intermediate connections beginning with the source city and ending
                  //with the destination city aligned in column format as indicated below:
                  //
                  //        cityX            ->             cityY           distance
                  //
                  //Display the intermediate distances one per line

                  System.out.println("=========================================================================================");	
                  System.out.printf("Total distance: %f miles.%n%n",totalDistance);                  
               }
               else
                  System.out.printf("There is no path.%n%n");
               break;
            case 4: //post-order depth-first-search traversal
               System.out.println();
               System.out.println("PostOrder DFS Traversal For The Graph In "+args[0]);
               System.out.println("==========================================================================");
               
			   //Call the dfsTraverse method
               
               System.out.println("==========================================================================");
               System.out.println();
               System.out.println();
               break;
            case 5: //breadth-first-search traversal
               System.out.println();
               System.out.println("BFS Traversal For The Graph In "+args[0]);
               System.out.println("==========================================================================");
               
			   //Call the bfsTraverse method
               
               System.out.println("==========================================================================");
               System.out.println();
               System.out.println();
               break;
            case 6: //number of edges
               System.out.println();
               System.out.println("The graph has "+g.countEdges()+".");
               System.out.println();
               break;                         
            case 7: //topoSort
               System.out.println();
               System.out.println("Topological Sorting of The Graph In "+args[0]);
               System.out.println("==========================================================================");
               int[] top = new int[(int)g.size()];
               topoSort(g,top);
               for (i=1; i<=g.size(); i++)
               {
                   c1 = g.retrieveVertex(new City(top[i-1]));
                   f.apply(c1);
               }
               System.out.println("==========================================================================");
               System.out.printf("%n%n");
               break;
            case 8: //primMST
               System.out.printf("Enter the root of the MST: ");
               console = new Scanner(System.in);
               j=console.nextInt();
               int[] mst = new int[(int)g.size()];
               double totalWeight = primMST(g,j,mst);
               for (i=1; i<=g.size(); i++)
                   System.out.printf("parent[%d] = %d%n",i,mst[i-1]);
               System.out.printf("The weight of the minimum spanning tree/forest is %.2f miles.%n%n",totalWeight);    
               break;
            default: ;
         } //end switch
      }//end while
   }//end main

   /**
    * This method reads a text file formatted as described in the project description.
    * @param filename the name of the DIMACS formatted graph file.
    * @return an instance of a graph.
    */
   private static Graph<City> readGraph(String filename)
   {
      try
      {
         Graph<City> newGraph = new Graph();
         try (FileReader reader = new FileReader(filename)) 
         {
            char temp;
            City c1, c2, aCity;
            String tmp;
            int k, m, v1, v2, j, size=0, nEdges=0;
            Integer key, v1Key,v2Key;
            Double weight;
            Scanner in = new Scanner(reader);
            while (in.hasNext())
            {
                tmp = in.next();
                temp = tmp.charAt(0);
                if (temp == 'p')
                {
                    size = in.nextInt();
                    nEdges = in.nextInt();
                }
                else if (temp == 'c')
                {
                    in.nextLine();
                }
                else if (temp == 'n')
                {
                    key = in.nextInt();
                    tmp = in.nextLine();
                    aCity = new City(key,tmp);
                    newGraph.insertVertex(aCity);
                }
                else if (temp == 'e')
                {
                    v1Key = in.nextInt();
                    v2Key = in.nextInt();
                    weight = in.nextDouble();
                    c1 = new City(v1Key);
                    c2 = new City(v2Key);
                    newGraph.insertEdge(c1,c2,weight);
                }
            }
         }
         return newGraph;
      }
      catch(IOException exception)
      {
            System.out.println("Error processing file: "+exception);
      }
      return null;
   } 

   /**
    * Display the menu interface for the application.
    * @return the menu option selected.
    */  
   private static int menu()
   {
      Scanner console = new Scanner(System.in);
      int option;
      do
      {
         System.out.println("  BASIC WEIGHTED GRAPH APPLICATION   ");
         System.out.println("=====================================");
         System.out.println("[1] Adjacency Matrix");
         System.out.println("[2] Transitive Matrix");
         System.out.println("[3] Single-source Shortest Path");
         System.out.println("[4] Postorder DFS Traversal");
         System.out.println("[5] BFS Traversal");
         System.out.println("[6] Number of Edges");
         System.out.println("[7] Topological Sort Labeling");
         System.out.println("[8] Prim's Minimal Spanning Tree");
         System.out.println("[0] Quit");
         System.out.println("=====================================");
         System.out.printf("Select an option: ");
         option = console.nextInt();
         if (option < 0 || option > 8)
         {
            System.out.println("Invalid option...Try again");
            System.out.println();
         }
         else
            return option;
      }while(true);
   }
   
   /**
    * This method computes the cost and path matrices using the 
    * Floyd all-pairs shortest path algorithm.
    * @param g an instance of a weighted directed graph.
    * @param dist a matrix containing distances between pairs of vertices.
    * @param path a matrix of intermediate vertices along the path between a pair
    * of vertices. 0 indicates that the two vertices are adjacent.
    * @return none.
    */
   private static void floyd(Graph<City> g, double dist[][], int path[][]) throws GraphException
   {
      //Implement this method or dijkstra method below (not both).
   }               
   
   /**
    * This method computes the cost and path arrays using the 
    * Dijkstra's single-source shortest path greedy algorithm.
    * @param g an instance of a weighted directed graph
    * @param dist an array containing shortest distances from a source vertex
    * @param pred an array containing predecessor vertices along the shortest path
    * @throws GraphException on call to retrieveEdge on non-existent edge
    */
   private static void dijkstra(Graph<City> g, double[] dist, int[] pred, int source, int destination) throws GraphException
   {
      //Implement this method or floyd method above (not both).   
   }   
   
   /**
    * an auxilliary method for the topoSort method.
    * @param g a weighted directed graph
    * @param v current vertex
    * @param seen a 1-D boolean matrix of vertices that
    * have been marked.
    * @param linearOrder a 1-D array containing the 
    * topologically sorted list.
    * @param index current index.
    */
   private static void dfsOut(Graph<City> g, int v, boolean seen[], int linearOrder[], int[] index) throws GraphException
   {
      //implement this method (for Project # 4)       
   }
   
   /**
    * This method generates a listing of the vertices of a weighted
    * directed graph using the reverse dfs topological sorting.
    * @param g a weighted directed graph
    * @param linearOrder an array in which the topologically sorted
    * list is stored.
    */
   private static void topoSort(Graph<City> g, int linearOrder[]) throws GraphException
   {
      //implement this method (for Project # 4)

   }

   /**
    * auxilliary data structure to store the information
    * about an edge for Prim's algorithm
    * 
    */
   private static class EdgeType implements Comparable<EdgeType>
   {
       /**
        * compares edge using weight as the primary key,
        * source vertex as the secondary key and destination
        * vertex as the tertiary key
        * @param another a reference to an edge
        * @return -1 when this edge comes before the specified
        * edge, 0 if the edges are identical, otherwise 1
        */
      public  int compareTo(EdgeType another)
      {
         if (weight < another.weight)
            return -1;
         if (weight > another.weight)
            return 1;
         if (source < another.source)
            return -1;
         if (source > another.source)
            return 1;
         if (destination < another.destination)
            return -1;
         if (destination > another.destination)
            return 1;
         return 0;
      }
      public double weight;
      public int source;
      public int destination;
      public boolean chosen;
   }

   /**
    * This method generates a minimum spanning tree rooted at a given
    * vertex, root. If no such MST exists, then it generates a minimum
    * spanning forest.
    * @param g a weighted directed graph
    * @param r root of the minimum spanning tree, when one exists.
    * @param parent the parent implementation of the minimum spanning tree/forest
    * @return the weight of such a tree or forest.
    * @throws GraphException when this graph is empty
    * <pre>
    * {@code
    * If a minimum spanning tree rooted at r is in the graph,
    * the parent implementation of a minimum spanning tree or forest is
    * determined. If no such tree exist, the parent implementation 
    * of an MSF is generated. If the tree is empty, an exception 
    * is generated.
    * }
    * </pre>
    */ 
   private static double  primMST(Graph<City> g, int root, int [] parent) throws GraphException
   {
      double totalWeight = 0;
      //implement this function : (for Project # 4)
      
	  return totalWeight;
   }            
}
