package hackaton.com.twitterhelphackaton.models;

/**
 * Created by ederdoski on 13/04/2019.
 */

public class Querys {

    private String id;
    private String query;
    private String query2;

    public Querys(String id, String query, String query2) {
        this.id     = id;
        this.query  = query;
        this.query2 = query2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery2() {
        return query2;
    }

    public void setQuery2(String query2) {
        this.query2 = query2;
    }
}
