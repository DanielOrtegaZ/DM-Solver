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

import java.util.ArrayList;

public class DMSolver{
    
    /* ATRIBUTES */
    
    /* CONSTRUCTORS */
    
    /* PUBLIC METHODS */
	
	/* PRIVATE METHODS */
    
    public static void main(String[] args) {
    
        System.out.println("Iniciando Simplex\n\n");
        
        // Creando el objeto Simplex
        Simplex1 simplex = new Simplex1();
        FuncionZ z = new FuncionZ(1, 2);
        
        // Creando las condiciones y arreglo de condiciones
        ArrayList<Condicion> condiciones = new ArrayList<Condicion>();
        Condicion c1 = new Condicion(1,Condicion.MAYOR,1,12);
        
        // Adding to Array of Condiciones
        condiciones.add( c1 );
        
        simplex.procesar(z,condiciones);
    }
}
