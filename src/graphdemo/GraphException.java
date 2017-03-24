package graphdemo;

/**
 * @file GraphException.java
 * @author Duncan
 * @date 99-99-9999
 * Description: This class reports an exception in a graph method.
 */
public class GraphException extends Exception 
{

    /**
     * Creates a new instance of <code>GraphException</code> without detail
     * message.
     */
    public GraphException() 
    {
        super();
    }

    /**
     * Constructs an instance of <code>GraphException</code> with the specified
     * detail message.
     * @param msg the detail message.
     */
    public GraphException(String msg) 
    {
        super(msg);
    }
}
