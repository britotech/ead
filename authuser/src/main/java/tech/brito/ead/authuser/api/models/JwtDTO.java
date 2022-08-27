package tech.brito.ead.authuser.api.models;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtDTO {

    @NonNull
    private String token;

    private String type = "Bearer";
}
