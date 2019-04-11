// Ricardo Ramirez
// Linear Probing Hast Table

/**
 * Implements an unbalanced binary search tree.
 * @author Mark Allen Weiss - Source Code
 */

/**
 * Initially this code was created by Mark Allen Weiss, but my assignement consited
 * in modifing this code to extent new features. Originall
 */

public class LinearProbingHastTable<K,V> 
{
    /**
     * Construct the hash table.
     */
    public LinearProbingHastTable( )
    {
        this( DEFAULT_TABLE_SIZE );
    }

    /**
     * Construct the hash table.
     * @param size the approximate initial size.
     */
    public LinearProbingHastTable( int size )
    {
        allocateArray( size );
        doClear( );
    }

    /**
     * Insert into the hash table. If the item is
     * already present, do nothing.
     * @param value the item to insert.
     */
    public boolean insert( K key, V value)
    {

            // Insert value as active
        int currentPos = findPos( key );
        if( isActive( currentPos ) )
            return false;

        if( array[ currentPos ] == null )
            ++occupied;
        array[ currentPos ] = new Entry<>( key, value, true );
        theSize++;
        
            // Rehash; see Section 5.5
        if( occupied > array.length / 2 )
            rehash( );
        
        return true;
    }

    /**
     * Expand the hash table.
     */
    private void rehash( )
    {
        Entry<K,V> [ ] oldArray = array;

            // Create a new double-sized, empty table
        allocateArray( 2 * oldArray.length );
        occupied = 0;
        theSize = 0;

            // Copy table over
        for( Entry<K,V> entry : oldArray )
            if( entry != null && entry.isActive )
                insert( entry.keyVal, entry.element );
   }

    /**
     * Method that performs quadratic probing resolution.
     * @param value the item to search for.
     * @return the position where the search terminates.
     */
    private int findPos( K key )
    {
        int offset = 1;
        int currentPos = myhash( key );
        
        while( array[ currentPos ] != null &&
                !array[ currentPos ].keyVal.equals( key ) )
        {
            currentPos += offset;  // Compute ith probe
            //offset += 2;
            if( currentPos >= array.length )
                currentPos -= array.length;
        }
        
        return currentPos;
    }
    
    public int getHashValue( K key )
    {
    	return myhash( key );
    }
    
    public int getLocation( K key )
    {
    	int location = findPos( key );
    	if ( location >= 0 && location < array.length )
    		return location;
    	else return -1;
    }

    /**
     * delete from the hash table.
     * @param value the item to delete.
     * @return true if item deleted
     */
    public boolean delete( K key )
    {
        int currentPos = findPos( key );
        if( isActive( currentPos ) )
        {
            array[ currentPos ].isActive = false;
            theSize--;
            return true;
        }
        else
            return false;
    }
    
    /**
     * Get current size.
     * @return the size.
     */
    public int size( )
    {
        return theSize;
    }
    
    /**
     * Get length of internal table.
     * @return the size.
     */
    public int capacity( )
    {
        return array.length;
    }

    /**
     * Find an item in the hash table.
     * @param value the item to search for.
     * @return the matching item.
     */
    public boolean contains( K key )
    {
        int currentPos = findPos( key );
        return isActive( currentPos );
    }

    public V find( K key )
    {
    	V foundVal;
    	int currentPos = findPos( key );
    	if( isActive( currentPos ) )
        {
            foundVal = array[ currentPos ].element;
            return foundVal;
        }
    	return null;
    }
    /**
     * Return true if currentPos exists and is active.
     * @param currentPos the result of a call to findPos.
     * @return true if currentPos is active.
     */
    private boolean isActive( int currentPos )
    {
        return array[ currentPos ] != null && array[ currentPos ].isActive;
    }

    /**
     * Make the hash table logically empty.
     */
    public void makeEmpty( )
    {
        doClear( );
    }

    private void doClear( )
    {
        occupied = 0;
        for( int i = 0; i < array.length; i++ )
            array[ i ] = null;
    }
    
    private int myhash( K key )
    {
        int hashVal = key.hashCode( );

        hashVal %= array.length;
        if( hashVal < 0 )
            hashVal += array.length;

        return hashVal;
    }
    
    private static class Entry<K,V>
    {
    	public K  keyVal;    // the key
        public V  element;   // the element
        public boolean isActive;  // false if marked deleted

        public Entry( K k, V e )
        {
            this( k, e, true );
        }

        public Entry( K k, V e, boolean i )
        {
        	keyVal   = k;
            element  = e;
            isActive = i;
        }
    }

    private static final int DEFAULT_TABLE_SIZE = 10;

    private Entry<K, V> [ ] array; // The array of elements
    private int occupied;                 // The number of occupied cells
    private int theSize;                  // Current size

    /**
     * Internal method to allocate array.
     * @param arraySize the size of the array.
     */
    private void allocateArray( int arraySize )
    {
        array = new Entry[ arraySize ];
    }
    
    public String toString()
    {
    	String hashTable = "";
    	for (int i = 0; i != array.length; i++)
    	{
    		if ( array[i] != null)
    		{
    		String StringKey = "" + array[i].keyVal;
    		String StringValue = "" + array[i].element;
    		
    		hashTable += i + " " + StringKey + " " + StringValue + " ";

    		if (!array[i].isActive)
    			hashTable += "deleted";
			hashTable += "\n" + "";
			
    		}
    		else hashTable += i + "\n";

    	}
    	
    	return hashTable;
    }

    

    // Simple main
    public static void main( String [ ] args )
    {
    	// Creating Hash Table
    	// Key: String, Value: Integer
        LinearProbingHastTable<String, Integer> BankList = new LinearProbingHastTable<>( );
        
        // Insert Method
        System.out.printf("Insert Method: \n");

        BankList.insert("Robert", 13246795);
        BankList.insert("James", 46562132);
        BankList.insert("Bob", 46562165);
        BankList.insert("Mark", 5468798);
        BankList.insert("Omar", 16575465);
        
        System.out.printf(BankList.toString());
        System.out.printf("\n");

        // Rehash Method (Called implicitly by exceding limite of table)
        System.out.printf("Rehash Method (adding new memeber, Smith): ");
        
        BankList.insert("Smith", 13246795);

        System.out.printf(BankList.toString());
        System.out.printf("\n");

        // Find Method
        System.out.printf("Find Method (James): ");
        
        System.out.println(BankList.find("James"));
        System.out.printf("\n");

        // Delete Method
        System.out.printf("Delete Method (Smith): ");
        
        BankList.delete("Smith");
        
        System.out.printf(BankList.toString());
        System.out.printf("\n");

        // GetHashValue Method
        System.out.printf("Find getHashValue (Mark): ");
        
        System.out.println(BankList.getHashValue("Mark"));
        System.out.printf("\n");

        // GetLocation Method
        System.out.printf("Find getLocation (Mark): ");
        
        System.out.println(BankList.getLocation("Mark"));
        System.out.printf("\n");

        // Rehash Method (Called explicitly)
        System.out.printf("Rehash Method (Use directly and removing deleted members): ");

        BankList.rehash();
        
        System.out.printf(BankList.toString());

        
        
    }

}
