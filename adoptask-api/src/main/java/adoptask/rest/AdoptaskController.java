package adoptask.rest;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import adoptask.dto.BusquedaDto;
import adoptask.dto.ProtectoraDto;
import adoptask.dto.PublicacionDto;
import adoptask.dto.ResumenAnimalDto;
import adoptask.dto.ResumenProtectoraDto;
import adoptask.dto.UsuarioDto;
import adoptask.servicio.IServicioProtectoras;
import adoptask.servicio.IServicioUsuarios;
import adoptask.dto.AuthDto;
import utils.JWTUtil;

@RestController
@RequestMapping("/")
public class AdoptaskController {

	private IServicioUsuarios servicioUsuarios;
	private IServicioProtectoras servicioProtectoras;

	private JWTUtil jwtUtil;

	@PostMapping("auth/login")
	public AuthDto verificarPassword(@RequestParam String nick, @RequestParam String password) {

		UsuarioDto usuario = servicioUsuarios.verificarPassword(nick, password);

		String token = jwtUtil.generateToken(usuario.getId());

		AuthDto auth = new AuthDto();
		auth.setToken(token);
		auth.setId(usuario.getId());
		auth.setFoto(usuario.getFoto());

		return auth;
	}

	@GetMapping("publicaciones")
	public Page<ResumenAnimalDto> getPublicaciones(BusquedaDto busquedaDto) {

		return servicioUsuarios.getPublicaciones(busquedaDto);
	}

	@GetMapping("publicaciones/{id}")
	public PublicacionDto getPublicacion(@PathVariable String id) {

		return servicioUsuarios.getPublicacion(id);
	}

	@PostMapping("usuarios")
	public ResponseEntity<Void> createUsuario(@RequestBody UsuarioDto usuarioDto) {

		String id = servicioUsuarios.altaUsuario(usuarioDto);
		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

		return ResponseEntity.created(nuevaURL).build();
	}

	@GetMapping("usuarios/{id}")
	public UsuarioDto getUsuario(@PathVariable String id, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();
		if (!idUsuario.equals(id)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso denegado: el ID del usuario no coincide.");
		}

		return servicioUsuarios.getUsuario(id);
	}

	@DeleteMapping("usuarios/{id}")
	public ResponseEntity<Void> deleteUsuario(@PathVariable String id, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();
		if (!idUsuario.equals(id)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso denegado: el ID del usuario no coincide.");
		}
		servicioUsuarios.bajaUsuario(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PatchMapping("usuarios/{id}")
	public ResponseEntity<Void> updateUsuario(@PathVariable String id, @RequestBody UsuarioDto usuarioDto,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();
		if (!idUsuario.equals(id)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso denegado: el ID del usuario no coincide.");
		}
		usuarioDto.setId(id);
		servicioUsuarios.updateUsuario(usuarioDto);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("usuarios/{id}/favoritos")
	public Page<ResumenAnimalDto> getFavoritos(@PathVariable String id, @RequestParam int page, @RequestParam int size,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();
		if (!idUsuario.equals(id)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso denegado: el ID del usuario no coincide.");
		}
		Pageable paginacion = PageRequest.of(page, size);

		return servicioUsuarios.getFavoritos(id, paginacion);
	}

	@PostMapping("usuarios/{id}/favoritos")
	public ResponseEntity<Void> addFavorito(@PathVariable String id, @RequestParam String idAnimal,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();
		if (!idUsuario.equals(id)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso denegado: el ID del usuario no coincide.");
		}
		servicioUsuarios.addFavorito(id, idAnimal);
		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idAnimal}").buildAndExpand(idAnimal)
				.toUri();

		return ResponseEntity.created(nuevaURL).build();
	}

	@DeleteMapping("usuarios/{id}/favoritos/{idAnimal}")
	public ResponseEntity<Void> removeFavorito(@PathVariable String id, @PathVariable String idAnimal,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();
		if (!idUsuario.equals(id)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso denegado: el ID del usuario no coincide.");
		}
		servicioUsuarios.removeFavorito(id, idAnimal);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("protectoras")
	public Page<ResumenProtectoraDto> getProtectoras(@RequestParam int page, @RequestParam int size) {

		Pageable paginacion = PageRequest.of(page, size);

		return servicioProtectoras.getProtectoras(paginacion);
	}

	@PostMapping("protectoras")
	public ResponseEntity<Void> createProtectora(@RequestBody ProtectoraDto protectoraDto,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();
		protectoraDto.setIdAdmin(idUsuario);
		String id = servicioProtectoras.altaProtectora(protectoraDto);
		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

		return ResponseEntity.created(nuevaURL).build();
	}

	@GetMapping("protectoras/{id}")
	public ProtectoraDto getProtectora(@PathVariable String id, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		return servicioProtectoras.getProtectora(id, idUsuario);
	}
}
