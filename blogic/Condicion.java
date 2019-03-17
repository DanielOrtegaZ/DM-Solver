/*
 * UNIVERSITY:  INSTITUTO POLITECNICO NACIONAL (IPN)
 *              ESCUELA SUPERIOR DE COMPUTO (ESCOM)
 *   SUBJECT:     _
 *   PROFESSOR:   _
 *
 * DESARROLLADORES: Martin Barrera
 *                  Daniel Ortega
 *
 * PRACTICE _:  TITULO DE LA PRACTICA
 *               - DESCRIPCION
 *		
*/

public class Condicion{
    
    /* ATRIBUTES */
    public static final int IGUAL = 0;
    public static final int MAYOR_IGUAL = 1;
    public static final int MENOR_IGUAL = 2;
    public static final int MAYOR = 3;
    public static final int MENOR = 4;
    
    public float coefx;
    public float coefy;
    public int relacion;
    public int solucion;
    
    /* CONSTRUCTORS */
    public Condicion(int coefx, int coefy, int rel, int sol){
        this.coefx = coefx;
        this.coefy = coefy;
        this.relacion = rel;
        this.solucion = sol;
    }
    
    /* PUBLIC METHODS */
    
    public String toString(){
        return coefx+"X + " + coefy + "y" + " "+relacionString()+" "+solucion;
    }
	
	/* PRIVATE METHODS */
	private String relacionString(){
	    
	    if(relacion == IGUAL)        return "=";
	    if(relacion == MAYOR_IGUAL)  return ">=";
	    if(relacion == MENOR_IGUAL)  return "<=";
	    if(relacion == MAYOR)        return ">";
	    else /*(relacion == MENOR)*/ return "<";
	    
	}
    
}
