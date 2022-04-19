package tech.brito.ead.course.api.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class ModuleDto implements Serializable {

    private static final long serialVersionUID = 1l;

    @NotBlank
    @Size(min = 4, max = 100)
    private String title;

    @NotBlank
    @Size(min = 4, max = 255)
    private String description;
}
