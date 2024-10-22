package adoptask.rest;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import adoptask.dto.BusquedaDto;
import adoptask.dto.DocumentoDto;
import adoptask.dto.ProtectoraDto;
import adoptask.dto.PublicacionDto;
import adoptask.dto.ResumenAnimalDto;
import adoptask.dto.ResumenProtectoraDto;
import adoptask.dto.TareaDto;
import adoptask.dto.UsuarioDto;
import adoptask.dto.VoluntarioDto;
import adoptask.servicio.IServicioProtectoras;
import adoptask.servicio.IServicioUsuarios;
import adoptask.dto.ActividadDto;
import adoptask.dto.AnimalDto;
import adoptask.dto.AuthDto;
import utils.JWTUtil;

@RestController
@RequestMapping("/")
public class AdoptaskController {

	private static final String DIRECTORIO_FOTOS_PERFIL = "/usuarios/{id}/";
	private static final String DIRECTORIO_LOGOS = "/protectoras/{id}/";
	private static final String DIRECTORIO_IMAGENES_ANIMAL = "/protectoras/{id}/animales/{idAnimal}/imagenes/";
	private static final String DIRECTORIO_DOCUMENTOS_ANIMAL = "/protectoras/{id}/animales/{idAnimal}/documentos/";
	private static final String DIRECTORIO_DOCUMENTOS = "/protectoras/{id}/documentos/";

	private IServicioUsuarios servicioUsuarios;
	private IServicioProtectoras servicioProtectoras;

	private JWTUtil jwtUtil;

	@Autowired
	public AdoptaskController(IServicioUsuarios servicioUsuarios, IServicioProtectoras servicioProtectoras,
			JWTUtil jwtUtil) {
		this.servicioUsuarios = servicioUsuarios;
		this.servicioProtectoras = servicioProtectoras;
		this.jwtUtil = jwtUtil;
	}

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
	public ResponseEntity<Void> createUsuario(@RequestPart("usuario") @RequestBody UsuarioDto usuarioDto,
			@RequestPart(value = "foto", required = false) MultipartFile foto) {

		String id = servicioUsuarios.altaUsuario(usuarioDto);

		if (foto != null && !foto.isEmpty()) {
			try {
				String directorioBase = System.getProperty("user.dir");
				String directorioRelativo = String.format(DIRECTORIO_FOTOS_PERFIL, id);
				Path rutaDirectorio = Paths.get(directorioBase, directorioRelativo);
				if (!Files.exists(rutaDirectorio)) {
					Files.createDirectories(rutaDirectorio);
				}
				String nombreFoto = foto.getOriginalFilename();
				Path rutaFoto = Paths.get(directorioBase, directorioRelativo, nombreFoto);
				Files.write(rutaFoto, foto.getBytes());

				servicioUsuarios.altaUsuarioFoto(id, rutaFoto.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

		return ResponseEntity.created(nuevaURL).build();
	}

	@GetMapping("usuarios/{id}")
	public UsuarioDto getUsuario(@PathVariable String id, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();
		if (!idUsuario.equals(id)) {
			throw new AccessDeniedException("El ID del usuario no coincide.");
		}

		return servicioUsuarios.getUsuario(id);
	}

	@DeleteMapping("usuarios/{id}")
	public ResponseEntity<Void> deleteUsuario(@PathVariable String id, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();
		if (!idUsuario.equals(id)) {
			throw new AccessDeniedException("El ID del usuario no coincide.");
		}

		servicioUsuarios.bajaUsuario(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PatchMapping("usuarios/{id}")
	public ResponseEntity<Void> updateUsuario(@PathVariable String id,
			@RequestPart("usuario") @RequestBody UsuarioDto usuarioDto,
			@RequestPart(value = "foto", required = false) MultipartFile foto, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();
		if (!idUsuario.equals(id)) {
			throw new AccessDeniedException("El ID del usuario no coincide.");
		}

		if (foto != null && !foto.isEmpty()) {
			try {
				String directorioBase = System.getProperty("user.dir");
				String directorioRelativo = String.format(DIRECTORIO_FOTOS_PERFIL, id);
				Path rutaDirectorio = Paths.get(directorioBase, directorioRelativo);
				if (!Files.exists(rutaDirectorio)) {
					Files.createDirectories(rutaDirectorio);
				}
				String nombreFoto = foto.getOriginalFilename();
				Path rutaFoto = Paths.get(directorioBase, directorioRelativo, nombreFoto);
				Files.write(rutaFoto, foto.getBytes());

				usuarioDto.setFoto(rutaFoto.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			throw new AccessDeniedException("El ID del usuario no coincide.");
		}

		Pageable paginacion = PageRequest.of(page, size);

		return servicioUsuarios.getFavoritos(id, paginacion);
	}

	@PostMapping("usuarios/{id}/favoritos")
	public ResponseEntity<Void> addFavorito(@PathVariable String id, @RequestParam String idAnimal,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();
		if (!idUsuario.equals(id)) {
			throw new AccessDeniedException("El ID del usuario no coincide.");
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
			throw new AccessDeniedException("El ID del usuario no coincide.");
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
	public ResponseEntity<Void> createProtectora(@RequestPart("protectora") @RequestBody ProtectoraDto protectoraDto,
			@RequestPart(value = "logotipo", required = false) MultipartFile logo, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();
		protectoraDto.setIdAdmin(idUsuario);
		String id = servicioProtectoras.altaProtectora(protectoraDto);

		if (logo != null && !logo.isEmpty()) {
			try {
				String directorioBase = System.getProperty("user.dir");
				String directorioRelativo = String.format(DIRECTORIO_LOGOS, id);
				Path rutaDirectorio = Paths.get(directorioBase, directorioRelativo);
				if (!Files.exists(rutaDirectorio)) {
					Files.createDirectories(rutaDirectorio);
				}
				String nombreLogo = logo.getOriginalFilename();
				Path rutaLogo = Paths.get(directorioBase, directorioRelativo, nombreLogo);
				Files.write(rutaLogo, logo.getBytes());

				servicioProtectoras.altaProtectoraLogo(id, rutaLogo.toString(), idUsuario);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

		return ResponseEntity.created(nuevaURL).build();
	}

	@GetMapping("protectoras/{id}")
	public ProtectoraDto getProtectora(@PathVariable String id, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		return servicioProtectoras.getProtectora(id, idUsuario);
	}

	@DeleteMapping("protectoras/{id}")
	public ResponseEntity<Void> deleteProtectora(@PathVariable String id, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		servicioProtectoras.bajaProtectora(id, idUsuario);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PatchMapping("protectoras/{id}")
	public ResponseEntity<Void> updateProtectora(@PathVariable String id,
			@RequestPart("protectora") @RequestBody ProtectoraDto protectoraDto,
			@RequestPart(value = "logotipo", required = false) MultipartFile logo, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		if (logo != null && !logo.isEmpty()) {
			try {
				String directorioBase = System.getProperty("user.dir");
				String directorioRelativo = String.format(DIRECTORIO_LOGOS, id);
				Path rutaDirectorio = Paths.get(directorioBase, directorioRelativo);
				if (!Files.exists(rutaDirectorio)) {
					Files.createDirectories(rutaDirectorio);
				}
				String nombreLogo = logo.getOriginalFilename();
				Path rutaLogo = Paths.get(directorioBase, directorioRelativo, nombreLogo);
				Files.write(rutaLogo, logo.getBytes());

				protectoraDto.setLogotipo(rutaLogo.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		protectoraDto.setId(id);
		protectoraDto.setIdAdmin(idUsuario);
		servicioProtectoras.updateProtectora(protectoraDto);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("protectoras/{id}/acceso")
	public VoluntarioDto accessProtectora(@PathVariable String id, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		return servicioProtectoras.verificarAcceso(idUsuario, id);
	}

	@GetMapping("protectoras/{id}/voluntarios")
	public Page<VoluntarioDto> getVoluntarios(@PathVariable String id, @RequestParam int page, @RequestParam int size,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		Pageable paginacion = PageRequest.of(page, size);

		return servicioProtectoras.getVoluntarios(id, paginacion, idUsuario);
	}

	@PostMapping("protectoras/{id}/voluntarios")
	public ResponseEntity<Void> addVoluntario(@PathVariable String id, @RequestParam String nick,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		String idVoluntario = servicioProtectoras.addVoluntario(id, nick, idUsuario);

		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idVoluntario}")
				.buildAndExpand(idVoluntario).toUri();

		return ResponseEntity.created(nuevaURL).build();
	}

	@DeleteMapping("protectoras/{id}/voluntarios/{idVoluntario}")
	public ResponseEntity<Void> removeVoluntario(@PathVariable String id, @PathVariable String idVoluntario,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		servicioProtectoras.removeVoluntario(id, idVoluntario, idUsuario);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PatchMapping("protectoras/{id}/voluntarios/{idVoluntario}")
	public ResponseEntity<Void> updateVoluntario(@PathVariable String id, @RequestBody VoluntarioDto voluntarioDto,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		voluntarioDto.setIdProtectora(id);
		servicioProtectoras.updatePermisos(voluntarioDto, idUsuario);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("protectoras/{id}/animales")
	public Page<ResumenAnimalDto> getAnimales(@PathVariable String id, @RequestParam String categoria,
			@RequestParam String estado, @RequestParam int page, @RequestParam int size,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		Pageable paginacion = PageRequest.of(page, size);

		return servicioProtectoras.getAnimales(id, categoria, estado, paginacion, idUsuario);
	}

	@PostMapping("protectoras/{id}/animales")
	public ResponseEntity<Void> createAnimal(@PathVariable String id,
			@RequestPart("animal") @RequestBody AnimalDto animalDto, @RequestPart("portada") MultipartFile portada,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();
		animalDto.setIdProtectora(id);
		String idAnimal = servicioProtectoras.altaAnimal(animalDto, idUsuario);

		if (portada != null && !portada.isEmpty()) {
			try {
				String directorioBase = System.getProperty("user.dir");
				String directorioRelativo = String.format(DIRECTORIO_IMAGENES_ANIMAL, id, idAnimal);
				Path rutaDirectorio = Paths.get(directorioBase, directorioRelativo);
				if (!Files.exists(rutaDirectorio)) {
					Files.createDirectories(rutaDirectorio);
				}
				String nombrePortada = portada.getOriginalFilename();
				Path rutaPortada = Paths.get(directorioBase, directorioRelativo, nombrePortada);
				Files.write(rutaPortada, portada.getBytes());

				servicioProtectoras.altaAnimalPortada(idAnimal, rutaPortada.toString(), idUsuario);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

		return ResponseEntity.created(nuevaURL).build();
	}

	@GetMapping("protectoras/{id}/animales/{idAnimal}")
	public AnimalDto getAnimal(@PathVariable String idAnimal, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		return servicioProtectoras.getAnimal(idAnimal, idUsuario);
	}

	@DeleteMapping("protectoras/{id}/animales/{idAnimal}")
	public ResponseEntity<Void> deleteAnimal(@PathVariable String idAnimal, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		servicioProtectoras.bajaAnimal(idAnimal, idUsuario);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PatchMapping("protectoras/{id}/animales/{idAnimal}")
	public ResponseEntity<Void> updateAnimal(@PathVariable String idAnimal, @RequestBody AnimalDto animalDto,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		animalDto.setId(idAnimal);
		servicioProtectoras.updateAnimal(animalDto, idUsuario);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("protectoras/{id}/animales/{idAnimal}/imagenes")
	public ResponseEntity<Void> addImagenAnimal(@PathVariable String id, @PathVariable String idAnimal,
			@RequestParam MultipartFile imagen, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		Path rutaImagen = null;
		if (imagen != null && !imagen.isEmpty()) {
			try {
				String directorioBase = System.getProperty("user.dir");
				String directorioRelativo = String.format(DIRECTORIO_IMAGENES_ANIMAL, id, idAnimal);
				String nombreImagen = imagen.getOriginalFilename();
				rutaImagen = Paths.get(directorioBase, directorioRelativo, nombreImagen);
				Files.write(rutaImagen, imagen.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String idImagen = servicioProtectoras.addImagenAnimal(idAnimal, rutaImagen.toString(), idUsuario);

		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idImagen}").buildAndExpand(idImagen)
				.toUri();

		return ResponseEntity.created(nuevaURL).build();
	}

	@DeleteMapping("protectoras/{id}/animales/{idAnimal}/imagenes/{idImagen}")
	public ResponseEntity<Void> removeImagenAnimal(@PathVariable String idAnimal, @PathVariable String idImagen,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		servicioProtectoras.removeImagenAnimal(idAnimal, idImagen, idUsuario);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("protectoras/{id}/animales/{idAnimal}/documentos")
	public ResponseEntity<Void> addDocumentoAnimal(@PathVariable String id, @PathVariable String idAnimal,
			@RequestParam String nombre, @RequestParam MultipartFile documento, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		Path rutaDocumento = null;
		if (documento != null && !documento.isEmpty()) {
			try {
				String directorioBase = System.getProperty("user.dir");
				String directorioRelativo = String.format(DIRECTORIO_DOCUMENTOS_ANIMAL, id, idAnimal);
				Path rutaDirectorio = Paths.get(directorioBase, directorioRelativo);
				if (!Files.exists(rutaDirectorio)) {
					Files.createDirectories(rutaDirectorio);
				}
				String nombreDocumento = documento.getOriginalFilename();
				rutaDocumento = Paths.get(directorioBase, directorioRelativo, nombreDocumento);
				Files.write(rutaDocumento, documento.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String idDocumento = servicioProtectoras.addDocumentoAnimal(idAnimal, nombre, rutaDocumento.toString(),
				idUsuario);

		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idDocumento}")
				.buildAndExpand(idDocumento).toUri();

		return ResponseEntity.created(nuevaURL).build();
	}

	@DeleteMapping("protectoras/{id}/animales/{idAnimal}/documentos/{idDocumento}")
	public ResponseEntity<Void> removeDocumentoAnimal(@PathVariable String idAnimal, @PathVariable String idDocumento,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		servicioProtectoras.removeDocumentoAnimal(idAnimal, idDocumento, idUsuario);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("protectoras/{id}/tareas")
	public Page<TareaDto> getTareas(@PathVariable String id, @RequestParam int page, @RequestParam int size,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		Pageable paginacion = PageRequest.of(page, size);

		return servicioProtectoras.getTareas(id, paginacion, idUsuario);
	}

	@PostMapping("protectoras/{id}/tareas")
	public ResponseEntity<Void> addTarea(@PathVariable String id, @RequestBody TareaDto tareaDto,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		tareaDto.setIdProtectora(id);
		String idTarea = servicioProtectoras.addTarea(tareaDto, idUsuario);

		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idTarea}").buildAndExpand(idTarea)
				.toUri();

		return ResponseEntity.created(nuevaURL).build();
	}

	@DeleteMapping("protectoras/{id}/tareas/{idTarea}")
	public ResponseEntity<Void> removeTarea(@PathVariable String idTarea, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		servicioProtectoras.removeTarea(idTarea, idUsuario);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PatchMapping("protectoras/{id}/tareas/{idTarea}")
	public ResponseEntity<Void> updateTarea(@PathVariable String idTarea, @RequestBody TareaDto tareaDto,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		servicioProtectoras.updateTarea(tareaDto, idUsuario);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("protectoras/{id}/documentos")
	public List<DocumentoDto> getDocumentos(@PathVariable String id, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		return servicioProtectoras.getDocumentos(id, idUsuario);
	}

	@PostMapping("protectoras/{id}/documentos")
	public ResponseEntity<Void> addDocumento(@PathVariable String id, @RequestParam String nombre,
			@RequestParam MultipartFile documento, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		Path rutaDocumento = null;
		if (documento != null && !documento.isEmpty()) {
			try {
				String directorioBase = System.getProperty("user.dir");
				String directorioRelativo = String.format(DIRECTORIO_DOCUMENTOS, id);
				Path rutaDirectorio = Paths.get(directorioBase, directorioRelativo);
				if (!Files.exists(rutaDirectorio)) {
					Files.createDirectories(rutaDirectorio);
				}
				String nombreDocumento = documento.getOriginalFilename();
				rutaDocumento = Paths.get(directorioBase, directorioRelativo, nombreDocumento);
				Files.write(rutaDocumento, documento.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String idDocumento = servicioProtectoras.addDocumento(id, nombre, rutaDocumento.toString(), idUsuario);

		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idDocumento}")
				.buildAndExpand(idDocumento).toUri();

		return ResponseEntity.created(nuevaURL).build();
	}

	@DeleteMapping("protectoras/{id}/documentos/{idDocumento}")
	public ResponseEntity<Void> removeDocumento(@PathVariable String id, @PathVariable String idDocumento,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		servicioProtectoras.removeDocumento(id, idDocumento, idUsuario);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("protectoras/{id}/historial")
	public Page<ActividadDto> getHistorial(@PathVariable String id, @RequestParam int page, @RequestParam int size,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		Pageable paginacion = PageRequest.of(page, size);

		return servicioProtectoras.getHistorial(id, paginacion, idUsuario);
	}

}
