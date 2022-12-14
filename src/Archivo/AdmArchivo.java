package Archivo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Nodo.NodoArbol;

public class AdmArchivo implements ICarfarTablaHuffman {
	private List<NodoArbol> nodosArbol;
	private HashMap<Character, Integer> tablaFrecuencias;
	private HashMap<Character, String> tablaHasHuffmanCodigos;
	private HashMap<String, Character> tablaHasInvHuffmanCodigos;

	@Override
	public void cargarTablaFrecuencias(String nameFile) {

		nodosArbol = new ArrayList<NodoArbol>();
		tablaFrecuencias = new HashMap<Character, Integer>();
		tablaHasHuffmanCodigos = new HashMap<Character, String>();

		try {
			RandomAccessFile file = new RandomAccessFile(nameFile, "r");
			char dato;
			long cont = 0;
			long tamano = file.length();
			while (cont < tamano) {
				file.seek(cont);
				dato = (char) file.readByte();
				if (!tablaFrecuencias.containsKey(dato)) {
					tablaFrecuencias.put(dato, 1);
					tablaHasHuffmanCodigos.put(dato, "");
				} else {
					tablaFrecuencias.put(dato, tablaFrecuencias.get(dato) + 1);
				}
				cont++;
			}
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cargarTablaCaracteres(String nombreArchivo) {

		tablaHasHuffmanCodigos = new HashMap<Character, String>();

		try {
			RandomAccessFile archivo = new RandomAccessFile(nombreArchivo, "r");
			char dato;
			String cadena = "";
			long cont = 0;
			long tamanio = archivo.length();
			while (cont < tamanio) {
				archivo.seek(cont);
				dato = (char) archivo.readByte();
				cadena = cadena + dato;
				cont++;
			}
			archivo.close();

			cadena = cadena.substring(16, cadena.length());
			String[] datos = new String[cadena.length()];
			String code = "";
			int pos = 0;
			for (int i = 0; i < cadena.length(); i++) {
				if (cadena.substring(i,i+1).equals(",") || cadena.substring(i,i+1).equals("\n")) {
					datos[pos] = code;
					pos++;
					code = "";
				} else {
					code = code + cadena.substring(i, i + 1);
				}
			}
			if (pos % 2 != 0) {
				pos = pos - 1;
			}
			String[] date = new String[pos];
			for (int i = 0; i < date.length; i++) {
				date[i] = datos[i];
			}
			for (int i = 0; i < date.length; i++) {
				if (i % 2 == 0) {
					String binary = date[i+1].replaceAll("[^0-9]", "");
					int valorASCII = Integer.parseInt(date[i]);//a los q estan en posicion par, los casteamos a char
					char caracter = (char)valorASCII;
					tablaHasHuffmanCodigos.put(caracter, binary);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<NodoArbol> leerTablaHuffman() {
		return nodosArbol;
	}

	@Override
	public void comprimir(String nomArchivo, String nomArchivoDestino) {
		String strBuffer = "";
		String strBuffertmp = "";
		File arch = new File(nomArchivoDestino);
		if (arch.delete()){
			System.out.println("archivo borrado");
		}
		try {
			RandomAccessFile archivoOrigen = new RandomAccessFile(nomArchivo, "r");
			RandomAccessFile archivoDestino = new RandomAccessFile(nomArchivoDestino, "rw");
			char dato;
			long cont = 0;
			long tamano = archivoOrigen.length();
			while (cont < tamano) {
				archivoOrigen.seek(cont);
				dato = (char) archivoOrigen.readByte();
				strBuffer = strBuffer + this.tablaHasHuffmanCodigos.get(dato);
				strBuffertmp = strBuffertmp + " " + this.tablaHasHuffmanCodigos.get(dato);
				strBuffer = procesarbuffer(strBuffer, archivoDestino);
				cont++;
			}
			//System.out.println(strBuffertmp);
			archivoOrigen.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String procesarbuffer(String strBuff, RandomAccessFile archivo) throws IOException {
		String Auxstr = strBuff, strIingByte = "";
		while (Auxstr.length() >= 8) {
			strIingByte = Auxstr.substring(0, 8);
			Auxstr = Auxstr.substring(8, Auxstr.length());
			archivo.writeByte(stringByteToByte(strIingByte));
		}
		return Auxstr;
	}

	private byte stringByteToByte(String strToByte) {

		byte Byteresult = 0;
		int Intresult = 0;

		if (strToByte.length() > 0)
			if (Integer.parseInt(strToByte.substring(0, 1)) > 0)
				Intresult = Intresult + 128;
		if (strToByte.length() > 1)
			if (Integer.parseInt(strToByte.substring(1, 2)) > 0)
				Intresult = Intresult + 64;
		if (strToByte.length() > 2)
			if (Integer.parseInt(strToByte.substring(2, 3)) > 0)
				Intresult = Intresult + 32;
		if (strToByte.length() > 3)
			if (Integer.parseInt(strToByte.substring(3, 4)) > 0)
				Intresult = Intresult + 16;
		if (strToByte.length() > 4)
			if (Integer.parseInt(strToByte.substring(4, 5)) > 0)
				Intresult = Intresult + 8;
		if (strToByte.length() > 5)
			if (Integer.parseInt(strToByte.substring(5, 6)) > 0)
				Intresult = Intresult + 4;
		if (strToByte.length() > 6)
			if (Integer.parseInt(strToByte.substring(6, 7)) > 0)
				Intresult = Intresult + 2;
		if (strToByte.length() > 7)
			if (Integer.parseInt(strToByte.substring(7, 8)) > 0)
				Intresult = Intresult + 1;
		Byteresult = (byte) Intresult;
		return Byteresult;
	}

	@Override
	public void descomprimir(String nomArchivo, String nomArchivoDestino) {
		File arch = new File(nomArchivoDestino);
		String codigo="";
		if (arch.delete())
			System.out.println("archivo borrado");
		invertirTablatHuffman();
		try {
			RandomAccessFile archivoOrigen = new RandomAccessFile(nomArchivo, "r");
			RandomAccessFile archivoDestino = new RandomAccessFile(nomArchivoDestino, "rw");
			String strCharacter="";

			long cont = 0;
			long tamano = archivoOrigen.length();

			while (cont < tamano) {
				archivoOrigen.seek(cont);
				strCharacter = strCharacter + completarByte(Integer.toBinaryString((char) archivoOrigen.readByte() & 0xff));
				cont++;
			}

			for(char c: strCharacter.toCharArray()) {
				if(!this.tablaHasInvHuffmanCodigos.containsKey(codigo)) {
					codigo=codigo+String.valueOf(c);
				} else {
					archivoDestino.writeByte((char)this.tablaHasInvHuffmanCodigos.get(codigo));
					codigo=String.valueOf(c);
				}	
			}
			archivoOrigen.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String completarByte(String strByte) {
		String strIni="";
		for(int i = strByte.length(); i < 8; i++) {
			strIni=strIni+"0";
		}
		return (strIni!=null && strIni.length()>0) ? strIni+strByte : strByte;
	}

	private void invertirTablatHuffman() {
		this.tablaHasInvHuffmanCodigos=new HashMap<String, Character>();
		if (!this.tablaHasHuffmanCodigos.isEmpty()) {
			for (Character key : this.tablaHasHuffmanCodigos.keySet()) {
				this.tablaHasInvHuffmanCodigos.put(this.tablaHasHuffmanCodigos.get(key), key);
			}
		}
	}

	/**
	 * Este metodo sirve para enviar una tabla de codigos.
	 * @return
	 */
	public HashMap<Character, String> getTablaHasHuffmanCodigos() {
		return this.tablaHasHuffmanCodigos;
	}

	/**
	 * Con este metodo se podra obtener las frecuencias correspondientes a cada caracter.
	 * @return
	 */
	public HashMap<Character, Integer> getTablaFrecuencias() {
		return tablaFrecuencias;
	}
}
