package Archivo;

import java.util.List;

import Nodo.NodoArbol;

public interface ICarfarTablaHuffman {

	/**
	 * Con el nombre del archivo cargamos la tabla de frecuencias.
	 * @param nombreArchivo
	 */
	public void cargarTablaFrecuencias(String nombreArchivo);

	/**
	 * Con el nombre del archivo podremos cargar una tabla de codigos para luego poder descomprimir el archivo correspondiente.
	 * @param nombreArchivo
	 */
	public void cargarTablaCaracteres(String nombreArchivo);
	public List<NodoArbol> leerTablaHuffman();
	public void comprimir(String nomArchivo,String nomArchivoDestino);
	public void descomprimir(String nomArchivo,String nomArchivoDestino);
}
