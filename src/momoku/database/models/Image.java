package momoku.database.models;

public class Image extends Model<String> {
    private String whoisthis;

    public Image(
            String filename,
            String whoisthis) {
        super(filename);
        this.whoisthis = whoisthis;
    }

    public String getFilename() {
        return primaryKey;
    }

    public String getWhoisthis() {
        return whoisthis;
    }

    public void setWhoisthis(String whoisthis) {
        this.whoisthis = whoisthis;
    }
}
