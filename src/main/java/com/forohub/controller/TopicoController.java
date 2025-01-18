package com.forohub.controller;

import com.forohub.domain.topico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<RespuestaTopico> registrarTopico(@RequestBody @Valid RegistrarTopico registrarTopico){
        if (topicoRepository.existsByTituloAndMensaje(registrarTopico.titulo(), registrarTopico.mensaje())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Topico duplicado.");
        }
        Topico topico = topicoRepository.save(new Topico(registrarTopico));
        RespuestaTopico respuestaTopico = new RespuestaTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFecha(), topico.getStatus(), topico.getAutor(), topico.getCurso());

        return ResponseEntity
                .created(URI.create("/topicos/" + topico.getId()))
                .body(respuestaTopico);
    }

    @GetMapping
    public ResponseEntity<Page<ListarTopicos>> listarTopicos(
            @PageableDefault(size = 10) Pageable paginacion) {
        Page<Topico> topicos = topicoRepository.findAll(paginacion);
        Page<ListarTopicos> respuesta = topicos.map(topico -> new ListarTopicos(
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFecha(),
                topico.getStatus(),
                topico.getAutor(),
                topico.getCurso()
        ));
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListarTopicos> obtenerDetalleTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));
        ListarTopicos respuesta = new ListarTopicos(
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFecha(),
                topico.getStatus(),
                topico.getAutor(),
                topico.getCurso()
        );
        return ResponseEntity.ok(respuesta);
    }


    @PutMapping("/{id}")
    public ResponseEntity<RespuestaTopico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid ActualizarTopico datosActualizar) {

        Topico topicoExistente = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));

        if (topicoRepository.existsByTituloAndMensaje(datosActualizar.titulo(), datosActualizar.mensaje())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Topico duplicado.");
        }

        topicoExistente.actualizarDatos(datosActualizar);
        Topico topicoActualizado = topicoRepository.save(topicoExistente);

        RespuestaTopico respuesta = new RespuestaTopico(
                topicoActualizado.getId(),
                topicoActualizado.getTitulo(),
                topicoActualizado.getMensaje(),
                topicoActualizado.getFecha(),
                topicoActualizado.getStatus(),
                topicoActualizado.getAutor(),
                topicoActualizado.getCurso()
        );
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTopico(@PathVariable Long id) {
        Optional<Topico> topicoExistente = topicoRepository.findById(id);
        if (topicoExistente.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok("Tópico eliminado exitosamente");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado");
        }
    }


}
