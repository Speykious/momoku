package momoku.database.models;

import momoku.database.repositories.ImageRepository;

public class Image extends Model<Image, Integer, ImageRepository> {
    private String filename;
    private String whoisthis;

    public Image(
            int id,
            String filename,
            String whoisthis) {
        super(id, ImageRepository.REPOSITORY);
        this.filename = filename;
        this.whoisthis = whoisthis;
    }

    public int getId() {
        return primaryKey;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getWhoisthis() {
        return whoisthis;
    }

    public void setWhoisthis(String whoisthis) {
        this.whoisthis = whoisthis;
    }
}
