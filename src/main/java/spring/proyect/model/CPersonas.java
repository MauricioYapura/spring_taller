package spring.proyect.model;

import java.util.Date;

public class CPersonas {
	public long codp;
	public String nombre;
	public String ap;
	public String am;
	public int estado;
	public Date fnac;
	public char genero;
	public String direc;
	public String celular;
	public String foto;
	public char ecivil;
	private String login;
	public String token;
	public long getCodp() {
		return codp;
	}
	public void setCodp(long codp) {
		this.codp = codp;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getAp() {
		return ap;
	}
	public void setAp(String ap) {
		this.ap = ap;
	}
	public String getAm() {
		return am;
	}
	public void setAm(String am) {
		this.am = am;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public Date getFnac() {
		return fnac;
	}
	public void setFnac(Date fnac) {
		this.fnac = fnac;
	}
	public char getGenero() {
		return genero;
	}
	public void setGenero(char genero) {
		this.genero = genero;
	}
	
	public String getDirec() {
		return direc;
	}
	public void setDirec(String direc) {
		this.direc = direc;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public char getEcivil() {
		return ecivil;
	}
	public void setEcivil(char ecivil) {
		this.ecivil = ecivil;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	

	
	
}
