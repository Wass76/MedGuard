package com.CareemSystem.object.Model;

import com.CareemSystem.object.Enum.ExtensionName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Extension {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "extension_id"
    )
    @SequenceGenerator(
            sequenceName = "extension_id",
            name = "extension_id",
            allocationSize = 1
    )
    private Integer id;
    private ExtensionName name;


}
