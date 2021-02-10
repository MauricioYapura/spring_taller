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

import spring.proyect.manager.DictaManger;
import spring.proyect.model.CAreas;
import spring.proyect.model.CDicta;
import spring.proyect.model.CGrupos;
import spring.proyect.model.CPersonas;
import spring.proyect.model.CProgra;

@RestController
public class DictaController {
	@Autowired
	DictaManger dictaManager;
	
	@GetMapping("/api/listaDicta")
	public List<CDicta> listaAreas(Model model){
		List<CDicta> listDic=this.dictaManager.listaDicta();
		return listDic;
	}
	
	
	@PostMapping("/api/guardarDicta")
	public int  addDicta(@RequestBody CDicta dic) {
		boolean exist;
		exist=this.dictaManager.isExists(dic.codp, dic.codg, dic.gestion);
		if (exist) {
			return 0;
		}
		try {
			int res1= this.dictaManager.setAddDicta(dic.codp, dic.codg, dic.gestion, dic.estado);
			
		} catch (Exception e) {
			e.printStackTrace();		
		}
		return 1;
	}
	@PutMapping("/api/modDicta/{codp}/{codg}/{gestion}")
	public int modificarDicta(@RequestBody CDicta dic,@PathVariable("codp") int codp, @PathVariable("codg") int codg, @PathVariable("gestion")int gestion) {
		boolean exist;
		exist=this.dictaManager.isExists(dic.codp, dic.codg, dic.gestion);
		if (exist) {
			return 0;
		}
		int res1= this.dictaManager.setModDicta(dic.codp, dic.codg, dic.gestion, dic.estado, codp,codg,gestion);	
		return 1;
	}
	
	@DeleteMapping("/api/deleteDicta/{codp}/{codg}/{gestion}")
	public int eliminarProgra( @PathVariable("codp") int codp,@PathVariable("codg") int codg,@PathVariable("gestion") int gestion ) {
		int res = this.dictaManager.setDelDicta(codp, codg, gestion);
		return 1;
	}
	///
	@GetMapping("/api/listaDocentesAll")
	public List<CPersonas> listaPersonas(Model model){
		List<CPersonas> lisPer=this.dictaManager.listaPersona();
		return lisPer;
	}
	
	///
	@GetMapping("/api/listaGruposDicta")
	public List<CGrupos> listaGruposAll(Model model){
		List<CGrupos> lisGrupos=this.dictaManager.listaGrupos();
		return lisGrupos;
	}
	// bisqueda gestion
		@GetMapping("/api/busquedaGesDicta/{gestion}")
		public List<CDicta> busGestionDicta(Model model, @PathVariable("gestion") int gestion) {
			List<CDicta> listaGestionD = this.dictaManager.busquedaGesDcita(gestion);
			return listaGestionD;
		}
}
