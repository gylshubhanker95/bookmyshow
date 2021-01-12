package com.bookmyshow.bookmyshowapi.models;

import com.bookmyshow.bookmyshowapi.enums.CertificateType;
import com.bookmyshow.bookmyshowapi.enums.Genre;
import com.bookmyshow.bookmyshowapi.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "movies")
public class Movie extends Auditable{

    private String movieName;

    private String aboutMovie;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    private CertificateType certificateType;

}
