package spring.proyect.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import spring.proyect.model.CDatos;
import spring.proyect.model.CMenus;
import spring.proyect.model.CRoles;

@Service
public class UsuRolManager {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource2) {
		jdbcTemplate = new JdbcTemplate(dataSource2);
	}

	public List<CDatos> listaLogin(int x1, int x2) {

		String xsql = "select d.login , d.codp, d.estado from cdatos d " + "where d.estado between ? and ?";
		List roles = this.jdbcTemplate.query(xsql, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				CDatos usu = new CDatos();
				usu.setLogin(rs.getString("login"));
				usu.setCodp(rs.getInt("codp"));
				usu.setEstado(rs.getInt("estado"));

				return usu;
			}
		}, new Object[] { x1, x2 });
		return roles;
	}

	public List<CRoles> listaUsurolRoles(String login) {
		String xsql = "select r.codr, r.nombre, r.estado "
				+ " from croles as r, cdatos as  d, usurol as us where r.codr=us.codr and d.login='" + login
				+ "' and us.login='" + login + "'";
		List menu = this.jdbcTemplate.query(xsql, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				CRoles rol = new CRoles();
				rol.setCodr(rs.getInt("codr"));
				rol.setNombre(rs.getString("nombre"));
				rol.setEstado(rs.getInt("estado"));

				return rol;
			}
		}, new Object[] {});
		return menu;
	}

/// asignado
	public List<CRoles> listarUsurolAsignado(String login, int asignado) {
		String xsql = null;
		if (asignado == 0) {
			// no asignado
			xsql = " select r.* from croles r where not exists (select * from usurol us where ? =us.login and r.codr=us.codr)";
			List est = this.jdbcTemplate.query(xsql, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					CRoles rol = new CRoles();
					rol.setCodr(rs.getInt("codr"));
					rol.setNombre(rs.getString("nombre"));
					rol.setEstado(rs.getInt("estado"));
					return rol;
				}
			}, new Object[] { login });
			return est;
		}
		if (asignado == 1) {
			// asignado

			xsql = " select r.*  from croles as r, usurol as us  where us.login=? and r.codr=us.codr";
			List est = this.jdbcTemplate.query(xsql, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					CRoles rol = new CRoles();
					rol.setCodr(rs.getInt("codr"));
					rol.setNombre(rs.getString("nombre"));
					rol.setEstado(rs.getInt("estado"));
					return rol;
				}
			}, new Object[] { login });
			return est;
		}
		if (asignado == 2) {
			// todos
			String xsql1 = " select r.*  from croles as r";
			List est1 = this.jdbcTemplate.query(xsql1, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					CRoles rol = new CRoles();
					rol.setCodr(rs.getInt("codr"));
					rol.setNombre(rs.getString("nombre"));
					rol.setEstado(rs.getInt("estado"));
					return rol;
				}
			}, new Object[] {});
			return est1;
		}

		return null;
	}
	//adicionar

			public int setAddUsurol(String login, int codr) {
				String xsql = "insert into usurol( codr, login )" + "  values(?,?)";
				return this.jdbcTemplate.update(xsql, codr, login);
			}
			//modificar
			public int setDelUsurol(String login,int codr) {
				String xsql = "DELETE FROM usurol " + 
						" WHERE codr=? and login=?;";
				return this.jdbcTemplate.update(xsql, codr, login);
			}

			public boolean isExists(String xlogin, int xcodr) {
				
				String sql = "SELECT count(*) FROM usurol WHERE login = ? and codr=?" ;
				boolean result = false;
				int count = jdbcTemplate.queryForObject(sql, new Object[] { xlogin, xcodr}, Integer.class);
				if (count > 0) {
				result = true;
				}
				return result;
			}

}
