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

import spring.proyect.manager.EtapasManager;
import spring.proyect.model.CAreas;
import spring.proyect.model.CEtapas;

@RestController
public class EtapasController {
	@Autowired
	EtapasManager etapasManager;
	
	@GetMapping("/api/listaEtapas")
	public List<CEtapas> listaAreas(Model model){
		List<CEtapas> listEtapas=this.etapasManager.listaEtapas();
		return listEtapas;
	}

	@GetMapping("/api/listaEtapas/{xestado}")
	public List<CEtapas> listaAreasEstado(Model model, @PathVariable("xestado") String xestado, HttpSession session) 	{
	
		//parametro para la consulta de areas
		int xest1=0, xest2=1;
		
		if (xestado.equals("0")) {
			xest1=0; xest2=0;
		}
		
		if (xestado.equals("1")) {
			xest1=1; xest2=1;
		}	
		
		List<CEtapas> lisPer=null;
		lisPer = this.etapasManager.listaEtapas(xest1, xest2);

		return lisPer;
	}
	@PostMapping("/api/guardarEtapa")
	public int  addEtapa(@RequestBody CEtapas et) {
		
		boolean exist;
		exist=this.etapasManager.isExists(et.nombre);
		if (exist) {
			return 0;
		}
		try {
			int res1= this.etapasManager.setAddEtapa(et.code, et.nombre, et.estado);
			
		} catch (Exception e) {
			e.printStackTrace();		
		}
		return 1;
	}
	
	@PutMapping("/api/modEtapa")
	public int modificarEtapa(@RequestBody CEtapas et) {
		boolean exist;
		exist=this.etapasManager.isExists(et.nombre);
		if (exist) {
			return 0;
		}
			int res1= this.etapasManager.setModEtapa(et.code, et.nombre, et.estado);	
		return 1;
	}
	@PutMapping("/api/modEstadoEtapa/{code}")
	public int eliminacionLogicaEtapa ( @PathVariable("code") int xcode ,@RequestBody CEtapas et) {
		System.out.println("Proceso de eliminacion logica...");
		int res1 = this.etapasManager.setElimLogica( xcode );	
		return 1;
	}
	@PutMapping("/api/modEstadoHabEtapa/{code}")
	public int habilitarEtapa ( @PathVariable("code") int xcode, @RequestBody CEtapas et) {
		System.out.println("Proceso de eliminacion logica..."+xcode);
		this.etapasManager.setHabilitarEtapa(xcode );	
		return 1;
	}
	
}
