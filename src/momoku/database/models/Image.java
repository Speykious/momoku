package momoku.database.models;

import momoku.database.repositories.ImageRepository;

public class Image extends Model<Image, String, ImageRepository> {
    private String whoisthis;

    public Image(
            String filename,
            String whoisthis) {
        super(filename, ImageRepository.REPOSITORY);
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
