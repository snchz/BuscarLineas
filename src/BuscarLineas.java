import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class BuscarLineas {

	public BuscarLineas() {
	}
	

	public static void main(String[] args) {
		BuscarLineas bl=new BuscarLineas();
		List<String> listado=new ArrayList<String>();
		String fichero=null;
		for (int i=0;i<args.length;i++){
			if (i==0)
				fichero=args[i];
			else
				listado.add(args[i]);
		}
		try{
			if (args.length<2){
				System.err.println("USO: BuscarLineas.jar Fichero Termino1 Termino2...");
			}else {
				System.out.println("Buscando en el fichero "+fichero+" el listado de palabras siguientes: "+listado.toString());
				bl.generarFicheroBusqueda(fichero, listado);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Lee del "fichero" y busca cada linea que tenga alguno de los terminos en la lista "listaAbuscar"
	 * Como salida genera un fichero Resultado_***_.txt indicando en que lineas encuentra alguno de los terminos
	 * @param fichero
	 * @param listaAbuscar
	 */
	public void generarFicheroBusqueda(String fichero, List<String> listaAbuscar){
		BufferedReader br=null;
		BufferedWriter bw=null;
		try {
			System.out.println("Inicio");
			br = new BufferedReader(new FileReader(fichero));
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			bw = new BufferedWriter(new FileWriter("Resultado_"+timeStamp+".txt"));
			bw.append("Resultado de busqueda en el fichero "+fichero+" del listado de palabras: "+listaAbuscar.toString());
		    String line = br.readLine();
		    int nLinea=1;
		    while (line != null) {
		    	if (encuentraLista(line,listaAbuscar)){
		    		bw.append("\nLinea Encontrada: "+String.format("%1$-10s",nLinea)+"\t\tContenido: "+line);
		    	}
		    	if (nLinea%100000==0)
		    		System.out.println(nLinea);
		        line = br.readLine();
		        nLinea++;
		    }
		    bw.newLine();
		    bw.append("Lineas leidas en total: "+(nLinea-1));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bw!=null){
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Fin");
		}
	}


	/**
	 * Dada una linea "line" retorna true si la linea tiene alguno de los terminos de la lista "listaAbuscar"
	 * @param line
	 * @param listaAbuscar
	 * @return
	 */
	private boolean encuentraLista(String line, List<String> listaAbuscar) {
		boolean res=false;
		for(String s:listaAbuscar){
			if (line.contains(s))
				res=true;
			else
				return false;
		}
		return res;
	}

}
