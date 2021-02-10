package spring.proyect.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
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

import spring.proyect.manager.PersonasManager;
import spring.proyect.model.CAlumnos;
import spring.proyect.model.CDatos;
import spring.proyect.model.CDocentes;
import spring.proyect.model.CPersonas;

@RestController
public class PersonaController {
	@Autowired
	PersonasManager perManager;
	
	
	@GetMapping({"api/viewPersona"})
	public CPersonas listaUnaPersona(@RequestParam("codp")int codp) {
		System.out.println(codp);
		CPersonas per =new CPersonas();
		try {
			
			per=this.perManager.getDatosPersona(codp );
		} catch (Exception e) {
			per=null;
		}
		return per;
	}
	
	@GetMapping("/api/listaPersonas")
	public List<CPersonas> listaPersonas(Model model){
		
		
		List<CPersonas> lisPer=this.perManager.listaPersona();
		return lisPer;
	}
	
	@GetMapping("/api/listaPersonal/{xestado}")
	public List<CPersonas> listaPersonasEstado(Model model, @PathVariable("xestado") String xestado, HttpSession session) 	{
		String xsession=(String) session.getAttribute("xusuario");
		//parametro para la consulta de PERSONAS
		int xest1=0, xest2=1;
		
		if (xestado.equals("0")) {
			xest1=0; xest2=0;
		}
		
		if (xestado.equals("1")) {
			xest1=1; xest2=1;
		}	
		
		List<CPersonas> lisPer=null;
		lisPer = this.perManager.listaPersonas(xest1, xest2);

		return lisPer;
	}
	
	@PostMapping("/api/guardarPersona")
	public int  addPersona(@RequestBody CPersonas per) {
		int res1 = 0;	
		
		boolean exist;
		exist=this.perManager.isExists(per.nombre ,per.ap, per.am);
		if (exist) {
			
			return 0;
		}
		try {
			
			 res1= this.perManager.setAddPersona( per.nombre, per.ap, per.am, per.estado, 
					per.fnac, per.genero, per.direc, per.celular, per.foto, per.ecivil);
			
		} catch (Exception e) {
			e.printStackTrace();		
		}
	
		return res1;
	}
	
	// alumnos
	@PostMapping("/api/addAlumnos/{codp}/{ru}")
	public int addAlumos(@PathVariable("codp")int xcodp,@PathVariable("ru")int xru){
		System.out.println(xcodp);
		try {
			int alu= this.perManager.setAlumno(xcodp,xru);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return 1;
	}
	
	///docentes
	@PostMapping("/api/addDocentes/{codp}/{cedula}")
	public int addADocentes(@PathVariable("codp")int xcodp,@PathVariable("cedula")String xcedlula){
		System.out.println(xcodp);
		try {
			int alu= this.perManager.setDocente(xcodp,xcedlula);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return 1;
	}
	
	@PutMapping("/api/modPersona")
	public int modificarPersona(@RequestBody CPersonas per) {
		boolean exist;
		exist=this.perManager.isExists(per.nombre ,per.ap, per.am);
		if (exist) {
			
			return 0;
		}
		CPersonas perFoto = perManager.getDatosPersona((int) per.getCodp());
		
		System.out.println("procesos de moificacion de datos...");
			int res1= this.perManager.setModPersona(per.codp, per.nombre, per.ap, per.am, per.estado, 
					per.fnac, per.genero, per.direc, per.celular, per.foto, per.ecivil);	
		return 1;
	}
	@PutMapping("/api/modEstado/{ru}")
	public int eliminacionLogicaPersoana ( @PathVariable("ru") int xru ,@RequestBody CPersonas per) {
		System.out.println("Proceso de eliminacion logica...");
		int res1 = this.perManager.setElimLogica( xru );	
		return 1;
	}
	@PutMapping("/api/modEstadoH/{codp}")
	public int habilitarPersona ( @PathVariable("codp") int xcodp, @RequestBody CPersonas per) {
		System.out.println("Proceso de eliminacion logica..."+xcodp);
		this.perManager.setHabilitarPersona(xcodp );	
		return 1;
	}
	
	/// imagen dela persona
	@PostMapping("/api/persona/upload")
	public ResponseEntity<?> update(@RequestParam("archivo") MultipartFile archivo, @RequestParam("codp") int codp) { 
		Map<String,  Object> response= new HashMap<>();
		System.out.println(archivo);
		CPersonas per = perManager.getDatosPersona(codp);
		String nombreFotoAnterior=per.getFoto();
		//System.out.println("nombreee anterior:::"+nombreFotoAnterior);
		if(nombreFotoAnterior != null && nombreFotoAnterior.length() >0) {
			Path rutFotoAnteriror= Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
			File archivoFotoAnterior=rutFotoAnteriror.toFile();
			if(archivoFotoAnterior.exists()&& archivoFotoAnterior.canRead()) {
				archivoFotoAnterior.delete();
			}
		}
		//System.out.println(per.getFoto());
		if (!archivo.isEmpty()) {
			String nomArchivo=per.getNombre()+"_"+per.getCodp()+"".replace(" ", "");
			Path rutaArchivo= Paths.get("uploads").resolve(nomArchivo).toAbsolutePath();
			//System.out.println(rutaArchivo);
			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				 response.put("mensaje", "error al subir el archivo "+nomArchivo);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
			
		
			
			  per.setFoto(nomArchivo);
			  perManager.setfoto(per.getCodp(), per.getFoto());
			  
			  response.put("persona", per);
			  response.put("mensaje", "sesubio corrctamente la imagen "+nomArchivo);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	@GetMapping("api/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
		Path rutaArchivo= Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
		Resource recurso =null;
		try {
			recurso = new UrlResource(rutaArchivo.toUri());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!recurso.exists() && !recurso.isReadable() ) {
			 rutaArchivo= Paths.get("src/main/resources/static/images").resolve("no-usuario.png").toAbsolutePath();
			try {
				recurso = new UrlResource(rutaArchivo.toUri());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		HttpHeaders  cabecera=new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+recurso.getFilename()+"\"");
		return new ResponseEntity<Resource>(recurso,cabecera, HttpStatus.OK);
		
	}
	
 public int borrarFoto( CPersonas per) {

	 String nombreFotoAnterior=per.getFoto();
		System.out.println(nombreFotoAnterior);
		if(nombreFotoAnterior != null && nombreFotoAnterior.length() >0) {
			Path rutFotoAnteriror= Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
			File archivoFotoAnterior=rutFotoAnteriror.toFile();
			if(archivoFotoAnterior.exists()&& archivoFotoAnterior.canRead()) {
				archivoFotoAnterior.delete();
			}
		}
	 return 1;
 }
 
 
 @GetMapping({"/api/mostrarDatosPer"})
	public ResponseEntity<?> mostrarDat(@RequestParam("codp") int codp) {
	 List<Object> object = new ArrayList<Object>();
	 System.out.println(codp);
		CPersonas per =new CPersonas();
		CAlumnos alu = new CAlumnos();
		CDocentes doc = new CDocentes();
		try {
			per=this.perManager.getDatosPersona(codp);		
			object.add(per);
			System.out.println(per.codp);
			System.out.println("alumno");
			
			try {
				alu=this.perManager.getAlumno(codp);
				System.out.println(codp);
				object.add(alu);
			} catch (Exception e) {
				System.out.println("error alumno");
			}
			 try {
				 doc=this.perManager.getDocente(codp);
				 object.add(doc);
			} catch (Exception e) {
				System.out.println("error docente");
			}			
			
		} catch (Exception e) {
		System.out.println("error");
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(object, HttpStatus.OK);
	}
 
	@PostMapping("/api/addCdatos")
	public boolean addADatos(@RequestBody CDatos dat){
		boolean exist;
		exist=this.perManager.isExistsLogin(dat.login);
		if (exist) {
			return false;
		}
		try {
			int dato= this.perManager.setAddCdatos(dat.getLogin(), dat.getPassword(), dat.getCodp());
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return true;
	}
 
 	@PutMapping("/api/modCdatos")
	public boolean  modificarLogin(@RequestBody CDatos dat) {
 		
		CDatos datos = perManager.getCdatos(dat.getLogin());
		System.out.println(datos.getLogin()+"   "+ datos.getPassword());
		if(datos != null) {
			this.perManager.setModCdatos(dat.getPassword(),datos.getLogin());
			return true ;	
		}else {
			return false;
		}
		
	}

}
