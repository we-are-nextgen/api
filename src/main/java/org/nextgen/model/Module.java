package org.nextgen.model;



import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.CascadeType;
import java.util.List;


@Entity
public class Module extends BaseRewardable {
    public String moduleId;   // C1, C2, P1, X1
    public String name;
    public String description;
    public String type;       // primer, quiz, lab, capstone, etc.

    @ManyToOne
    @JsonbTransient
    public Layer layer;

    @ElementCollection
    public List<String> objectives;

    @ElementCollection
    public List<String> linkedLabs;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    public List<Component> components;
}
