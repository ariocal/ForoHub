package com.forohub.domain.topico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos", uniqueConstraints = {@UniqueConstraint(columnNames = {"titulo", "mensaje"})})
@Entity(name = "Topico")
@Getter
@AllArgsConstructor
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fecha;
    private Boolean status;
    private String autor;
    private String curso;

    public Topico() {
    }

    public Topico(RegistrarTopico datos){
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fecha = LocalDateTime.now();
        this.status = true;
        this.autor = datos.autor();
        this.curso = datos.curso();
    }

    public void actualizarDatos(ActualizarTopico datosActualizar) {
        if (datosActualizar.titulo() != null) {
            this.titulo = datosActualizar.titulo();
        }
        if (datosActualizar.mensaje() != null) {
            this.mensaje = datosActualizar.mensaje();
        }
        if (datosActualizar.autor() != null) {
            this.autor = datosActualizar.autor();
        }
        if (datosActualizar.curso() != null) {
            this.curso = datosActualizar.curso();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}