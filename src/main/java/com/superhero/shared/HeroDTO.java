package com.superhero.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.lang.NonNullApi;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeroDTO {
    private Long id;
    @JsonProperty("name")
    @NonNull
    private String name;

}
