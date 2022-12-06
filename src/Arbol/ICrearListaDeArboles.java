package Arbol;

import java.util.HashMap;
import java.util.List;
import Nodo.NodoArbol;

public interface ICrearListaDeArboles {

	/**
	 * Nos va a permitir mediante una tabla de frecuencias, generar una lista de nodos, en la que cada nodo tendra un caracter con su frecuencia correspondiente.
	 * @param mapTablaHuffman
	 * @return
	 */
	public List<NodoArbol> crearListaArboles(HashMap<Character,Integer> mapTablaHuffman);

	/**
	 * Genera un arbol de Huffman recibiendo una lista de nodos, donde cada nodo contiene su caracter y su frecuencia correspondiente
	 * @param listaArbol
	 */
	public void crearArbolHuffman(List<NodoArbol> listaArbol);

	/**
	 * Con este metodo podremos comenzar a asignar los codigos correspondientes a cada caracter dependiendo de su ubicacion en el arbol de huffman
	 * @param nodoArbol
	 * @param codigo
	 */
	public void generarCodigos(NodoArbol nodoArbol,String codigo);

}
