package br.com.rotina.project.user.dto;

public record UserResponse(
        Integer id,
        String name,
        String email,
        String phone
) {
}
