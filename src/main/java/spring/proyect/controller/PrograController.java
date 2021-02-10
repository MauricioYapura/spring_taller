package spring.proyect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import spring.proyect.manager.PrograManager;
import spring.proyect.manager.ProyectosManager;
import spring.proyect.model.CDicta;
import spring.proyect.model.CGrupos;
import spring.proyect.model.CPersonas;
import spring.proyect.model.CProgra;
import spring.proyect.model.CProyectos;

@RestController
public class PrograController {
	@Autowired
	PrograManager prograManager;
	@Autowired
	ProyectosManager proyectosManager;

	@GetMapping("/api/listaProgra")
	public List<CProgra> listaAreas(Model model) {
		List<CProgra> listPro = prograManager.listaProgra();
		return listPro;
	}

	@PostMapping("/api/guardarProgra")
	public int addProgra(@RequestBody CProgra dic) {
		boolean exist;
		System.out.println(dic.getCodp()+ "+" +dic.getCodg()+" "+ dic.getGestion());
		exist=this.prograManager.isExists(dic.codp, dic.codg, dic.gestion);
		if (exist) {
			
			return 0;
		}
		try {
			System.out.println("entra para crear");
			int res1 = this.prograManager.setAddProgra(dic.codp, dic.codg, dic.gestion, dic.estado, dic.notaFinal);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	@PutMapping("/api/modProgra/{codp}/{codg}/{gestion}")
	public int modificarProgra(@RequestBody CProgra dic, @PathVariable("codp") int codp, @PathVariable("codg") int codg,
			@PathVariable("gestion") int gestion) {
		
		boolean exist;
		
		exist=this.prograManager.isExists(dic.codp, dic.codg, dic.gestion);
		if (exist) {
			
			return 0;
		}
		//CProyectos pro=proyectosManager.getDatosPro(codp,codg,gestion);
		
		int res1 = this.prograManager.setModProgra(dic.codp, dic.codg, dic.gestion, dic.estado, dic.notaFinal, codp,
				codg, gestion);
		return 1;
	}

	@DeleteMapping("/api/deleteProgra/{codp}/{codg}/{gestion}")
	public int eliminarProgra(@PathVariable("codp") int codp, @PathVariable("codg") int codg,
			@PathVariable("gestion") int gestion) {
		int res = this.prograManager.setDelProgra(codp, codg, gestion);
		return 1;
	}

	///
	@GetMapping("/api/listaAlumnosAll")
	public List<CPersonas> listaPersonas(Model model) {
		List<CPersonas> lisPer = this.prograManager.listaPersona();
		return lisPer;
	}

	///
	@GetMapping("/api/listaGruposProgra")
	public List<CGrupos> listaGruposAll(Model model) {
		List<CGrupos> lisGrupos = this.prograManager.listaGrupos();
		return lisGrupos;
	}

	// bisqueda gestion
	@GetMapping("/api/busquedaGestion/{gestion}")
	public List<CProgra> busGestion(Model model, @PathVariable("gestion") int gestion) {
		List<CProgra> listaGestion = this.prograManager.busquedaGes(gestion);
		return listaGestion;
	}

	// bisqueda gestion
	@GetMapping("/api/busquedaGrup/{grupo}")
	public List<CProgra> busGupo(Model model, @PathVariable("grupo") int grupo) {
		List<CProgra> listaGestion = this.prograManager.busquedaGgrupo(grupo);
		return listaGestion;
	}

}
