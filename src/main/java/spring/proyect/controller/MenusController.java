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

import spring.proyect.manager.MenusGManager;


import spring.proyect.model.CMenus;
import spring.proyect.model.CProcesos;


@RestController
public class MenusController {
	@Autowired
	MenusGManager menuManager;
	//stado
	@GetMapping("/api/listaMenusM/{xestado}")
	public List<CMenus> menuslist( @PathVariable("xestado") String xestado  ){
		
		//parametro para la consulta de areas
		int xest1=0, xest2=1;
		
		if (xestado.equals("0")) {
			xest1=0; xest2=0;
		}
		
		if (xestado.equals("1")) {
			xest1=1; xest2=1;
		}	
		List<CMenus> lismenu=this.menuManager.listaMenu(xest1,xest2);
		return lismenu;
	}
	// solo
	@GetMapping({"/api/listaProcesos/{codr}"})
	public List<CProcesos> listaMepro(Model model , @PathVariable int codr)  	{
		
		List<CProcesos> lisPro = this.menuManager.listarolmepro(codr);
			
	    return lisPro;
	}
	//asignado
	@GetMapping({"/api/listaProcesosA/{codr}/{asig}"})
	public List<CProcesos> listaMeproAsignado(Model model , @PathVariable int codr,@PathVariable int asig)  	{
		List<CProcesos> lisPro = this.menuManager.listarolmeproAsignado(codr, asig);
			
	    return lisPro;
	}
	/// add
		@PostMapping("/api/guardarMenu")
		public int  addMenus(@RequestBody CMenus me) {
			boolean exist;
			exist=this.menuManager.isExists( me.nombre);
			if (exist) {
				return 0;
			}
			try {
				int res1= this.menuManager.setAddMenu(me.codm, me.nombre, me.estado);
				
			} catch (Exception e) {
				e.printStackTrace();		
			}
			return 1;
		}
		//modd
		@PutMapping("/api/modMenu")
		public int modificarPersona(@RequestBody CMenus me) {
			boolean exist;
			exist=this.menuManager.isExists( me.nombre);
			if (exist) {
				return 0;
			}
				int res1= this.menuManager.setModMenu(me.codm, me.nombre, me.estado);	
			return 1;
		}
		/// dell 
		@PutMapping("/api/modEstadoMenu/{codm}")
		public int eliminacionLogicaPersoana ( @PathVariable("codm") int xcodm ,@RequestBody CMenus me) {
			System.out.println("Proceso de eliminacion logica...");
			int res1 = this.menuManager.setElimLogica( xcodm );	
			return 1;
		}
		//hab
		@PutMapping("/api/modEstadoHabMenu/{codm}")
		public int habilitarRol ( @PathVariable("codm") int xcodm, @RequestBody CMenus me) {
			System.out.println("Proceso de hab logica..."+xcodm);
			this.menuManager.setHabilitar(xcodm );	
			return 1;
		}
		
		@PostMapping("/api/guardarMepro/{codm}/{codp}")
		public int  addMepro(@PathVariable("codm") int xcodm,@PathVariable("codp") int xcodp) {
			
			boolean exist;
			exist=this.menuManager.isExists(xcodm,xcodp);
			if (exist) {
				return 0;
			}
			try {
				
				int res1= this.menuManager.setAddMepro(xcodm, xcodp);
				
			} catch (Exception e) {
				e.printStackTrace();		
			}
			return 1;
		}
		@DeleteMapping("/api/deleteMepro/{codm}/{codp}")
		public int eliminarProgra( @PathVariable("codm") int codm,@PathVariable("codp") int codp ) {
			int res = this.menuManager.setDelMepro(codm, codp);
			return 1;
		}
}
