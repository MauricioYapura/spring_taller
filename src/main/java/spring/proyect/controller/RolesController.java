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

import spring.proyect.manager.RolesManager;
import spring.proyect.model.CAreas;
import spring.proyect.model.CMenus;
import spring.proyect.model.CProcesos;
import spring.proyect.model.CRoles;

@RestController
public class RolesController {
	@Autowired
	RolesManager rolesManager;
	
	@GetMapping("/api/listaRolesM/{xestado}")
	public List<CRoles> roles( @PathVariable("xestado") String xestado  ){
		
		//parametro para la consulta de areas
		int xest1=0, xest2=1;
		
		if (xestado.equals("0")) {
			xest1=0; xest2=0;
		}
		
		if (xestado.equals("1")) {
			xest1=1; xest2=1;
		}	
		List<CRoles> lisrol=this.rolesManager.listaRoles(xest1,xest2);
		return lisrol;
		
	}
	/// add
	@PostMapping("/api/guardarRol")
	public int  addAreas(@RequestBody CRoles ro) {
		
		boolean exist;
		exist=this.rolesManager.isExistslogin(ro.nombre);
		if (exist) {
			return 0;
		}
		try {
			int res1= this.rolesManager.setAddRole(ro.codr, ro.nombre, ro.estado);
			
		} catch (Exception e) {
			e.printStackTrace();		
		}
		return 1;
	}
	//modd
	@PutMapping("/api/modRol")
	public int modificarPersona(@RequestBody CRoles ro) {
		boolean exist;
		exist=this.rolesManager.isExistslogin(ro.nombre);
		if (exist) {
			return 0;
		}
			int res1= this.rolesManager.setModRole(ro.codr, ro.nombre, ro.estado);	
		return 1;
	}
	/// dell 
	@PutMapping("/api/modEstadoRol/{codr}")
	public int eliminacionLogicaPersoana ( @PathVariable("codr") int xcodr ,@RequestBody CRoles ro) {
		System.out.println("Proceso de eliminacion logica...");
		int res1 = this.rolesManager.setElimLogica( xcodr );	
		return 1;
	}
	@PutMapping("/api/modEstadoHabRol/{codr}")
	public int habilitarRol ( @PathVariable("codr") int xcodr, @RequestBody CRoles rol) {
		System.out.println("Proceso de hab logica..."+xcodr);
		this.rolesManager.setHabilitarRole(xcodr );	
		return 1;
	}
/////rolme
	@GetMapping({"/api/listaRolmeAs/{codr}"})
	public List<CMenus> listaRolme(Model model , @PathVariable int codr)  	{
		
		List<CMenus> lisAlus = this.rolesManager.listarolme(codr);
			
	    return lisAlus;
	}
	// solo
		@GetMapping({"/api/listaMenuRolme/{codr}"})
		public List<CMenus> listaMepro(Model model , @PathVariable int codr)  	{
			
			List<CMenus> lisPro = this.rolesManager.listarolmeSolo(codr);
				
		    return lisPro;
		}
		//asignado
		@GetMapping({"/api/listaMenuRolmeA/{codr}/{asig}"})
		public List<CMenus> listaMeproAsignado(Model model , @PathVariable int codr,@PathVariable int asig)  	{
			List<CMenus> lisPro = this.rolesManager.listarRolmeAsignado(codr, asig);
				
		    return lisPro;
		}//add
		@PostMapping("/api/guardarRolme/{codr}/{codm}")
		public int  addMepro(@PathVariable("codr") int xcodr,@PathVariable("codm") int xcodm) {
			
			boolean exist;
			exist=this.rolesManager.isExists(xcodr,xcodm);
			if (exist) {
				return 0;
			}
			try {
				int res1= this.rolesManager.setAddRolme(xcodr, xcodm);
				
			} catch (Exception e) {
				e.printStackTrace();		
			}
			return 1;
		}
		@DeleteMapping("/api/deleteRolme/{codr}/{codm}")
		public int eliminarProgra( @PathVariable("codr") int codr,@PathVariable("codm") int codm ) {
			int res = this.rolesManager.setDelRolme(codr, codm);
			return 1;
		}
	
}
