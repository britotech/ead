package tech.brito.ead.authuser.api.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ImageUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1l;

    @NotBlank
    private String imageUrl;
}
