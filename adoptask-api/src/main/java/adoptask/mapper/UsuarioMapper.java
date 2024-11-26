package adoptask.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import adoptask.dto.UsuarioDto;
import adoptask.dto.VoluntarioDto;
import adoptask.modelo.Permiso;
import adoptask.modelo.Protectora;
import adoptask.modelo.Usuario;

@Component
public class UsuarioMapper {

	public Usuario toEntity(UsuarioDto usuarioDto) {
		if (usuarioDto == null) {
			return null;
		}
		Usuario usuario = new Usuario(usuarioDto.getNick(), usuarioDto.getNombre(), usuarioDto.getEmail(),
				usuarioDto.getPassword(), usuarioDto.getFoto());
		return usuario;
	}

	public UsuarioDto toDTO(Usuario usuario) {
		if (usuario == null) {
			return null;
		}
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setId(usuario.getId());
		usuarioDto.setNick(usuario.getNick());
		usuarioDto.setNombre(usuario.getNombre());
		usuarioDto.setEmail(usuario.getEmail());
		usuarioDto.setPassword(usuario.getPassword());
		usuarioDto.setFoto(usuario.getFoto());
		return usuarioDto;
	}

	public VoluntarioDto toVoluntarioDTO(Usuario usuario, Protectora protectora) {
		if (usuario == null || protectora == null) {
			return null;
		}
		VoluntarioDto voluntarioDto = new VoluntarioDto();
		voluntarioDto.setId(usuario.getId());
		voluntarioDto.setIdProtectora(protectora.getId());
		voluntarioDto.setNombreProtectora(protectora.getNombre());
		voluntarioDto.setNick(usuario.getNick());
		voluntarioDto.setNombre(usuario.getNombre());
		voluntarioDto.setFoto(usuario.getFoto());
		voluntarioDto.setAdmin(protectora.isAdmin(usuario.getId()));
		voluntarioDto.setPermisos(usuario.getPermisos().stream()
				.filter(p -> p.getIdProtectora().equals(protectora.getId()))
				.map(Permiso::getTipo)
				.collect(Collectors.toList()));
		return voluntarioDto;
	}
}
