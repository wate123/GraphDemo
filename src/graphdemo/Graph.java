package graphdemo;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * @param <E> the data type
 * @file Graph.java
 * @author Duncan, YOUR NAME
 * @date 99-99-9999
 * Description:This class implements a weighted digraph whose vertices
 * hold objects that implement the Comparable interface.
 */
public class Graph<E extends Comparable<E>> implements GraphAPI<E>
{
    /*
     * number of vertices (size of this graph)
     */
    private long order;
    /**
     * pointer to the list of vertices
     */
    private Vertex first;
   
   /**
    * A vertex of a graph stores a data item and references
    * to its edge list and the succeeding vertex. The data
    * object extends the comparable interface.
    */      
   private class Vertex
   {
       /**
        * pointer to the next vertex
        */
       public Vertex pNextVertex;
       /**
        * the data item
        */
       public E data;
       /**
        * indegree
        */
       public long inDeg;
       /**
        * outdegree
        */
       public long outDeg;
       /**
        * pointer to the edge list
        */
      public Edge pEdge;
      /**
       * Field for tracking vertex accesses
       */
      public long processed;
   }
   
   /** 
    * An edge of a graph contains a reference to the destination
    * vertex, a reference to the succeeding edge in the edge list and
    * the weight of the directed edge.
    */   
   private class Edge
   {
       /**
        * pointer to the destination vertex
        */
       public Vertex destination;
       /**
        * weight on this edge
        */
       public Double weight;
       /**
        * pointer to the next edge
        */
       public Edge pNextEdge;
   }   
   /**
    * Constructs an empty weighted directed graph
   */
   public Graph()
   {
      first = null;
      order = 0;
   }

   @Override
   public void insertVertex(E obj)
   {
      Vertex newPtr;
      Vertex locPtr;
      Vertex predPtr;

      newPtr = new Vertex();
      newPtr.pNextVertex = null;
      newPtr.data  = obj;
      newPtr.inDeg = 0;    
      newPtr.outDeg = 0;    
      newPtr.processed = 0;
      newPtr.pEdge = null;

      locPtr = first;
      predPtr = null;
      while (locPtr != null && obj.compareTo(locPtr.data) > 0)
      {
         predPtr = locPtr;
         locPtr = locPtr.pNextVertex;
      }
      /*key already exist. */
      if (locPtr != null && obj.compareTo(locPtr.data)==0)
      {
         locPtr.data = obj;
         return;
      }
      /* insert before first vertex */
      if (predPtr == null)
         first = newPtr;
      else
         predPtr.pNextVertex = newPtr;
      newPtr.pNextVertex = locPtr;   
      order++;
   }

   @Override
   public void deleteVertex(E key)
   {
      Vertex predPtr;
      Vertex walkPtr;
      if (isEmpty())
         return;
      predPtr = null;
      walkPtr = first;
      while (walkPtr != null && key.compareTo(walkPtr.data) > 0)
      {
         predPtr = walkPtr;
         walkPtr = walkPtr.pNextVertex;
      }
      if (walkPtr == null || key.compareTo(walkPtr.data) != 0)
         return;
      /* vertex found. Test degree */
      if ((walkPtr.inDeg > 0) || (walkPtr.outDeg > 0))
         return;
      /* OK to delete */
      if (predPtr == null)
         first = walkPtr.pNextVertex;
      else
         predPtr.pNextVertex = walkPtr.pNextVertex;
      order--;
   }

   @Override
   public void insertEdge(E fromKey, E toKey, Double weight)
   {
      Edge tmpEdge;
      Edge newEdge;
      Edge pred;
      Vertex tmpFrom;
      Vertex tmpTo;
      
      if (isEmpty())
         return;
      newEdge = new Edge();
      /* check whether originating vertex exists */
      tmpFrom = first;
      while (tmpFrom != null && fromKey.compareTo(tmpFrom.data) > 0)
         tmpFrom = tmpFrom.pNextVertex;
      if (tmpFrom == null || fromKey.compareTo(tmpFrom.data) != 0)
         return;
      /* locate destination vertex */
      tmpTo = first;
      while (tmpTo != null && toKey.compareTo(tmpTo.data) > 0)
         tmpTo = tmpTo.pNextVertex;
      if (tmpTo == null || toKey.compareTo(tmpTo.data) != 0)
         return;
      /*check if edge already exists */
      tmpEdge = tmpFrom.pEdge;
      while (tmpEdge != null && tmpEdge.destination != tmpTo)
         tmpEdge = tmpEdge.pNextEdge;
      if (tmpEdge != null && tmpEdge.destination != null)
         return;
      tmpFrom.outDeg++;
      tmpTo.inDeg++;
      newEdge.destination = tmpTo;
      newEdge.weight = weight;
      newEdge.pNextEdge = null;
      if (tmpFrom.pEdge == null)
      {
         tmpFrom.pEdge = newEdge;
         return;
      }
      pred = null;
      tmpEdge = tmpFrom.pEdge;
      while (tmpEdge != null && tmpEdge.destination != tmpTo)
      {
         pred = tmpEdge;
         tmpEdge = tmpEdge.pNextEdge;
      }
      if (pred == null)
         tmpFrom.pEdge = newEdge;
      else
         pred.pNextEdge = newEdge;
      newEdge.pNextEdge = tmpEdge;
   }         

   @Override
   public void deleteEdge(E fromKey, E toKey)
   {
      Edge tmpEdge;
      Edge newEdge;
      Edge pred;
      Vertex tmpFrom;
      Vertex tmpTo;
      newEdge = new Edge();
      /* find source vertex */
      tmpFrom = first;
      while (tmpFrom != null && fromKey.compareTo(tmpFrom.data)>0)
         tmpFrom = tmpFrom.pNextVertex;
      if (tmpFrom == null || fromKey.compareTo(tmpFrom.data) != 0)
         return;
      /* locate destination vertex */
      tmpTo = first;
      while (tmpTo != null && toKey.compareTo(tmpTo.data)>0)
         tmpTo = tmpTo.pNextVertex;
      if (tmpTo == null || toKey.compareTo(tmpTo.data) != 0)
         return;
      /*check if edge does not exist */
      tmpEdge = tmpFrom.pEdge;
      pred = null;
      while (tmpEdge != null && tmpEdge.destination != tmpTo)
      {
         pred = tmpEdge;
         tmpEdge = tmpEdge.pNextEdge;
      }
      /* if edge does not exist */
      if (tmpEdge == null)
         return;
      /* update degrees */
      if (pred != null)
          pred.pNextEdge = tmpEdge.pNextEdge;
      tmpFrom.outDeg--;
      tmpTo.inDeg--;
   }

   @Override
   public Double retrieveEdge(E fromKey, E toKey) throws GraphException
   {
      Edge tmpEdge;
      Edge newEdge;
      Edge pred;
      Vertex tmpFrom;
      Vertex tmpTo;
      newEdge = new Edge();
      /* find source vertex */
      tmpFrom = first;
      while (tmpFrom != null && fromKey.compareTo(tmpFrom.data)>0)
         tmpFrom = tmpFrom.pNextVertex;
      if (tmpFrom == null || fromKey.compareTo(tmpFrom.data) != 0)
         throw new GraphException("Non-existent edge - retrieveEdge().");
      /* locate destination vertex */
      tmpTo = first;
      while (tmpTo != null && toKey.compareTo(tmpTo.data)>0)
         tmpTo = tmpTo.pNextVertex;
      if (tmpTo == null || toKey.compareTo(tmpTo.data) != 0)
         throw new GraphException("Non-existent edge - retrieveEdge().");
      /*check if edge does not exist */
      tmpEdge = tmpFrom.pEdge;
      pred = null;
      while (tmpEdge != null && tmpEdge.destination != tmpTo)
      {
         pred = tmpEdge;
         tmpEdge = tmpEdge.pNextEdge;
      }
      /* if edge does not exist */
      if (tmpEdge == null)
         throw new GraphException("Non-existent edge - retrieveEdge().");
      return tmpEdge.weight;
   }

   @Override
   public E retrieveVertex(E key) throws GraphException
   {
      Vertex tmp;
      if (isEmpty())
         throw new GraphException("Non-existent vertex - retrieveVertex().");
      tmp = first;
      while (tmp != null && key.compareTo(tmp.data) != 0)
         tmp = tmp.pNextVertex;
      if (tmp == null)
         throw new GraphException("Non-existent vertex - retrieveVertex().");
      return tmp.data;
   }

   @Override
   public void bfsTraverse(Function func)
   {
      if (isEmpty())
         return;
      Vertex walkPtr = first;
      while (walkPtr != null)
      {
         walkPtr.processed = 0;
         walkPtr = walkPtr.pNextVertex;
      }
      ArrayList<Vertex> queue = new ArrayList();
      Vertex toPtr;
      Edge edgeWalk;
      Vertex tmp;      
      walkPtr = first;
      while (walkPtr != null)
      {
         if (walkPtr.processed < 2)
         {
            if (walkPtr.processed < 1)
            {
               queue.add(walkPtr);
               walkPtr.processed = 1;
            }
         }
         while (!queue.isEmpty())
         {
            tmp = queue.remove(0);
            func.apply(tmp.data);
            tmp.processed = 2;
            edgeWalk = tmp.pEdge;
            while (edgeWalk != null)
            {
               toPtr = edgeWalk.destination;
               if (toPtr.processed == 0)
               {
                  toPtr.processed = 1;
                  queue.add(toPtr);
               }
               edgeWalk = edgeWalk.pNextEdge;
            }            
         }
         walkPtr = walkPtr.pNextVertex;      
      }
   }
   
   @Override
   public void dfsTraverse(Function func)
   {
      if (isEmpty())
         return;
      Vertex walkPtr = first;
      while(walkPtr != null)
      {
         walkPtr.processed = 0;
         walkPtr = walkPtr.pNextVertex;
      }
      ArrayList<Vertex> stack = new ArrayList();
      Vertex toPtr;      
      Edge edgeWalk;
      Vertex tmp;      
      walkPtr = first;
      while (walkPtr != null)
      {
         if (walkPtr.processed < 2)
         {
            if (walkPtr.processed < 1)
            {
               walkPtr.processed = 1;
               stack.add(0,walkPtr);
            }
            while (!stack.isEmpty())
            {
                tmp = stack.get(0);
                edgeWalk = tmp.pEdge;
                while(edgeWalk != null)
                {
                   toPtr = edgeWalk.destination;
                   if (toPtr.processed == 0)
                   {
                      toPtr.processed = 1;
                      stack.add(0,toPtr);
                      edgeWalk = toPtr.pEdge;
                   }
                   else
                      edgeWalk = edgeWalk.pNextEdge;
                }
                tmp = stack.remove(0);
                func.apply(tmp.data);
                tmp.processed = 2;
            }
         }
         walkPtr = walkPtr.pNextVertex;
      }            
   }

   @Override
   public boolean isEmpty()
   {
      return first == null;
   }

   @Override
   public long size()
   {
      return order;
   }

   @Override
   public boolean isVertex(E key)
   {
      Vertex tmp;
      if (isEmpty())
         return false;
      tmp = first;
      while (tmp != null && key.compareTo(tmp.data) != 0)
         tmp = tmp.pNextVertex;
      return tmp != null;      
   }

   /*--------------------Begin Code Augmentation ---------------*/
   @Override
   public boolean isEdge(E fromKey, E toKey)
   {
      try{
         retrieveEdge(fromKey, toKey);
         return true;
      }catch(GraphException e){
         return false;
      }
   }

   @Override
   public boolean isReachable(E fromKey, E toKey)
   {
      if(isEmpty())
         return false;
      if(fromKey.compareTo(toKey)==0)
         return true;
      Vertex vtmp = first;
      while(vtmp.data.compareTo(fromKey)!=0){
         vtmp = vtmp.pNextVertex;
         if(vtmp==null)
            return false;
      }
      ArrayList<Vertex> queue = new ArrayList();
      queue.add(vtmp);
      Edge etmp;
      int to = 1, toLater = 0;
      for(int iteration = 0; iteration < order; iteration++){
         while(to != 0){
            etmp = queue.remove(0).pEdge;
            to--;
            while(etmp!=null){
               if(etmp.destination.data.compareTo(toKey)==0)
                  return true;
               queue.add(etmp.destination);
               toLater++;
               etmp = etmp.pNextEdge;
            }
         }
         to+=toLater;
         toLater = 0;
      }
      return false;
   }

   @Override
   public long countEdges()
   {
      Vertex tmp = first;
      int s = 0;
      while(tmp!=null){
         s+=tmp.outDeg;
         tmp = tmp.pNextVertex;
      }
      return s;
   }
   /*--------------------End Code Augmentation ---------------*/

   @Override
   public long outDegree(E key) throws GraphException
   {
      Vertex tmp;
      if (isEmpty())
         throw new GraphException("Non-existent vertex - outDegree().");
      tmp = first;
      while (tmp != null && key.compareTo(tmp.data) != 0)
         tmp = tmp.pNextVertex;
      if (tmp == null)
         throw new GraphException("Non-existent vertex - outDegree().");
      return tmp.outDeg;
   }

   @Override
   public long inDegree(E key) throws GraphException
   {
      Vertex tmp;
      if (isEmpty())
         throw new GraphException("Non-existent vertex - inDegree().");
      tmp = first;
      while (tmp != null && key.compareTo(tmp.data) != 0)
         tmp = tmp.pNextVertex;
      if (tmp == null)
         throw new GraphException("Non-existent vertex - inDegree().");
      return tmp.inDeg;
   }    
}
