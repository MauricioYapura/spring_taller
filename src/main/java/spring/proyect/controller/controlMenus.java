package spring.proyect.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import spring.proyect.manager.MenusManager;
import spring.proyect.model.CMenus;
import spring.proyect.model.CPersonas;
import spring.proyect.model.CProcesos;
import spring.proyect.model.CRoles;

@RestController
public class controlMenus {

	
	@Autowired
	MenusManager menuManager;

	
	@RequestMapping("/ejemploTaller1")
	public String primerMetodo(){
		return "HOLA MUNDO - TALLER 1 - GESTION 2020";
	}
	
	@PostMapping({"api/login"})
	public CPersonas listaUnaPersona(@RequestParam("user")String user, @RequestParam("password")String clave) {
		System.out.println("llego "+ user+" clave: "+clave );
		CPersonas per =new CPersonas();
		try {
			
			per=this.menuManager.getDatosPersonas(user, clave);
			String token = getJWTToken(user);
			System.out.println("Este es el Token::"+token);
			per.setToken(token);

		} catch (Exception e) {
			per=null;
		}
		return per;
	}
	
	@GetMapping({"/api/listaRolme/{codr}"})
	public List<CMenus> listaRolme(Model model , @PathVariable int codr)  	{
		
		List<CMenus> lisAlus = this.menuManager.listarolme(codr);
		for (CMenus pros : lisAlus) {
			List<CProcesos>procesosMenus=menuManager.listarolmepro(pros.getCodm());
			pros.setProcesos(procesosMenus);
		}	
	    return lisAlus;
	}
	
	@GetMapping("/api/listaroles/{login}")
	public List<CRoles> roles(@PathVariable String login){
		List<CRoles> lisrol=this.menuManager.listaRoles(login);
		return lisrol;
		
	}
	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}
