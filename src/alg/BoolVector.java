package alg;

public class BoolVector {
	long[] payload;
	
	
	public BoolVector( int size ) {
	    payload = new long[size];
	}
	
	int noBits() {
	    return payload.length*64;
	}
	
	void set( int index, boolean value ) {
		int i = index/64;
		int j = index%64;
		long chunk = payload[i];
		long singleBit = (1L << j);
		if( value && (chunk & singleBit) == 0L )
			payload[i] = chunk | singleBit;
		else if( !value && (chunk & singleBit) != 0L )
			payload[i] = chunk & ~singleBit;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for( int i = 0; i < payload.length; i++ ) {
			for (int j = 0; j < 64; j++) {
				if( ((payload[i] >> j) & 1L) == 1L )
					sb.append("1");
				else
					sb.append('0');
			}
		}
		return sb.toString();
	}
	
	static enum Oper { AND, OR, NOT }
	
	private static long apply( long x, long y, Oper o ) {
	    switch (o) {
	    case AND:
	        return x & y;
	    case OR:
	        return x | y;
	    case NOT:
	        return ~x ;
	    }
	    throw new AssertionError("impossible");
	}
	
	private static BoolVector apply( BoolVector x, BoolVector y, Oper o ) {
	    if( x.payload.length != y.payload.length )
	        throw new AssertionError("size mismatch");
	    BoolVector ret = new BoolVector(x.payload.length);
	    for (int i = 0; i < x.payload.length; i++) {
            ret.payload[i] = apply(x.payload[i],y.payload[i],o);
        }
	    return ret;
	}
	
	public static BoolVector and( BoolVector x, BoolVector y ) {
	    return apply(x,y,Oper.AND);
	}
    public static BoolVector or( BoolVector x, BoolVector y ) {
        return apply(x,y,Oper.OR);
    }
    public static BoolVector not( BoolVector x ) {
        return apply(x,x,Oper.NOT);
    }
	
	public static void main( String[] args ) {
	    int size = 2;
		BoolVector x = new BoolVector(size);
		for( int i = 0; i < x.noBits(); i++ ) {
		    if( i%2 == 0 )
		        x.set(i, true);
        }	
        BoolVector y = new BoolVector(size);
        for( int i = 0; i < y.noBits(); i++ ) {
            if( i%3 == 0 )
                y.set(i, true);
        }   
		System.out.println(x ); 
        System.out.println(y ); 
        System.out.println("------------"); 
        System.out.println( not(x) ); 
        System.out.println( and(x,y) ); 
        System.out.println( or(x,y) ); 
    }

	
}
