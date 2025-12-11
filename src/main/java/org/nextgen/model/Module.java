package org.nextgen.model;



import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.List;


@Entity
public class Module extends BaseEntity{
    public String moduleId;   // C1, C2, P1, X1
    public String name;
    public String description;
    public String type;       // primer, quiz, lab, capstone, etc.

    @ManyToOne
    public Layer layer;

    @ElementCollection
    public List<String> objectives;

    @ElementCollection
    public List<String> linkedLabs;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    public List<Component> components;
}
