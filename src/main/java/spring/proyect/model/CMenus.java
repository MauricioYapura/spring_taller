package spring.proyect.model;

import java.util.ArrayList;
import java.util.List;

public class CMenus {
	
	public int codm;
	public String nombre;
	public int estado;
	private List<CProcesos> procesos=new ArrayList<CProcesos>();
	
	public List<CProcesos> getProcesos() {
		return procesos;
	}
	public void setProcesos(List<CProcesos> procesos) {
		this.procesos = procesos;
	}
	
	public int getCodm() {
		return codm;
	}
	public void setCodm(int codm) {
		this.codm = codm;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	

}
