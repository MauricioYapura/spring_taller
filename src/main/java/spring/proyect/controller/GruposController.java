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

import spring.proyect.manager.GruposManager;
import spring.proyect.model.CAreas;
import spring.proyect.model.CGrupos;

@RestController
public class GruposController {
	@Autowired
	GruposManager gruposManger;
	
	@GetMapping("/api/listaGrupos")
	public List<CGrupos> listaAreas(Model model){
		List<CGrupos> lisGrupos=this.gruposManger.listaGrupos();
		return lisGrupos;
	}
	
	@GetMapping("/api/listaGrupos/{xestado}")
	public List<CGrupos> listaGruposEstado(Model model, @PathVariable("xestado") String xestado, HttpSession session) 	{
	
		//parametro para la consulta de areas
		int xest1=0, xest2=1;
		
		if (xestado.equals("0")) {
			xest1=0; xest2=0;
		}
		
		if (xestado.equals("1")) {
			xest1=1; xest2=1;
		}	
		
		List<CGrupos> lisGrup=null;
		lisGrup = this.gruposManger.listaGrupos(xest1, xest2);

		return lisGrup;
	}
	
	@PostMapping("/api/guardarGrupo")
	public int  addAreas(@RequestBody CGrupos gr) {
		
		boolean exist;
		exist=this.gruposManger.isExists(gr.nombre);
		if (exist) {
			return 0;
		}
		try {
			int res1= this.gruposManger.setAddGrupo(gr.codg, gr.nombre, gr.estado);
			
		} catch (Exception e) {
			e.printStackTrace();		
		}
		return 1;
	}
	
	@PutMapping("/api/modGrupo")
	public int modificarPersona(@RequestBody CGrupos gr) {
		boolean exist;
		exist=this.gruposManger.isExists(gr.nombre);
		if (exist) {
			return 0;
		}
			int res1= this.gruposManger.setModGrupos(gr.codg, gr.nombre, gr.estado);	
		return 1;
	}
	@PutMapping("/api/modEstadoGrupo/{codg}")
	public int eliminacionLogicaGrupo ( @PathVariable("codg") int xcodg ,@RequestBody CGrupos gr) {
		System.out.println("Proceso de eliminacion logica...");
		int res1 = this.gruposManger.setElimLogica( xcodg );	
		return 1;
	}
	@PutMapping("/api/modEstadoHabGrupo/{codg}")
	public int habilitarArea ( @PathVariable("codg") int xcodg, @RequestBody CGrupos gr) {
		System.out.println("Proceso de eliminacion logica..."+xcodg);
		this.gruposManger.setHabilitarGrupos(xcodg);	
		return 1;
	}
	
}
