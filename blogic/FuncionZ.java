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

public class FuncionZ{
    
    /* ATRIBUTES */
    public double coefx;
    public double coefy;
    
    /* CONSTRUCTORS */
    public FuncionZ(double coefx, double coefy){
        this.coefx = coefx;
        this.coefy = coefy;
    }
    
    /* PUBLIC METHODS */
	
    public String toString(){
        return coefx + "X + " + coefy + "y = z";
	}
	
	/* PRIVATE METHODS */
}
