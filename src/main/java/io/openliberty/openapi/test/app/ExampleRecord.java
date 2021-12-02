package io.openliberty.openapi.test.app;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@XmlRootElement(name = "record")
@Schema()
public class ExampleRecord {
    
    public ExampleRecord() {
    }
    
    public ExampleRecord(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
    @Schema(description = "A unique numerical id", example = "42")
    public int id;
    @Schema(description = "A descriptive name", example = "Fuzzy Bumblebee")
    public String name;
}
