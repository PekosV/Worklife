package com.linkedin.model.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@ApiModel(description = "PostDto object ")
public class PostRequestDto implements Serializable {

  @NotBlank
  @ApiModelProperty(value = "context", example = "Check out our Brand new offices", position = 1)
  private String context;

  @NotBlank
  @ApiModelProperty(value = "visible", example = "Public", position = 2)
  private Integer visible;

  @ApiModelProperty(value = "", example = "image.png", position = 3)
  private MultipartFile file;
}
