package Huffman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Arbol.ArbolHuffman;
import Archivo.AdmArchivo;

public class Main {
	
	public static Scanner scr = new Scanner(System.in);
	private static ArbolHuffman arbol;
	private static AdmArchivo admArchivo;
	private static String NOMBRE_ARCHIVO = "src/Archivos/";
	private static String NOMBRE_ARCHIVO_TABLA;
	private static String NOMBRE_ARCHIVO_DESCOMPRIMIR= "src/Archivos/";
	private static Double premios = 0.0;
	private static Double recaudaciones = 0.0;
	
	public static void main(String[] args) {

		System.out.println("Bienvenido");
		menu();

	}
	public static void menu() {

		System.out.println("Ingrese la opcion numerica que desee:\n1 --> Comprimir.\n2 --> Descomprimir.\n3 --> Salir.");
		String opcion = scr.nextLine();

		int cont = 2;
		while (cont > 1) {
			if (opcion.equals("1")) {

				System.out.println("Ingrese el nombre el archivo que desea comprimir.");
				String nombreArchivo = scr.nextLine();
				menuCompresion(nombreArchivo);
				NOMBRE_ARCHIVO = "src/Archivos/";

				menu();
				opcion = "3";
			} else if (opcion.equals("2")) {

				System.out.println("Ingrese el nombre el archivo que desea descomprimir.");
				String nombreArchivo = scr.nextLine();
				menuDescompresion(nombreArchivo);
				NOMBRE_ARCHIVO = "src/Archivos/";
				NOMBRE_ARCHIVO_TABLA = "";
				NOMBRE_ARCHIVO_DESCOMPRIMIR="src/Archivos/";

				if (recaudaciones > premios) {
					System.out.println("Desea generar un archivo con los datos de recaudacion y premios?\n1 --> Si.\n2 --> No.");
					String resp = scr.nextLine();
					if (resp.equals("1")) {
						try {
							generarArchivoSalida(recaudaciones, premios, recaudaciones - premios);
						} catch (Exception e) {
							e.getMessage();
						}
					}
				}

				menu();
				opcion = "3";
			} else if (opcion.equals("3")) {
				cont = 1;
			} else {
				System.out.println("Opcion incorrecta, ingrese el numero correcto:\n1 --> Comprimir.\n2 --> Descomprimir.\n3 --> Salir.");
				opcion = scr.nextLine();
			}
		}
	}
	public static void menuCompresion(String nombreArchivo) {

		NOMBRE_ARCHIVO = NOMBRE_ARCHIVO + nombreArchivo;
		admArchivo= new AdmArchivo();
		arbol= new ArbolHuffman();

		admArchivo.cargarTablaFrecuencias(NOMBRE_ARCHIVO);
		arbol.setTablaHasHuffmanCodigos(admArchivo.getTablaHasHuffmanCodigos());
		arbol.crearArbolHuffman(arbol.crearListaArboles(admArchivo.getTablaFrecuencias()));
		arbol.generarCodigos(arbol.getRaiz(),"");

		admArchivo.comprimir(NOMBRE_ARCHIVO, NOMBRE_ARCHIVO + ".compress");

	}
	public static void menuDescompresion(String nombreArchivo) {

		NOMBRE_ARCHIVO = NOMBRE_ARCHIVO + nombreArchivo;
		NOMBRE_ARCHIVO_TABLA = NOMBRE_ARCHIVO + ".codigos";
		admArchivo = new AdmArchivo();
		arbol = new ArbolHuffman();

		admArchivo.cargarTablaCaracteres(NOMBRE_ARCHIVO_TABLA);

		admArchivo.descomprimir(NOMBRE_ARCHIVO + ".compress", NOMBRE_ARCHIVO_DESCOMPRIMIR + nombreArchivo);

		if (nombreArchivo.substring(0,6).equals("recaud")) {
			premios = premios + generarPremios(NOMBRE_ARCHIVO);
		} else {
			recaudaciones = recaudaciones + generarRecaudacion(NOMBRE_ARCHIVO);
		}
		System.out.println("Premios: " + premios + ", Recaudaciones: " + recaudaciones);
	}
	public static void generarArchivoSalida(Double ingreso, Double egreso, Double total) throws IOException {

		// Asegurarse de crear un paquete llamado "Archivo" dentro del proyecto o cambiar el nombre de la ruta
		FileWriter archivoSalida = new FileWriter("src/Archivos/TablaSalida.txt"); // "TablaSalida.txt" es el nombre que elegimos que tenga la salida
		archivoSalida.write("Ingreso\tEgreso\tTotal\n"); // Escribimos los encabezados
		archivoSalida.write(ingreso + "\t" + egreso + "\t" + total); // Agregamos los numero totales
		archivoSalida.close();

	}
	public static Double generarPremios(String rutaArchivoJugada) {
		List<Double> premios = new ArrayList<>();
		Double cantidad = 0.0;
		try {
			FileReader archivo = new FileReader(rutaArchivoJugada);
			BufferedReader lector = new BufferedReader(archivo);
			String linea;
			while ((linea = lector.readLine()) != null) {
				String linea2 = linea.replaceAll("[^0-9.]", ""); //La regex elimina todos los caracteres distintos de 0-9 y .
				if (!linea2.isBlank()) {
					premios.add(Double.parseDouble(linea2));
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		for (int i=0; i < premios.size(); i++) {
			cantidad=cantidad + premios.get(i);
		}
		return cantidad;
	}
	public static Double generarRecaudacion(String rutaArchivoJugada) {
		List<Double> apuestas = new ArrayList<>();
		Double cantidad = 0.0;
		try {
			FileReader archivo = new FileReader(rutaArchivoJugada);
			BufferedReader lector = new BufferedReader(archivo);
			String linea;
			while ((linea = lector.readLine()) != null) {
				for (int i=0; i<linea.length(); i++) {
					if (linea.charAt(i) == '$') {
						String linea2 = linea.substring(0, i);
						apuestas.add(Double.parseDouble(linea2));
					}
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		for (int i=0; i < apuestas.size(); i++) {
			cantidad = cantidad + apuestas.get(i);
		}
		return cantidad;
	}
	/*
	jugada_2022-11-29_1231231_noche.txt
	jugada_2022-11-29_9284749_maniana.txt
	jugada_2022-11-29_9974461_noche.txt
	jugada_2022-11-29_22840661_maniana.txt
	jugada_2022-11-29_27324748_noche.txt
	jugada_2022-11-29_30630198_maniana.txt
	jugada_2022-11-29_33488586_noche.txt
	jugada_2022-11-29_36737334_noche.txt
	jugada_2022-11-29_47346028_maniana.txt
	jugada_2022-12-01_424242422_maniana.txt
	recaudacion_2022-11-28_maniana.txt
	*/
}