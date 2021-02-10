package spring.proyect.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import spring.proyect.manager.ProyectosManager;
import spring.proyect.model.CEtapas;
import spring.proyect.model.CPersonas;
import spring.proyect.model.CProgra;
import spring.proyect.model.CProyectos;


@RestController
public class CProyectosController {

	@Autowired
	ProyectosManager proyectosManager;
	
	@GetMapping("/api/listaProyectos")
	public List<CProyectos> listaProyectos(Model model) {
		List<CProyectos> listPro = proyectosManager.listaProgra();
		return listPro;
	}
	@GetMapping("/api/listaProyectosEst/{estado}")
	public List<CProyectos> listaProyectosEst(Model model,@PathVariable("estado") String xestado) {
		//parametro para la consulta de PERSONAS
		int xest1=0, xest2=1;
		
		if (xestado.equals("0")) {
			xest1=0; xest2=0;
		}
		
		if (xestado.equals("1")) {
			xest1=1; xest2=1;
		}	
		List<CProyectos> listPro = proyectosManager.listaPrograEstadp(xest1, xest2);
		return listPro;
	}
	@GetMapping("/api/listaProyectoSolo/{codpro}")
	public boolean listaProyectoSolo(Model model,@PathVariable("codpro") int codpro) {
		CProyectos pro=new CProyectos();
		
		try {
			  pro = proyectosManager.getDatosPro(codpro);
			  return true;		
		} catch (Exception e) {
			// TODO: handle exception
			 return false;
		}	 
	}
	@GetMapping("/api/listaProyectos/{estado}")
	public List<CProyectos> listaProyectos(Model model, @PathVariable("estado") int est) {
		List<CProyectos> listPro = proyectosManager.listaPrograEstadp(est, est);
		return listPro;
	}
	
	@PostMapping("/api/guardarProyectos")
	public int addProyectos(@RequestBody CProyectos proy) {
		int res1 = 0;
		boolean exist;
		exist=this.proyectosManager.isExists(proy.titulo);
		if (exist) {
			return 0;
		}
		try {
			 res1 = this.proyectosManager.setAddProyectos(proy.titulo, proy.resumen, proy.file, proy.notadefensa, proy.estado, proy.fecha, proy.estadoproy, proy.codTutor, proy.codarea, proy.codp, proy.codg, proy.gestion);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res1;
	}
	

	@PutMapping("/api/modProy")
	public int modificarProy(@RequestBody CProyectos proy) {
	
//		boolean exist;
//		exist=this.proyectosManager.isExists(proy.titulo);
//		if (exist) {
//			return 0;
//		}
			int res1= this.proyectosManager.setModProyectos(proy.codpro, proy.titulo, proy.resumen, proy.file, proy.notadefensa, proy.estado, proy.fecha, proy.estadoproy, proy.codTutor, proy.codarea, proy.codp, proy.codg, proy.gestion);	
		return 1;
	}
	@PutMapping("/api/modEstadoProyectos/{codpro}")
	public int eliminacionLogicaProy ( @PathVariable("codpro") int codpro ,@RequestBody CProyectos proy) {
		System.out.println("Proceso de eliminacion logica...");
		int res1 = this.proyectosManager.setElimLogica( codpro );	
		return 1;
	}
	@PutMapping("/api/modEstadoHabProyec/{codpro}")
	public int habilitarEtapa ( @PathVariable("codpro") int codpro, @RequestBody CProyectos proy) {
		System.out.println("Proceso de eliminacion logica..."+codpro);
		this.proyectosManager.setHabilitarproyectos(codpro );	
		return 1;
	}
	
	
	/// docdel proyect
		@PostMapping("/api/proyecto/uploadPro")
		public ResponseEntity<?> updateP(@RequestParam("archivo") MultipartFile archivo, @RequestParam("pro") int codpro)  { 
			Map<String,  Object> response= new HashMap<>();
			
			CProyectos pro1=proyectosManager.getDatosPro(codpro);
			
			String nombreDocAnterior=pro1.getFile();
			//System.out.println("nombreee anterior:::"+nombreFotoAnterior);
			if(nombreDocAnterior != null && nombreDocAnterior.length() >0) {
				Path rutDocAnteriror= Paths.get("uploadsPro").resolve(nombreDocAnterior).toAbsolutePath();
				File archivoDocAnterior=rutDocAnteriror.toFile();
				if(archivoDocAnterior.exists()&& archivoDocAnterior.canRead()) {
					archivoDocAnterior.delete();
				}
			}
			//System.out.println(per.getFoto());
			if (!archivo.isEmpty()) {
				
				String nomArchivo=pro1.getTitulo()+"_"+pro1.getCodpro()+".pdf".replace(" ", "");
				
				Path rutaArchivo= Paths.get("uploadsPro").resolve(nomArchivo).toAbsolutePath();
				
				try {
					Files.copy(archivo.getInputStream(), rutaArchivo);
					pro1.setFile(nomArchivo);
					System.out.println(pro1.getTitulo());
					proyectosManager.setfoto(pro1.getCodpro(), pro1.getFile());
					/// proyectosManager.setAddProyectos(proy.titulo, proy.resumen, nomArchivo, proy.notadefensa, proy.estado, proy.fecha, proy.estadoproy, proy.codTutor, proy.codarea, proy.codp, proy.codg, proy.gestion);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					 response.put("mensaje", "error al subir el archivo "+nomArchivo);
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}	
							 
				  response.put("mensaje", "sesubio corrctamente el file "+nomArchivo);
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}
	
}
