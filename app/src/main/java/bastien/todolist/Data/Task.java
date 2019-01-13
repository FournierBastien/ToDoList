package bastien.todolist.Data;

public class Task {

    private int id;
    private String titre;
    private int user_id;
    private String description;
    private String dateLimite;
    private String heureLimite;

    public Task(int user_id,String titre,  String description, String dateLimite,String heureLimite) {
        this.titre = titre;
        this.user_id = user_id;
        this.description = description;
        this.dateLimite = dateLimite;
        this.heureLimite = heureLimite;
    }

    public Task() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String title) {
        this.titre = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDateLimite() {
        return dateLimite;
    }

    public void setDateLimite(String dateLimite) {
        this.dateLimite = dateLimite;
    }
    public String getHeureLimite() { return heureLimite; }

    public void setHeureLimite(String heureLimite) { this.heureLimite = heureLimite; }

}
