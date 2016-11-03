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
			if (fichero==null){
				System.out.println("Introduce el nombre del fichero:");
				fichero=System.console().readLine();;
			}
			if (args.length<2){
				boolean salir=false;
				String entrada="";
				while (!salir){
					System.out.println("Introduce el valor a buscar (N si no queres buscar mas):");
					entrada=System.console().readLine();;
					if (entrada.equals("N"))
						salir=true;
					else
						listado.add(entrada);
				}
				
			}
			System.out.println("Buscando en el fichero "+fichero+" el listado de palabras siguientes: "+listado.toString());
			bl.generarFicheroBusqueda(fichero, listado);
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
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
		    //StringBuilder salida= new StringBuilder();
		    int nLinea=1;
		    while (line != null) {
		    	if (encuentraLista(line,listaAbuscar)){//2014-02-072014-02-07
		    		bw.append("\nLinea Encontrada: "+String.format("%1$-10s",nLinea)+"\t\tContenido: "+line);		
		    		//bw.newLine();
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
