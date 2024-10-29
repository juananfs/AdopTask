package adoptask.rest;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import adoptask.utils.JWTUtil;
import adoptask.dto.ActividadDto;
import adoptask.dto.AnimalDto;
import adoptask.dto.AuthDto;

@RestController
@RequestMapping("/")
public class AdoptaskController {

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

	private ResponseEntity<Resource> getArchivo(File file) {

		if (!file.exists()) {
			return ResponseEntity.notFound().build();
		}

		Resource resource = new FileSystemResource(file);

		String contentType = null;
		try {
			contentType = Files.probeContentType(file.toPath());
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();
		if (contentType != null) {
			responseBuilder.contentType(MediaType.parseMediaType(contentType));
		}

		return responseBuilder.body(resource);
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

	@PostMapping("publicaciones")
	public PagedModel<ResumenAnimalDto> getPublicaciones(@RequestBody BusquedaDto busquedaDto) {

		Page<ResumenAnimalDto> resultado = servicioUsuarios.getPublicaciones(busquedaDto);

		return PagedModel.of(resultado.getContent(), new PagedModel.PageMetadata(resultado.getSize(),
				resultado.getNumber(), resultado.getTotalElements(), resultado.getTotalPages()));
	}

	@GetMapping("publicaciones/{id}")
	public PublicacionDto getPublicacion(@PathVariable String id) {

		return servicioUsuarios.getPublicacion(id);
	}

	@PostMapping("usuarios")
	public ResponseEntity<Void> createUsuario(@ModelAttribute UsuarioDto usuarioDto,
			@RequestPart(value = "imagen", required = false) MultipartFile foto) {

		String id = servicioUsuarios.altaUsuario(usuarioDto);

		if (foto != null && !foto.isEmpty()) {
			try {
				String directorioRelativo = String.format(IServicioUsuarios.DIRECTORIO_FOTOS_PERFIL, id);
				Path rutaDirectorio = Paths.get(directorioRelativo);
				if (!Files.exists(rutaDirectorio)) {
					Files.createDirectories(rutaDirectorio);
				}
				String nombreFoto = foto.getOriginalFilename();
				Path rutaFoto = Paths.get(directorioRelativo, nombreFoto);
				Files.write(rutaFoto, foto.getBytes());

				servicioUsuarios.altaUsuarioFoto(id, nombreFoto);
			} catch (Exception e) {
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
	public ResponseEntity<Void> updateUsuario(@PathVariable String id, @ModelAttribute UsuarioDto usuarioDto,
			@RequestPart(value = "imagen", required = false) MultipartFile foto, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();
		if (!idUsuario.equals(id)) {
			throw new AccessDeniedException("El ID del usuario no coincide.");
		}

		if (foto != null && !foto.isEmpty()) {
			try {
				String directorioRelativo = String.format(IServicioUsuarios.DIRECTORIO_FOTOS_PERFIL, id);
				Path rutaDirectorio = Paths.get(directorioRelativo);
				if (!Files.exists(rutaDirectorio)) {
					Files.createDirectories(rutaDirectorio);
				}
				String nombreFoto = foto.getOriginalFilename();
				Path rutaFoto = Paths.get(directorioRelativo, nombreFoto);
				Files.write(rutaFoto, foto.getBytes());

				usuarioDto.setFoto(nombreFoto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		usuarioDto.setId(id);
		servicioUsuarios.updateUsuario(usuarioDto);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("usuarios/{id}/{nombreArchivo}")
	public ResponseEntity<Resource> getImagenUsuario(@PathVariable String id, @PathVariable String nombreArchivo) {

		File file = new File(String.format(IServicioUsuarios.DIRECTORIO_FOTOS_PERFIL, id) + nombreArchivo);

		return getArchivo(file);
	}

	@GetMapping("usuarios/{id}/favoritos")
	public PagedModel<ResumenAnimalDto> getFavoritos(@PathVariable String id, @RequestParam int page,
			@RequestParam int size, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();
		if (!idUsuario.equals(id)) {
			throw new AccessDeniedException("El ID del usuario no coincide.");
		}

		Pageable paginacion = PageRequest.of(page, size);

		Page<ResumenAnimalDto> resultado = servicioUsuarios.getFavoritos(id, paginacion);

		return PagedModel.of(resultado.getContent(), new PagedModel.PageMetadata(resultado.getSize(),
				resultado.getNumber(), resultado.getTotalElements(), resultado.getTotalPages()));
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

	@DeleteMapping("usuarios/{id}/favoritos/{idPublicacion}")
	public ResponseEntity<Void> removeFavorito(@PathVariable String id, @PathVariable String idPublicacion,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();
		if (!idUsuario.equals(id)) {
			throw new AccessDeniedException("El ID del usuario no coincide.");
		}

		servicioUsuarios.removeFavorito(id, idPublicacion);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("protectoras")
	public PagedModel<ResumenProtectoraDto> getProtectoras(@RequestParam int page, @RequestParam int size) {

		Pageable paginacion = PageRequest.of(page, size);

		Page<ResumenProtectoraDto> resultado = servicioProtectoras.getProtectoras(paginacion);

		return PagedModel.of(resultado.getContent(), new PagedModel.PageMetadata(resultado.getSize(),
				resultado.getNumber(), resultado.getTotalElements(), resultado.getTotalPages()));
	}

	@PostMapping("protectoras")
	public ResponseEntity<Void> createProtectora(@ModelAttribute ProtectoraDto protectoraDto,
			@RequestPart(value = "imagen", required = false) MultipartFile logo, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();
		protectoraDto.setIdAdmin(idUsuario);
		String id = servicioProtectoras.altaProtectora(protectoraDto);

		if (logo != null && !logo.isEmpty()) {
			try {
				String directorioRelativo = String.format(IServicioProtectoras.DIRECTORIO_PROTECTORA, id);
				Path rutaDirectorio = Paths.get(directorioRelativo);
				if (!Files.exists(rutaDirectorio)) {
					Files.createDirectories(rutaDirectorio);
				}
				String nombreLogo = logo.getOriginalFilename();
				Path rutaLogo = Paths.get(directorioRelativo, nombreLogo);
				Files.write(rutaLogo, logo.getBytes());

				servicioProtectoras.altaProtectoraLogo(id, nombreLogo, idUsuario);
			} catch (Exception e) {
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
	public ResponseEntity<Void> updateProtectora(@PathVariable String id, @ModelAttribute ProtectoraDto protectoraDto,
			@RequestPart(value = "imagen", required = false) MultipartFile logo, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		if (logo != null && !logo.isEmpty()) {
			try {
				String directorioRelativo = String.format(IServicioProtectoras.DIRECTORIO_PROTECTORA, id);
				Path rutaDirectorio = Paths.get(directorioRelativo);
				if (!Files.exists(rutaDirectorio)) {
					Files.createDirectories(rutaDirectorio);
				}
				String nombreLogo = logo.getOriginalFilename();
				Path rutaLogo = Paths.get(directorioRelativo, nombreLogo);
				Files.write(rutaLogo, logo.getBytes());

				protectoraDto.setLogotipo(nombreLogo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		protectoraDto.setId(id);
		protectoraDto.setIdAdmin(idUsuario);
		servicioProtectoras.updateProtectora(protectoraDto);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("protectoras/{id}/{nombreArchivo}")
	public ResponseEntity<Resource> getImagenProtectora(@PathVariable String id, @PathVariable String nombreArchivo) {

		File file = new File(String.format(IServicioProtectoras.DIRECTORIO_PROTECTORA, id) + nombreArchivo);

		return getArchivo(file);
	}

	@PostMapping("protectoras/{id}/acceso")
	public VoluntarioDto accessProtectora(@PathVariable String id, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		return servicioProtectoras.verificarAcceso(idUsuario, id);
	}

	@GetMapping("protectoras/{id}/voluntarios")
	public PagedModel<VoluntarioDto> getVoluntarios(@PathVariable String id, @RequestParam int page,
			@RequestParam int size, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		Pageable paginacion = PageRequest.of(page, size);

		Page<VoluntarioDto> resultado = servicioProtectoras.getVoluntarios(id, paginacion, idUsuario);

		return PagedModel.of(resultado.getContent(), new PagedModel.PageMetadata(resultado.getSize(),
				resultado.getNumber(), resultado.getTotalElements(), resultado.getTotalPages()));
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
	public PagedModel<ResumenAnimalDto> getAnimales(@PathVariable String id, @RequestParam String categoria,
			@RequestParam String estado, @RequestParam int page, @RequestParam int size,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		Pageable paginacion = PageRequest.of(page, size);

		Page<ResumenAnimalDto> resultado = servicioProtectoras.getAnimales(id, categoria, estado, paginacion,
				idUsuario);

		return PagedModel.of(resultado.getContent(), new PagedModel.PageMetadata(resultado.getSize(),
				resultado.getNumber(), resultado.getTotalElements(), resultado.getTotalPages()));
	}

	@PostMapping("protectoras/{id}/animales")
	public ResponseEntity<Void> createAnimal(@PathVariable String id, @ModelAttribute AnimalDto animalDto,
			@RequestPart("imagen") MultipartFile portada, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();
		animalDto.setIdProtectora(id);
		String idAnimal = servicioProtectoras.altaAnimal(animalDto, idUsuario);

		if (portada != null && !portada.isEmpty()) {
			try {
				String directorioRelativo = String.format(IServicioProtectoras.DIRECTORIO_IMAGENES_ANIMAL, id,
						idAnimal);
				Path rutaDirectorio = Paths.get(directorioRelativo);
				if (!Files.exists(rutaDirectorio)) {
					Files.createDirectories(rutaDirectorio);
				}
				String nombrePortada = portada.getOriginalFilename();
				Path rutaPortada = Paths.get(directorioRelativo, nombrePortada);
				Files.write(rutaPortada, portada.getBytes());

				servicioProtectoras.altaAnimalPortada(idAnimal, nombrePortada, idUsuario);
			} catch (Exception e) {
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
			@RequestParam MultipartFile imagen, Authentication authentication) throws IOException {

		String idUsuario = (String) authentication.getPrincipal();

		String directorioRelativo = String.format(IServicioProtectoras.DIRECTORIO_PROTECTORA, id, idAnimal);
		String nombreImagen = imagen.getOriginalFilename();
		Path rutaImagen = Paths.get(directorioRelativo, nombreImagen);
		Files.write(rutaImagen, imagen.getBytes());

		servicioProtectoras.addImagenAnimal(idAnimal, nombreImagen, idUsuario);

		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nombreArchivo}")
				.buildAndExpand(nombreImagen).toUri();

		return ResponseEntity.created(nuevaURL).build();
	}

	@GetMapping("protectoras/{id}/animales/{idAnimal}/imagenes/{nombreArchivo}")
	public ResponseEntity<Resource> getImagenAnimal(@PathVariable String id, @PathVariable String idAnimal,
			@PathVariable String nombreArchivo) {

		File file = new File(
				String.format(IServicioProtectoras.DIRECTORIO_IMAGENES_ANIMAL, id, idAnimal) + nombreArchivo);

		return getArchivo(file);
	}

	@DeleteMapping("protectoras/{id}/animales/{idAnimal}/imagenes/{nombreArchivo}")
	public ResponseEntity<Void> removeImagenAnimal(@PathVariable String idAnimal, @PathVariable String nombreArchivo,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		servicioProtectoras.removeImagenAnimal(idAnimal, nombreArchivo, idUsuario);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("protectoras/{id}/animales/{idAnimal}/documentos")
	public ResponseEntity<Void> addDocumentoAnimal(@PathVariable String id, @PathVariable String idAnimal,
			@RequestParam MultipartFile documento, Authentication authentication) throws IOException {

		String idUsuario = (String) authentication.getPrincipal();

		String directorioRelativo = String.format(IServicioProtectoras.DIRECTORIO_DOCUMENTOS_ANIMAL, id, idAnimal);
		Path rutaDirectorio = Paths.get(directorioRelativo);
		if (!Files.exists(rutaDirectorio)) {
			Files.createDirectories(rutaDirectorio);
		}
		String nombreDocumento = documento.getOriginalFilename();
		Path rutaDocumento = Paths.get(directorioRelativo, nombreDocumento);
		Files.write(rutaDocumento, documento.getBytes());

		servicioProtectoras.addDocumentoAnimal(idAnimal, nombreDocumento, idUsuario);

		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nombreArchivo}")
				.buildAndExpand(nombreDocumento).toUri();

		return ResponseEntity.created(nuevaURL).build();
	}

	@GetMapping("protectoras/{id}/animales/{idAnimal}/documentos/{nombreArchivo}")
	public ResponseEntity<Resource> getDocumentoAnimal(@PathVariable String id, @PathVariable String idAnimal,
			@PathVariable String nombreArchivo) {

		File file = new File(
				String.format(IServicioProtectoras.DIRECTORIO_DOCUMENTOS_ANIMAL, id, idAnimal) + nombreArchivo);

		return getArchivo(file);
	}

	@DeleteMapping("protectoras/{id}/animales/{idAnimal}/documentos/{nombreArchivo}")
	public ResponseEntity<Void> removeDocumentoAnimal(@PathVariable String idAnimal, @PathVariable String nombreArchivo,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		servicioProtectoras.removeDocumentoAnimal(idAnimal, nombreArchivo, idUsuario);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("protectoras/{id}/tareas")
	public PagedModel<TareaDto> getTareas(@PathVariable String id, @RequestParam int page, @RequestParam int size,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		Pageable paginacion = PageRequest.of(page, size);

		Page<TareaDto> resultado = servicioProtectoras.getTareas(id, paginacion, idUsuario);

		return PagedModel.of(resultado.getContent(), new PagedModel.PageMetadata(resultado.getSize(),
				resultado.getNumber(), resultado.getTotalElements(), resultado.getTotalPages()));
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
	public PagedModel<DocumentoDto> getDocumentos(@PathVariable String id, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		List<DocumentoDto> resultado = servicioProtectoras.getDocumentos(id, idUsuario);

		return PagedModel.of(resultado,
				new PagedModel.PageMetadata(resultado.size(), 0, resultado.size(), resultado.isEmpty() ? 0 : 1));
	}

	@PostMapping("protectoras/{id}/documentos")
	public ResponseEntity<Void> addDocumento(@PathVariable String id, @RequestParam MultipartFile documento,
			Authentication authentication) throws IOException {

		String idUsuario = (String) authentication.getPrincipal();

		String directorioRelativo = String.format(IServicioProtectoras.DIRECTORIO_DOCUMENTOS, id);
		Path rutaDirectorio = Paths.get(directorioRelativo);
		if (!Files.exists(rutaDirectorio)) {
			Files.createDirectories(rutaDirectorio);
		}
		String nombreDocumento = documento.getOriginalFilename();
		Path rutaDocumento = Paths.get(directorioRelativo, nombreDocumento);
		Files.write(rutaDocumento, documento.getBytes());

		servicioProtectoras.addDocumento(id, nombreDocumento, idUsuario);

		URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nombreArchivo}")
				.buildAndExpand(nombreDocumento).toUri();

		return ResponseEntity.created(nuevaURL).build();
	}

	@GetMapping("protectoras/{id}/documentos/{nombreArchivo}")
	public ResponseEntity<Resource> getDocumento(@PathVariable String id, @PathVariable String nombreArchivo) {

		File file = new File(String.format(IServicioProtectoras.DIRECTORIO_DOCUMENTOS, id) + nombreArchivo);

		return getArchivo(file);
	}

	@DeleteMapping("protectoras/{id}/documentos/{nombreArchivo}")
	public ResponseEntity<Void> removeDocumento(@PathVariable String id, @PathVariable String nombreArchivo,
			Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		servicioProtectoras.removeDocumento(id, nombreArchivo, idUsuario);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("protectoras/{id}/historial")
	public PagedModel<ActividadDto> getHistorial(@PathVariable String id, @RequestParam int page,
			@RequestParam int size, Authentication authentication) {

		String idUsuario = (String) authentication.getPrincipal();

		Pageable paginacion = PageRequest.of(page, size);

		Page<ActividadDto> resultado = servicioProtectoras.getHistorial(id, paginacion, idUsuario);

		return PagedModel.of(resultado.getContent(), new PagedModel.PageMetadata(resultado.getSize(),
				resultado.getNumber(), resultado.getTotalElements(), resultado.getTotalPages()));
	}

}
