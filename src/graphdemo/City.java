package graphdemo;

/**
 * @file City.java
 * @author Duncan
 * @date 99-99-9999
 * Description: data type of items to be inserted in the weighted digraph.
 */
public class City implements Comparable<City>
{
    /**
     * label (name) of the city
     */
    private String label;
    /**
     * unique integer code/identifier for this city
     */
    private Integer key;
   
    /**
     * Creates a city with the specified code and label
     * @param aKey unique code for the city
     * @param aLabel label for this city
     */
   public City(Integer aKey, String aLabel)
   {
      label = aLabel;
      key = aKey;
   }

   /**
    * Creates an anonymous city with the specified code
    * @param aKey code for the city
    */
   public City(Integer aKey)
   {
      label = " ";
      key = aKey;
   }

   /**
    * Gives the label associated with this city
    * @return the label of this city
    */
   public String getLabel()
   {
      return label;
   }
   
   /**
    * Gives the unique code for this city
    * @return the code for this city
    */
   public Integer getKey()
   {
      return key;
   }

   /**
    * Compares the code for two cities
    * @param another a reference to a city
    * @return -1 when the key for this city comes after the 
    * specified city, 0 when the keys are identical, otherwise, 1.
    */
   public int compareTo(City another)
   {
      return key.compareTo(another.key);
   }
}
