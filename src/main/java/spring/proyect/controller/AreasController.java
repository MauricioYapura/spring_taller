package spring.proyect.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import spring.proyect.manager.AreasManager;
import spring.proyect.model.CAreas;




@RestController
public class AreasController {
	@Autowired
	AreasManager areasManager;
	
//// verficar exist
	
	
	
	@GetMapping("/api/listaAreas")
	public List<CAreas> listaAreas(Model model){
		List<CAreas> listArea=this.areasManager.listaAreas();
		return listArea;
	}
	
	@GetMapping("/api/listaAreas/{xestado}")
	public List<CAreas> listaAreasEstado(Model model, @PathVariable("xestado") String xestado, HttpSession session) 	{
	
		//parametro para la consulta de areas
		int xest1=0, xest2=1;
		
		if (xestado.equals("0")) {
			xest1=0; xest2=0;
		}
		
		if (xestado.equals("1")) {
			xest1=1; xest2=1;
		}	
		
		List<CAreas> lisPer=null;
		lisPer = this.areasManager.listaAreas(xest1, xest2);

		return lisPer;
	}
	
	
	
	@PostMapping("/api/guardarArea")
	public int  addAreas(@RequestBody CAreas ar) {
		boolean exist;
		exist=this.areasManager.isExists(ar.nombre);
		if (exist) {
			return 0;
		}
		try {
			int res1= this.areasManager.setAddArea(ar.coda, ar.nombre, ar.estado);
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@PutMapping("/api/modArea")
	public int modificarPersona(@RequestBody CAreas ar) {
		boolean exist;
		exist=this.areasManager.isExists(ar.nombre);
		if (exist) {
			return 0;
		}
			int res1= this.areasManager.setModArea(ar.coda, ar.nombre, ar.estado);	
		return 1;
	}
	@PutMapping("/api/modEstadoArea/{coda}")
	public int eliminacionLogicaPersoana ( @PathVariable("coda") int xcoda ,@RequestBody CAreas ar) {
		System.out.println("Proceso de eliminacion logica...");
		int res1 = this.areasManager.setElimLogica( xcoda );	
		return 1;
	}
	@PutMapping("/api/modEstadoHabArea/{coda}")
	public int habilitarArea ( @PathVariable("coda") int xcoda, @RequestBody CAreas ar) {
		System.out.println("Proceso de eliminacion logica..."+xcoda);
		this.areasManager.setHabilitarPersona(xcoda );	
		return 1;
	}
	
}
