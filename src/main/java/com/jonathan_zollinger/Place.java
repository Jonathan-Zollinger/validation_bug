package com.jonathan_zollinger;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Serdeable
@Data
public class Place {
    private final String place;

    public Place(@Pattern(regexp = "^\\d{5}(-|\\s*)?(\\d{4})?$") //allows standard 5-digit zip as well as "" + 4 versions
                 @NotBlank String zipcode){
        place = zipcode;
    }
    public Place(
            @Pattern(regexp = "^([a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*$")
            @NotBlank String city,
            @Pattern(regexp = "^[A-Z][a-z]+(?: +[A-Z][a-z]+)*$")
            @NotBlank String state){
        place = String.format("%s, %s", city, state);
    }
}
