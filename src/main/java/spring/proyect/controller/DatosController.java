package spring.proyect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.proyect.manager.UsuRolManager;
import spring.proyect.model.CDatos;
import spring.proyect.model.CMenus;
import spring.proyect.model.CRoles;

@RestController
public class DatosController {
	@Autowired
	UsuRolManager loginManager;
	@GetMapping("/api/listaLogin/{xestado}")
	public List<CDatos> listLogin( @PathVariable("xestado") String xestado  ){
		
		//parametro para la consulta de areas
		int xest1=0, xest2=1;
		
		if (xestado.equals("0")) {
			xest1=0; xest2=0;
		}
		
		if (xestado.equals("1")) {
			xest1=1; xest2=1;
		}	
		List<CDatos> lisLogin=this.loginManager.listaLogin(xest1,xest2);
		return lisLogin;
	}
/////usurol
	@GetMapping({"/api/listaUsuRolSolo/{login}"})
	public List<CRoles> listaRolme(Model model , @PathVariable String login)  	{
		
		List<CRoles> rol = this.loginManager.listaUsurolRoles(login);
			
	    return rol;
	}
	//asignado
			@GetMapping({"/api/listaRolesAsig/{login}/{asig}"})
			public List<CRoles> listaUsurolAsignado(Model model , @PathVariable String login,@PathVariable int asig)  	{
				List<CRoles> lisRol = this.loginManager.listarUsurolAsignado(login, asig);
					
			    return lisRol;
			}
			
			
			//add
			@PostMapping("/api/guardarUsurol/{login}/{codr}")
			public int  addUsurol(@PathVariable("login") String xlogin,@PathVariable("codr") int xcodr) {
				boolean exist;
				exist=this.loginManager.isExists(xlogin,xcodr);
				if (exist) {
					return 0;
				}
				try {
					int res1= this.loginManager.setAddUsurol(xlogin, xcodr);
					
				} catch (Exception e) {
					e.printStackTrace();		
				}
				return 1;
			}
			@DeleteMapping("/api/deleteUsurol/{login}/{codr}")
			public int eliminarUsurol( @PathVariable("login") String xlogin,@PathVariable("codr") int xcodr ) {
				int res = this.loginManager.setDelUsurol(xlogin, xcodr);
				return 1;
			}
}
