package adoptask.modelo;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.data.mongodb.core.index.Indexed;

public class DatosAnimal {

	private String nombre;
	private CategoriaAnimal categoria;
	private String raza;
	private SexoAnimal sexo;
	private LocalDate fechaNacimiento;
	private int peso;

	public static class Builder {
		private String nombre;
		@Indexed
		private CategoriaAnimal categoria;
		private String raza;
		private SexoAnimal sexo;
		private LocalDate fechaNacimiento;
		private int peso;

		public Builder(String nombre, CategoriaAnimal categoria) {
			this.nombre = nombre;
			this.categoria = categoria;
		}

		public Builder raza(String raza) {
			this.raza = raza;
			return this;
		}

		public Builder sexo(SexoAnimal sexo) {
			this.sexo = sexo;
			return this;
		}

		public Builder fechaNacimiento(LocalDate fechaNacimiento) {
			this.fechaNacimiento = fechaNacimiento;
			return this;
		}

		public Builder peso(int peso) {
			this.peso = peso;
			return this;
		}

		public DatosAnimal build() {
			return new DatosAnimal(this);
		}
	}

	public DatosAnimal() {
	}

	private DatosAnimal(Builder builder) {
		nombre = builder.nombre;
		categoria = builder.categoria;
		raza = builder.raza;
		sexo = builder.sexo;
		fechaNacimiento = builder.fechaNacimiento;
		peso = builder.peso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public CategoriaAnimal getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaAnimal categoria) {
		this.categoria = categoria;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public SexoAnimal getSexo() {
		return sexo;
	}

	public void setSexo(SexoAnimal sexo) {
		this.sexo = sexo;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public int getEdad() {
		return Period.between(fechaNacimiento, LocalDate.now()).getYears();
	}

}
