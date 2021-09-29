public class Node {

    double upper_bound ; // borne sup (avec fraction) meilleur des cas (U)
    double lower_bound ; // le cout (sans fraction) pire des cas (C)
    int level; // niveau du sommet dans l'arbre
    boolean selected ; // 1 si on prend l'objet 0 sinon
    double total_value ; // la valeur des objets dans le sac
    double total_weight ; // le poids des objets dans le sac

    public Node(){}
    public Node(Node node){
        this.total_value = node.total_value;
        this.total_weight = node.total_weight;
        this.upper_bound = node.upper_bound;
        this.lower_bound = node.lower_bound;
        this.level = node.level;
        this.selected = node.selected;
    }

}
