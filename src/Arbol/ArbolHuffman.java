package Arbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Nodo.NodoArbol;

public class ArbolHuffman implements ICrearListaDeArboles {

	private static NodoArbol raiz;
	private HashMap<Character, String> tablaHasHuffmanCodigos;

	@Override
	public List<NodoArbol> crearListaArboles(HashMap<Character, Integer> tablaFrecuencias) {
		List<NodoArbol> nodosArbol = new ArrayList<NodoArbol>();
		int pos = 0;
		for (Character key : tablaFrecuencias.keySet()) {
			if (key > 0) {
				pos++;
				NodoArbol nodo = new NodoArbol( key, tablaFrecuencias.get(key));
				nodosArbol.add(nodo);
			}
		}
		return nodosArbol;
	}

	@Override
	public void crearArbolHuffman(List<NodoArbol> listaArbol) {
		List<NodoArbol> listaArbolAux = new ArrayList<NodoArbol>();

		if (listaArbol != null && listaArbol.size() > 1) {
			int i = 0;
			for (; i < listaArbol.size(); i = i + 2) {
				NodoArbol nodo = null;
				if (i + 1 < listaArbol.size()) {
					nodo = new NodoArbol('*',listaArbol.get(i).getFrecuencia() + listaArbol.get(i + 1).getFrecuencia());
					nodo.setIzquierda(listaArbol.get(i));
					nodo.setDerecha(listaArbol.get(i + 1));
				} else {
					nodo =listaArbol.get(i);
				}
				listaArbolAux.add(nodo);
			}
			crearArbolHuffman(listaArbolAux);
		} else
			this.raiz = listaArbol.get(0);
	}

	@Override
	public void generarCodigos(NodoArbol nodoArbol, String codigo) {

		if (nodoArbol.getIzquierda() == null && nodoArbol.getDerecha() == null && nodoArbol.getClave() != 0)
			tablaHasHuffmanCodigos.put( nodoArbol.getClave(), codigo);
		if (nodoArbol.getIzquierda() != null)
			generarCodigos(nodoArbol.getIzquierda(), codigo + 0);
		if (nodoArbol.getDerecha() != null)
			generarCodigos(nodoArbol.getDerecha(), codigo + 1);
	}

	/**
	 * Este metodo sirve para enviar una tabla de codigos previamente llenada de un objeto tipo ArbolHuffman.
	 * @return
	 */
	public HashMap<Character, String> getTablaHasHuffmanCodigos() {
		return this.tablaHasHuffmanCodigos;
	}

	/**
	 * Este metodo recibe una tabla de codigos vacia que sera almacenada en un objeto de tipo ArbolHuffman.
	 * @param tableHashCodigos
	 */
	public void setTablaHasHuffmanCodigos(HashMap<Character, String> tableHashCodigos) {
		this.tablaHasHuffmanCodigos = tableHashCodigos;
	}

	/**
	 * Nos va permitir devolver un arbol de huffman previamente generado.
	 * @return
	 */
	public NodoArbol getRaiz() {
		return raiz;
	}
}
