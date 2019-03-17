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

public class Simplex1{
    
    /* ATRIBUTES */
    
    /* CONSTRUCTORS */
    
    /* PUBLIC METHODS */
    public void procesar(FuncionZ z,ArrayList<Condicion> condiciones){
    
        System.out.println("Funcion Z");
        System.out.println(z+"\n");
    
        System.out.println("Condiciones");
        int i;
        for(i=0; i<condiciones.size(); i++){
            System.out.println(condiciones.get(i));
        }
    }
	
	/* PRIVATE METHODS */
    
}
