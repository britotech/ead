package tech.brito.ead.authuser.api.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class ImageUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1l;

    @NotBlank
    @Size(max = 255)
    private String imageUrl;
}
